/**
 * Created by iansari on 5/4/16.
 */


import com.cloudbees.groovy.cps.NonCPS
import groovy.json.JsonSlurper

def version = '1.0'


@NonCPS
private def readEnvVarsFromRemote() {

    def payload = new URL("http://mvpartisans.com/prod_env.json").text
    def jsonResp = new JsonSlurper().parseText(payload)

    return (Map) jsonResp;
}

/**
@NonCPS
 def readEnvVarsFromFile(def projectName) {
    def fileName = projectName + "_env.json"
    println "fileName :" +fileName
    String envJson = readFile "env.json" 
    println "Json text : "
    def jsonResp = new JsonSlurper().parseText(envJson)
    
    println "printing json" + jsonResp

    return (Map) jsonResp;
}
**/

@NonCPS
def readEnvVarsFromFile(def projectName) {
    def fileName = projectName + "_env.json"
    def envJson = readFile fileName 
    def jsonResp = new JsonSlurper().parseText(envJson)

    println jsonResp
    return (Map) jsonResp;
}

def setEnvVars(Map envVars) {
    println envVars;
    envVars.each{ k, v -> 
    println "Key: ${k} : Value:${v}"
    env[k] = v 
    }
/**    envVars.each {
        echo it.key
        echo it.value
        env[it.key] = it.value
    } **/
}


def sayHello(Map envVars){
    println "hello"
    println envVars
}



@NonCPS
def initEnvironment(Map overrides) {

    Map envVars = readEnvVarsFromRemote();
    //override vars
    setEnvVars(envVars);
}

@NonCPS
def initEnvironment() {

    Map envVars = readEnvVarsFromRemote();
    setEnvVars(envVars);
}

@NonCPS
def initEnvironmentForProject(def projectName) {

    Map envVars = readEnvVarsFromFile(projectName);
    println envVars
    setEnvVars(envVars);
}


return this;
