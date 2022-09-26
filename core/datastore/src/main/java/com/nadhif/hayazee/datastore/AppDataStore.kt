package com.nadhif.hayazee.datastore

import android.content.SharedPreferences
import com.nadhif.hayazee.model.common.User
import javax.inject.Inject


class AppDataStore @Inject constructor(
    sharedPreferences: SharedPreferences
) : BaseDataStore(sharedPreferences) {

    companion object {
        const val USER_DATA_STORE = "USER_DATA_STORE"
        const val IS_LOGIN_DATA_STORE = "IS_LOGIN_DATA_STORE"
    }

    fun saveUser(user: User) {
        saveDataObject(USER_DATA_STORE, user)
    }

    fun getUser(): User? {
        return getSavedDataObject(USER_DATA_STORE, User::class.java)
    }

    fun isLoggedIn(): Boolean {
        return getUser() != null
    }
}