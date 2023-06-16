package com.relevanx.capstone_v1.stater_view

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object SavedPreference {

    const val EMAIL = "email"
    const val USERNAME ="username"
    const val UID = "uid"
    const val TOKEN = "token"

    private  fun getSharedPreference(ctx: Context?): SharedPreferences? {
        return PreferenceManager.getDefaultSharedPreferences(ctx)
    }

    private fun  editor(context: Context, const:String, string: String){
        getSharedPreference(
            context
        )?.edit()?.putString(const,string)?.apply()
    }

    fun setEmail(context: Context, email: String){
        editor(
            context,
            EMAIL,
            email
        )
    }

    fun setUsername(context: Context, username:String){
        editor(
            context,
            USERNAME,
            username
        )
    }

    fun setUid(context: Context, uid:String){
        editor(
            context,
            UID,
            uid
        )
    }

    fun setToken(context: Context, token:String){
        editor(
            context,
            TOKEN,
            "Bearer $token"
        )
    }

    fun getEmail(context: Context)= getSharedPreference(
        context
    )?.getString(EMAIL,"")

    fun getUsername(context: Context) = getSharedPreference(
        context
    )?.getString(USERNAME,"")

    fun getUid(context: Context) = getSharedPreference(
        context
    )?.getString(UID,"")

    fun getToken(context: Context) = getSharedPreference(
        context
    )?.getString(TOKEN,"")

    fun clear(context: Context){
        getSharedPreference(
            context
        )?.edit()?.clear()?.apply()
    }
}