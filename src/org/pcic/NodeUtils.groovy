package org.pcic


class NodeUtils implements Serializable {
    def steps

    NodeUtils(steps) {
        this.steps = steps
    }

    void installAndRun(String command) {
        steps.sh(script: 'npm install')
        steps.sh(script: "npm run ${command}")
    }
}
