pipeline {
    agent any

    tools {
        maven '3.9'
    }

    environment {
        DOCKER_IMAGE       = 'jimmynow/uniservicios'
        DOCKER_TAG         = "${BUILD_NUMBER}"
        DOCKER_CREDENTIALS = credentials('dockerhub-credentials')
    }

    stages {

        // ─── 1. Checkout ─────────────────────────────────────────────
        stage('Checkout') {
            steps {
                checkout scm
                sh 'find . -name "*.sh" | xargs dos2unix 2>/dev/null || true'
                sh 'git config core.autocrlf false || true'
                echo "Checkout completado"
            }
        }

        // ─── 2. Build (Verificar Entorno Jenkins) ────────────────────
        stage('Build') {
            steps {
                sh 'javac src/main/java/util/JenkinsDetectorMain.java'
                sh 'java -cp src/main/java util.JenkinsDetectorMain'
                echo "Build #${BUILD_NUMBER} en job: ${JOB_NAME}"
                echo "Entorno Jenkins verificado"
            }
        }

        // ─── 3. Test (Compilacion) ───────────────────────────────────
        stage('Test') {
            steps {
                sh 'mvn -B -ntp clean compile'
                echo "Codigo compilado correctamente"
            }
        }

        // ─── 4. Verify CI (Ejecucion de Tests) ───────────────────────
        stage('Verify CI') {
            steps {
                sh 'mvn -B -ntp test'
            }
            post {
                always {
                    junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
                }
            }
        }

        // ─── 5. Package ──────────────────────────────────────────────
        stage('Package') {
            steps {
                sh 'mvn -B -ntp package -DskipTests'
                archiveArtifacts artifacts: 'target/*.war', fingerprint: true
                echo "WAR generado y archivado"
            }
        }

        // ─── 6. Docker Build & Push ──────────────────────────────────
        stage('Docker Build') {
            steps {
                script {
                    echo "Construyendo imagen Docker..."
                    sh "echo ${DOCKER_CREDENTIALS_PSW} | docker login -u ${DOCKER_CREDENTIALS_USR} --password-stdin"
                    sh "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
                    sh "docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} ${DOCKER_IMAGE}:latest"
                    echo "Subiendo imagen a Docker Hub..."
                    sh "docker push ${DOCKER_IMAGE}:${DOCKER_TAG}"
                    sh "docker push ${DOCKER_IMAGE}:latest"
                }
            }
        }

        // ─── 7. Deploy ───────────────────────────────────────────────
        stage('Deploy') {
            steps {
                script {
                    echo "Desplegando aplicacion..."
                    sh "docker rm -f poliservis-app || true"
                    sh """docker run -d \
                        --name poliservis-app \
                        -p 8085:8080 \
                        --network poliservis-net \
                        -e MYSQL_HOST=poli-servis-jimmyarias772.g.aivencloud.com \
                        -e MYSQL_PORT=13512 \
                        -e MYSQL_DATABASE=defaultdb \
                        -e MYSQL_USER=avnadmin \
                        -e MYSQL_PASSWORD=AVNS_z0fh2LSeHStzFyIugpo \
                        --restart unless-stopped \
                        ${DOCKER_IMAGE}:${DOCKER_TAG}"""
                }
            }
        }
    }

    post {
        success {
            echo "Pipeline #${BUILD_NUMBER} completado con exito — ${JOB_NAME}"
        }
        failure {
            echo "Pipeline #${BUILD_NUMBER} FALLO — Revisar logs"
        }
        always {
            cleanWs()
        }
    }
}