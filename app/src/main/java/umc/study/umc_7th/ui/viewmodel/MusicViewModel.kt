package umc.study.umc_7th.ui.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import umc.study.umc_7th.R
import umc.study.umc_7th.data.repository.AlbumRepository
import umc.study.umc_7th.data.repository.SongRepository
import umc.study.umc_7th.data.source.local.Album
import umc.study.umc_7th.data.source.local.Song
import javax.inject.Inject

@HiltViewModel
open class MusicViewModel @Inject constructor(
    val songRepository: SongRepository,
    val albumRepository: AlbumRepository
) : ViewModel() {

    protected open val _songList = MutableLiveData<List<Song>>()
    open val songList: LiveData<List<Song>> get() = _songList

    open fun loadSong(AlbumId: Int) {
        viewModelScope.launch {
            val songs = songRepository.getSongsByAlbumId(AlbumId)
            _songList.postValue(songs)
        }
    }

    private val _currentSongIndex = MutableLiveData<Int>(0)
    open val currentSongIndex: LiveData<Int> get() = _currentSongIndex

    // 다음 곡
    open fun nextSong() {
        _songList.value?.let { songs ->
            val nextIndex = (_currentSongIndex.value ?: 0) + 1
            if (nextIndex < songs.size) {
                _currentSongIndex.value = nextIndex
            }
        }
    }

    // 이전 곡
    open fun previousSong() {
        _songList.value?.let { songs ->
            val prevIndex = (_currentSongIndex.value ?: 0) - 1
            if (prevIndex >= 0) {
                _currentSongIndex.value = prevIndex
            }
        }
    }

    // 현재 곡 가져오기
    open fun getCurrentSong(): Song? {
        val index = _currentSongIndex.value ?: 0
        return _songList.value?.getOrNull(index)
    }

    open fun getFirstSong(): Song? {
        return _songList.value?.getOrNull(0)
    }

    fun insertDummySongs() {
        viewModelScope.launch {
            val dummySongs = listOf(
                Song(
                    id = 1,
                    title = "LILAC",
                    singer = "아이유",
                    playTime = 200,
                    coverImg = R.drawable.img_album_exp2,
                    albumIdx = 1
                ),
                Song(
                    id = 2,
                    title = "Flu",
                    singer = "아이유",
                    playTime = 180,
                    coverImg = R.drawable.img_album_exp2,
                    albumIdx = 1
                ),
                Song(
                    id = 3,
                    title = "Butter",
                    singer = "방탄소년단",
                    playTime = 210,
                    coverImg = R.drawable.img_album_exp,
                    albumIdx = 2
                ),
                Song(id = 4),
                Song(
                    id = 5,
                    title = "",
                    singer = "에스파",
                    playTime = 220,
                    coverImg = R.drawable.img_album_exp3,
                    albumIdx = 3
                ),
                Song(
                    id = 7,
                    title = "Next Level",
                    singer = "에스파",
                    playTime = 210,
                    coverImg = R.drawable.img_album_exp3,
                    albumIdx = 3
                ),
                Song(
                    id = 8,
                    title = "Bboom Bboom",
                    singer = "모모랜드",
                    playTime = 190,
                    coverImg = R.drawable.img_album_exp5,
                    albumIdx = 5
                )
            )
            dummySongs.forEach { songRepository.insertSong(it) }
            _songList.value = songRepository.getAllSongs()
        }
    }

    protected open val _album = MutableLiveData<List<Album>>()
    open val album: LiveData<List<Album>> get() = _album

    open fun loadAlbum(albumId: Int) {
        viewModelScope.launch {
            _album.value = albumRepository.getAlbumById(albumId)
        }
    }

    fun loadAllAlbums() {
        viewModelScope.launch {
            val albumData = albumRepository.getAllAlbums()  // getAllAlbums 호출
            _album.postValue(albumData)
        }
    }

    fun insertDummyAlbums() {
        viewModelScope.launch {
            val dummyAlbums = listOf(
                Album(
                    id = 1,
                    title = "IU 5th Album 'LILAC'",
                    singer = "아이유 (IU)",
                    coverImg = R.drawable.img_album_exp2
                ),
                Album(
                    id = 2,
                    title = "Butter",
                    singer = "방탄소년단 (BTS)",
                    coverImg = R.drawable.img_album_exp
                ),
                Album(
                    id = 3,
                    title = "iScreaM Vol.10 : Next Level Remixes",
                    singer = "에스파 (AESPA)",
                    coverImg = R.drawable.img_album_exp3
                ),
                Album(
                    id = 4,
                    title = "MAP OF THE SOUL : PERSONA",
                    singer = "방탄소년단 (BTS)",
                    coverImg = R.drawable.img_album_exp4
                ),
                Album(
                    id = 5,
                    title = "GREAT!",
                    singer = "모모랜드",
                    coverImg = R.drawable.img_album_exp5
                )
            )
            // Dummy albums 삽입
            dummyAlbums.forEach { albumRepository.insertAlbum(it) }

            // DB에서 전체 앨범 목록 가져와 LiveData에 반영
            _album.value = albumRepository.getAllAlbums()
        }
    }

    private val _currentTime = MutableStateFlow(0f)
    val currentTime : StateFlow<Float> = _currentTime

    private var _isPlaying = MutableStateFlow(false) // 재생 상태 관리
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val handler = Handler(Looper.getMainLooper())
    private var updateRunnable: Runnable? = null

    init {
        startProgress()
    }

    private fun startProgress() {
        updateRunnable = object : Runnable {
            override fun run() {
                if (_isPlaying.value) {
                    _currentTime.value += 1f / 60f
                    if (_currentTime.value >= 1f) _currentTime.value = 0f // 최대치 도달 시 0으로 초기화
                }
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(updateRunnable!!)
    }

    fun togglePlayPause() {
        _isPlaying.value = !_isPlaying.value // 재생/일시정지 전환
    }

    fun updateCurrentTime(newTime: Float) {
        _currentTime.value = newTime / 60f
    }

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacks(updateRunnable!!)
    }
}