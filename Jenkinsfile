#! groovy
import java.security.InvalidParameterException
import java.util.logging.Logger

Logger logger = Logger.getLogger('vizzyy.jenkins.deploy')
currentBuild.displayName = "Home Pipeline [ " + currentBuild.number + " ]"


try {
    if (ISSUE_NUMBER)
        echo "Building from pull request..."
} catch (Exception e) {
    ISSUE_NUMBER = false
    echo "Building from jenkins job..."
}

String getVersion(){
    def versionNumber = sh (
            script: 'cat build.gradle | grep "version = "',
            returnStdout: true
    ).trim()

    return versionNumber.substring(10)
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

                        currentVersion = getVersion()

                        //ec2 can only ssh through jumpbox
                        sh("""
                            echo "SCP file to remote server"
                            scp -i ~/ec2pair.pem build/libs/Home*.jar ec2-user@vizzyy.com:~/spring_react
                            ssh -i ~/ec2pair.pem ec2-user@vizzyy.com 'ln -sf ~/spring_react/Home-"""+currentVersion+""".jar ~/spring_react/home.jar'
                        """)

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
//                        for(int i=0; i<12; i++){
//
//                            try {
//                                def health = sh (
//                                        script: 'curl --cert-type P12 --cert ~/home-cert.p12:changeit https://www.vizzyy.com/actuator/health',
//                                        returnStdout: true
//                                ).trim()
//                                echo health
//                                if (health == "{\"status\":\"UP\"}"){
//                                    deployed = true
//                                    break
//                                }
//                            } catch ( Exception e) {
//                                echo "could not parse"
//                                e.printStackTrace()
//                            }
//
//                            sleep time: i, unit: 'SECONDS'
//
//                        }
//
//                        if(!deployed)
//                            throw new InvalidParameterException()

                    }
                }
            }
        }

        stage("Version") {
            steps {
                script {
                    if (env.Build == "true") {

                        versionNumber = getVersion()
                        echo versionNumber.toString()
                        versions = versionNumber.split("[.-]")
                        echo versions[0]
                        echo versions[1]
                        echo versions[2]
                        echo versions[3]
                        System.out.println(versions)
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