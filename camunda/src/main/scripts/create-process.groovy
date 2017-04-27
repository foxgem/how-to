description ("Creates a new Camunda BPM process definition.") {
    usage "grails create-process [NAME]"
    argument name:'Name', description: "The package and name of the process definition, e.g. my.package.SampleProcess"
    flag name:'force', description: "Whether to overwrite existing files"
}

def classLoader = this.class.classLoader
def constants = classLoader.parseClass(new File('src/main/groovy/camunda/Constants.groovy'))

def path = constants.PROCESS_PATH
def type = constants.TYPE
def extension = constants.EXTENSION

def (pkg, name) = constants.generate(args[0])
def artifact = "${pkg}.${name}${type}"
def model = model(artifact)
def overwrite = flag('force') ? true : false

render(
        template: "processes/${type}.${extension}.template",
        destination: file( "${path}/${artifact.replace('.', '/')}.${extension}"),
        model: model,
        overwrite: overwrite
)

render(
        template: "testing/${type}.groovy.template",
        destination: file( "src/integration-test/groovy/${artifact.replace('.', '/')}Spec.groovy"),
        model: model,
        overwrite: overwrite
)

render(
        template: "services/${type}.groovy.template",
        destination: file( "grails-app/services/${artifact.replace('.', '/')}Service.groovy"),
        model: model,
        overwrite: overwrite
)
