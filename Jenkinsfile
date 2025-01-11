pipeline {
    agent any

    environment {
        DOCKERHUB_USERNAME = 'yasinbk'  // Replace with your Docker Hub username
        DOCKERHUB_PASSWORD = credentials('dockerhub-credentials')  // Correct credential ID for Docker Hub password
        VM2_USER = 'vagrant'           // Replace with VM2 SSH user
        VM2_IP = '192.168.43.203'     // Replace with VM2 IP address
        VM2_APP_PATH = '~/app'        // Directory on VM2 to deploy
        DOCKER_CLI_EXPERIMENTAL = "enabled"
    }

    stages {
        stage('Checkout Code') {
            steps {
                git url: 'https://github.com/Yass-Bak/examen.git', branch: 'main'
            }
        }

        stage('Build Docker Images') {
            steps {
                sh 'docker-compose build'
            }
        }

        stage('Run Backend Tests') {
            steps {
                sh '''
                docker-compose up -d
                docker-compose exec -T backend  
                docker-compose down
                '''
            }
        }

        stage('Run Frontend Tests') {
            steps {
                sh '''
                docker-compose up -d
                docker-compose exec frontend 
                docker-compose down
                '''
            }
        }

        stage('Push Docker Images to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-credentials') {
                        sh 'docker-compose push'
                    }
                }
            }
        }

        stage('Deploy on VM2') {
            steps {
                sshagent(['vm2-ssh-credentials']) {
                    sh '''
                    # Create the app directory on the remote machine
                    ssh -o StrictHostKeyChecking=no $VM2_USER@$VM2_IP "mkdir -p $VM2_APP_PATH"
        
                    # Copy the docker-compose.yml file
                    scp docker-compose.yml $VM2_USER@$VM2_IP:$VM2_APP_PATH/
        
                    # Copy the backend and frontend directories
                    scp -r backend frontend $VM2_USER@$VM2_IP:$VM2_APP_PATH/
        
                    # Execute Docker Compose on the remote machine
                    ssh -o StrictHostKeyChecking=no $VM2_USER@$VM2_IP "
                        cd $VM2_APP_PATH &&
                        sudo docker-compose pull &&
                        sudo docker-compose up -d --build
                    "
                    '''
                }
            }
}
    }

    post {
        always {
                cleanWs()            
        }
    }
}
