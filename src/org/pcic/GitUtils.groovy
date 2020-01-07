package org.pcic


class GitUtils implements Serializable {
    def steps

    GitUtils(steps) {
        this.steps = steps
    }

    ArrayList isCommitTagged() {
        String gitTags = steps.sh(script: 'git tag --contains', returnStdout: true)

        if (gitTags.isEmpty()) {
            return []
        }

        return gitTags.trim().split('\n')
    }

    void gitFetch() {
        steps.sh(script: 'git fetch')
    }
}
