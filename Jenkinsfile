@Library('pcic-pipeline-library')_

node {
    stage('Code Collection') {
        collectCode()
    }

    stage('Run Tests') {
        def gradleHome = tool 'gradle'
        sh "'${gradleHome}/bin/gradle' test"
    }
}
