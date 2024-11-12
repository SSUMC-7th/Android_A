package umc.study.umc_7th.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import umc.study.umc_7th.data.repository.MockAlbumRepository
import umc.study.umc_7th.data.repository.MockSongRepository
import umc.study.umc_7th.data.source.local.Album
import umc.study.umc_7th.data.source.local.MockAlbumDao
import umc.study.umc_7th.data.source.local.MockSongDao
import umc.study.umc_7th.data.source.local.Song

class MockMusicViewModel : MusicViewModel(
    MockSongRepository(MockSongDao()), MockAlbumRepository(MockAlbumDao())
) {

    override val _songList = MutableLiveData<List<Song>>()
    override val songList: LiveData<List<Song>> get() = _songList

    override fun loadSong(AlbumId: Int) {
        viewModelScope.launch {
            _songList.value = songRepository.getSongsByAlbumId(AlbumId)
        }
    }

    private val _currentSongIndex = MutableLiveData<Int>(0)
    override val currentSongIndex: LiveData<Int> get() = _currentSongIndex

    // 다음 곡
    override fun nextSong() {
        _songList.value?.let { songs ->
            val nextIndex = (_currentSongIndex.value ?: 0) + 1
            if (nextIndex < songs.size) {
                _currentSongIndex.value = nextIndex
            }
        }
    }

    // 이전 곡
    override fun previousSong() {
        _songList.value?.let { songs ->
            val prevIndex = (_currentSongIndex.value ?: 0) - 1
            if (prevIndex >= 0) {
                _currentSongIndex.value = prevIndex
            }
        }
    }

    // 현재 곡 가져오기
    override fun getCurrentSong(): Song? {
        val index = _currentSongIndex.value ?: 0
        return _songList.value?.getOrNull(index)
    }

    override fun getFirstSong(): Song? {
        return _songList.value?.getOrNull(0)
    }


    override val _album = MutableLiveData<List<Album>>()
    override val album: LiveData<List<Album>> get() = _album

    // Mock 데이터 로드
    override fun loadAlbum(albumId: Int) {
        viewModelScope.launch {
            _album.value = albumRepository.getAlbumById(albumId)
        }
    }
}