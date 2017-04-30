package my

import org.camunda.bpm.engine.delegate.DelegateExecution

import java.util.logging.Logger

class TestProcessService {
    
    // Use java.util.logging in order to see something in vanilla tomcat catalina.out
    static Logger logger = Logger.getLogger(TestProcessService.name)
    
    def serviceMethod(DelegateExecution execution) {
        println("${TestProcessService.class.simpleName} called from Camunda BPM ProcessInstance (id='${execution.processInstanceId}').")
        execution.getVariables(execution.processInstanceId).each {
            logger.info("- Process Variable '${it.key}' = ${it.value} (${it.value?.class})")
            println("- Process Variable '${it.key}' = ${it.value} (${it.value?.class})")
        }
    }
    
}
