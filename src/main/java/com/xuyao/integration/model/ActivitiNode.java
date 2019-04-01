package com.xuyao.integration.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class ActivitiNode  {

    private static final long serialVersionUID = 140130456195360098L;

    private Long id;

    private String workflowCode; //工作流编码

    private String nodeName; //节点名称

    private Integer approvalType; //审批类型，0顺签，1会签

    private Integer approverType; //审批人类型，0人，1角色

    private List<String> approverList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkflowCode() {
        return workflowCode;
    }

    public void setWorkflowCode(String workflowCode) {
        this.workflowCode = workflowCode == null ? null : workflowCode.trim();
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName == null ? null : nodeName.trim();
    }

    public Integer getApprovalType() {
        return approvalType;
    }

    public void setApprovalType(Integer approvalType) {
        this.approvalType = approvalType;
    }

    public Integer getApproverType() {
        return approverType;
    }

    public void setApproverType(Integer approverType) {
        this.approverType = approverType;
    }

    public List<String> getApproverList() {
        return approverList;
    }

    public void setApproverList(List<String> approverList) {
        this.approverList = approverList;
    }
}