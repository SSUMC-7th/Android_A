package umc.study.umc_7th

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SongViewModel(application: Application) : AndroidViewModel(application) {
    private val _replay = MutableLiveData(false)
    val replay : LiveData<Boolean> = _replay

    private val _played = MutableLiveData(true)
    val played : LiveData<Boolean> = _played

    private val _shuffle = MutableLiveData(false)
    val shuffle : LiveData<Boolean> = _shuffle

    fun toggleReplay() {
        _replay.value = _replay.value != true
    }
    fun togglePlayed() {
        _played.value = _played.value != true
    }
    fun toggleShuffle() {
        _shuffle.value = _shuffle.value != true
    }
}

class MyApplication : Application() {
    val songViewModel : SongViewModel by lazy { SongViewModel(this) }

}