package inigo.gitgui.git.main

import gitrunner.utils.runCommand


object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        /*"git status".runCommand()
        "git clone https://github.com/inigodm/bot.git".runCommand()
        Git.cloneRepository()
            .setURI("https://github.com/eclipse/jgit.git")
            .setProgressMonitor(TextProgressMonitor(PrintWriter(System.out)))
            .setDirectory(File(System.getProperty("user.home")+"/codel/gitgui/borrame"))
            .call();
    */
        "ls -lrt /bin".runCommand()
    }
}
