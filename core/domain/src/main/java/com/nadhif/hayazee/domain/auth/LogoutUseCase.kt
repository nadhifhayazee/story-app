package com.nadhif.hayazee.domain.auth

import com.nadhif.hayazee.datastore.AppDataStore
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val appDataStore: AppDataStore
) {

    operator fun invoke() {
        appDataStore.deleteDataStore()
    }
}