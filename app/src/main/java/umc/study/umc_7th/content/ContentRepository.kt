package umc.study.umc_7th.content

class ContentRepository(private val contentDao: ContentDao) {
    suspend fun getAllContents(): List<Content> {
        return contentDao.getAllContents()
    }

    suspend fun getContentById(id: Int): Content {
        return contentDao.getContentById(id)
    }

    suspend fun insert(content: Content) {
        contentDao.insert(content)
    }

    suspend fun deleteContentById(id: Int) {
        contentDao.deleteContentById(id)
    }
}