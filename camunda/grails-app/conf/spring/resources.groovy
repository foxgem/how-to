import grails.util.Environment
import org.camunda.bpm.engine.HistoryService
import org.camunda.bpm.engine.ManagementService
import org.camunda.bpm.engine.RepositoryService
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.spring.ProcessEngineFactoryBean
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration

beans = {

    processEngineConfiguration(SpringProcessEngineConfiguration) {
        processEngineName = "engine"
        dataSource = ref("dataSource")
        transactionManager = ref("transactionManager")
        databaseSchemaUpdate = true
        jobExecutorActivate = false
        deploymentResources = "file:grails-app/processes/**/*.bpmn"

        if(Environment.current == Environment.TEST) {
            expressionManager = new org.camunda.bpm.engine.test.mock.MockExpressionManager()
        }

    }

    processEngine(ProcessEngineFactoryBean) {
        processEngineConfiguration = ref("processEngineConfiguration")
    }

    repositoryService(RepositoryService) { bean ->
        bean.factoryBean = "processEngine"
        bean.factoryMethod = "getRepositoryService"
    }

    runtimeService(RuntimeService) { bean ->
        bean.factoryBean = "processEngine"
        bean.factoryMethod = "getRuntimeService"
    }

    taskService(TaskService) { bean ->
        bean.factoryBean = "processEngine"
        bean.factoryMethod = "getTaskService"
    }

    historyService(HistoryService) { bean ->
        bean.factoryBean = "processEngine"
        bean.factoryMethod = "getHistoryService"
    }

    managementService(ManagementService) { bean ->
        bean.factoryBean = "processEngine"
        bean.factoryMethod = "getManagementService"
    }

}
