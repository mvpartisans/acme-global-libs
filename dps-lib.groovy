/**
 * Created by iansari on 5/4/16.
 */


def invoke(def selector, boolean broadcast) {

    echo " Selector : ${selector} "

    List nodeLabels = getNodesFromSelectors(selector);
    Map envVars = [repo: '/dps-service', branch: 'master']

    node {

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


        stage 'Uninstall MSI'
        checkpoint 'Uninstall MSI'
        sh 'echo step-2.1'
        sh "echo step-2.2"
        sh '''
    # step 2 (uninstall msi)
    # the job is allowed to continue even if this step fails
    # ${perl} ${bf_scripts}\\uninstall_pp_msi.pl
    '''

        stage 'Extract'
        checkpoint 'Extract'
        sh 'echo step-3.1'
        sh "echo step-3.2"

        sh '''
    #echo $$shell=new-object -com shell.application  > ${PS_UNZIP_SCRIPT}
    #echo $$dest=$$shell.namespace("${BF_ROOT}") >> ${PS_UNZIP_SCRIPT}
    #echo $$zips = get-childitem ${BF_ROOT}\\*.zip >> ${PS_UNZIP_SCRIPT}
    #echo foreach ($$zip in $$zips) >> ${PS_UNZIP_SCRIPT}
    #echo { >> ${PS_UNZIP_SCRIPT}
    #echo Write-Host $$zip.fullname >> ${PS_UNZIP_SCRIPT}
    #echo $$zipObj = $$shell.namespace($$zip.fullname) >> ${PS_UNZIP_SCRIPT}
    #echo $$dest.Copyhere($$zipObj.items(),16) >> ${PS_UNZIP_SCRIPT}
    #echo } >> ${PS_UNZIP_SCRIPT}

    #${PS_EXE} ${BF_ROOT}\${PS_UNZIP_SCRIPT}

    #dir /S /B
    '''
        stage 'Execute Deployment'
        checkpoint 'Execute Deployment'
        sh 'echo step-4.1'
        sh "echo step-4.2"
        sh '''
    # ${PS_EXE} ${BF_ROOT}\${PS_DEPLOY_SCRIPT}
    '''
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

