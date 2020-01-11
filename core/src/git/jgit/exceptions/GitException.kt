package inigo.gitgui.git.exceptions

import kotlin.Exception

class GitException: Exception{
    constructor(message: String?) : super(message)
}