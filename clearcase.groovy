/**
 * Make ClearCase View 
 * @param PRIMARY_GROUP
 * @return
 */
def clearCaseMakeView(def primaryGroup, def streamName){
    env.CLEARCASE_PRIMARY_GROUP = primaryGroup
    vobName = "${primaryGroup}[3:]"
    println "vobname: ${vobName}"
    //'cleartool mkview -tag devops_{vobName} -tmode unix -stream  ${streamname}@/vobs/${vobName} -stgloc -auto'
    def command =  "cleartool mkview -tag devops_${vobName} -tmode unix -stream  ${streamName}@/vobs/${vobName} -stgloc -auto"
    println command
    sh "${command}"
}

/**
 * Cleanup View
 * @param PRIMARY_GROUP
 * @return
 */
def clearCaseRemoveView(def primaryGroup){
    def vobName = "${primaryGroup}[3:]"
    def command = "cleartool rmview -tag devops_${vobName}"
    //sh "${command}"
    println "cleanup"
}

return this;
