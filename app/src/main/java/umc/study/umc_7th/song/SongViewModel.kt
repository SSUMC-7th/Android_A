package umc.study.umc_7th.song

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import umc.study.umc_7th.Content
import umc.study.umc_7th.network.Server

class SongViewModel: ViewModel() {
    val content = mutableStateOf<Content?>(null)

    fun getContent(id: Long, onFailed: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                val music = Server.getMusic(id)!!
                content.value = music
            }
            catch (e: Exception) {
                e.printStackTrace()
                onFailed()
            }
        }
    }
}