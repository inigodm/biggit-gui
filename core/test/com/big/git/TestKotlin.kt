package com.big.git

import org.big.git.BigGitApp
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

class TestKotlin{

    var bga: BigGitApp? = null

    @Before
    fun init(){
        bga = BigGitApp()
    }

    @Test
    fun junit_working(){
        assertTrue("siu" == "siu")
        assertFalse(bga == null)
    }
}