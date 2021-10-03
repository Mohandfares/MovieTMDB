package com.dz.movietmdp.presentation.actor


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dz.movietmdp.common.Constants
import com.dz.movietmdp.common.Resource
import com.dz.movietmdp.domain.usecase.getactor.GetActorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ActorViewModel @Inject constructor(
    private val actorUseCase: GetActorUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf<ActorState>(ActorState())
    val state: State<ActorState> = _state

    init {
        savedStateHandle.get<String?>(Constants.PARAM_ACTOR)?.let { params ->
            val personId = params.split(",")[0]
            val creditId = params.split(",")[1]
            getActor(personId, creditId)
        }
    }

    private fun getActor(personId: String,creditId: String) {
        actorUseCase(personId, creditId).onEach { result ->
            _state.value = when (result) {
                is Resource.Error -> ActorState(error = result.message ?: "An unexpected error")
                is Resource.Loading -> ActorState(isLoading = true)
                is Resource.Success -> ActorState(actorDetail = result.data)
            }
        }.launchIn(viewModelScope)
    }

    fun tryAgain() {
        savedStateHandle.get<String?>(Constants.PARAM_ACTOR)?.let { params ->
            val personId = params.split(",")[0]
            val creditId = params.split(",")[1]
            getActor(personId, creditId)
        }
    }

}