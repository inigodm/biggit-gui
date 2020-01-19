package git.utils

class StatusResponse{
    var files = mutableListOf<FileInfo>();

    fun evaluate(line: String){
        val info = line.split("\\s+".toRegex())
        if (info.get(0) == "?") files.add(FileInfo(path = info.get(1)))
        if (info.get(0) == "!") files.add(FileInfo(path = info.get(1)))
        if (info.get(0) == "1") files.add(FileInfo(inHEAD = info.get(6), inIndex = info.get(7), path = info.get(8)))
        if (info.get(0) == "2") files.add(FileInfo(inHEAD = info.get(6), inIndex = info.get(7), path = info.get(9), previousPath = info.get(10), xScore=info.get(8)))
        if (info.get(0).toUpperCase() == "U") files.add(FileInfo(path = info.get(10)))
    }
}

/*var file = sr.files.get(0)
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
        }*/

fun buildVoidStatusResponse(): MutableMap<String, MutableSet<FileInfo>> {
    var res = mutableMapOf<String, MutableSet<FileInfo>>()
    res.put("added", mutableSetOf())
    res.put("changed", mutableSetOf())
    res.put("conflicting", mutableSetOf())
    res.put("ignoredNotInIndex", mutableSetOf())
    res.put("missing", mutableSetOf())
    res.put("modified", mutableSetOf())
    res.put("removed", mutableSetOf())
    res.put("uncommittedChanges", mutableSetOf())
    res.put("untracked", mutableSetOf())
    res.put("untrackedFolders", mutableSetOf())
    return res
}

data class FileInfo(
        val inHEAD: String = "",
        val inIndex: String = "",
        val path: String,
        val previousPath: String = "",
        val xScore: String = ""
        val octhead: FilePermissions = FilePermissions(""))

class FilePermissions(){
    constructor(octal: String) : this() {

    }
}
/*
Changed Tracked Entries

Following the headers, a series of lines are printed for tracked entries. One of three different line formats may be used to describe an entry depending on the type of change. Tracked entries are printed in an undefined order; parsers should allow for a mixture of the 3 line types in any order.

Ordinary changed entries have the following format:

1 <XY> <sub> <mH> <mI> <mW> <hH> <hI> <path>

Renamed or copied entries have the following format:

2 <XY> <sub> <mH> <mI> <mW> <hH> <hI> <X><score> <path><sep><origPath>

Field       Meaning
--------------------------------------------------------
<XY>        A 2 character field containing the staged and
	    unstaged XY values described in the short format,
	    with unchanged indicated by a "." rather than
	    a space.
<sub>       A 4 character field describing the submodule state.
	    "N..." when the entry is not a submodule.
	    "S<c><m><u>" when the entry is a submodule.
	    <c> is "C" if the commit changed; otherwise ".".
	    <m> is "M" if it has tracked changes; otherwise ".".
	    <u> is "U" if there are untracked changes; otherwise ".".
<mH>        The octal file mode in HEAD.
<mI>        The octal file mode in the index.
<mW>        The octal file mode in the worktree.
<hH>        The object name in HEAD.
<hI>        The object name in the index.
<X><score>  The rename or copy score (denoting the percentage
	    of similarity between the source and target of the
	    move or copy). For example "R100" or "C75".
<path>      The pathname.  In a renamed/copied entry, this
	    is the target path.
<sep>       When the `-z` option is used, the 2 pathnames are separated
	    with a NUL (ASCII 0x00) byte; otherwise, a tab (ASCII 0x09)
	    byte separates them.
<origPath>  The pathname in the commit at HEAD or in the index.
	    This is only present in a renamed/copied entry, and
	    tells where the renamed/copied contents came from.
--------------------------------------------------------

Unmerged entries have the following format; the first character is a "u" to distinguish from ordinary changed entries.

u <xy> <sub> <m1> <m2> <m3> <mW> <h1> <h2> <h3> <path>

Field       Meaning
--------------------------------------------------------
<XY>        A 2 character field describing the conflict type
	    as described in the short format.
<sub>       A 4 character field describing the submodule state
	    as described above.
<m1>        The octal file mode in stage 1.
<m2>        The octal file mode in stage 2.
<m3>        The octal file mode in stage 3.
<mW>        The octal file mode in the worktree.
<h1>        The object name in stage 1.
<h2>        The object name in stage 2.
<h3>        The object name in stage 3.
<path>      The pathname.
--------------------------------------------------------

Other Items

Following the tracked entries (and if requested), a series of lines will be printed for untracked and then ignored items found in the worktree.

Untracked items have the following format:

? <path>

Ignored items have the following format:

! <path>


 */