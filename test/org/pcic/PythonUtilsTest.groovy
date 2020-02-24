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
    public void applyPythonVersion_python2() {
        int version = 2
        String expected = 'pip'

        String result = pythonUtils.applyPythonVersion(expected, version)

        assert result == expected
    }

    @Test
    public void applyPythonVersion_python3() {
        int version = 3
        String expected = 'pip3'

        String result = pythonUtils.applyPythonVersion('pip', version)

        assert result == expected
    }
}
