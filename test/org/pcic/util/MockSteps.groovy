package util


class MockSteps implements Serializable {

    def sh(Map args) {
        def retVal

        if(args.script == 'git tag --contains' && args.containsKey('returnStdout')) {
            retVal = "1.0.0\nsome-tag"
        }

        return retVal
    }
}
