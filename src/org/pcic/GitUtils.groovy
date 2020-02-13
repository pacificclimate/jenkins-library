package org.pcic


class GitUtils implements Serializable {
    def steps

    GitUtils(steps) {
        this.steps = steps
    }

    ArrayList getCommitTags() {
        String tags = steps.sh(script: 'git tag --contains', returnStdout: true)

        if (tags.isEmpty()) {
            return []
        }

        return tags.trim().split('\n')
    }

    void gitFetch() {
        steps.sh(script: 'git fetch')
    }
}
