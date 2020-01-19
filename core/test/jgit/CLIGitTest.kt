package jgit

import inigo.gitgui.git.cli.CLIGit
import inigo.gitgui.git.exceptions.GitException
import git.utils.runCommand
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File
import java.io.StringWriter

class CLICLIGitTest{
    lateinit var git: CLIGit
    var sw = StringWriter()

    @BeforeEach
    fun setup() {
        "rm -rf /home/inigo/borrame".runCommand()
        "mkdir /home/inigo/borrame".runCommand()
        git = CLIGit("/home/inigo/borrame")
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
    fun `open should throw a CLIGitException when opening non existing repository`(){
        assertThrows<GitException>{
            git.open("/home/inigo/borrame")
        }
    }

    @Test
    fun `open should open existing repository`(){
        git.init("/home/inigo/borrame")
        git = CLIGit("/home/inigo/borrame")
    }

    @Test
    fun `status should show current status`(){
        git = CLIGit("/home/inigo/borrame")
        //git.clone("https://github.com/inigodm/bot.git", "/home/inigo/borrame/bot")
        git.init("/home/inigo/borrame")
        "touch /home/inigo/borrame/touched".runCommand()
        val res = git.status()
        assertTrue(res.contains("touched"))
    }
}