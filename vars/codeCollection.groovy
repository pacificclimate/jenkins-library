/**
 * Perform a checkout for code and a fetch for tags
 */
def call() {
    checkout scm
    sh 'git fetch'
}
