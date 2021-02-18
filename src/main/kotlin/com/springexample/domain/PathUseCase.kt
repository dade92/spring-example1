package com.springexample.domain

import arrow.core.Either
import arrow.core.Right
import java.lang.RuntimeException

interface PathUseCase {
    fun retrieve(pathId: PathId): Either<RuntimeException, Path>
}

class PathUseCaseImpl: PathUseCase {
    override fun retrieve(pathId: PathId): Either<RuntimeException, Path> = Right(Path(pathId.id, "INFO"))
}

data class PathId(
    val id: Long
)

data class Path(
    val id: Long,
    val info: String
)