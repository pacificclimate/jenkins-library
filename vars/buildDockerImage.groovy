import org.pcic.util.Utils

/**
 * Build a docker image given the name and server URI.  By default this function
 * will build this image on docker-dev01.
 *
 * @param imageName the name of the image
 * @param serverUri URI of the docker server to build on
 * @return image the built docker image object
 */
 def call(String imageName, Map params = [:]) {
     Utils utils = new Utils()
     Map defaults = [serverUri: PCIC_DOCKER_DEV01, buildArgs: '--pull .']
     Map processed = utils.processParams(defaults, params)
     def image

     withDockerServer([uri: processed.serverUri]) {
         image = docker.build(imageName, processed.buildArgs)
     }

     return image
 }
