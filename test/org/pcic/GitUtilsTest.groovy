package org.pcic

import util.MockSteps

import org.junit.Before
import org.junit.Test


public class GitUtilsTest {
    GitUtils gitUtils

    @Before
    public void init() {
        MockSteps mockSteps = new MockSteps()
        this.gitUtils = new GitUtils(mockSteps)
    }

    @Test
    public void getCommitTags_returnsList() {
        ArrayList result = gitUtils.getCommitTags()

        assert result == ['1.0.0', 'some/tag']
    }
}
