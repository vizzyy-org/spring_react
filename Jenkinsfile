#! groovy
import java.security.InvalidParameterException

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

                        def deployed = false
                        for(int i=0; i<10; i++){

                            try {
                                def health = sh (
                                        script: 'curl --cert-type P12 --cert ~/home-cert.p12:changeit https://www.vizzyy.com/actuator/health',
                                        returnStdout: true
                                ).trim()
                                echo health
                                if (health == "{\"status\":\"UP\"}"){
                                    deployed = true
                                    break
                                }
                            } catch ( Exception e) {
                                echo "could not parse"
                                e.printStackTrace()
                            }

                            sleep time: i, unit: 'SECONDS'

                        }

                        if(!deployed)
                            throw new InvalidParameterException()

                    }
                }
            }
        }

        stage("Version") {
            steps {
                script {
                    if (env.Build == "true") {

                        def versionNumber = sh (
                                script: 'cat build.gradle | grep "version = "',
                                returnStdout: true
                        ).trim()
                        versions = versionNumber.split(".|-")
                        newMinor = Integer.getInteger(versions[2]) + 1
                        newVersion = "version = '"+versions[0]+"."+versions[1]+"."+newMinor+"-SNAPSHOT'"
                        sh """
                            sed -E s/[[:digit:]]+\\.[[:digit:]]+\\.[[:digit:]]+-SNAPSHOT/$newVersion/g build.gradle > build.gradle
                            git commit -am "Jenkins incremented build version."
                            git push origin master
                        """

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