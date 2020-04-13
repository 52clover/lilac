package io.jenkins.plugins.sample;

import hudson.Launcher;
import hudson.Extension;
import hudson.FilePath;
import hudson.util.FormValidation;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;

import jenkins.tasks.SimpleBuildStep;
import org.jenkinsci.Symbol;
import org.json.JSONObject;

public class HelloWorldBuilder extends Builder implements SimpleBuildStep {

    private String host;
    private String type;
    private String id;
    private String variable;
    private final String version = "v0.8.1";

    public String getHost() {
        return host;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getVariable() { return variable; }

    public String getVersion() { return version; }

    public String getUrl(String type) { return this.getHost() + "/api/v1/" + type + "/trigger"; }

    public HashMap<String, String> parseVariable() {
        HashMap<String, String> variables = new HashMap<>();
        String variableString = this.getVariable();
        String[] variableArray = variableString.split("\n");
        for (String variable: variableArray) {
            String[] item = variable.split(":");
            if (item.length != 2) {
                continue;
            }
            String key = item[0].trim();
            String value = item[1].trim();
            variables.put(key, value);
        }
        return variables;
    }

    public String post(String url, HashMap<String, Object> parameters) throws IOException {
        // 创建http的post请求。
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        // 创建请求参数
        String variable = new JSONObject(parameters).toString();
        StringEntity entity = new StringEntity(variable, "utf-8");
        entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json"));
        httpPost.setEntity(entity);

        // 设置请求头
        httpPost.setHeader("Content-type", "application/json");

        // 发送请求获取响应
        CloseableHttpResponse response = client.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();

        String result = null;
        if (responseEntity != null) {
            result =  EntityUtils.toString(responseEntity);
        }

        // 关闭链接
        EntityUtils.consume(entity);
        response.close();

        return result;
    }

    @DataBoundConstructor
    public HelloWorldBuilder(String host, String type, String id, String variable) {
        this.host = host;
        this.type = type;
        this.id = id;
        this.variable = variable;
    }

    @Override
    public void perform(Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener listener) throws InterruptedException, IOException {
        listener.getLogger().println("Clover Jenkins Plugin Version : " + this.getVersion());
        listener.getLogger().println("Now Running Cases In Host : " + this.getHost());
        listener.getLogger().println("Now Running Cases By Type : " + this.getType());
        listener.getLogger().println("Now Running Cases Use Id : " + this.getId());
        HashMap<String, String> variables = this.parseVariable();
        for (String key : variables.keySet()) {
            listener.getLogger().println("Now Running Cases Use variable: key=" + key + " value=" + variables.get(key));
        }

        HashMap<String, Object> paramters = new HashMap<>();
        paramters.put("id", this.getId());
        paramters.put("trigger", "jenkins");
        paramters.put("variables", variables);

        String url = this.getUrl(this.getType());
        listener.getLogger().println("Now Running Cases By Trigger : " + url);
        String result = this.post(url, paramters);
        if (result != null) {
            listener.getLogger().println("Now Running Cases Get Response : " + result);
            JSONObject jsonResponse = new JSONObject(result);
            String address = this.getHost() + "/report/detail?id=" + jsonResponse.get("data");
            listener.getLogger().println("Report : " + address);
        }
    }

    @Symbol("greet")
    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        public FormValidation doCheckName(@QueryParameter String value)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error(Messages.HelloWorldBuilder_DescriptorImpl_errors_missingName());
            if (value.length() < 4)
                return FormValidation.warning(Messages.HelloWorldBuilder_DescriptorImpl_warnings_tooShort());
            return FormValidation.ok();
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return Messages.HelloWorldBuilder_DescriptorImpl_DisplayName();
        }

    }
}
