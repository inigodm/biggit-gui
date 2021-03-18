package inigo.gitgui.git

import java.io.OutputStream

interface GitAllCommands {
    fun open(directory: String)
    fun setOutStream(os: OutputStream)

    /* ----------------------------------------------------------- */
    /* SETUP & INIT Configuring user information, initializing and cloning repositories */
    fun clone(URI: String, directory: String, allBranches: Boolean = false)
    fun init(directory: String)

    /* ----------------------------------------------------------- */
    /* SETUP Configuring user information used across all local repositories 
        --global for all repositories in the system
        --local for current repository in dir
    */
    /* git --version
        get git current version
    */
    fun version()

    /* git config --global user.name “[firstname lastname]”
        set a name that is identifiable for credit when review version history 
    */
    fun config(firstname: String, lastname: String)
    
    /* git config --global user.email “[valid-email]”
        set an email address that will be associated with each history marker
    */
    fun config(email: String)

    /* git config --global color.ui auto
        set automatic command line coloring for Git for easy reviewing
    */
    fun config(autoColorUI: Boolean)

    /* git config --global core.excludesfile [file]
        system wide ignore pattern for all local repositories
    */
    fun config(excludesFile: String)

    /* git config --global --list
        list current global config
    */
    fun config()


    /* ----------------------------------------------------------- */
    /* STAGE & SNAPSHOT Working with snapshots and the Git staging area */
    /* git status
        show modified files in working directory, staged for your next commit
    */
    fun status(): String

    /* git add [file]
        add a file/directory as it looks now to your next commit (stage)
        use "." to add all local changes quickly
    */
    fun add(file: String)

    /* git reset [file]
        unstage a file while retaining the changes in working directory
    */
    fun reset(file: String)
    
    /* git commit -m “[descriptive message]”
        commit your staged content as a new commit snapshot
    */
    fun commit(message: String)    

    /* git rm [file]
        delete the file from project and stage the removal for commit
    */
    fun rm(file: String)

    /* git mv [existing-path] [new-path]
        change an existing file path and stage the move
    */
    fun mv(existingPath: String, newPath: String)

    /* ----------------------------------------------------------- */
    /* TEMPORARY COMMITS Temporarily store modified, tracked files  in order to change branches */
    enum class StashCommand {
        PUSH, POP, DROP, LIST
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
    fun stash(command: StashCommand = PUSH)

    /* ----------------------------------------------------------- */
    /* INSPECT & COMPARE Examining logs, diffs and object information
    /* git log
        show the commit history for the currently active branch
        --stat -M show all commit logs with indication of any paths that moved
    */ 
    fun log(showMoved: Boolean = false)
    
    /* git log branchB..branchA
        show the commits on branchA that are not on branchB
    */
    fun log(branchB: String, branchA: String)
    
    /* git log --follow [file]
        show the commits that changed file, even across renames
    */
    fun follow(file: String)

    /* git show [SHA]
        show any object in Git in human-readable format
    */
    fun show()

    /* git diff
        diff of what is changed but not staged 
        --staged diff of what is staged but not yet committed
    */
    fun diff(staged: Boolean = false)

    /* git diff branchB...branchA
        show the diff of what is in branchA that is not in branchB
    */
    fun diff(branchB: String, branchA: String)

    /* ----------------------------------------------------------- */
    /* SHARE & UPDATE Retrieving updates from another repository and updating local repos */
    /* git checkout [branch]
        switch to an existing branch, so you can work on it
    */
    fun checkout()

    /* git remote add [alias] [url]
        add a git URL as an alias
    */
    fun alias(URI: String, alias: String)
    
    /* git fetch [alias]
        fetch down all the branches from that Git remote
    */
    fun fetch(alias: String)
    
    /* git merge [alias]/[branch]
        merge a remote branch into your current branch to bring it up to date
    */
    fun merge(alias: String, branch:String)
    
    /* git push [alias] [branch]
        Transmit local branch commits to the remote repository branch
    */
    fun push(alias: String, branch: String)
    
    /* git pull
        fetch and merge any commits from the tracking remote branch
    */
    fun pull()

    /* ----------------------------------------------------------- */
    /* REWRITE HISTORY Rewriting branches, updating commits and clearing history */
    /* git rebase [branch]
        apply any commits of current branch ahead of specified one
    */
    fun rebase()

    /* git reset --hard [commit]
        clear staging area, rewrite working tree from specified commit
    */
    fun reset(commit: String, hard: Boolean = true)

}
