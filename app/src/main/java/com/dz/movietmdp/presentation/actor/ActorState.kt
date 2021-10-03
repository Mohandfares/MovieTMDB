package com.dz.movietmdp.presentation.actor

import com.dz.movietmdp.domain.model.ActorDetail

data class ActorState(
    val isLoading: Boolean = false,
    val actorDetail: ActorDetail? = null,
    val error: String = ""
)
