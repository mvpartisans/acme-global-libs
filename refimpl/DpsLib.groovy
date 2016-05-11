/**
 * Created by iansari on 5/10/16.
 */


def init(def selector, boolean broadcast) {

    echo " Selector : ${selector} "

    List nodeLabels = getNodesFromSelectors(selector);
    Map envVars = [repo: '/dps-service', branch: 'master']

    stage 'Copy Scripts'
    //checkpoint 'Copy Scripts'

    //runStep nodeLabels, envVars, broadcast, {
    println "broadcast: ${broadcast}"

    if (broadcast) {
        println nodeLabels.size()

        for (int i = 0; i < nodeLabels.size(); i++) {

            println "name of node: ${nodeLabels[i]}"
            //println envVars["repo"]

            node(nodeLabels[i]) {
/*                    bat "echo executing on node: ${nodeLabels[i]}"
                    bat "xcopy /s /v /z /y /i \"$MSI_STAGING_DIR\\*.*\" . "*/
            }
        }

    } else {
        ///node(nodeLabels[0]) {
        //   bat "echo executing on node: ${nodeLabels[0]}"
        //   bat "xcopy /s /v /z /y /i \"$MSI_STAGING_DIR\\*.*\" . "
        //}
    }
    //}

}

@NonCPS
def buildSteps(cl) {
    println "1*******************************************************************************************************************"
    def arr = cl.call();
    println "number of steps : ${arr.size()}"
/*    node() {
        arr.each {
            println it.key
            it.value.call();
        }
    }*/
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