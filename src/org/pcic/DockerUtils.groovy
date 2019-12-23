package org.pcic


class DockerUtils implements Serializable {
    def steps

    DockerUtils(steps) {
        this.steps = steps
    }

    void removeImage(String imageName) {
        steps.sh(script: "docker rmi ${imageName}")
    }

    ArrayList determineTags(String branchName, ArrayList gitTags) {
        ArrayList publishTags = []

        if(!gitTags.isEmpty() && branchName == 'master') {
            publishTags.addAll(gitTags)
            publishTags.add('latest')
        } else {
            publishTags = [branchName]
        }

        return publishTags
    }

    String getContainerArgs(String project) {
        Map available = ['default': '-u root',
                         'pdp': '-u root --volumes-from pdp_data --env-file /storage/data/projects/comp_support/jenkins/pdp_envs/pdp_deployment.env']
        String args
        if(available.containsKey(project)) {
            args = available[project]
        } else {
            throw new Exception("Key ${project} not available in ${available.keySet()}")
        }

        return args
    }
}
