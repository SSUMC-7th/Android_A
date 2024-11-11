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
import umc.study.umc_7th.data.source.local.AlbumDao
import umc.study.umc_7th.data.source.local.Song
import umc.study.umc_7th.data.source.local.SongDao
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
            _songList.value = songRepository.getSongsByAlbumId(AlbumId)
        }
    }

    fun insertDummySongs() {
        viewModelScope.launch {
            val dummySongs = listOf(
                Song(
                    title = "LILAC",
                    singer = "아이유",
                    playTime = 200,
                    coverImg = R.drawable.img_album_exp2,
                    albumidx = 1
                ),
                Song(
                    title = "Flu",
                    singer = "아이유",
                    playTime = 180,
                    coverImg = R.drawable.img_album_exp2,
                    albumidx = 1
                ),
                Song(
                    title = "Butter",
                    singer = "방탄소년단",
                    playTime = 210,
                    coverImg = R.drawable.img_album_exp,
                    albumidx = 2
                ),
                Song(),
                Song(
                    title = "",
                    singer = "에스파",
                    playTime = 220,
                    coverImg = R.drawable.img_album_exp3,
                    albumidx = 3
                ),
                Song(
                    title = "Next Level",
                    singer = "에스파",
                    playTime = 210,
                    coverImg = R.drawable.img_album_exp3,
                    albumidx = 3
                ),
                Song(
                    title = "Bboom Bboom",
                    singer = "모모랜드",
                    playTime = 190,
                    coverImg = R.drawable.img_album_exp5,
                    albumidx = 5
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

    fun insertDummyAlbums() {
        viewModelScope.launch {
            val dummyAlbums = listOf(
                Album(
                    title = "IU 5th Album 'LILAC'",
                    singer = "아이유 (IU)",
                    coverImg = R.drawable.img_album_exp2
                ),
                Album(
                    title = "Butter",
                    singer = "방탄소년단 (BTS)",
                    coverImg = R.drawable.img_album_exp
                ),
                Album(
                    title = "iScreaM Vol.10 : Next Level Remixes",
                    singer = "에스파 (AESPA)",
                    coverImg = R.drawable.img_album_exp3
                ),
                Album(
                    title = "MAP OF THE SOUL : PERSONA",
                    singer = "방탄소년단 (BTS)",
                    coverImg = R.drawable.img_album_exp4
                ),
                Album(
                    title = "GREAT!",
                    singer = "모모랜드",
                    coverImg = R.drawable.img_album_exp5
                )
            )
            dummyAlbums.forEach { albumRepository.insertAlbum(it) }
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