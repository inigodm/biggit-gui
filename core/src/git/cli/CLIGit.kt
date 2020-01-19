package inigo.gitgui.git.cli

import com.google.gson.Gson
import git.utils.StatusResponse
import inigo.gitgui.git.Git
import git.utils.buildVoidStatusResponse
import git.utils.runCommand
import java.io.File
import java.io.OutputStream
import java.io.PrintStream
import java.util.function.Consumer

class CLIGit(var path: String, var consumer: Consumer<String> = Consumer { println(it) }) : Git{
    lateinit var ps: PrintStream

    override fun clone(URI: String, directory: String, allBranches: Boolean) {
        path = directory
        "git clone $URI $path".runCommand(File(path))
    }

    override fun init(directory: String) {
        "git init".runCommand(File(path))
    }

    override fun open(directory: String) {
        "git open $directory".runCommand()
    }

    override fun status(): String {
        val output = mutableListOf<String>()
        var res = buildVoidStatusResponse()
        try {
            "git status --porcelain=v2".runCommand(File(path), Consumer { output.add(it); consumer.accept(it) })
            ps.flush()
        } catch (e: Exception) {
            e.message
            e.printStackTrace()
        }
        var sr = StatusResponse()
        output.forEach {
            val clas = it.substring(2, 4)
            sr.evaluate(it)

        }
        println(output)
        val gson = Gson()
        println(gson.to(res))
        return gson.toJson(res)
    }

    override fun setOutStream(os: OutputStream) {
        ps = PrintStream(os);
        consumer = Consumer { ps.println(it) }
    }
}

/*
X          Y     Meaning
-------------------------------------------------
	 [AMD]   not updated
M        [ MD]   updated in index
A        [ MD]   added to index
D                deleted from index
R        [ MD]   renamed in index
C        [ MD]   copied in index
[MARC]           index and work tree matches
[ MARC]     M    work tree changed since index
[ MARC]     D    deleted in work tree
[ D]        R    renamed in work tree
[ D]        C    copied in work tree
-------------------------------------------------
D           D    unmerged, both deleted
A           U    unmerged, added by us
U           D    unmerged, deleted by them
U           A    unmerged, added by them
D           U    unmerged, deleted by us
A           A    unmerged, both added
U           U    unmerged, both modified
-------------------------------------------------
?           ?    untracked
!           !    ignored
-------------------------------------------------
 */