package org.pcic


class GitHubUtils implements Serializable {
    def steps

    GitHubUtils(steps) {
        this.steps = steps
    }

    void setBuildStatus(String context, String message, String state) {
        steps.step([
            $class: "GitHubCommitStatusSetter",
            contextSource: [
              $class: "ManuallyEnteredCommitContextSource",
              context: context
            ],
            errorHandlers: [[
              $class: "ChangingBuildStatusErrorHandler",
              result: "UNSTABLE"
            ]],
            reposSource: [
                $class: "ManuallyEnteredRepositorySource",
                url: "https://github.com/${getRepo()}"
            ],
            statusResultSource: [
                $class: "ConditionalStatusResultSource",
                results: [[
                    $class: "AnyBuildResult",
                    message: message,
                    state: state
                ]]
            ]
        ])
    }

    private String getRepo() {
        def tokens = "${steps.env.JOB_NAME}".tokenize('/')
        def repo = tokens[tokens.size() - 2]
        return "pacificclimate/${repo}"
    }
}
