/**
 * Created by iansari on 5/13/16.
 */
node {

    withEnv(["key1=val3", "key2=val3"]) {
        sh 'echo $key1'
        sh 'echo $key2'
    }

    withEnv(toList([key1: "val1", key2: "val2"])) {
        sh 'echo $key1'
    }


    sh 'echo "outside scoped block: " $key1'

}

@NonCPS
def toList(Map envVars) {
    envVars.collect { k, v -> "$k=$v" }
}