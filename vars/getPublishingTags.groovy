/**
 * If the master branch has been tagged we also add the `latest` tag.  Otherwise
 * we just use the branch name as the tag.
 *
 * @return dockerTags a list of the tags for the Docker image
 */
def ArrayList call() {
    String gitTags = sh(script: 'git tag --contains', returnStdout: true).trim()
    def dockerTags = []

    if(branchName == 'master' && !gitTags.isEmpty()) {
        // It is possible for a commit to have multiple git tags. We want to
        // ensure we add all of them in.
        dockerTags.addAll(gitTags.split('\n'))
        dockerTags.add('latest')
    } else {
        String branchName = getBranchName()
        dockerTags.add(branchName)
    }

    return dockerTags
}

/**
 * Get the original branch name.
 *
 * In the case where a branch has been filed as a PR the `branchName`
 * environment varible updates from `some-branch-name` to `PR-[pull request #]`.
 * To keep image tagging consistent on Docker Hub we want to use the original
 * name.
 *
 * @return name the name of the branch
 */
def String getBranchName() {
    String name

    if (branchName.contains('PR')) {
        name = CHANGE_BRANCH
    } else {
        name = branchName
    }

    return name
}
