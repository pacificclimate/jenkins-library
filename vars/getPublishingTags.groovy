/**
 * If the master branch has been tagged we also add the `latest` tag.  Otherwise
 * we just use the branch name as the tag.
 *
 * @return tags a list of the tags for the image
 */
def ArrayList call() {
    String tag = sh (script: 'git tag --contains', returnStdout: true).trim()

    def tags = []
    if(BRANCH_NAME == 'master' && !tag.isEmpty()) {
        // It is possible for a commit to have multiple git tags. We want to
        // ensure we add all of them in.
        tags.addAll(tag.split('\n'))
        tags.add('latest')
    } else {
        String branch_name = get_branch_name()
        tags.add(branch_name)
    }

    return tags
}

/**
 * Get the original branch name.
 *
 * In the case where a branch has been filed as a PR the `BRANCH_NAME`
 * environment varible updates from `some-branch-name` to `PR-[pull request #]`.
 * To keep image tagging consistent on Docker Hub we want to use the original
 * name.
 *
 * @return name the name of the branch
 */
def get_branch_name() {
    String name
    if (BRANCH_NAME.contains('PR')) {
        name = CHANGE_BRANCH
    } else {
        name = BRANCH_NAME
    }

    return name
}
