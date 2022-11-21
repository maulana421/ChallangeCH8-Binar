package com.solanacode.challange8.utils

object UpdateProfile {

    fun validateEditProfile(name: String,email: String): String{
        if(name.isBlank() || email.isBlank()){
            return "Field cannot be empety"
        }
        return "success"

    }
}