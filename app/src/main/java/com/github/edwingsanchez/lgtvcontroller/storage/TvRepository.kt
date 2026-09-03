package com.github.edwingsanchez.lgtvcontroller.storage

import com.github.edwingsanchez.lgtvcontroller.domain.model.Tv
import kotlinx.coroutines.flow.Flow

interface TvRepository {
    fun getAllTvsStream(): Flow<List<Tv>>

    fun getTvStream(id: String): Flow<Tv>

    suspend fun insertTv(tv: Tv)

    suspend fun updateTv(tv: Tv)

    suspend fun deleteTv(tv: Tv)
}