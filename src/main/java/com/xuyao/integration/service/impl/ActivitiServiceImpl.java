package com.xuyao.integration.service.impl;


import com.xuyao.integration.model.ActivitiNode;
import com.xuyao.integration.service.IActivitiService;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivitiServiceImpl implements IActivitiService {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    private String idPrefix = "pre_";


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Deployment deploymentProcess(List<ActivitiNode> nodes, Object type, String tenantCode)  {
        if(CollectionUtils.isEmpty(nodes)) {
            throw new RuntimeException("节点不能为空");
        }
        if(type == null) {
            throw new RuntimeException("类型不能为空");
        }
        if(StringUtils.isBlank(tenantCode)) {
            throw new RuntimeException("公司编码不能为空");
        }

        synchronized (type){
            long l = countProcess(type, tenantCode);
            if(l > 0) {
                throw new RuntimeException(type + "对应的工作流已存在");
            }

            Process process = getProcess(nodes, type);
            BpmnModel model = new BpmnModel();
            model.addProcess(process);

            String deploymentName = getDeploymentName(type);
            return repositoryService.createDeployment().addBpmnModel(deploymentName + ".bpmn", model).name(deploymentName).tenantId(tenantCode).deploy();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteProcess(Object type, String tenantCode)  {
        if(type == null) {
            throw new RuntimeException("类型不能为空");
        }
        if(StringUtils.isBlank(tenantCode)) {
            throw new RuntimeException("公司编码不能为空");
        }

        long l = countProcessInstance(type, tenantCode);
        if(l > 0) {
            throw new RuntimeException("有运行中的工作流实例，不能删除");
        }

        Deployment deployment = findDeployment(type, tenantCode);
        if(deployment != null){
            repositoryService.deleteDeployment(deployment.getId());
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void suspendProcess(Object type, String tenantCode)  {
        if(type == null) {
            throw new RuntimeException("类型不能为空");
        }
        if(StringUtils.isBlank(tenantCode)) {
            throw new RuntimeException("公司编码不能为空");
        }

        String deploymentName = getDeploymentName(type);
        repositoryService.suspendProcessDefinitionByKey(deploymentName, tenantCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void activateProcess(Object type, String tenantCode)  {
        if(type == null) {
            throw new RuntimeException("类型不能为空");
        }
        if(StringUtils.isBlank(tenantCode)) {
            throw new RuntimeException("公司编码不能为空");
        }

        String deploymentName = getDeploymentName(type);
        repositoryService.activateProcessDefinitionByKey(deploymentName, tenantCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Deployment reDeploymentProcess(List<ActivitiNode> nodes, Object type, String tenantCode)  {
        if(CollectionUtils.isEmpty(nodes)) {
            throw new RuntimeException("节点不能为空");
        }
        if(type == null) {
            throw new RuntimeException("类型不能为空");
        }
        if(StringUtils.isBlank(tenantCode)) {
            throw new RuntimeException("公司编码不能为空");
        }

        synchronized (type){
            long l = countProcessInstance(type, tenantCode);
            if(l > 0) {
                throw new RuntimeException("有运行中的工作流实例，不能重建");
            }

            deleteProcess(type, tenantCode);
            return deploymentProcess(nodes, type, tenantCode);
        }
    }

    @Override
    public long countProcess(Object type, String tenantCode)  {
        if(type == null) {
            throw new RuntimeException("类型不能为空");
        }
        if(StringUtils.isBlank(tenantCode)) {
            throw new RuntimeException("公司编码不能为空");
        }

        String deploymentName = getDeploymentName(type);
        return repositoryService.createDeploymentQuery().deploymentName(deploymentName).deploymentTenantId(tenantCode).count();
    }

    @Override
    public long countProcessInstance(Object type, String tenantCode)  {
        if(type == null) {
            throw new RuntimeException("类型不能为空");
        }
        if(StringUtils.isBlank(tenantCode)) {
            throw new RuntimeException("公司编码不能为空");
        }

        String deploymentName = getDeploymentName(type);
        return taskService.createTaskQuery().processDefinitionKey(deploymentName).taskTenantId(tenantCode).count();
    }

    @Override
    public Deployment findDeployment(Object type, String tenantCode)  {
        if(type == null) {
            throw new RuntimeException("类型不能为空");
        }
        if(StringUtils.isBlank(tenantCode)) {
            throw new RuntimeException("公司编码不能为空");
        }

        String deploymentName = getDeploymentName(type);
        return repositoryService.createDeploymentQuery().deploymentName(deploymentName).deploymentTenantId(tenantCode).singleResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteProcessInstance(String businessKey, String tenantCode)  {
        return deleteProcessInstance(businessKey, tenantCode, false);
    }

    @Override
    public boolean deleteProcessInstance(String businessKey, String tenantCode, boolean processFinish)  {
        if(StringUtils.isBlank(businessKey)) {
            throw new RuntimeException("业务编号不能为空");
        }
        if(StringUtils.isBlank(tenantCode)) {
            throw new RuntimeException("公司编码不能为空");
        }
        synchronized (businessKey){
            List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(businessKey).processInstanceTenantId(tenantCode).orderByProcessInstanceStartTime().desc().list();
            HistoricProcessInstance processInstance = CollectionUtils.isEmpty(list) ? null : list.get(0);
             if(processInstance != null){
                String processInstanceId = processInstance.getId();
                if(!processFinish) {
                    runtimeService.deleteProcessInstance(processInstanceId, "del");
                }
                historyService.deleteHistoricProcessInstance(processInstanceId);
                return true;
            }
            return false;
        }
    }

    @Override
    public ProcessInstance startProcessInstance(Object type, String businessKey, String tenantCode)  {
        if(type == null) {
            throw new RuntimeException("类型不能为空");
        }
        if(StringUtils.isBlank(businessKey)) {
            throw new RuntimeException("业务编号不能为空");
        }
        if(StringUtils.isBlank(tenantCode)) {
            throw new RuntimeException("公司编码不能为空");
        }
        synchronized (businessKey){
            String deploymentName = getDeploymentName(type);
            return runtimeService.startProcessInstanceByKeyAndTenantId(deploymentName, businessKey, tenantCode);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean completeProcessInstance(String businessKey, String tenantCode, List<String> candidateGroups)  {
        if(StringUtils.isBlank(businessKey)) {
            throw new RuntimeException("业务编号不能为空");
        }
        if(StringUtils.isBlank(tenantCode)) {
            throw new RuntimeException("公司编码不能为空");
        }
        if(CollectionUtils.isEmpty(candidateGroups)) {
            throw new RuntimeException("审批人不能为空");
        }
        synchronized (businessKey) {
            Task task = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).taskTenantId(tenantCode).taskCandidateGroupIn(candidateGroups).orderByTaskCreateTime().desc().list().get(0);
            taskService.complete(task.getId());
            return getFinishedProcessInstance(businessKey, tenantCode) != null;
        }
    }

    @Override
    public boolean canApproval(String businessKey, String tenantCode, List<String> candidateGroups)  {
        if(StringUtils.isBlank(businessKey)) {
            throw new RuntimeException("业务编号不能为空");
        }
        if(StringUtils.isBlank(tenantCode)) {
            throw new RuntimeException("公司编码不能为空");
        }
        if(CollectionUtils.isEmpty(candidateGroups)) {
            throw new RuntimeException("审批人不能为空");
        }
        return taskService.createTaskQuery().processInstanceBusinessKey(businessKey).taskTenantId(tenantCode).taskCandidateGroupIn(candidateGroups).count() >= 1;
    }

    @Override
    public HistoricProcessInstance getFinishedProcessInstance(String businessKey, String tenantCode)  {
        if(StringUtils.isBlank(businessKey)) {
            throw new RuntimeException("业务编号不能为空");
        }
        if(StringUtils.isBlank(tenantCode)) {
            throw new RuntimeException("公司编码不能为空");
        }
        return historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(businessKey).processInstanceTenantId(tenantCode).finished().singleResult();
    }



    private Process getProcess(List<ActivitiNode> nodes, Object type){
        Process process = new Process();
        String processId = getProcessId(type);
        process.setId(processId);

        //工作流节点
        process.addFlowElement(createStartEvent());
        long index = 0;
        for (ActivitiNode node : nodes) {
            if(node.getId() == null) {
                node.setId(index++);
            }
            process.addFlowElement(createGroupTask(idPrefix + node.getId(), node.getNodeName(), node.getApproverList()));
        }
        process.addFlowElement(createEndEvent());

        //节点连线
        ActivitiNode first = null;
        ActivitiNode last = null;
        String firstId = null;
        String lastId = null;
        first = nodes.get(0);
        if(nodes.size() == 1){
            firstId = idPrefix + first.getId();
            lastId = firstId;
            process.addFlowElement(createSequenceFlow("start", firstId));
            process.addFlowElement(createSequenceFlow(lastId, "end"));
        }else{
            last = nodes.get(nodes.size() -1);
            firstId = idPrefix + first.getId();
            lastId = idPrefix + last.getId();
            process.addFlowElement(createSequenceFlow("start", firstId));
            for (int i = 0; i < nodes.size(); i++) {
                if(i < nodes.size() - 1){
                    process.addFlowElement(createSequenceFlow(idPrefix + nodes.get(i).getId(), idPrefix + nodes.get(i + 1).getId()));
                }
            }
            process.addFlowElement(createSequenceFlow(lastId, "end"));
        }

        return process;
    }

    private String getDeploymentName(Object type){
        return "activiti" + type;
    }

    private String getProcessId(Object type){
        return getDeploymentName(type);
    }



    //任务节点-组
    private UserTask createGroupTask(String id, String name, String candidateGroup) {
        List<String> candidateGroups=new ArrayList<String>();
        candidateGroups.add(candidateGroup);
        return createGroupTask(id, name, candidateGroups);
    }

    //任务节点-组
    private UserTask createGroupTask(String id, String name, List<String> candidateGroups) {
        UserTask userTask = new UserTask();
        userTask.setName(name);
        userTask.setId(id);
        userTask.setCandidateGroups(candidateGroups);
        return userTask;
    }

    //任务节点-用户
    private UserTask createUserTask(String id, String name, String candidateUser) {
        List<String> candidateUsers=new ArrayList<String>();
        candidateUsers.add(candidateUser);
        return createUserTask(id, name, candidateUsers);
    }

    //任务节点-用户
    private UserTask createUserTask(String id, String name, List<String> candidateUsers) {
        UserTask userTask = new UserTask();
        userTask.setName(name);
        userTask.setId(id);
        userTask.setCandidateUsers(candidateUsers);
        return userTask;
    }

    //创建任务
    private UserTask createAssigneeTask(String id, String name, String assignee) {
        UserTask userTask = new UserTask();
        userTask.setName(name);
        userTask.setId(id);
        userTask.setAssignee(assignee);
        return userTask;
    }

    //连线
    private SequenceFlow createSequenceFlow(String from, String to, String name, String conditionExpression) {
        SequenceFlow flow = new SequenceFlow();
        flow.setSourceRef(from);
        flow.setTargetRef(to);
        if(StringUtils.isNotBlank(name)) {
            flow.setName(name);
        }
        if(StringUtils.isNotBlank(conditionExpression)) {
            flow.setConditionExpression(conditionExpression);
        }
        return flow;
    }

    //连线
    private SequenceFlow createSequenceFlow(String from, String to) {
        return createSequenceFlow(from, to, null, null);
    }

    //排他网关
    private ExclusiveGateway createExclusiveGateway(String id, String name) {
        ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
        exclusiveGateway.setId(id);
        exclusiveGateway.setName(name);
        return exclusiveGateway;
    }

    //并行网关
    private ParallelGateway createParallelGateway(String id, String name){
        ParallelGateway gateway = new ParallelGateway();
        gateway.setId(id);
        gateway.setName(name);
        return gateway;
    }

    //开始节点
    private StartEvent createStartEvent() {
        StartEvent startEvent = new StartEvent();
        startEvent.setId("start");
        return startEvent;
    }

    //结束结点
    private EndEvent createEndEvent() {
        EndEvent endEvent = new EndEvent();
        endEvent.setId("end");
        return endEvent;
    }

}
