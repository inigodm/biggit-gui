package jgit

import inigo.gitgui.git.exceptions.GitException
import inigo.gitgui.git.jgit.JGit
import git.utils.runCommand
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import java.io.File
import java.io.StringWriter

@ExtendWith(MockitoExtension::class)
class JGitTest{
    lateinit var git: JGit
    var sw = StringWriter()

    @BeforeEach
    fun setup() {
        "rm -rf /home/inigo/borrame".runCommand()
        git = JGit()
    }

    @Test
    fun `clone must clone an URL into a directory`(){
        git.clone("https://github.com/inigodm/bot.git", "/home/inigo/borrame/bot")
        assertTrue(File("/home/inigo/borrame/bot").exists())
    }

    @Test
    fun  `init must initialize a new repository`(){
        git.init("/home/inigo/borrame")
        assertThat(File("/home/inigo/borrame/.git").exists())
    }

    @Test
    fun `open should throw a GitException when opening non existing repository`(){
        assertThrows<GitException> {
            git.open("/home/inigo/borrame")
        }
    }

    @Test
    fun `open should open existing repository`(){
        git.init("/home/inigo/borrame")
        git = JGit()
        git.open("/home/inigo/borrame")
    }

    @Test
    fun `status should show current status`(){
        git = JGit()
        git.open("/home/inigo/codel/gitgui/")
        val res = git.status()
        assertTrue(res.contains("gitgui"))
    }
}