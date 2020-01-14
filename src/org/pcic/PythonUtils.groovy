package org.pcic


class PythonUtils implements Serializable {
    def steps

    PythonUtils(steps) {
        this.steps = steps
    }

    String getPipString(int pythonVersion) {
        if (pythonVersion == 3) {
            return 'pip3'
        }

        return 'pip'
    }

    void preparePackage(String pip) {
        steps.sh(script: "${pip} install twine wheel")
        steps.sh(script: 'python setup.py sdist bdist_wheel')
    }

    void releasePackage(String pypiUrl, String username, String password) {
        steps.sh(script: "twine upload --repository-url ${pypiUrl} --skip-existing -u ${username} -p ${password} dist/*")
    }

    void installRequirements(String pip, ArrayList requirementsFiles) {
        String required = '-r ' + requirementsFiles.join(' -r ')
        steps.sh(script: "${pip} install ${required}")
        steps.sh(script: "${pip} install -e .")
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
