package my

import org.camunda.bpm.engine.RuntimeService
import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.runtime.Execution
import org.camunda.bpm.engine.test.mock.Mocks
import spock.lang.Specification

@Integration
@Rollback
class TestProcessSpec extends Specification {

    RuntimeService runtimeService
    TaskService taskService

    def mockTestProcessService = Mock(TestProcessService)

    def setup() {
        Mocks.register("sampleService", mockTestProcessService)
    }

    def cleanup() {
        Mocks.reset()
    }

    void "Testing a happy walk through TestProcess"() {

        given: "a new instance of TestProcess"
        runtimeService.startProcessInstanceByKey("TestProcess")

        when: "completing the user task"
        def task = taskService.createTaskQuery().singleResult()
        taskService.complete(task.id)

        then: "the service method defined for the subsequent service task was called exactly once"
        1 * mockTestProcessService.serviceMethod(_ as Execution)

        and: "nothing else was called"
        0 * _

        and: "the process instance finished"
        !runtimeService.createProcessInstanceQuery().singleResult()

    }

}