package umc.study.umc_7th.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
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

    /* Firebase
    private val database = FirebaseDatabase.getInstance()
    private val likesRef = database.getReference("likes")

    override fun saveLike(songId: String, userId: String) {
        val likeData = mapOf("songId" to songId, "userId" to userId, "isLiked" to true)
        likesRef.child(userId).child(songId).setValue(likeData)
            .addOnSuccessListener {

            }
            .addOnFailureListener { error ->
                // Handle failure
            }
    }

    override fun getLikedSongs(userId: String, onComplete: (List<Song>) -> Unit) {
        likesRef.child(userId).get()
            .addOnSuccessListener { snapshot ->
                val likedSongs = snapshot.children.mapNotNull { it.getValue(Song::class.java) }
                onComplete(likedSongs)
            }
            .addOnFailureListener { error ->
                // Handle failure
            }
    }
    Firebase */

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

    // Get only liked songs
    override fun getLikedSongs(): LiveData<List<Song>> {
        return _songList.map { songs -> songs.filter { it.isLike } }
    }

    // Toggle like status of a song
    override fun toggleLikeStatus(song: Song) {
        viewModelScope.launch {
            val updatedSong = song.copy(isLike = !song.isLike)
            songRepository.updateSong(updatedSong)

            // Update local list after modification
            _songList.value = _songList.value?.map { if (it.id == song.id) updatedSong else it }
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

    override fun loadAllAlbums() {
        viewModelScope.launch {
            val albumData = albumRepository.getAllAlbums()  // getAllAlbums 호출
            _album.postValue(albumData)
        }
    }

    override fun getLikedAlbums(): LiveData<List<Album>> {
        return _album.map { albums -> albums.filter { it.isLiked } }
    }

    override fun toggleLikeStatusAlbum(album: Album) {
        viewModelScope.launch {
            val updatedAlbum = album.copy(isLiked = !album.isLiked)
            albumRepository.updateAlbum(updatedAlbum)

            // Update local list after modification
            _album.value = _album.value?.map { if (it.id == album.id) updatedAlbum else it }
        }
    }
}
