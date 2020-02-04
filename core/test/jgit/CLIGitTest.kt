package jgit

import com.google.gson.Gson
import gitrunner.utils.FileInfo
import gitrunner.utils.StatusResponse
import inigo.gitgui.git.cli.CLIGit
import inigo.gitgui.git.exceptions.GitException
import gitrunner.utils.runCommandSync
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File
import java.io.StringWriter

class CLICLIGitTest{
    lateinit var git: CLIGit
    var path = File("/home/inigo/borrame/")

    @BeforeEach
    fun setup() {
        "rm -rf /home/inigo/borrame".runCommandSync()
        "mkdir /home/inigo/borrame".runCommandSync()
        git = CLIGit("/home/inigo/borrame")
    }

    @Test
    fun `clone must clone an URL into a directory`(){
        "cp -r /home/inigo/codel/bot /home/inigo/borrame".runCommandSync()
        assertTrue(File("/home/inigo/borrame/bot").exists())
    }

    @Test
    fun  `init must initialize a new repository`(){
        git.init("/home/inigo/borrame")
        assertThat(File("/home/inigo/borrame/.git").exists())
    }

    @Test
    fun `open should open existing repository`(){
        git.init("/home/inigo/borrame")
        git = CLIGit("/home/inigo/borrame")
    }

    @Test
    fun `Should obtain info about untracked file and its state and path`(){
        git.init("/home/inigo/borrame")
        "touch /home/inigo/borrame/touched".runCommandSync()
        val res = (Gson()).fromJson(git.status(), StatusResponse::class.java)
        assertThat(res.files).contains(FileInfo(state = "?", path = "touched"))
    }

    @Test
    fun `Should obtain info about new added file have`(){
        git.init("/home/inigo/borrame")
        "touch touched".runCommandSync(path)
        "ls -lrt".runCommandSync(path)
        "git add touched".runCommandSync(path)
        "git status".runCommandSync(path)
        "pwd".runCommandSync()
        val res = (Gson()).fromJson(git.status(), StatusResponse::class.java)
        println("files " +res.files)
        assertThat(res.files).contains(FileInfo(inHEAD = "0000000000000000000000000000000000000000",
                inIndex = "e69de29bb2d1d6434b8b29ae775ad8c2e48c5391",
                path = "touched",
                state = "A."))
    }

    @Test
    fun `should transform log response`(){
        git = CLIGit("/home/inigo/codel/biggit-gui")
        git.log()
    }
}
