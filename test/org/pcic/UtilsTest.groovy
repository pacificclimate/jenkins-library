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
        ArrayList keys = ['buildArgs', 'buildDocs', 'pipIndexUrl']
        Map args = [buildArgs: '.', pipIndexUrl: 'https://test.url.com']
        Map expected = [buildArgs: '.', buildDocs: false, pipIndexUrl: 'https://test.url.com']

        Map result = utils.getUpdatedArgs(keys, args)

        assert result == expected
    }

    @Test(expected = Exception.class)
    public void getUpdatedArgs_badList() {
        ArrayList keys = ['badKey']
        Map args = []

        Map result = utils.getUpdatedArgs(keys, args)
    }
}
