package inigo.gitgui.git.cli

import com.google.gson.Gson
import inigo.gitgui.git.Git
import inigo.gitgui.git.utils.buildFileInfo
import inigo.gitgui.git.utils.buildVoidStatusResponse
import inigo.gitgui.git.utils.runCommand
import java.io.File
import java.io.OutputStream
import java.io.PrintStream
import java.util.function.Consumer

class CLIGit(var path: String, var consumer: Consumer<String> = Consumer { println(it) }) : Git{
    lateinit var ps: PrintStream

    override fun clone(URI: String, directory: String, allBranches: Boolean) {
        "git clone $URI $directory".runCommand(workingDir = File(directory))
        path = directory
    }

    override fun init(directory: String) {
        "git init".runCommand(workingDir = File(path))
    }

    override fun open(directory: String) {
        "git open $directory".runCommand()
    }

    override fun status(): String {
        val output = mutableListOf<String>()
        var res = buildVoidStatusResponse()
        try {
            "git status --porcelain=v2".runCommand(workingDir = File(path), consumer = Consumer { output.add(it); consumer.accept(it) })
            ps.flush()
        }catch (e: Exception){
            e.message
            e.printStackTrace()
        }
        output.forEach {
            val clas = it.substring(2, 4)
            val file = buildFileInfo(it)
            when(clas){
                " M", "M " -> res.get("modified")!!.add(file)
                "AM", "A " -> res.get("added")!!.add(file)
                "??" -> if (it.endsWith("/")){
                            res.get("untrackeddirectory")!!.add(file)
                        }else{
                            res.get("untracked")!!.add(file)
                        }
                "RM", "R " -> {
                        res.get("removed")!!.add(file)
                        res.get("added")!!.add(file)
                        res.get("modified")!!.add(file)
                        }
                "!!" -> res.get("ignored")!!.add(file)
            }
            if (clas.substring(1,2).equals(" ")) res.get("staged")!!.add(file)
        }
        println(output)
        val gson = Gson()
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