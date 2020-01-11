package inigo.gitgui.git

interface Git{
    fun clone(URI: String, directory: String, allBranches: Boolean = false)
    fun init(directory: String)
    fun open(directory: String)
    fun status(): String
}