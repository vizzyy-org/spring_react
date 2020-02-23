#! groovy

currentBuild.displayName = "Home Pipeline [ " + currentBuild.number + " ]"

try {
    if (ISSUE_NUMBER)
        echo "Building from pull request..."
} catch (Exception e) {
    ISSUE_NUMBER = false
    echo "Building from jenkins job..."
}

pipeline {
    agent any
    stages {
        stage("Acknowledge") {
            steps {
                script {
                    if(env.Build == "true" && ISSUE_NUMBER) {
                        prTools.comment(ISSUE_NUMBER, """{"body": "Jenkins triggered $currentBuild.displayName"}""", "spring_react")
                    }
                }
            }
        }

        stage("Build") {
            steps {
                script {
                    if(env.Build == "true") {
                        prTools.checkoutBranch(ISSUE_NUMBER, "vizzyy-org/spring_react")

                        sh('''
                            cd src/main/frontend
                            npm install
                            cd ../../../
                            ./gradlew clean build
                        ''')
                    }
                }
            }
        }

        stage("Deploy") {
            steps {
                script {
                    if (env.Deploy == "true") {

                        //ec2 can only ssh through jumpbox
                        sh('''
                            echo "SCP file to remote server"
                            scp -i ~/ec2pair.pem build/libs/Home*.jar ec2-user@vizzyy.com:~/spring_react
                        ''')

                    }
                }
            }
        }

        stage("Start") {
                    steps {
                        script {
                            if (env.Deploy == "true") {

                                sh('''
                                    ssh -i ~/ec2pair.pem ec2-user@vizzyy.com 'sudo systemctl restart home'
                                ''')

                            }
                        }
                    }
                }
    }
    post {
        success {
            script {
                if(env.Build == "true" && ISSUE_NUMBER) {
                    prTools.merge(ISSUE_NUMBER, """{"commit_title": "Jenkins merged $currentBuild.displayName","merge_method": "merge"}""", "spring_react")
                    prTools.comment(ISSUE_NUMBER, """{"body": "Jenkins successfully deployed $currentBuild.displayName"}""" , "spring_react")
                }
            }
        }
        failure {
            script {
                if(env.Build == "true" && ISSUE_NUMBER) {
                    prTools.comment(ISSUE_NUMBER, """{"body": "Jenkins failed during $currentBuild.displayName"}""", "spring_react")
                }
            }
        }
    }
}