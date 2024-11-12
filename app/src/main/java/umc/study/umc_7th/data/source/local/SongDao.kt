package umc.study.umc_7th.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface SongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSong(song: Song)

    @Update
    suspend fun updateSong(song: Song)

    @Delete
    suspend fun deleteSong(song: Song)

    @Query("SELECT * FROM songTable WHERE id = :songId")
    suspend fun getSongById(songId: Int): Song?

    @Query("SELECT * FROM songTable")
    suspend fun getAllSongs(): List<Song>

    @Query("SELECT * FROM songTable WHERE albumidx = :albumIdx")
    suspend fun getSongsByAlbumId(albumIdx: Int): List<Song>
}