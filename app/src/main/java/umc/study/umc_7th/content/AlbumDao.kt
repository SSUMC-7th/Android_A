package umc.study.umc_7th.content

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update

@Entity(tableName = "albums")
data class AlbumContent(
    @PrimaryKey(autoGenerate = true)val id : Int =0,
    val albumTitle: String,
    val author : String,
    val isLike : Boolean,
    val albumImage : Int,

)

@Dao
interface AlbumDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(albumContent: AlbumContent)

    @Query("SELECT * FROM albums WHERE isLike=1")
    suspend fun getAllLikeAlbum(): List<AlbumContent>

    @Query("SELECT * FROM albums WHERE albumTitle = :title")
    suspend fun getAlbumByTitle(title: String): AlbumContent

    @Update
    suspend fun updateAlbum(album : AlbumContent)

    @Query("SELECT * FROM albums")
    suspend fun getAllAlbums(): List<AlbumContent>


}