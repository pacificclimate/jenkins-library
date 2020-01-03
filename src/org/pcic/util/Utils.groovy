package org.pcic.util

import org.pcic.GitUtils
import org.pcic.DockerUtils


class Utils implements Serializable {
    def steps

    Utils(steps) {
        this.steps = steps
    }

    Map processParams(Map expected, Map args) {
        def unknownKeys = (expected.keySet() + args.keySet()) - expected.keySet()

        if (unknownKeys.size() > 0) {
            throw new Exception("Key(s) ${unknownKeys} not available in ${expected.keySet()}")
        }

        return expected + args
    }

    String getBranchName() {
        String name

        if (steps.env.BRANCH_NAME.contains('PR')) {
            name = steps.env.CHANGE_BRANCH
        } else {
            name = steps.env.BRANCH_NAME
        }

        return name
    }

    boolean isPublishable(ArrayList tags) {
        return (steps.env.BRANCH_NAME == 'master' && !tags.isEmpty())
    }
}
