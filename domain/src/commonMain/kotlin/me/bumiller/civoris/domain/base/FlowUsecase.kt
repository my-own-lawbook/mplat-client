package me.bumiller.civoris.domain.base

import kotlinx.coroutines.flow.Flow

/**
 * Base class for any usecase that returns a flow.
 */
fun interface FlowUsecase<in Input, out Output> : BaseUsecase<Input, Flow<Output>>