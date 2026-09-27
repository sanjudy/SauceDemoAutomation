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

        stage('Clear WebDriver Cache') {
            steps {
                bat '''
                    if exist "%LOCALAPPDATA%\\selenium" (
                        echo Found existing Selenium cache, clearing it...
                        rmdir /s /q "%LOCALAPPDATA%\\selenium"
                        echo Cache cleared successfully.
                    ) else (
                        echo No existing Selenium cache found, skipping.
                    )
                    exit /b 0
                '''
            }
        }

        stage('Run Tests') {
            steps {
                bat "mvn test -Dbrowser=${params.BROWSER} -DsuiteXmlFile=src/test/resources/testng.xml"
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