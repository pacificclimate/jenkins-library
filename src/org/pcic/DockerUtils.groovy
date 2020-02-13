package org.pcic

import org.pcic.util.Utils
import org.pcic.GitUtils
import org.pcic.util.Global


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

    String formatDockerString(String str, String replacement = '-') {
        for (String illegalChar : Global.constants.illegalDockerChars) {
            if (str.contains(illegalChar)) {
                str = str.replaceAll(illegalChar, replacement)
            }
        }

        return str
    }

    String buildImageName(String registry, String base, String tag) {
        return formatDockerString(registry) + '/' + formatDockerString(base) + ":" + formatDockerString(tag)
    }

    ArrayList getTags() {
        GitUtils gitUtils = new GitUtils(steps)
        String branchName = utils.getBranchName()
        ArrayList gitTags = gitUtils.getCommitTags()
        ArrayList tags = []

        if(utils.isPublishable(branchName, gitTags)) {
            tags.addAll(gitTags)
            tags.add('latest')
        }

        return tags
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
