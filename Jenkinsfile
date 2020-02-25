#! groovy

import groovy.json.JsonSlurper

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
                    if (env.Build == "true" && ISSUE_NUMBER) {
                        prTools.comment(ISSUE_NUMBER, """{"body": "Jenkins triggered $currentBuild.displayName"}""", "spring_react")
                    }
                }
            }
        }

        stage("Build") {
            steps {
                script {
                    if (env.Build == "true") {
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

        stage("Confirm") {
            steps {
                script {
                    if (env.Deploy == "true") {

                        JsonSlurper jsonSlurper = new JsonSlurper()

                        for(int i=0; i<30; i++){

                            try {
                                def health = sh (
                                        script: 'curl --cert-type P12 --cert ~/home-cert.p12:changeit https://www.vizzyy.com/actuator/health',
                                        returnStdout: true
                                ).trim()
                                echo health
                                def result = jsonSlurper.parseText(health)
                                echo result
                                if (result.status == "UP")
                                    break
                                else
                                    String script = "sleep " + (1000 * i)
                                    echo script
                                    sh script
                            } catch ( Exception e) {
                                echo "could not parse"
                                e.printStackTrace()
                            }

                        }

                    }
                }
            }
        }

        stage("Version") {
            steps {
                script {
                    if (env.Build == "true") {

                        echo "This is where we'll increment the the version number in the gradle.build file."

                    }
                }
            }
        }
    }
    post {
        success {
            script {
                if (env.Build == "true" && ISSUE_NUMBER) {
                    prTools.merge(ISSUE_NUMBER, """{"commit_title": "Jenkins merged $currentBuild.displayName","merge_method": "merge"}""", "spring_react")
                    prTools.comment(ISSUE_NUMBER, """{"body": "Jenkins successfully deployed $currentBuild.displayName"}""", "spring_react")
                }
            }
        }
        failure {
            script {
                if (env.Build == "true" && ISSUE_NUMBER) {
                    prTools.comment(ISSUE_NUMBER, """{"body": "Jenkins failed during $currentBuild.displayName"}""", "spring_react")
                }
            }
        }
    }
}