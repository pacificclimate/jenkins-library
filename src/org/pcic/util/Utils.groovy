package org.pcic.util

import org.pcic.GitUtils
import org.pcic.DockerUtils


class Utils implements Serializable {
    def steps

    Utils(steps) {
        this.steps = steps
    }

    /**
     * Returns map of updated default arguments
     */
    Map getUpdatedArgs(ArrayList keys, Map argsToUpdate) {
        return updateArgs(getDefaultArgs(keys), argsToUpdate)
    }

    /**
     * Returns a map of default arguments, given a list of keys.
     *
     * Default arguments are common to many steps and classes, so are defined
     * once here. Because only a subset of the arguments is required or valid
     * for any given step, the keys of those arguments are passed in and only
     * those defaults are returned.
     */
    Map getDefaultArgs(ArrayList keys) {
        Map defaults = [aptPackages: [],
                        buildArgs: '--pull .',
                        buildDocs: false,
                        containerData: 'default',
                        registryUrl: '',
                        serverUri: steps.env.PCIC_DOCKER_DEV01,
                        pipIndexUrl:'https://pypi.pacificclimate.org/simple',
                        pypiUrl: 'https://pypi.pacificclimate.org/',
                        pythonVersion: 3]
        return defaults.subMap(keys)
    }

    /**
     * Update default map given update map
     */
    Map updateArgs(Map defaults, Map updates) {
        // Check if there is anything to update
        if (updates.size() == 0) {
            return defaults
        }

        // Check that all keys in update map exist in default args
        def unknownKeys = updates.keySet() - defaults.keySet()
        if (unknownKeys.size() > 0) {
            throw new Exception("Key(s) ${unknownKeys} not available in ${expected.keySet()}")
        }

        // The way groovy handles this expression is that keys in both
        // `defaults` and `updates` will be updated to have the value from
        // `updates`.  Other key, value pairs are left as is.
        return defaults + updates
    }

    String getBranchName() {
        if (steps.env.BRANCH_NAME.contains('PR')) {
            return steps.env.CHANGE_BRANCH
        }

        return steps.env.BRANCH_NAME
    }

    boolean isPublishable(String branchName, ArrayList tags) {
        return (branchName == 'master' && !tags.isEmpty())
    }

    /**
     * Installs packages using apt-get
     *
     * In some cases before we can install a package the package list must be
     * updated.  Updating the list is a costly operation timewise and thus we'd
     * like to avoid running it unnecessarily.  Before running the installation
     * we check against a list of packages known to require an update.
     *
     * @param packages list of packages to install
     */
    void installAptPackages(ArrayList packages) {
        ArrayList requireUpdate = ['git'].intersect(packages)

        if (requireUpdate.size() > 0) {
            updatePackageList()
        }

        aptGetInstall(packages)
    }

    void updatePackageList() {
        steps.sh(script: 'apt-get update')
    }

    void aptGetInstall(ArrayList packages) {
        String installs = packages.join(' ')
        steps.sh(script: "apt-get install -y ${installs}")
    }
}
