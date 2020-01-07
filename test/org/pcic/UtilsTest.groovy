package org.pcic.util

import util.MockSteps

import org.junit.Before
import org.junit.Test


public class UtilsTest {
    Utils utils

    @Before
    public void init() {
        MockSteps mockSteps = new MockSteps()
        this.utils = new Utils(mockSteps)
    }

    @Test
    public void getUpdatedArgs_correctList() {
        ArrayList keys = ['buildArgs', 'serverUri', 'pythonVersion']
        Map args = [buildArgs: '.', pythonVersion: 2]
        Map expected = [buildArgs: '.', serverUri: 'used-for-testing',
                        pythonVersion: 2]

        Map result = utils.getUpdatedArgs(keys, args)

        assert result == expected
    }

    @Test
    public void getDefaultArgs_badKey() {
        Map result = utils.getDefaultArgs(['badKey'])

        assert result == [:]
    }

    @Test
    public void updateArgs_noUpdates() {
        Map expected = [key: 'value']

        Map result = utils.updateArgs(expected, [:])

        assert result == expected
    }

    @Test(expected = Exception.class)
    public void updateArgs_badKey() {
        utils.updateArgs([key: 'value'], [badKey: 'newValue'])
    }

    @Test
    public void getBranchName_changeBranch() {
        assert utils.getBranchName() == 'change-branch-name'
    }

    @Test
    public void isPublishable_true() {
        assert utils.isPublishable('master', ['tag']) == true
    }

    @Test
    public void isPublishable_false() {
        assert utils.isPublishable('badBranch', []) == false
    }

    @Test
    public void getExceptionType_returnString() {
        Exception e = new Exception('java.lang.Exception: some test exception')
        assert utils.getExceptionType(e) == 'java.lang.Exception'
    }
}
