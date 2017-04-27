package my

import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.engine.runtime.Execution

import java.util.logging.Logger

class TestProcessService {
    
    // Use java.util.logging in order to see something in vanilla tomcat catalina.out
    static Logger logger = Logger.getLogger(TestProcessService.name)
    
    RuntimeService runtimeService

    def serviceMethod(Execution execution) {
        println("${TestProcessService.class.simpleName} called from Camunda BPM ProcessInstance (id='${execution.processInstanceId}').")
        runtimeService.getVariables(execution.processInstanceId).each {
            logger.info("- Process Variable '${it.key}' = ${it.value} (${it.value?.class})")
            println("- Process Variable '${it.key}' = ${it.value} (${it.value?.class})")
        }
    }
    
}
