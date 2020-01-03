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
| [`collectCode`](https://github.com/pacificclimate/jenkins-library/blob/dev/vars/collectCode.groovy) | Uses the standard `checkout scm` command (only available for multi-branch pipelines) along with a git fetch. |
| [`publishDockerImage`](https://github.com/pacificclimate/jenkins-library/blob/dev/vars/publishDockerImage.groovy) | Publishes a docker image to a registry.  By default the name of the image should contain the registry to publish to in the form `your-registry/image-name:tag`.  There is an option to specify a registry with a URL. |
| [`publishPythonPackage`](https://github.com/pacificclimate/jenkins-library/blob/dev/vars/publishPythonPackage.groovy) | Creates a python package out of the project and publishes it to pypi. |
| [`isPypiPublishable`](https://github.com/pacificclimate/jenkins-library/blob/dev/vars/isPypiPublishable.groovy) | Checks branch conditions to see if the python package should be published. |  
| [`removeDockerImage`](https://github.com/pacificclimate/jenkins-library/blob/dev/vars/removeDockerImage.groovy) | Removes the built Docker image from the server. |
| [`runNodeTestSuite`](https://github.com/pacificclimate/jenkins-library/blob/dev/vars/runNodeTestSuite.groovy) | Runs a node test suite. |
| [`runPythonTestSuite`](https://github.com/pacificclimate/jenkins-library/blob/dev/vars/runPythonTestSuite.groovy) | Runs a python test suite. |



## Structure
This section outlines how the project is strucuted. If you are looking to add functionality please read this section before coding.  

### Steps
All of the steps are located in the [`vars/`](https://github.com/pacificclimate/jenkins-library/tree/dev/vars) directory.  Each `.groovy` file corresponds to a step where the step name is the name of the file.  This is the name you would use in a `Jenkinsfile` to use the step.  In the file there **must** be a `call` function:
```
def call(...) {
    // some code
}
```
Generally you want to try and keep steps as simple as possible.  Any complex logic or processing should be done in a class.

### Classes
Classes makeup the backbone of shared library steps.  Their purpose is to house any processes that are too complex to neatly fit into steps.  Classes can be found in the [`src/org/pcic/`](https://github.com/pacificclimate/jenkins-library/tree/dev/src/org/pcic) directory.  There you will also find a `util/` directory that contains miscellaneous utility classes.

### Tests
Tests can be found in the [`test/org/pcic/`](https://github.com/pacificclimate/jenkins-library/tree/dev/test/org/pcic).  Furthermore, the `util/` directory contains the supporting classes needed to run the tests.

`MockSteps.groovy` is used for classes that require the `this`* argument.  Create a `MockSteps` class and pass that in as a parameter in place of `this`.  The purpose of this class is to mimic the behaviour of Jenkins pipeline steps used in the `src` classes.

\* `this` refers to a `Script` groovy object that defines Jenkins pipeline context.  Included in this are pipeline steps such as `sh`, `env`, `error`, etc...

## Testing
First install `gradle`:
```
$ sudo apt install gradle
```

Then run the tests using:
```
$ gradle test
```
