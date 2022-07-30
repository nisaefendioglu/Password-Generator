package com.nisaefendioglu.passwordgenerator.data.repository

import com.nisaefendioglu.passwordgenerator.data.remote.source.PasswordRemoteDataSource
import com.nisaefendioglu.passwordgenerator.domain.repository.PasswordRepository
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PasswordRepositoryImpl @Inject constructor(private val passwordRemoteDataSource: PasswordRemoteDataSource) :
    PasswordRepository {
    override fun getPasswordGenerator() =
        flow { emitAll(passwordRemoteDataSource.getPasswordGenerator()) }
}