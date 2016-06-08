def checkoutFromClearCase(PRIMARY_GROUP){
  env.CLEARCASE_PRIMARY_GROUP={PRIMARY_GROUP}
  vobName = "{${PRIMARY_GROUP}[3:]}"
  cleartool mkview -tag devops_{vobName} -tmode unix -stream  {streamname}@/vobs/{vobName} -stgloc -auto
  cleartool setview devops_{vobName}
}

def cleanupClearCaseCheckout(PRIMARY_GROUP){
    vobName = "{${PRIMARY_GROUP}[3:]}"  
    cleartool rmview -tag devops_{vobName}
}
