package com.solanacode.challange8.test


import com.google.common.truth.Truth.assertThat
import com.solanacode.challange8.utils.UpdateProfile
import org.junit.Before
import org.junit.Test

class UpdateProfileTest {

    private lateinit var updateProfile : UpdateProfile

    @Before
    fun setup(){
        updateProfile = UpdateProfile
    }



    @Test
    fun `field is empety or blank` (){
        val validate = updateProfile.validateEditProfile(""," ")
        assertThat(validate == "success")
    }
}