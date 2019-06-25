package org.big.git

import org.big.git.BigGitApp
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*

class TestKotlin{

    var bga: BigGitApp? = null

    @Before
    fun init(){
        bga = spy(BigGitApp::class.java)
    }

    @Test
    fun junit_working(){
        assertTrue("siu" == "siu")
        assertFalse(bga == null)
    }

    @Test
    fun mockito_working(){
        var t = spy(T::class.java)
        t.foo("lala")
        verify(t, only()).foo(anyString())
    }

}

class T{
    fun foo(fo: String){
        println(fo)
    }
}