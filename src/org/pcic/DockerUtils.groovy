package org.pcic

import org.pcic.util.Utils

class DockerUtils implements Serializable {
    def steps
    def utils

    DockerUtils(steps) {
        this.steps = steps
        this.utils = new Utils(steps)
    }

    void removeImage(String imageName) {
        steps.sh(script: "docker rmi ${imageName}")
    }

    String buildImageName(String suffix) {
        if (suffix.contains('/')) {
            throw new Exception("Image name suffix: ${suffix} cannot contain `/` character")
        }
        return steps.env.BASE_REGISTRY + suffix + ":" + utils.getBranchName()
    }

    ArrayList determineTags(String branchName, ArrayList gitTags) {
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

        if(!available.containsKey(project)) {
            throw new Exception("Key ${project} not available in ${available.keySet()}")
        }

        return available[project]
    }
}
