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
    public void getUpdatedDefaultParams_correctList() {
        ArrayList keys = ['buildArgs', 'buildDocs', 'pipIndexUrl']
        Map params = [buildArgs: '.', pipIndexUrl: 'https://test.url.com']
        Map expected = [buildArgs: '.', buildDocs: false, pipIndexUrl: 'https://test.url.com']

        Map result = utils.getUpdatedDefaultParams(keys, params)

        assert result == expected
    }
}
