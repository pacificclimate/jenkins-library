/**
 * Clean up image on dev01
 *
 * @param image_name name of the image to clean up
 * @param server_uri URI of the docker server to build on
 */
def call(String imageName, String serverUri = PCIC_DOCKER_DEV01) {
    withDockerServer([uri: serverUri]){
        sh "docker rmi ${imageName}"
    }
}
