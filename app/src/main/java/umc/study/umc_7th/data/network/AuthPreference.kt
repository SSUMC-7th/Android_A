package umc.study.umc_7th.data.network

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.serialization.json.Json
import umc.study.umc_7th.User

class AuthPreference(context: Context) {
    companion object {
        private const val PREF_NAME = "auth_tokens"
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_USER = "user"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREF_NAME,
        Context.MODE_PRIVATE
    )

    var accessToken: String?
        get() = prefs.getString(KEY_ACCESS_TOKEN, null)
        set(value) = prefs.edit { 
            putString(KEY_ACCESS_TOKEN, value)
        }

    var refreshToken: String?
        get() = prefs.getString(KEY_REFRESH_TOKEN, null)
        set(value) = prefs.edit { 
            putString(KEY_REFRESH_TOKEN, value)
        }

    var user: User?
        get() = prefs.getString(KEY_USER, null)?.let { Json.decodeFromString<User>(it) }
        set(value) = prefs.edit {
            putString(KEY_USER, value?.let { Json.encodeToString(User.serializer(), it) })
        }
}