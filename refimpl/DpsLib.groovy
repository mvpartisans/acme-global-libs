import groovy.json.JsonSlurper

/**
 * Created by iansari on 5/10/16.
 */

def broadcast = false;
def selector = null;

/**
 * Initializer
 * @param _selector
 * @param _broadcast
 */
def init(def _selector, boolean _broadcast) {

    broadcast = _broadcast;
    selector = _selector;
    initEnvironment();
}

/**
 * Method takes a closure with a Map, the Map key is used as the Stage Name and the Map value is executable Groovy and/or shell code
 * @param cl
 * @return
 */
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

/**
 * Method executes BuIld step on all Nodes if Broadcast is set to true
 * @param step
 * @return
 */
def excuteStepOnNodes(step) {
    println " Selector : ${selector} "
    List nodeLabels = getNodesFromSelectors(selector);

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

/**
 * Create a List from a Map, as iterating a Map exposes non-seralizable classes non permitted in Pipeline
 * @param map
 * @return List
 */
@NonCPS
List<List<Object>> get_map_entries(map) {
    map.collect { k, v -> [k, v] }
}

/**
 * Method returns All Nodes that map to a Selector
 * @todo implement Selector to Collector Mapping
 * @param selector
 * @return
 */
def getNodesFromSelectors(def selector) {

    List nodeLabels = ["dump-slave-1", "dump-slave-1", "dump-slave-1"]

    return nodeLabels
}

/**
 * Init envvars from JSON over REST
 * @return
 */
@NonCPS
def initEnvironment() {

    Map envVars = readEnvVarsFromRemote();
    setEnvVars(envVars);
}

/**
 * Read JSON from URL
 * @return MAP of Key/Val
 */
@NonCPS
private def readEnvVarsFromRemote() {

    def payload = new URL("http://mvpartisans.com/prod_env.json").text
    def jsonResp = new JsonSlurper().parseText(payload)

    return (Map) jsonResp;
}

/**
 * Iterate over Map and create Jenkins ENV Variables
 * @param envVars
 * @return
 */
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