def call(String node, String command) {
    nodejs(node) {
        sh 'npm install'
        sh "npm run ${command}"
    }
}
