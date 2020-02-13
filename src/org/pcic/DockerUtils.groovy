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

    void removeImage(String image) {
        steps.sh(script: "docker rmi ${image}")
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

        def formatted = []
        for (String tag : tags) {
            formatted.add(formatDockerString(tag))
        }
        
        return formatted
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

    String getImageId(String imageName) {
        return steps.sh(script: "docker images --filter=reference=${imageName} --format '{{.ID}}'", returnStdout: true).trim()
    }

    ArrayList getDeletableImages(imageName) {
        ArrayList tags = getTags()
        def matcher = imageName =~ /.*\/.*:/
        String imageBase = matcher[0]
        ArrayList deletable = [imageName]

        for (String tag : tags) {
            deletable.add(imageBase + tag)
        }

        return deletable
    }
}
