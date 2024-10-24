package umc.study.umc_7th

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class SongViewModel(application: Application) : AndroidViewModel(application) {

    private val _like = MutableLiveData(false)
    open val like : LiveData<Boolean> = _like

    private val _unLike = MutableLiveData(false)
    open val unLike : LiveData<Boolean> = _unLike

    fun toggleLike(){
        _like.value = _like.value != true
    }

    fun toggleUnLike(){
        _unLike.value = _unLike.value != true
    }



    private val _replay = MutableLiveData(false)
    open val replay : LiveData<Boolean> = _replay

    private val _played = MutableLiveData(false)
    open val played : LiveData<Boolean> = _played

    private val _duration = MutableLiveData(200f)
    open val duration : LiveData<Float> = _duration

    private val _currentPosition = MutableLiveData(0f)
    open val currentPosition : LiveData<Float> = _currentPosition

    private val _shuffle = MutableLiveData(false)
    open val shuffle : LiveData<Boolean> = _shuffle

    private var playbackJob : Job? = null

    open fun toggleReplay() {
        _replay.value = _replay.value != true
    }
    open fun togglePlayed() {
        _played.value = _played.value != true

        if (_played.value == true) {
            startPlayback()
        }else {
            stopPlayedback()
        }
    }

    private fun startPlayback(){
        playbackJob?.cancel()
        playbackJob = viewModelScope.launch {
            while (_played.value == true && _currentPosition.value!! < _duration.value!!){
                delay(1000L)
                _currentPosition.value = _currentPosition.value!! + 1f
            }
        }
    }
    private fun stopPlayedback(){
        playbackJob?.cancel()
    }

    open fun toggleShuffle() {
        _shuffle.value = _shuffle.value != true
    }
    open fun updatePosition(position: Float){
        _currentPosition.value = position
    }
    fun setDuration(duration: Float){
        _duration.value = duration
    }
    fun resetProgress(){ // 그 .. beforeSongPlayClick() 에 넣어줄 것
        _currentPosition.value = 0f
    }





}

class MyApplication : Application() {
    val songViewModel : SongViewModel by lazy { SongViewModel(this) }

}

//프리뷰 데이터를 위한 가짜 뷰 모델 생성
class FakeSongViewModel(application: Application) : SongViewModel(application) {
    override val replay = MutableLiveData(false)
    override val played = MutableLiveData(true)
    override val shuffle = MutableLiveData(false)
    override val currentPosition = MutableLiveData(0f)
    override val duration = MutableLiveData(200f)

    override fun updatePosition(newPosition: Float) {
        currentPosition.value = newPosition
    }

    override fun toggleReplay() {
        replay.value = replay.value?.not()
    }

    override fun togglePlayed() {
        played.value = played.value?.not()
    }

    override fun toggleShuffle() {
        shuffle.value = shuffle.value?.not()
    }
}