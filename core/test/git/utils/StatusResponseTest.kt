package git.utils

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
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

        ))
    }
}

@ExtendWith(MockitoExtension::class)
class FilePermissionTest{
    @Test
    fun `should transfor an integer into permissions`(){
        assertAll(
                 { assertTrue(FilePermissions(6).read) },
                 { assertFalse(FilePermissions(3).read) },
                 { assertTrue(FilePermissions(2).write) },
                 { assertFalse(FilePermissions(5).write) },
                 { assertTrue(FilePermissions(5).execute) },
                 { assertFalse(FilePermissions(6).execute) }
        )
    }
}
