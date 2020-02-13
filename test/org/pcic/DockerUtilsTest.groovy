package org.pcic

import util.MockSteps

import org.junit.Before
import org.junit.Test


public class DockerUtilsTest {
    DockerUtils dockerUtils
    MockSteps mockSteps

    @Before
    public void init() {
        this.mockSteps = new MockSteps()
        this.dockerUtils = new DockerUtils(mockSteps)
    }

    @Test
    public void buildImageName_goodName() {
        String result = dockerUtils.buildImageName('registry', 'base', 'tag')

        assert result == 'registry/base:tag'
    }

    @Test
    public void buildImageName_formattedName() {
        String result = dockerUtils.buildImageName('registry', 'feature/base', 'tag:2.0')

        assert result == 'registry/feature-base:tag-2.0'
    }

    @Test
    public void getTags_noTags() {
        ArrayList result = dockerUtils.getTags()

        assert result == []
    }

    @Test
    public void getTags_tags() {
        // test different case with master branch
        String temp = mockSteps.env.BRANCH_NAME
        mockSteps.env.BRANCH_NAME = 'master'

        ArrayList result = dockerUtils.getTags()

        assert result == ['1.0.0', 'some-tag', 'latest']

        // reset environment variable
        mockSteps.env.BRANCH_NAME = temp
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
        dockerUtils.getContainerArgs('badKey')
    }

    @Test
    public void formatDockerString_illegalString() {
        dockerUtils.formatDockerString('some/str') == 'some-str'
    }

    @Test
    public void formatDockerString_diffReplacement() {
        dockerUtils.formatDockerString('some/str', '+') == 'some+str'
    }

    @Test
    public void getDeletableImages_master() {
        // test different case with master branch
        String temp = mockSteps.env.BRANCH_NAME
        mockSteps.env.BRANCH_NAME = 'master'

        ArrayList result = dockerUtils.getDeletableImages('registry/some-tool:master')

        assert result == ['registry/some-tool:master',
                          'registry/some-tool:1.0.0',
                          'registry/some-tool:some-tag',
                          'registry/some-tool:latest']

        // reset environment variable
        mockSteps.env.BRANCH_NAME = temp
    }

    @Test
    public void getDeletableImages_nonMaster() {
        ArrayList result = dockerUtils.getDeletableImages('registry/some-tool:master')

        assert result == ['registry/some-tool:master']
    }
}
