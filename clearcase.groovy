/**
 * Checkout from ClearCase
 * @param PRIMARY_GROUP
 * @return
 */
def checkoutFromClearCase(PRIMARY_GROUP){
    env.CLEARCASE_PRIMARY_GROUP={PRIMARY_GROUP}
    vobName = "{${PRIMARY_GROUP}[3:]}"
    sh 'cleartool mkview -tag devops_{vobName} -tmode unix -stream  {streamname}@/vobs/{vobName} -stgloc -auto'
    sh 'cleartool setview devops_{vobName}'
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
