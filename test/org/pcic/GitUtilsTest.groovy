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
        // prepare
        ArrayList expected_tags = ['1.0.0', 'some-tag']
        int expected_size = 2

        // execute
        ArrayList result = gitUtils.isCommitTagged()

        // assert
        assert result == expected_tags
        assert result.size() == expected_size
    }
}
