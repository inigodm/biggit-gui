package org.big.git

import org.big.git.BigGitApp
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class TestKotlin{

    var bga: BigGitApp? = null

    @BeforeEach
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
        verify(t, times(1)).foo(anyString())
    }

}

class T{
    fun foo(fo: String){
        println(fo)
    }
}