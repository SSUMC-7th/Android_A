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

    override val _album = MutableLiveData<List<Album>>()
    override val album: LiveData<List<Album>> get() = _album

    // Mock 데이터 로드
    override fun loadAlbum(albumId: Int) {
        viewModelScope.launch {
            _album.value = albumRepository.getAlbumById(albumId)
        }
    }
}