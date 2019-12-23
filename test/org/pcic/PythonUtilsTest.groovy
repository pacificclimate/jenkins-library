package org.pcic

import util.MockSteps

import org.junit.Before
import org.junit.Test


public class PythonUtilsTest {
    PythonUtils pythonUtils

    @Before
    public void init() {
        MockSteps mockSteps = new MockSteps()
        this.pythonUtils = new PythonUtils(mockSteps)
    }

    @Test
    public void getPipString_python2() {
        // prepare
        int version = 2
        String expected = 'pip'

        // execute
        String result = pythonUtils.getPipString(version)

        // assert
        assert result == expected
    }

    @Test
    public void getPipString_python3() {
        // prepare
        int version = 3
        String expected = 'pip3'

        // execute
        String result = pythonUtils.getPipString(version)

        // assert
        assert result == expected
    }

    @Test
    public void test_test() {
        assert 1 == 2
    }
}
