package org.pcic


class PythonUtils implements Serializable {
    def steps

    PythonUtils(steps) {
        this.steps = steps
    }

    String applyPythonVersion(String base, int pythonVersion) {
        if (pythonVersion != 2) {
            return base + pythonVersion.toString()
        }

        return base
    }

    void preparePackage(String pip, String python) {
        steps.sh(script: "${pip} install twine wheel")
        steps.sh(script: "${python} setup.py sdist bdist_wheel")
    }

    void releasePackage(String pypiUrl, String username, String password) {
        steps.sh(script: "twine upload --repository-url ${pypiUrl} --skip-existing -u ${username} -p ${password} dist/*")
    }

    void installRequirements(String pip, ArrayList requirementsFiles, boolean selfInstall) {
        String required = '-r ' + requirementsFiles.join(' -r ')
        steps.sh(script: "${pip} install ${required}")
        if(selfInstall) {
          steps.sh(script: "${pip} install -e .")
        }
    }

    void buildDocs(String python) {
        steps.sh(script: "${python} setup.py install")
        steps.sh(script: "${python} setup.py build_sphinx")
        steps.sh(script: "${python} setup.py install")
    }

    void runPytest(String python, String args) {
        steps.sh(script: "${python} -m pytest ${args}")
    }
}
