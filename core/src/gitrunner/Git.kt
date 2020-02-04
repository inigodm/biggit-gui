package inigo.gitgui.git

import java.io.OutputStream

interface Git{
    fun clone(URI: String, directory: String, allBranches: Boolean = false)
    fun init(directory: String)
    fun open(directory: String)
    fun status(): String
    fun setOutStream(os: OutputStream)
    fun log()
}
