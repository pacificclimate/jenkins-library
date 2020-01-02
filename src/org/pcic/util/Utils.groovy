package org.pcic.util

import org.pcic.GitUtils
import org.pcic.DockerUtils


class Utils implements Serializable {

    Map processParams(Map expected, Map args) {
        def unknownKeys = (expected.keySet() + args.keySet()) - expected.keySet()

        if (unknownKeys.size() > 0) {
            throw new Exception("Key(s) ${unknownKeys} not available in ${expected.keySet()}")
        }

        return expected + args
    }

    String getBranchName() {
        String name

        if (BRANCH_NAME.contains('PR')) {
            name = CHANGE_BRANCH
        } else {
            name = BRANCH_NAME
        }

        return name
    }
}
