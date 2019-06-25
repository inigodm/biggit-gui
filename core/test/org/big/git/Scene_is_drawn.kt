package org.big.git

import org.junit.Assert
import org.junit.Test

class app_works_when {
    @Test
    fun core_has_container_of_elements_to_be_drawn() {
        Assert.assertEquals(0, BigGitApp().drawables.size)
    }
}