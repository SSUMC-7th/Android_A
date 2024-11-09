package umc.study.umc_7th.content

import android.os.Parcelable
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.parcelize.Parcelize

@Entity(tableName = "songs")
@Parcelize
data class Content(
    @PrimaryKey(autoGenerate = true) val id : Int =0,
    val title: String,
    val author : String,
    val image: Int?= null,
    val length: Int,
    var islike : Boolean,
) : Parcelable

@Dao
interface ContentDao{
    @Query("SELECT * FROM songs")
    suspend fun getAllContents(): List<Content>

    @Query("SELECT * FROM songs WHERE id = :id")
    suspend fun getContentById(id: Int): Content

    @Query("SELECT * FROM songs WHERE title = :title AND author = :author LIMIT 1")
    suspend fun getContentByTitleAndAuthor(title: String, author: String): Content?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(content: Content)

    @Query("DELETE FROM songs WHERE id = :id")
    suspend fun deleteContentById(id: Int)

    @Update
    suspend fun updateContent(content:Content)


}