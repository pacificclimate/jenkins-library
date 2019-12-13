import pcic.DockerUtils


/**
 * Build a docker image given the name and server URI.  By default this function
 * will build this image on docker-dev01.
 *
 * @param imageName the name of the image
 * @param serverUri URI of the docker server to build on
 * @return image the built docker image object
 */
def call(String imageName, String serverUri = PCIC_DOCKER_DEV01) {
    DockerUtils dockerUtils = new DockerUtils(this)
    return dockerUtils.buildWithServer(imageName, serverUri)
}
