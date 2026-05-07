pipeline {
    agent any

    tools {
        maven '3.9'
    }

    environment {
        DOCKER_IMAGE = 'jimmynow/uniservicios'
        DOCKER_TAG   = "${BUILD_NUMBER}"
    }

    stages {

        // ─── 1. Checkout con fix de saltos de linea ─────────────────
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
                sh 'mvn clean compile'
                echo "Codigo compilado correctamente"
            }
        }

        // ─── 4. Verify CI (Ejecucion de Tests) ───────────────────────
        stage('Verify CI') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    // Esto genera la retroalimentacion de "Passed/Failed" en la UI de Jenkins
                    junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
                }
            }
        }

        // ─── 5. Package ──────────────────────────────────────────────
        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
                archiveArtifacts artifacts: 'target/*.war', fingerprint: true
                echo "WAR generado y archivado"
            }
        }

        // ─── 6. Docker Build ─────────────────────────────────────────
        stage('Docker Build') {
            steps {
                script {
                    sh "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
                    sh "docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} ${DOCKER_IMAGE}:latest"
                    echo "Imagen Docker construida: ${DOCKER_IMAGE}:${DOCKER_TAG}"
                }
            }
        }

        // ─── 7. Deploy ───────────────────────────────────────────────
        stage('Deploy') {
            steps {
                sh 'docker-compose up -d'
                echo "Despliegue completado con docker-compose"
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
