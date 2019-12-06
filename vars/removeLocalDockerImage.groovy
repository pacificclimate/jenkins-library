/**
 * Clean up image on dev01
 *
 * @param image_name name of the image to clean up
 */
def call(String image_name) {
    withDockerServer([uri: PCIC_DOCKER]){
        sh "docker rmi ${image_name}"
    }
}
