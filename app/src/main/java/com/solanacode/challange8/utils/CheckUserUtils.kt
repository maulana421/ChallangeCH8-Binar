@file:Suppress("ReplaceCallWithBinaryOperator", "ReplaceCallWithBinaryOperator")

package com.solanacode.challange8.utils

object CheckUserUtils {
    fun validateUser(token : String): Boolean{
        if(token.equals("undefined")){
            return false
        }
        return true

    }
}