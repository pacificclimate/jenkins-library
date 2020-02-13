import org.pcic.util.Utils


/**
 * Build a docker image given the name and server URI
 *
 * @param imageName the name of the image
 * @param options map containing optional arguments:
 *                serverUri: URI of the docker server to build on
 *                buildArgs: docker build arguments
 * @return docker image object
 */
 ArrayList call(String imageName, Map options = [:]) {
     Utils utils = new Utils(this)
     Map args = utils.getUpdatedArgs(['serverUri', 'buildArgs'], options)

     withDockerServer([uri: args.serverUri]) {
         return docker.build(imageName, args.buildArgs)
     }
 }
