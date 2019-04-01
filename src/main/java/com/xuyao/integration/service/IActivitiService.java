package com.xuyao.integration.service;

import com.xuyao.integration.model.ActivitiNode;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.List;

public interface IActivitiService {

        /**
         * 创建流程
         * @param nodes 工作流节点
         * @param type 类型
         * @param tenantCode 公司编码
         */
        Deployment deploymentProcess(List<ActivitiNode> nodes, Object type, String tenantCode);

        /**
         * 删除流程
         * @param type 类型
         * @param tenantCode 公司编码
         */
        boolean deleteProcess(Object type, String tenantCode) ;

        /**
         * 暂停流程
         * @param type 类型
         * @param tenantCode 公司编码
         */
        void suspendProcess(Object type, String tenantCode) ;

        /**
         * 启用流程
         * @param type 类型
         * @param tenantCode 公司编码
         */
        void activateProcess(Object type, String tenantCode) ;

        /**
         * 重新部署流程
         * @param nodes 工作流节点集合
         * @param type 类型
         * @param tenantCode 公司编码
         * @return
         */
        Deployment reDeploymentProcess(List<ActivitiNode> nodes, Object type, String tenantCode) ;

        /**
         * 统计工作流数量
         * @param type 类型
         * @param tenantCode 公司编码
         * @return
         */
        long countProcess(Object type, String tenantCode) ;

        /**
         * 统计工作流实例数量
         * @param type 类型
         * @param tenantCode 公司编码
         * @return
         */
        long countProcessInstance(Object type, String tenantCode) ;

        /**
         * 查询流程
         * @param type 类型
         * @param tenantCode 公司编码
         * @return
         */
        Deployment findDeployment(Object type, String tenantCode) ;

        /**
         * 删除流程实例
         * @param businessKey 业务编号
         * @param tenantCode 公司编码
         * @return
         */
        boolean deleteProcessInstance(String businessKey, String tenantCode) ;

        /**
         * 删除流程实例
         * @param businessKey
         * @param tenantCode 公司编码
         * @param processFinish 流程是否完成
         * @return
         */
        boolean deleteProcessInstance(String businessKey, String tenantCode, boolean processFinish) ;

        /**
         * 开启工作流实例
         * @param type 类型
         * @param businessKey 业务编号
         * @param tenantCode 公司编码
         * @return
         */
        ProcessInstance startProcessInstance(Object type, String businessKey, String tenantCode) ;

        /**
         * 提交流程实例
         * @param businessKey 业务编号
         * @param tenantCode 公司编码
         * @param candidateGroups 审批人列表
         * @return 流程是否完成
         */
        boolean completeProcessInstance(String businessKey, String tenantCode, List<String> candidateGroups) ;

        /**
         * 是否有审批权限
         * @param businessKey 业务编号
         * @param tenantCode 公司编码
         * @param candidateGroups 审批人列表
         * @return
         */
        boolean canApproval(String businessKey, String tenantCode, List<String> candidateGroups) ;

        /**
         * 获取结束的流程
         * @param businessKey
         * @param tenantCode 公司编码
         * @return
         */
        HistoricProcessInstance getFinishedProcessInstance(String businessKey, String tenantCode) ;

    }


