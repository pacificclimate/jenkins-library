@Library('pcic-pipeline-library')_

node {
    stage('Code Collection') {
        collectCode()
    }

    stage('Run Tests') {
        sh 'gradle test'
    }
}
