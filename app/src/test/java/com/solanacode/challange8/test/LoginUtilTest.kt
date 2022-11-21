@file:Suppress("MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate",
    "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate",
    "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate"
)

package com.solanacode.challange8.test


import com.google.common.truth.Truth.assertThat
import com.solanacode.challange8.utils.LoginUtils
import org.junit.Before
import org.junit.Test

class LoginUtilTest {
    lateinit var login : LoginUtils

    @Before
    fun setUp() {
        login = LoginUtils
    }


    @Test
    fun email_is_empty() {
        val email = ""
        val assertLogin = login.validateUserlogin(email, "12345678")
        assertThat(assertLogin == "success")
    }

    @Test
    fun password_is_empty() {
        val password = ""
        val assertLogin = login.validateUserlogin("maulanaibnu", password)
        assertThat(assertLogin == "success")
    }

    @Test
    fun `email not containts @`() {
        val assertLogin = login.validateUserlogin("maulanaibnu", "asdfghjgf")
        assertThat(assertLogin == "success")
    }

    @Test
    fun `email ath least 1 digit`() {
        val assertLogin = login.validateUserlogin("F", "asdfghjgf")
        assertThat(assertLogin == "success")
    }

    @Test
    fun `passowrd minimal 6 digit`() {
        val assertLogin = login.validateUserlogin("maulanaibnu", "dsd")
        assertThat(assertLogin == "success")
    }

}