package io.jenkins.plugins.sample;

import hudson.model.FreeStyleBuild;
import hudson.model.FreeStyleProject;
import hudson.model.Label;
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition;
import org.jenkinsci.plugins.workflow.job.WorkflowJob;
import org.jenkinsci.plugins.workflow.job.WorkflowRun;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

public class HelloWorldBuilderTest {

//    @Rule
//    public JenkinsRule jenkins = new JenkinsRule();
//
//    final String host = "http://www.52clover.cn";
//    final String type = "suite";
//    final String id = "1";
//
//    @Test
//    public void testConfigRoundtrip() throws Exception {
//        FreeStyleProject project = jenkins.createFreeStyleProject();
//        project.getBuildersList().add(new HelloWorldBuilder(host, type, id));
//        project = jenkins.configRoundtrip(project);
//        jenkins.assertEqualDataBoundBeans(new HelloWorldBuilder(host, type, id), project.getBuildersList().get(0));
//    }
//
//    @Test
//    public void testConfigRoundtripFrench() throws Exception {
//        FreeStyleProject project = jenkins.createFreeStyleProject();
//        HelloWorldBuilder builder = new HelloWorldBuilder(host, type, id);
////        builder.setUseFrench(true);
//        project.getBuildersList().add(builder);
//        project = jenkins.configRoundtrip(project);
//
//        HelloWorldBuilder lhs = new HelloWorldBuilder(host, type, id);
////        lhs.setUseFrench(true);
//        jenkins.assertEqualDataBoundBeans(lhs, project.getBuildersList().get(0));
//    }
//
//    @Test
//    public void testBuild() throws Exception {
//        FreeStyleProject project = jenkins.createFreeStyleProject();
//        HelloWorldBuilder builder = new HelloWorldBuilder(host, type, id);
//        project.getBuildersList().add(builder);
//
//        FreeStyleBuild build = jenkins.buildAndAssertSuccess(project);
//        jenkins.assertLogContains("Hello, " + host, build);
//    }
//
//    @Test
//    public void testBuildFrench() throws Exception {
//
//        FreeStyleProject project = jenkins.createFreeStyleProject();
//        HelloWorldBuilder builder = new HelloWorldBuilder(host, type, id);
////        builder.setUseFrench(true);
//        project.getBuildersList().add(builder);
//
//        FreeStyleBuild build = jenkins.buildAndAssertSuccess(project);
//        jenkins.assertLogContains("Bonjour, " + host, build);
//    }
//
//    @Test
//    public void testScriptedPipeline() throws Exception {
//        String agentLabel = "my-agent";
//        jenkins.createOnlineSlave(Label.get(agentLabel));
//        WorkflowJob job = jenkins.createProject(WorkflowJob.class, "test-scripted-pipeline");
//        String pipelineScript
//                = "node {\n"
//                + "  greet '" + host + "'\n"
//                + "}";
//        job.setDefinition(new CpsFlowDefinition(pipelineScript, true));
//        WorkflowRun completedBuild = jenkins.assertBuildStatusSuccess(job.scheduleBuild2(0));
//        String expectedString = "Hello, " + host + "!";
//        jenkins.assertLogContains(expectedString, completedBuild);
//    }

}