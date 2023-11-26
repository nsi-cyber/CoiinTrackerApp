package com.nsicyber.coiintrackerapp.data.db

import android.content.Context
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.delay

object HawkUtils {

    const val LAST_USER_UID = "lastuser"

    fun setLastUser(uid: String?) {
        Hawk.put(LAST_USER_UID, uid)
    }
    fun getLastUser(): String? {
        return Hawk.get(LAST_USER_UID, null)
    }

}