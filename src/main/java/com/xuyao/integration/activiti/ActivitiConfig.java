package com.xuyao.integration.activiti;

import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class ActivitiConfig {

  @Autowired
  private PlatformTransactionManager transactionManager;

  @Autowired
  private DataSource dataSource;


  private String jdbcType = "mysql";

  @Bean
  public SpringProcessEngineConfiguration getProcessEngineConfiguration() {
    SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();
    config.setDataSource(dataSource);
    config.setTransactionManager(transactionManager);
    config.setDatabaseType(jdbcType);
    config.setDatabaseSchemaUpdate("true");
    return config;
  }

  @Bean
  public ProcessEngineFactoryBean processEngine(ProcessEngineConfiguration processEngineConfiguration){
    ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
    processEngineFactoryBean.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);
    return processEngineFactoryBean;
  }

  //八大接口
  @Bean
  public RepositoryService repositoryService(ProcessEngine processEngine){
    return processEngine.getRepositoryService();
  }

  @Bean
  public RuntimeService runtimeService(ProcessEngine processEngine){
    return processEngine.getRuntimeService();
  }

  @Bean
  public TaskService taskService(ProcessEngine processEngine){
    return processEngine.getTaskService();
  }

  @Bean
  public HistoryService historyService(ProcessEngine processEngine){
    return processEngine.getHistoryService();
  }

  @Bean
  public FormService formService(ProcessEngine processEngine){
    return processEngine.getFormService();
  }

  @Bean
  public IdentityService identityService(ProcessEngine processEngine){
    return processEngine.getIdentityService();
  }

  @Bean
  public ManagementService managementService(ProcessEngine processEngine){
    return processEngine.getManagementService();
  }

  @Bean
  public DynamicBpmnService dynamicBpmnService(ProcessEngine processEngine){
    return processEngine.getDynamicBpmnService();
  }
}