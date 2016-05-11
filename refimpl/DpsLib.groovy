import groovy.json.JsonSlurper

/**
 * Created by iansari on 5/10/16.
 */

def broadcast = false;
def selector = null;

def init(def _selector, boolean _broadcast) {

    broadcast = _broadcast;
    selector = _selector;
    initEnvironment();

}


def buildSteps(cl) {
    //println cl
    def stepsMap = cl.call();
    println "number of steps : ${stepsMap.size()}"
    node() {

        def entries = get_map_entries(stepsMap)
        for (int i = 0; i < entries.size(); i++) {
            String stageName = entries[i][0]
            String step = entries[i][1]

            stage "${stageName}"
            excuteStepOnNodes(step)
        }
    }
}

def excuteStepOnNodes(step) {
    echo " Selector : ${selector} "
    List nodeLabels = getNodesFromSelectors(selector);
    Map envVars = [repo: '/dps-service', branch: 'master']

    if (broadcast) {
        for (int i = 0; i < nodeLabels.size(); i++) {
            //println "Name of node: ${nodeLabels[i]}"

            node("${nodeLabels[i]}") {
                step.call();
            }
        }

    } else {
        node("${nodeLabels[0]}") {
            step.call();
        }
    }
}


@NonCPS
List<List<Object>> get_map_entries(map) {
    map.collect { k, v -> [k, v] }
}

def getNodesFromSelectors(def selector) {

    List nodeLabels = ["dump-slave-1", "dump-slave-1", "dump-slave-1"]

    return nodeLabels
}

@NonCPS
def initEnvironment() {

    Map envVars = readEnvVarsFromRemote();
    setEnvVars(envVars);
}


@NonCPS
private def readEnvVarsFromRemote() {

    def payload = new URL("http://mvpartisans.com/prod_env.json").text
    def jsonResp = new JsonSlurper().parseText(payload)

    return (Map) jsonResp;
}

@NonCPS
private def setEnvVars(Map envVars) {
    //println envVars;
    envVars.each { k, v ->
        env[k] = v
    }
    envVars.each {
        //echo it.key
        //echo it.value
        env[it.key] = it.value
    }
}


return this;