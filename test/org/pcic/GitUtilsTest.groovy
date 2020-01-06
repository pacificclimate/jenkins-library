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
    public void isCommitTagged_returnsList() {
        ArrayList expected_tags = ['1.0.0', 'some-tag']

        ArrayList result = gitUtils.isCommitTagged()

        assert result == expected_tags
    }
}
