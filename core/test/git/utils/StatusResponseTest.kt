package git.utils

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class StatusResponseTest{

    @Test
    fun `should_obtain_info_when_untracked_file`(){
        var line = "? kk"
        var sr = StatusResponse()
        sr.evaluate(line)
        assertThat(sr.files).containsOnly(FileInfo(path="kk"))
    }

    @Test
    fun `should_obtain_info_when_ignored_file`(){
        var line = "! kk"
        var sr = StatusResponse()
        sr.evaluate(line)
        assertThat(sr.files).containsOnly(FileInfo(path="kk"))
    }

    @Test
    fun `should_obtain_info_when_unmerged_file`(){
        var line = "u .M N... 100644 100644 100644 100644 n1 n2 n3 kk"
        var sr = StatusResponse()
        sr.evaluate(line)
        assertThat(sr.files).containsOnly(FileInfo(path="kk"))
    }

    @Test
    fun `should_obtain_info_when_ordinary_changed_files`(){
        var line = "1 .M N... 100644 100644 100644 1886f3008faf9c94129829e5c07a98d20ccdffe6 1886f3008faf9c94129829e5c07a98d20ccdffe6 core/src/git/cli/CLIGit.kt"
        var sr = StatusResponse()
        sr.evaluate(line)
        assertThat(sr.files).containsOnly(FileInfo(
                path="core/src/git/cli/CLIGit.kt",
                inHEAD="1886f3008faf9c94129829e5c07a98d20ccdffe6",
                inIndex="1886f3008faf9c94129829e5c07a98d20ccdffe6"
        ))
    }

    @Test
    fun `should_obtain_info_when_renamed_or_copied_files`(){
        var line = "2 R. N... 100644 100644 100644 e69de29bb2d1d6434b8b29ae775ad8c2e48c5391 e69de29bb2d1d6434b8b29ae775ad8c2e48c5391 R100 kk        kaka"
        var sr = StatusResponse()
        sr.evaluate(line)
        assertThat(sr.files).containsOnly(FileInfo(
                path="kk",
                inHEAD="e69de29bb2d1d6434b8b29ae775ad8c2e48c5391",
                inIndex="e69de29bb2d1d6434b8b29ae775ad8c2e48c5391",
                xScore="R100",
                previousPath="kaka"
        )
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