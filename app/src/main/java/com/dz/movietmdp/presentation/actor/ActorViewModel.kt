package com.dz.movietmdp.presentation.actor

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.dz.movietmdp.common.Constants
import com.dz.movietmdp.domain.usecase.getactor.GetActorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActorViewModel @Inject constructor(
    private val actorUseCase: GetActorUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        savedStateHandle.get<String?>(Constants.PARAM_ACTOR)?.let { params ->
            Log.e("params",params)
        }
    }
}