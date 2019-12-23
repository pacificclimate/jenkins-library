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
        // prepare
        String branchName = 'master'
        def gitTags = ['1.0.0', 'some-tag']
        ArrayList expected_tags = ['1.0.0', 'some-tag', 'latest']
        int expected_size = 3

        // execute
        ArrayList result = dockerUtils.determineTags(branchName, gitTags)

        // assert
        assert result == expected_tags
        assert result.size() == expected_size
    }

    @Test
    public void determineTags_masterAndNoGitTags() {
        // prepare
        String branchName = 'master'
        def gitTags = []
        ArrayList expected_tags = ['master']
        int expected_size = 1

        // execute
        ArrayList result = dockerUtils.determineTags(branchName, gitTags)

        // assert
        assert result == expected_tags
        assert result.size() == expected_size
    }

    @Test
    public void determineTags_notMaster() {
        // prepare
        String branchName = 'not-master'
        def gitTags = ['1.0.0', 'some-tag']
        ArrayList expected_tags = ['not-master']
        int expected_size = 1

        // execute
        ArrayList result = dockerUtils.determineTags(branchName, gitTags)

        // assert
        assert result == expected_tags
        assert result.size() == expected_size
    }

    @Test
    public void getContainerArgs_default() {
        // prepare
        String project = 'default'
        String expected = '-u root'

        // execute
        String result = dockerUtils.getContainerArgs(project)

        // assert
        assert result == expected
    }

    @Test
    public void getContainerArgs_pdp() {
        // prepare
        String project = 'pdp'
        String expected = '-u root --volumes-from pdp_data --env-file /storage/data/projects/comp_support/jenkins/pdp_envs/pdp_deployment.env'

        // execute
        String result = dockerUtils.getContainerArgs(project)

        // assert
        assert result == expected
    }


    @Test(expected = Exception.class)
    public void getContainerArgs_exception() {
        // prepare
        String project = 'badKey'

        // execute
        String result = dockerUtils.getContainerArgs(project)
    }
}
