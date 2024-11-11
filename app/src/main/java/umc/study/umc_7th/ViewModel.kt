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
import umc.study.umc_7th.content.AppDataBase
import umc.study.umc_7th.content.Content
import umc.study.umc_7th.content.ContentDao
import umc.study.umc_7th.content.ContentRepository

open class SongViewModel(application: Application,
    private val repository: ContentRepository) : AndroidViewModel(application) {



//    private val _like = MutableLiveData(false)
//    open val like : LiveData<Boolean> = _like

    private val _unLike = MutableLiveData(false)
    open val unLike : LiveData<Boolean> = _unLike

    fun toggleLike(content:Content){
        val updatedContent = content.copy(islike = !content.islike)
        viewModelScope.launch {
            repository.updateContent(updatedContent)
            loadLikedSongs()
        }
    }

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


    private val _currentSong = MutableLiveData<Content?>(null)
    open val currentSong : LiveData<Content?> = _currentSong

    fun loadCurrentSong(id:Int){
        viewModelScope.launch {
            _currentSong.value = repository.getContentById(id)
        }
    }

    fun addContent(content:Content){
        viewModelScope.launch {
            repository.insert(content)

        }
    }
    private val _allSongs = MutableLiveData<List<Content>>().apply { value = emptyList() }
    val allSongs : LiveData<List<Content>> = _allSongs

    fun loadAllSongs(){
        viewModelScope.launch {
            _allSongs.value = repository.getAllContents()
        }
    }


    // 곡 넘김과 관련된 것들
//    fun setCurrentSong1(content: Content, isNewHistory : Boolean = true){
//        if(isNewHistory){
//            playbackHistory.add(content)
//            historyIndex = playbackHistory.size - 1
//        }
//
//        _currentSong.value = content
//        resetProgress()
//        togglePlayed()
//        viewModelScope.launch{
//            repository.insert(content)
//            val updatedContent = repository.getContentByTitleAndAuthor(content.title, content.author)
//
//            // `updatedContent`를 `_currentSong`에 설정하여 자동 증가된 ID 반영
//            if (updatedContent != null) {
//                _currentSong.value = updatedContent
//                resetProgress()
//                togglePlayed()
//                Log.d("SongViewModel", "Set current song with ID: ${updatedContent.id}, Title: ${updatedContent.title}")
//            }
//        }
//    }
    fun setCurrentSong(content: Content, isNewHistory: Boolean = true) {
        viewModelScope.launch {
            // 비동기 작업 내에서 히스토리 추가 및 currentSong 업데이트
            val existingContent = repository.getContentByTitleAndAuthor(content.title, content.author)

            val contentToSet = if (existingContent == null) {
                repository.insert(content)
                repository.getContentByTitleAndAuthor(content.title, content.author) ?: content
            } else {
                existingContent
            }

            // 재생 히스토리에 추가
            if (isNewHistory) {
                playbackHistory.add(contentToSet)
                historyIndex = playbackHistory.size - 1
            }

            // 업데이트된 곡을 currentSong에 설정
            _currentSong.value = contentToSet
            resetProgress()
            togglePlayed()

            Log.d("SongViewModel", "Set current song with ID: ${contentToSet.id}, Title: ${contentToSet.title}")
        }
    }


//    fun playPreviousSong() {
//        val currentSongId = _currentSong.value?.id ?: return  // 현재 곡 ID가 없으면 함수 종료
//        Log.d("SongViewModel", "Current song ID: $currentSongId")
//        viewModelScope.launch {
//            val previousSong = repository.getPreviousContent(currentSongId)
//            if (previousSong != null) {
//                setCurrentSong(previousSong)
//            }else {
//                Log.d("SongViewModel", "No previous song available.")
//            }
//        }
//    }
fun playPreviousSong() {
    if (historyIndex > 0) {
        historyIndex--
        _currentSong.value = playbackHistory[historyIndex]
    } else {
        Log.d("SongViewModel", "No previous song available.")
    }
}
//    fun playNextSong() {
//        val currentSongId = _currentSong.value?.id ?: return
//
//        viewModelScope.launch {
//            val nextSong = repository.getNextContent(currentSongId)
//            if (nextSong != null) {
//                setCurrentSong(nextSong)
//            }
//        }
//
//    }
fun playNextSong() {
    if (currentAlbumTracks.isNotEmpty() && albumTrackIndex < currentAlbumTracks.size - 1) {
        albumTrackIndex++
        setCurrentSong(currentAlbumTracks[albumTrackIndex], isNewHistory = false)
    } else {
        Log.d("SongViewModel", "No next song available in album.")
    }
}
    fun setAlbumTracks(tracks: List<Content>) {
        currentAlbumTracks = tracks
        albumTrackIndex = 0
        if (tracks.isNotEmpty()) {
            setCurrentSong(tracks[albumTrackIndex], isNewHistory = true)
        }
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

    // 재생 순서 관리
    private val playbackHistory = mutableListOf<Content>()
    private var historyIndex = -1

    //현재 앨범 재생 리스트
    private var currentAlbumTracks : List<Content> = emptyList()
    private var albumTrackIndex = 0



}






class MyApplication : Application() {
    val songViewModel : SongViewModel by lazy { SongViewModel(this, repository) }
    private val database by lazy { AppDataBase.getDatabase(this) }
    private val repository by lazy { ContentRepository(database.contentDao()) }
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