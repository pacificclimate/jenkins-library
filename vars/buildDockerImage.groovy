import org.pcic.util.Utils


/**
 * Build a docker image given the name and server URI.  By default this function
 * will build this image on docker-dev01.
 *
 * @param imageSuffix the name of the image
 * @param options map containing optional arguments:
 *                serverUri: URI of the docker server to build on
 *                buildArgs: docker build arguments
 * @return ArrayList the built docker image object and the name of the image
 */
 ArrayList call(String imageName, Map options = [:]) {
     Utils utils = new Utils(this)
     Map args = utils.getUpdatedArgs(['serverUri', 'buildArgs'], options)

     withDockerServer([uri: args.serverUri]) {
         return docker.build(imageName, args.buildArgs)
     }
 }
