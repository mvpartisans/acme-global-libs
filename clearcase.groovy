/**
 * Checkout from ClearCase
 * @param PRIMARY_GROUP
 * @return
 */
def clearCaseMakeView(def primaryGroup, def streamName){
    env.CLEARCASE_PRIMARY_GROUP = primaryGroup
    vobName = "${primaryGroup}[3:]"
    println "vobname: ${vobName}"
    //sh 'cleartool mkview -tag devops_{vobName} -tmode unix -stream  ${streamname}@/vobs/${vobName} -stgloc -auto'
    println "cleartool mkview -tag devops_${vobName} -tmode unix -stream  ${streamName}@/vobs/${vobName} -stgloc -auto"
    //sh 'cleartool setview devops_{vobName}'
}

/**
 * Cleanup View
 * @param PRIMARY_GROUP
 * @return
 */
def cleanupClearCaseCheckout(PRIMARY_GROUP){
    def vobName = "{${PRIMARY_GROUP}[3:]}"
    sh 'cleartool rmview -tag devops_{vobName}'
}

return this;
