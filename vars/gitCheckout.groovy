def call() {
    checkout scm
    sh 'git fetch'
}
