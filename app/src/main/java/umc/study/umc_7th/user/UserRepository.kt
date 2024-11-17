package umc.study.umc_7th.user

class UserRepository(private val userDao: UserDao) {
    suspend fun registerUser(email: String, password: String) :Boolean {
        val existingUser= userDao.getUserByEmail(email)
        return if(existingUser == null){
            userDao.insert(User(email = email, password = password))
            true
        }else{
            false
        }
    }

    suspend fun loginUser(email: String, password: String): Boolean {
        val user = userDao.getUserByEmail(email)
        return user != null && user.password == password

    }

}