pipeline {
    agent any
    stages {
         stage ('NPM Dependency Install')
         {
             steps
             {
                sh 'rm -R node_modules'
                sh 'npm install'
                echo '>>> NPM Install Successfully'   
             }
         }
         stage ('Unit Test')
         {
             steps
             {
                echo '>>> Unit Test Skiped Successfully'   
             }
         }
         stage ('Build')
         {
             steps
             {
                sh 'rm -R build && npm run build'
                echo '>>> Build Successfully'   
             }
         }
         stage ('Deploy')
         {
            steps
            {
                sh 'ls -a'
                sh 'cp -rfv /var/lib/jenkins/workspace/Login_Module_Pipeline/build/. /var/www/hackpro.jumpingcrab.com/html/'
                echo '>>> React.js Project Build files Moved to Nginx Server'
                echo '>>> Login UI Deployed Successfully'   
            }   
         }
    }
}