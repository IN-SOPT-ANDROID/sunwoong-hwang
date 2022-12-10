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

    private val _registerMusicListEvent = MutableLiveData<Event<Boolean>>()
    val registerMusicListEvent: LiveData<Event<Boolean>>
        get() = _registerMusicListEvent

    private val _registerMusicOnClickEvent = MutableLiveData<Event<Boolean>>()
    val registerMusicOnClickEvent: LiveData<Event<Boolean>>
        get() = _registerMusicOnClickEvent

    private val _registerMusicEvent = MutableLiveData<Event<Boolean>>()
    val registerMusicEvent: LiveData<Event<Boolean>>
        get() = _registerMusicEvent

    private fun checkRegisterMusic(): Boolean {
        return image.value != null && title.value != null && singer.value != null
    }

    fun setImageUriToPart(image: MultipartBody.Part) {
        this.image.value = image
    }

    fun startRegisterMusic() {
        _registerMusicOnClickEvent.value = Event(true)
    }

    fun getMusicList() {
        viewModelScope.launch {
            runCatching {
                musicRepository.getMusicList()
            }.fold({
                _musicList.value = it.result
                _registerMusicListEvent.value = Event(true)
            }, {
                _registerMusicListEvent.value = Event(false)
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
                _registerMusicEvent.value = Event(true)
            }, {
                _registerMusicEvent.value = Event(false)
            })
        }
    }
}