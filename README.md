# PCIC Jenkins Pipeline Shared Library
This repo contains the shared library for Jenkins.

## Setup in Jenkins
To get access to this library you have to let Jenkins know about it.

1. Navigate to **Manage Jenkins > Configure System > Global Pipeline Libraries**
2. Fill in the following sections:
    - **Name:** `pcic-pipeline-library`
    - **Default Version:** `master`
    - **Retrieval Method:**: `Modern SCM`
    - **Source Code Management:** `Git`
    - **Project Repository:** `https://github.com/pacificclimate/jenkins-library.git`
3. **Save**

To use the library steps in a Jenkinsfile add the following line to the top of your file:
```
@Library('pcic-pipeline-library')_
```
**NOTE:** The `_` is not a typo and must be included.

## Supported Steps
The following table shows all of the currently supported steps.

| Step | Description |
|------|-------------|
| [`buildDockerImage`](https://github.com/pacificclimate/jenkins-library/blob/dev/vars/buildDockerImage.groovy) | Build a Docker image onto a Docker server. |
| [`codeCollection`](https://github.com/pacificclimate/jenkins-library/blob/dev/vars/codeCollection.groovy) | Uses the standard `checkout scm` command (only available for multi-branch pipelines) along with a git fetch. |
| [`getPublishingTags`](https://github.com/pacificclimate/jenkins-library/blob/dev/vars/getPublishingTags.groovy) | Return a list of tags to use in the `publishDockerImage` step.  If the most recent commit has been tagged this step will return those tags along with a `latest` tag.  If there are no tags detected the branch name will be returned. |
| [`publishDockerImage`](https://github.com/pacificclimate/jenkins-library/blob/dev/vars/publishDockerImage.groovy) | Publishes a docker image to a registry.  By default the name of the image should contain the registry to publish to in the form `your-registry/image-name:tag`.  There is an option to specify a registry with a URL. |
| [`publishPythonPackage`](https://github.com/pacificclimate/jenkins-library/blob/dev/vars/publishPythonPackage.groovy) | Creates a python package out of the project and publishes it to pypi. |
| [`removeDockerImage`](https://github.com/pacificclimate/jenkins-library/blob/dev/vars/removeDockerImage.groovy) | Removes the built Docker image from the server. |
| [`runNodeTestSuite`](https://github.com/pacificclimate/jenkins-library/blob/dev/vars/runNodeTestSuite.groovy) | Runs a node test suite. |
| [`runPythonTestSuite`](https://github.com/pacificclimate/jenkins-library/blob/dev/vars/runPythonTestSuite.groovy) | Runs a python test suite. |



## Adding Steps
All of the steps are located in the [`vars/`](https://github.com/pacificclimate/jenkins-library/tree/dev/vars) directory.  Each `.groovy` file corresponds to a step where the step name is the name of the file.  This is the name you would use in a `Jenkinsfile` to use the step.  In the file there **must** be a `call` function:
```
def call(...) {
    // some code
}
```
This is the function that gets called from the `Jenkinsfile`. Helper functions can be in the same file but generally any classes used are kept in a `src/` directory.
