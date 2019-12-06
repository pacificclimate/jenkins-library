/**
 * Clean up image on dev01
 *
 * @param image_name name of the image to clean up
 * @param server_uri URI of the docker server to build on
 */
def call(String image_name, String server_uri = PCIC_DOCKER_DEV01) {
    withDockerServer([uri: server_uri]){
        sh "docker rmi ${image_name}"
    }
}
