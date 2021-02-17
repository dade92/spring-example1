package com.springexample.domain

interface PathUseCase {
    fun retrieve(pathId: PathId): Path
}

class PathUseCaseImpl: PathUseCase {
    override fun retrieve(pathId: PathId): Path {
        TODO("Not yet implemented")
    }
}

data class PathId(
    val id: Long
)

data class Path(
    val id: Long,
    val info: String
)