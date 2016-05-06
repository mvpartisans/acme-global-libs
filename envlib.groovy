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

@NonCPS
private def readEnvVarsFromFile(def projectName) {
    def fileName = projectName + "_env.json"
    def envJson = readFile fileName 
    def jsonResp = new JsonSlurper().parseText(envJson)
    
    println jsonResp

    return (Map) jsonResp;
}

@NonCPS
private def setEnvVars(Map envVars) {
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
    setEnvVars(envVars);
}


return this;
