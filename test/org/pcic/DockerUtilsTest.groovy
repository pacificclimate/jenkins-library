package org.pcic

import util.MockSteps

import org.junit.Before
import org.junit.Test


public class DockerUtilsTest {
    DockerUtils dockerUtils

    @Before
    public void init() {
        MockSteps mockSteps = new MockSteps()
        this.dockerUtils = new DockerUtils(mockSteps)
    }

    @Test
    public void determineTags_masterAndGitTags() {
        String branchName = 'master'
        def gitTags = ['1.0.0', 'some-tag']
        ArrayList expected_tags = ['1.0.0', 'some-tag', 'latest']

        ArrayList result = dockerUtils.determineTags(branchName, gitTags)

        assert result == expected_tags
    }

    @Test
    public void determineTags_masterAndNoGitTags() {
        String branchName = 'master'
        def gitTags = []
        ArrayList expected_tags = ['master']

        ArrayList result = dockerUtils.determineTags(branchName, gitTags)

        assert result == expected_tags
    }

    @Test
    public void determineTags_notMaster() {
        String branchName = 'not-master'
        def gitTags = ['1.0.0', 'some-tag']
        ArrayList expected_tags = ['not-master']

        ArrayList result = dockerUtils.determineTags(branchName, gitTags)

        assert result == expected_tags
    }

    @Test
    public void getContainerArgs_default() {
        String project = 'default'
        String expected = '-u root'

        String result = dockerUtils.getContainerArgs(project)

        assert result == expected
    }

    @Test
    public void getContainerArgs_pdp() {
        String project = 'pdp'
        String expected = '-u root --volumes-from pdp_data --env-file /storage/data/projects/comp_support/jenkins/pdp_envs/pdp_deployment.env'

        String result = dockerUtils.getContainerArgs(project)

        assert result == expected
    }


    @Test(expected = Exception.class)
    public void getContainerArgs_exception() {
        String project = 'badKey'

        String result = dockerUtils.getContainerArgs(project)
    }
}
