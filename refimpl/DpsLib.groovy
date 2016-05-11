/**
 * Created by iansari on 5/10/16.
 */

def broadcast = false;
def selector = null;

def init(def _selector, boolean _broadcast) {

    broadcast = _broadcast;
    selector = _selector;

}

//@NonCPS
def buildSteps(cl) {
    println "1*******************************************************************************************************************"
    println cl
    def stepsMap = cl.call();
    println "number of steps : ${stepsMap.size()}"
    node() {

        def entries = get_map_entries(stepsMap)
        for (int i = 0; i < entries.size(); i++) {
            String key = entries[i][0]
            String value = entries[i][1]

            stage "${key}"
            //sh "echo Key $key and value $value"
            //value.call();
            excuteStepOnNodes(value)
        }
    }
}

def excuteStepOnNodes(step) {
    echo " Selector : ${selector} "
    List nodeLabels = getNodesFromSelectors(selector);
    Map envVars = [repo: '/dps-service', branch: 'master']

    if (broadcast) {
        println nodeLabels.size()

        for (int i = 0; i < nodeLabels.size(); i++) {

            println "name of node: ${nodeLabels[i]}"
            //println envVars["repo"]

            node("${nodeLabels[i]}") {
/*                    bat "echo executing on node: ${nodeLabels[i]}"
                    bat "xcopy /s /v /z /y /i \"$MSI_STAGING_DIR\\*.*\" . "*/
                step.call();
            }
        }

    } else {
        node("${nodeLabels[0]}") {
            //   bat "echo executing on node: ${nodeLabels[0]}"
            //   bat "xcopy /s /v /z /y /i \"$MSI_STAGING_DIR\\*.*\" . "
            step.call();
        }
    }
}


@NonCPS
List<List<Object>> get_map_entries(map) {
    map.collect { k, v -> [k, v] }
}

def getNodesFromSelectors(def selector) {

    //List nodeLabels = ["label1", "label2", "label3"]
    List nodeLabels = ["master", "master", "master"]
    //List nodeLabels = ["dps_cats_int_web", "dps_cats_int_web-sideA"]


    return nodeLabels
}


def runStep(nodeLabels, envVars, broadcast, cl) {

    println nodeLabels;
    println envVars;
    cl();
}

return this;