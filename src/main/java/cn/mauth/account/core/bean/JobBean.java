package cn.mauth.account.core.bean;

import java.io.Serializable;

public class JobBean implements Serializable{
    private static final long serialVersionUID = 1L;

    private String jobClassName;
    private String jobGroupName;
    private String cronExpression;

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    public String getJobGroupName() {
        return jobGroupName;
    }

    public void setJobGroupName(String jobGroupName) {
        this.jobGroupName = jobGroupName;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    @Override
    public String toString() {
        return "JobBean{" +
                "jobClassName='" + jobClassName + '\'' +
                ", jobGroupName='" + jobGroupName + '\'' +
                ", cronExpression='" + cronExpression + '\'' +
                '}';
    }
}
