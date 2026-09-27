pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
        jdk 'JDK-17'
    }

    options {
        timestamps()
        timeout(time: 45, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }

    parameters {
        choice(
                name: 'BROWSER',
                choices: ['chrome', 'firefox'],
                description: 'Browser to run Selenium tests'
        )
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Environment Check') {
            steps {
                bat 'java -version'
                bat 'mvn -version'
                bat 'git --version'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean compile -DskipTests'
            }
        }

        stage('Run Tests') {
            steps {
                bat "mvn test -Dbrowser=${params.BROWSER} -DsuiteXmlFile=testng.xml"
            }
        }

        stage('Publish Reports') {
            steps {
                junit(
                        allowEmptyResults: true,
                        testResults: '**/target/surefire-reports/*.xml'
                )
            }
        }
    }

    post {
        always {
            archiveArtifacts(
                    artifacts: 'target/surefire-reports/**, screenshots/**, logs/**',
                    allowEmptyArchive: true
            )
        }

        success {
            echo 'Build and tests completed successfully.'
        }

        failure {
            echo 'Build or tests failed. Check the console output.'
        }
    }
}