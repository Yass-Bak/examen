pipeline {
    agent any

    environment {
        DOCKERHUB_USERNAME = 'yasinbk'   // Replace with your Docker Hub username
        DOCKERHUB_PASSWORD = credentials('dockerhub-credentials')  // Docker Hub credentials in Jenkins
        VM2_USER = 'vagrant'           // SSH user for the remote VM
        VM2_IP = '192.168.43.203'      // Remote VM IP address
        VM2_APP_PATH = '~/app'         // Directory on the remote VM
        DOCKER_CLI_EXPERIMENTAL = "enabled"
    }

    stages {
        stage('Checkout Code') {
            steps {
                git url: 'https://github.com/Yass-Bak/examen.git', branch: 'main'
            }
        }

        stage('Build Backend Docker Image') {
            steps {
                dir('backend') {
                    sh '''
                    docker build -t $DOCKERHUB_USERNAME/backend:$(git rev-parse --short HEAD) .
                    '''
                }
            }
        }

        stage('Build Frontend Docker Image') {
            steps {
                dir('frontend') {
                    sh '''
                    docker build -t $DOCKERHUB_USERNAME/frontend:$(git rev-parse --short HEAD) .
                    '''
                }
            }
        }

        stage('Push Docker Images to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-credentials') {
                        sh '''
                        docker push $DOCKERHUB_USERNAME/backend:$(git rev-parse --short HEAD)
                        docker push $DOCKERHUB_USERNAME/frontend:$(git rev-parse --short HEAD)
                        '''
                    }
                }
            }
        }

        stage('Deploy on VM2') {
            steps {
                sshagent(['vm2-ssh-credentials']) {
                    sh '''
                    # Create the application directory on the remote machine
                    ssh -o StrictHostKeyChecking=no $VM2_USER@$VM2_IP "mkdir -p $VM2_APP_PATH"

                    # Copy the docker-compose.yml file
                    scp docker-compose.yml $VM2_USER@$VM2_IP:$VM2_APP_PATH/

                    # Update docker-compose.yml to use images from Docker Hub
                    ssh -o StrictHostKeyChecking=no $VM2_USER@$VM2_IP "sed -i 's|context: ./backend|image: $DOCKERHUB_USERNAME/backend:$(git rev-parse --short HEAD)|' $VM2_APP_PATH/docker-compose.yml"
                    ssh -o StrictHostKeyChecking=no $VM2_USER@$VM2_IP "sed -i 's|context: ./frontend|image: $DOCKERHUB_USERNAME/frontend:$(git rev-parse --short HEAD)|' $VM2_APP_PATH/docker-compose.yml"

                    # Deploy the application on the remote VM
                    ssh -o StrictHostKeyChecking=no $VM2_USER@$VM2_IP "
                        cd $VM2_APP_PATH &&
                        sudo docker-compose pull &&
                        sudo docker-compose up -d
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
