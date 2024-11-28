package umc.study.umc_7th.utils

import android.content.Context

object SharedPreferencesHelper {

    private const val PREF_NAME = "user_prefs"

    fun saveUserInfo(context: Context, email: String, memberId: Int) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putInt("memberId", memberId)
        editor.apply()
    }

    fun saveUserToken(context: Context, token: String) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("token", token)
        editor.apply()
    }

    fun getUserInfo(context: Context): Triple<String?, Int?, String?> {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", null)
        val memberId = sharedPreferences.getInt("memberId", -1).takeIf { it != -1 }
        val token = sharedPreferences.getString("token", null)
        return Triple(email, memberId, token)
    }

    fun clearUserInfo(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}