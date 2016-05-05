/**
 * Created by iansari on 5/4/16.
 */


def invoke(def selector, boolean broadcast) {

    echo " Selector : ${selector} "

    List nodeLabels = getNodesFromSelectors(selector);
    Map envVars = [repo: '/dps-service', branch: 'master']

        stage 'Copy Scripts'
        checkpoint 'Copy Scripts'

        runStep nodeLabels, envVars, broadcast, {
            println "hello"

            if (broadcast) {
                nodeLabels.each { nodeLabel ->

                    println nodeLabel
                    //println envVars["repo"]

                    node('master') {
                        sh "echo executing on node: ${nodeLabel}"
                    }
                }
            } else {
                println nodeLabels[0]
            }
        }
}

def getNodesFromSelectors(def selector) {

    //List nodeLabels = ["label1", "label2", "label3"]
    List nodeLabels = ["master", "master", "master"]

    return nodeLabels
}


def runStep(nodeLabels, envVars, broadcast, cl) {

    println nodeLabels;
    println envVars;
    cl();
}

return this;

