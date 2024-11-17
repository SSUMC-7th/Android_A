package umc.study.umc_7th

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import umc.study.umc_7th.content.Album
import umc.study.umc_7th.content.AlbumContent
import umc.study.umc_7th.content.AlbumRepository
import umc.study.umc_7th.content.AppDataBase
import umc.study.umc_7th.content.Content
import umc.study.umc_7th.content.ContentDao
import umc.study.umc_7th.content.ContentRepository
import umc.study.umc_7th.user.UserRepository

open class SongViewModel(application: Application,
    private val repository: ContentRepository,
    private val albumrepository: AlbumRepository,
    ) : AndroidViewModel(application) {

    private val _unLike = MutableLiveData(false)
    open val unLike : LiveData<Boolean> = _unLike

    fun toggleUnLike(){
        _unLike.value = _unLike.value != true
    }

    private val _replay = MutableLiveData(false)
    open val replay : LiveData<Boolean> = _replay

    private val _played = MutableLiveData(false)
    open val played : LiveData<Boolean> = _played

    private val _duration = MutableLiveData(200f)
    open val duration : LiveData<Float> = _duration

    private val _currentPosition = MutableLiveData(0f)
    open val currentPosition : LiveData<Float> = _currentPosition

    private val _shuffle = MutableLiveData(false)
    open val shuffle : LiveData<Boolean> = _shuffle

    private var playbackJob : Job? = null

    open fun toggleReplay() {
        _replay.value = _replay.value != true
    }
    open fun togglePlayed() {
        _played.value = _played.value != true

        if (_played.value == true) {
            startPlayback()
        }else {
            stopPlayedback()
        }
    }

    private fun startPlayback(){
        playbackJob?.cancel()
        playbackJob = viewModelScope.launch {
            while (_played.value == true && _currentPosition.value!! < _duration.value!!){
                delay(1000L)
                _currentPosition.value = _currentPosition.value!! + 1f
            }
        }
    }
    private fun stopPlayedback(){
        playbackJob?.cancel()
    }

    open fun toggleShuffle() {
        _shuffle.value = _shuffle.value != true
    }
    open fun updatePosition(position: Float){
        _currentPosition.value = position
    }
    fun setDuration(duration: Float){
        _duration.value = duration
    }
    fun resetProgress(){ // 그 .. beforeSongPlayClick() 에 넣어줄 것
        _currentPosition.value = 0f
    }
    fun toggleLike(content:Content){
        val updatedContent = content.copy(islike = !content.islike)
        _currentSong.value = updatedContent
        viewModelScope.launch {
            repository.updateContent(updatedContent)
            loadLikedSongs()
        }
    }


    // 재생 순서 관리
    private val playbackHistory = mutableListOf<Content>()
    private var historyIndex = -1

    //현재 앨범 재생 리스트
    private var currentAlbumTracks : List<Content> = emptyList()
    private var albumTrackIndex = 0

    private val _currentSong = MutableLiveData<Content?>(null)
    open val currentSong : LiveData<Content?> = _currentSong

    fun setCurrentSong(content: Content, isNewHistory: Boolean = true) {
        viewModelScope.launch {
            val existingContent = repository.getContentByTitleAndAuthor(content.title, content.author)

            val contentToSet = if (existingContent == null) {
                repository.insert(content)
                repository.getContentByTitleAndAuthor(content.title, content.author) ?: content
            } else {
                existingContent
            }


            if (isNewHistory) {
                playbackHistory.add(contentToSet)
                historyIndex = playbackHistory.size - 1
            }

            _currentSong.value = contentToSet
            resetProgress()


            Log.d("SongViewModel", "Set current song with ID: ${contentToSet.id}, Title: ${contentToSet.title}")
        }
    }
    // 이전 곡 재생
    fun playPreviousSong() {
        if (currentAlbumTracks.isNotEmpty() && albumTrackIndex > 0) {
            albumTrackIndex--
            setCurrentSong(currentAlbumTracks[albumTrackIndex], isNewHistory = false)
        } else if (historyIndex > 0) {
            historyIndex--
            setCurrentSong(playbackHistory[historyIndex], isNewHistory = false)
        } else {
            Log.d("SongViewModel", "No previous song available.")
        }
    }

    // 다음 곡 재생
    fun playNextSong() {
        if (currentAlbumTracks.isNotEmpty() && albumTrackIndex < currentAlbumTracks.size - 1) {
            albumTrackIndex++
            setCurrentSong(currentAlbumTracks[albumTrackIndex], isNewHistory = false)
        } else if (historyIndex < playbackHistory.size - 1) {
            historyIndex++
            setCurrentSong(playbackHistory[historyIndex], isNewHistory = false)
        } else {
            Log.d("SongViewModel", "No next song available.")
        }
    }

    // 앨범 트랙 설정
    fun setAlbumTracks(tracks: List<Content>) {
        currentAlbumTracks = tracks
        albumTrackIndex = 0
        playbackHistory.addAll(tracks)
        historyIndex = playbackHistory.size - 1
        setCurrentSong(tracks[albumTrackIndex], isNewHistory = true)
    }

    //locker와 관련된 것들

    private val _likedSongs= MutableLiveData<List<Content>>()
    val likedSongs: LiveData<List<Content>> = _likedSongs

    fun loadLikedSongs(){
        viewModelScope.launch {
            _likedSongs.value = repository.getLikedContents()
        }
    }

    private val _selectAll = MutableLiveData(false)
    open val selectAll : LiveData<Boolean> = _selectAll
    fun SelectAll(){
        _selectAll.value = _selectAll.value != true
    }

    //한꺼번에 islike = false로 바꾸기
    fun unlikeAllSongs(){
        viewModelScope.launch {
            val likeSongs = repository.getLikedContents()
            likeSongs.forEach { song ->
                val updatedSong = song.copy(islike = false)
                repository.updateContent(updatedSong)
            }
            loadLikedSongs()
        }
    }

    //AlbumContent와 관련된 것
    private val _albumContents = MutableLiveData<AlbumContent>()
    val albumContents : LiveData<AlbumContent> = _albumContents

//    fun toggleLikeAlbum(album:AlbumContent){
//
//        val updatedContent = album.copy(isLike = !album.isLike)
//        _albumContents.value = updatedContent
//        viewModelScope.launch {
//            albumrepository.updateAlbum(updatedContent)
//            loadLikedAlbums()
//        }
//    }
    fun toggleLikeAlbum(albumTitle : String){
        viewModelScope.launch {
            val albumContent = albumrepository.getAlbumByTitle(albumTitle)

            albumContent?.let{
                val updatedContent = it.copy(isLike = !it.isLike)
                albumrepository.updateAlbum(updatedContent)
                _albumContents.value = updatedContent
                loadLikedAlbums()
            }
        }
    }
    fun getAlbumContent(album : AlbumContent) {
        viewModelScope.launch {
            albumrepository.insertAlbum(album = AlbumContent(albumTitle = album.albumTitle, author = album.author, isLike = false))
            _albumContents.value = albumrepository.getAlbumByTitle(album.albumTitle)
        }

    }

    private val _likedAlbums = MutableLiveData<List<AlbumContent>>()
    val likeAlbums : LiveData<List<AlbumContent>> = _likedAlbums

    fun loadLikedAlbums(){
        viewModelScope.launch {
            _likedAlbums.value = albumrepository.getAllLikedAlbums()
        }
    }



}






class MyApplication : Application() {
    val songViewModel : SongViewModel by lazy { SongViewModel(this, repository, albumRepository) }
    private val database by lazy { AppDataBase.getDatabase(this) }
    private val repository by lazy { ContentRepository(database.contentDao()) }
    private val albumRepository by lazy { AlbumRepository(database.albumDao())}

}





////프리뷰 데이터를 위한 가짜 뷰 모델 생성
//class FakeSongViewModel(application: Application) : SongViewModel(application) {
//    override val replay = MutableLiveData(false)
//    override val played = MutableLiveData(true)
//    override val shuffle = MutableLiveData(false)
//    override val currentPosition = MutableLiveData(0f)
//    override val duration = MutableLiveData(200f)
//
//    override fun updatePosition(newPosition: Float) {
//        currentPosition.value = newPosition
//    }
//
//    override fun toggleReplay() {
//        replay.value = replay.value?.not()
//    }
//
//    override fun togglePlayed() {
//        played.value = played.value?.not()
//    }
//
//    override fun toggleShuffle() {
//        shuffle.value = shuffle.value?.not()
//    }
//}