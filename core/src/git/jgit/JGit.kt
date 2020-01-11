package inigo.gitgui.git.jgit

import com.google.gson.Gson
import inigo.gitgui.git.exceptions.GitException
import inigo.gitgui.git.utils.FileInfo
import inigo.gitgui.git.utils.buildVoidStatusResponse
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.errors.RepositoryNotFoundException
import org.eclipse.jgit.lib.TextProgressMonitor
import java.io.File
import java.io.PrintWriter
import java.io.Writer

class JGit(var writer: Writer = PrintWriter(System.out)): inigo.gitgui.git.Git{
    lateinit var git: Git

    override fun clone(URI: String, directory: String, allBranches: Boolean) {
        writer.appendln("Cloning $URI into $directory")
        git = Git.cloneRepository()
            .setURI(URI)
            .setDirectory(File(directory))
            .setCloneAllBranches(allBranches)
            .setProgressMonitor(TextProgressMonitor(writer))
            .call();
    }

    override fun init(directory: String){
        git = Git.init().setDirectory(File(directory)).call()
    }

    override fun open(directory: String){
        try {
            git = Git.open(File(directory))
        }catch (e: RepositoryNotFoundException){
            throw GitException(e.message)
        }
    }

    override fun status(): String {
        val status = git.status()
            .setProgressMonitor(TextProgressMonitor(writer))
            .call()
        var res = buildVoidStatusResponse()
        status.added.forEach { res.get("added")!!.add(FileInfo(path=it)) }
        status.changed.forEach { res.get("changed")!!.add(FileInfo(path=it)) }
        status.conflicting.forEach { res.get("conflicting")!!.add(FileInfo(path=it)) }
        status.ignoredNotInIndex.forEach { res.get("ignoredNotInIndex")!!.add(FileInfo(path=it)) }
        status.missing.forEach { res.get("missing")!!.add(FileInfo(path=it)) }
        status.modified.forEach { res.get("modified")!!.add(FileInfo(path=it)) }
        status.uncommittedChanges.forEach { res.get("uncommittedChanges")!!.add(FileInfo(path=it)) }
        status.untracked.forEach { res.get("untracked")!!.add(FileInfo(path=it)) }
        status.untrackedFolders.forEach { res.get("untrackedFolders")!!.add(FileInfo(path=it)) }
        return Gson().toJson(res)
    }
}