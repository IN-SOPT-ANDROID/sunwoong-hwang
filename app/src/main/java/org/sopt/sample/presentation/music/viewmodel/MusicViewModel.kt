package org.sopt.sample.presentation.music.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import org.sopt.sample.data.model.Music
import org.sopt.sample.data.model.MusicRequest
import org.sopt.sample.domain.repository.MusicRepository
import org.sopt.sample.util.Event
import org.sopt.sample.util.extension.addSourceList

class MusicViewModel(private val musicRepository: MusicRepository) : ViewModel() {
    private val _musicList = MutableLiveData<List<Music>>()
    val musicList: LiveData<List<Music>>
        get() = _musicList
    private val image = MutableLiveData<MultipartBody.Part>()
    val title = MutableLiveData<String>()
    val singer = MutableLiveData<String>()
    val isValid = MediatorLiveData<Boolean>().apply {
        addSourceList(image, title, singer) {
            checkRegisterMusic()
        }
    }
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val _musicEvent = MutableLiveData<Event<Boolean>>()
    val musicEvent: LiveData<Event<Boolean>>
        get() = _musicEvent

    private fun checkRegisterMusic(): Boolean {
        if (image.value == null || title.value == null || singer.value == null) return false
        return title.value!!.isNotEmpty() && singer.value!!.isNotEmpty()
    }

    fun setImageUriToPart(image: MultipartBody.Part) {
        this.image.value = image
    }

    fun getMusicList() {
        viewModelScope.launch {
            runCatching {
                _isLoading.value = true
                musicRepository.getMusicList()
            }.fold({
                _musicList.value = it.result
                _isLoading.value = false
                _musicEvent.value = Event(true)
            }, {
                _musicEvent.value = Event(false)
            })
        }
    }

    fun registerMusic() {
        viewModelScope.launch {
            runCatching {
                musicRepository.registerMusic(
                    image.value!!,
                    MusicRequest(title.value.toString(), singer.value.toString()).toJsonObject()
                )
            }.fold({
                _musicEvent.value = Event(true)
            }, {
                _musicEvent.value = Event(false)
            })
        }
    }
}