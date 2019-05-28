package com.ml.democonstraintlayout.helper

import android.content.Context
import android.content.SharedPreferences

class UserSharedPref {
    val prefs: SharedPreferences
    val prefsEditer: SharedPreferences.Editor

    constructor(context: Context){
        prefs = context.getSharedPreferences("USER_DETAILS", 0);
        prefsEditer = prefs.edit()
        prefsEditer.commit()
    }

    fun setLoginCheck(login: Boolean){
        prefsEditer.putBoolean("LoginCheck", login)
        prefsEditer.apply()
    }

    fun getLoginCheck(): Boolean{
        return prefs.getBoolean("LoginCheck", false)
    }


    fun setUserId(usr_id: String){
        prefsEditer.putString("UserId", usr_id)
        prefsEditer.apply()
    }

    fun getUserId(): String{
        return prefs.getString("UserId", "0")
    }

    fun setUserPassword(usr_password: String){
        prefsEditer.putString("UserPassword", usr_password)
        prefsEditer.apply()
    }

    fun getUserPassword(): String{
        return prefs.getString("UserPassword", "0")
    }

    fun setUserType(usr_type: String){
        prefsEditer.putString("UserType", usr_type)
        prefsEditer.apply()
    }

    fun getUserType(): String{
        return prefs.getString("UserType", "0")
    }

    fun setMobileNum(mobile: String){
        prefsEditer.putString("mobile", mobile)
        prefsEditer.apply()
    }

    fun getMobileNum(): String{
        return prefs.getString("mobile", "0")
    }

    fun setUserStatus(usr_status: String){
        prefsEditer.putString("UserStatus", usr_status)
        prefsEditer.apply()
    }

    fun getUserStatus(): String{
        return prefs.getString("UserStatus", "0")
    }

    fun logout(){
        prefsEditer.clear()
        prefsEditer.commit()
    }

}