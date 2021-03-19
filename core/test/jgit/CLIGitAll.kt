package gitrunner.cli

import com.google.gson.Gson
import gitrunner.utils.StatusResponse
import gitrunner.utils.buildVoidStatusResponse
import gitrunner.utils.runCommand
import inigo.gitgui.git.GitAll
import java.io.File
import java.io.OutputStream
import java.io.PrintStream
import java.util.function.Consumer

class CLIGitAll(var path: String,
             var consumer: Consumer<String> = Consumer { println(it) },
             var ps: PrintStream = System.out) : GitAll {

    override fun open(directory: String) {
        "git open $directory".runCommand()
    }

    override fun setOutStream(os: OutputStream) {
        ps = PrintStream(os);
        consumer = Consumer { ps.println(it) }
    }

    /* ----------------------------------------------------------- */
    /* SETUP & INIT Configuring user information, initializing and cloning repositories */
    override fun clone(URI: String, path: String, allBranches: Boolean) {
        "git clone $URI $path".runCommand(File(path))
    }

    override fun init(path: String) {
        "git init".runCommand(File(path))
    }

    /* ----------------------------------------------------------- */
    /* SETUP Configuring user information used across all local repositories 
        --global for all repositories in the system
        --local for current repository in dir
    */
    /* git --version
        get git current version
    */
    override fun version() {
        "git --version".runCommand(File(path))
    }

    /* git config --global user.name “[firstname lastname]”
        set a name that is identifiable for credit when review version history 
    */
    override fun config(firstname: String, lastname: String) {
        "git config --global user.name \"$firstname $lastname\"".runCommand(File(path))
    }
    
    /* git config --global user.email “[valid-email]”
        set an email address that will be associated with each history marker
    */
    override fun config(email: String) {
        "git config --global user.email \"$email\"".runCommand(File(path))
    }

    /* git config --global color.ui auto
        set automatic command line coloring for Git for easy reviewing
    */
    override fun config(autoColorUI: Boolean) {
        "git config --global color.ui auto".runCommand(File(path))
    }

    /* git config --global core.excludesfile [file]
        system wide ignore pattern for all local repositories
    */
    override fun config(excludesFile: String) {
        "git config --global core.excludesfile $excludesFile".runCommand(File(path))
    }

    /* git config --global --list
        list current global config
    */
    override fun config() {
        "git config --global --list".runCommand(File(path))
    }

    /* ----------------------------------------------------------- */
    /* STAGE & SNAPSHOT Working with snapshots and the Git staging area */
    /* git status
        show modified files in working directory, staged for your next commit
    */
    override override fun status(): String {
        val output = mutableListOf<String>()
        var res = buildVoidStatusResponse()
        try {
            "git status --porcelain=v2".runCommand(File(path), Consumer {  output.add(it); println("llega esto $output"); consumer.accept(it) })
            ps.flush()
        } catch (e: Exception) {
            e.message
            e.printStackTrace()
        }
        var sr = StatusResponse()
        output.forEach {
            it.substring(2, 4)
            sr.evaluate(it)

        }
        val gson = Gson()
        return gson.toJson(sr)
    }

    /* git add [file]
        add a file/directory as it looks now to your next commit (stage)
        use "." to add all local changes quickly
    */
    override fun add(file: String) {
        "git add $file".runCommand(File(path))
    }

    /* git reset [file]
        unstage a file while retaining the changes in working directory
    */
    override fun reset(file: String) {
        "git reset $file".runCommand(File(path))
    }
    
    /* git commit -m “[descriptive message]”
        commit your staged content as a new commit snapshot
    */
    override fun commit(message: String) {
        "git commit -m $message".runCommand(File(path))
    }

    /* git rm [file]
        delete the file from project and stage the removal for commit
    */
    override fun rm(file: String) {
        "git rm $file".runCommand(File(path))
    }

    /* git mv [existing-path] [new-path]
        change an existing file path and stage the move
    */
    override fun mv(existingPath: String, newPath: String) {
        "git mv $existingPath $newPath".runCommand(File(path))
    }

    /* ----------------------------------------------------------- */
    /* TEMPORARY COMMITS Temporarily store modified, tracked files  in order to change branches */
    enum class StashCommand(command: String) {
        PUSH(""), POP("pop"), DROP("drop"), LIST("list")
    }
    /* git stash
        Save modified and staged changes
       git stash list
        list stack-order of stashed file changes
       git stash pop
        write working from top of stash stack
       git stash drop
        discard  the changes from top of stash stack
    */
    override fun stash(command: StashCommand = PUSH) {
        "git stash " + command.value().runCommand(File(path))
    }

    /* ----------------------------------------------------------- */
    /* INSPECT & COMPARE Examining logs, diffs and object information
    /* git log
        show the commit history for the currently active branch
        --stat -M show all commit logs with indication of any paths that moved
    */ 
    override fun log(showMoved: Boolean = false) {
        "git log".runCommand(File(path))
    }
    
    /* git log branchB..branchA
        show the commits on branchA that are not on branchB
    */
    override fun log(branchB: String, branchA: String) {
        "git log $branchB..$branchA".runCommand(File(path))
    }
    
    /* git log --follow [file]
        show the commits that changed file, even across renames
    */
    override fun follow(file: String) {
        "git log --follow $file".runCommand(File(path))
    }

    /* git show [SHA]
        show any object in Git in human-readable format
    */
    override fun show() {
        "git show".runCommand(File(path))
    }

    /* git diff
        diff of what is changed but not staged 
        --staged diff of what is staged but not yet committed
    */
    override fun diff(staged: Boolean = false) {
        if (staged) {
            "git diff --staged".runCommand(File(path))
        } else {
            "git diff".runCommand(File(path))
        }
        
    }

    /* git diff branchB..branchA
        show the diff of what is in branchA that is not in branchB
    */
    override fun diff(branchB: String, branchA: String) {
        "git diff $branchB..$branchA".runCommand(File(path))
    }

    /* ----------------------------------------------------------- */
    /* SHARE & UPDATE Retrieving updates from another repository and updating local repos */
    /* git checkout [branch]
        switch to an existing branch, so you can work on it
    */
    override fun checkout(branch: String) {
        "git checkout $branch".runCommand(File(path))
    }

    /* git remote add [alias] [url]
        add a git URL as an alias
    */
    override fun alias(alias: String, URI: String) {
        "git remote add $alias $URI".runCommand(File(path))
    }
    
    /* git fetch [alias]
        fetch down all the branches from that Git remote
    */
    override fun fetch(alias: String) {
        "git fetch $alias".runCommand(File(path))
    }
    /* git merge [alias]/[branch]
        merge a remote branch into your current branch to bring it up to date
    */
    override fun merge(aliasBranch: String) {
        "git merge $aliasBranch".runCommand(File(path))
    }
    
    /* git push [alias] [branch]
        Transmit local branch commits to the remote repository branch
    */
    override fun push(alias: String, branch: String) {
        "git push $alias $branch".runCommand(File(path))
    }
    
    /* git pull
        fetch and merge any commits from the tracking remote branch
    */
    override fun pull() {
        "git pull".runCommand(File(path))
    }

    /* ----------------------------------------------------------- */
    /* REWRITE HISTORY Rewriting branches, updating commits and clearing history */
    /* git rebase [branch]
        apply any commits of current branch ahead of specified one
    */
    override fun rebase(branch: String) {
        "git rebase $branch".runCommand(File(path))
    }

    /* git reset --hard [commit]
        clear staging area, rewrite working tree from specified commit
    */
    override fun reset(commit: String, hard: Boolean = true) {
        if (hard) {
            "git reset --hard $commit".runCommand(File(path))    
        } else {
            "git reset $commit".runCommand(File(path))
        }
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
