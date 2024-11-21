package umc.study.umc_7th.utils

import android.content.Context

object SharedPreferencesHelper {

    private const val PREF_NAME = "user_prefs"

    fun saveUserInfo(context: Context, email: String, token: String) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putString("token", token)
        editor.apply()
    }

    fun getUserInfo(context: Context): Pair<String?, String?> {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", null)
        val token = sharedPreferences.getString("token", null)
        return Pair(email, token)
    }

    fun clearUserInfo(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}