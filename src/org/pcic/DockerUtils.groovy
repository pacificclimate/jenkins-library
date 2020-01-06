package org.pcic

import org.pcic.util.Utils

class DockerUtils implements Serializable {
    def steps

    DockerUtils(steps) {
        this.steps = steps
    }

    void removeImage(String imageName) {
        steps.sh(script: "docker rmi ${imageName}")
    }

    String buildImageName(String suffix) {
        return steps.env.BASE_REGISTRY + suffix
    }

    ArrayList determineTags(String branchName, ArrayList gitTags) {
        Utils utils = new Utils(steps)
        ArrayList publishTags = []

        if(utils.isPublishable(branchName, gitTags)) {
            publishTags.addAll(gitTags)
            publishTags.add('latest')
        } else {
            publishTags = [branchName]
        }

        return publishTags
    }

    String getContainerArgs(String project) {
        Map available = ['default': '-u root',
                         'crmprtd': '',
                         'pdp': '-u root --volumes-from pdp_data --env-file /storage/data/projects/comp_support/jenkins/pdp_envs/pdp_deployment.env']

        if(available.containsKey(project)) {
            return available[project]
        } else {
            throw new Exception("Key ${project} not available in ${available.keySet()}")
        }

    }
}
