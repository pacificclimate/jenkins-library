package util


class MockSteps implements Serializable {
    Map env

    MockSteps() {
        this.env = [PCIC_DOCKER_DEV01: 'used-for-testing',
                    BRANCH_NAME: 'PR-100',
                    CHANGE_BRANCH: 'change-branch-name']
    }

    /**
     * Mocks the behaviour of `sh`
     */
    def sh(Map args) {
        // Return some tags
        if(args.script == 'git tag --contains' && args.containsKey('returnStdout')) {
            return "1.0.0\nsome-tag"
        }
    }
}
