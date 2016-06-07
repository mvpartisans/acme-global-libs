def checkoutScm(PRIMARY_GROUP){

env.CLEARCASE_PRIMARY_GROUP={PRIMARY_GROUP}
vobName = "{${PRIMARY_GROUP}[3:]}"
echo cleartool mkview -tag devops_{vobName} -tmode unix -stream  {streamname}@/vobs/{vobName} -stgloc -auto
echo cleartool setview devops_{vobName}
echo cleartool rmview -tag devops_{vobName}
}
