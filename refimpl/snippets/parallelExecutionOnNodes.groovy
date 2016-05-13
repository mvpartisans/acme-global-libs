/**
 * Created by iansari on 5/13/16.
 */

node {

    def branches = [:]
    def names = nodeNames()
    for (int i = 0; i < names.size(); ++i) {
        def nodeName = names[i];
        // Into each branch we put the pipeline code we want to execute
        branches["node_" + nodeName] = {
            node(nodeName) {
                echo "Triggering on " + nodeName
                sh 'echo hello Node'
            }
        }
    }

    parallel branches

}

@NonCPS
def nodeNames() {
    return jenkins.model.Jenkins.instance.nodes.collect { it.name }
}