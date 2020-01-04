package org.pcic.util

import org.pcic.GitUtils
import org.pcic.DockerUtils


class Utils implements Serializable {
    def steps

    Utils(steps) {
        this.steps = steps
    }

    Map getUpdatedDefaultParams(ArrayList defaultKeys, Map optionalParams) {
        return updateDefaultArgs(getApplicableParams(defaultKeys), optionalParams)
    }

    Map getApplicableParams(ArrayList keys) {
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

    Map updateDefaultArgs(Map defaults, Map updates) {
        if (updates.size() == 0) {
            return defaults
        }

        def unknownKeys = updates.keySet() - defaults.keySet()
        if (unknownKeys.size() > 0) {
            throw new Exception("Key(s) ${unknownKeys} not available in ${expected.keySet()}")
        }

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
