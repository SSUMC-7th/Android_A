package umc.study.umc_7th.content

class ContentRepository(private val contentDao: ContentDao) {
    suspend fun getAllContents(): List<Content> {
        return contentDao.getAllContents()
    }

    suspend fun getContentById(id: Int): Content {
        return contentDao.getContentById(id)
    }

    suspend fun getContentByTitleAndAuthor(title: String, author: String): Content? {
        return contentDao.getContentByTitleAndAuthor(title, author)
    }
    suspend fun insert(content: Content) {
        val existingContent = contentDao.getContentByTitleAndAuthor(content.title, content.author)

        // 데이터베이스에 동일한 title과 author가 없을 때만 삽입
        if (existingContent == null) {
            contentDao.insert(content)
            contentDao.getContentByTitleAndAuthor(content.title, content.author)!!
        } else {
            existingContent
        }
    }

    suspend fun updateContent(content:Content){
        contentDao.updateContent(content)
    }

    suspend fun deleteContentById(id: Int) {
        contentDao.deleteContentById(id)
    }
    suspend fun getPreviousContent(currentSongId: Int): Content? {
        return contentDao.getPreviousContent(currentSongId)
    }
    suspend fun getNextContent(currentSongId: Int): Content? {
        return contentDao.getNextContent(currentSongId)
    }


    // locker와 관련된 것들
    suspend fun getLikedContents(): List<Content>{
        return contentDao.getLikedContents()
    }


}