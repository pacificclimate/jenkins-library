package org.pcic


class PythonUtils implements Serializable {
    def steps

    PythonUtils(steps) {
        this.steps = steps
    }

    String getPipString(int pythonVersion) {
        String pip
        if (pythonVersion == 3) {
            pip = 'pip3'
        } else {
            pip = 'pip'
        }

        return pip
    }

    void preparePackage(String pip) {
        steps.sh(script: "${pip} install twine wheel")
        steps.sh(script: 'python setup.py sdist bdist_wheel')
    }

    void releasePackage(String pypiUrl, String username, String password) {
        steps.sh(script: "twine upload --repository-url ${pypiUrl} --skip-existing -u ${username} -p ${password} dist/*")
    }

    void installGitExecutable() {
        steps.sh(script: 'apt-get update')
        steps.sh(script: 'apt-get install -y git')
    }

    void installRequirements(String pip, ArrayList requirementsFiles, boolean egg) {
        String required = '-r ' + requirementsFiles.join(' -r ')
        steps.sh(script: "${pip} install ${required}")
        if (egg) {
            steps.sh(script: "${pip} install -e .")
        } else {
            steps.sh(script: "${pip} install .")
        }
    }

    void buildDocs() {
        steps.sh(script: 'python setup.py install')
        steps.sh(script: 'python setup.py build_sphinx')
        steps.sh(script: 'python setup.py install')
    }

    void runPytest(String args) {
        steps.sh(script: "py.test ${args}")
    }
}
