package org.pcic.util

import org.pcic.GitUtils
import org.pcic.DockerUtils


class Utils implements Serializable {
    def steps

    Utils(steps) {
        this.steps = steps
    }

    /**
     * This method takes in a list of keys and a map, and returns a map with
     * updates values.
     */
    Map getUpdatedArgs(ArrayList keys, Map argsToUpdate) {
        return updateArgs(getDefaultArgs(keys), argsToUpdate)
    }

    /**
     * Returns a submap given a list of keys
     */
    Map getDefaultArgs(ArrayList keys) {
        Map defaults = [buildArgs: '--pull .',
                        buildDocs: false,
                        containerData: 'default',
                        gitExecInstall: false,
                        registryUrl: '',
                        serverUri: steps.env.PCIC_DOCKER_DEV01,
                        pipIndexUrl:'https://pypi.pacificclimate.org/simple',
                        pypiUrl: 'https://pypi.pacificclimate.org/',
                        pythonVersion: 3]
        return defaults.subMap(keys)
    }

    /**
     * Takes two maps and returns the combination of those maps
     */
    Map updateArgs(Map defaults, Map updates) {
        // Check if there is anything to update
        if (updates.size() == 0) {
            return defaults
        }

        // Check that all keys in update map exist in default args
        def unknownKeys = updates.keySet() - defaults.keySet()
        if (unknownKeys.size() > 0) {
            throw new Exception("Key(s) ${unknownKeys} not available in ${expected.keySet()}")
        }

        // The way groovy handles this expression is that keys in both
        // `defaults` and `updates` will be updated to have the value from
        // `updates`.  Other key, value pairs are left as is.
        return defaults + updates
    }

    String getBranchName() {
        if (steps.env.BRANCH_NAME.contains('PR')) {
            return steps.env.CHANGE_BRANCH
        }

        return steps.env.BRANCH_NAME
    }

    boolean isPublishable(String branchName, ArrayList tags) {
        return (branchName == 'master' && !tags.isEmpty())
    }
}
