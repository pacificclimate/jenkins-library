package org.pcic


class GitUtils implements Serializable {
    def steps

    GitUtils(steps) {
        this.steps = steps
    }

    ArrayList isCommitTagged() {
        String gitTags = steps.sh(script: 'git tag --contains', returnStdout: true)
        ArrayList retTags

        if (!gitTags.isEmpty()) {
            retTags = gitTags.trim().split('\n')
        } else {
            retTags = []
        }

        return retTags
    }

    void gitFetch() {
        steps.sh(script: 'git fetch')
    }
}
