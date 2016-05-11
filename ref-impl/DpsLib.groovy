/**
 * Created by iansari on 5/4/16.
 */


def invoke(def selector, boolean broadcast) {

    echo " Selector : ${selector} "

    List nodeLabels = getNodesFromSelectors(selector);
    Map envVars = [repo: '/dps-service', branch: 'master']

    stage 'Copy Scripts'
    //checkpoint 'Copy Scripts'

    runStep nodeLabels, envVars, broadcast, {
        println "broadcast: ${broadcast}"

        if (broadcast) {
            println nodeLabels.size()

            for (int i=0; i< nodeLabels.size(); i++){

                println "name of node: ${nodeLabels[i]}"
                //println envVars["repo"]

                node(nodeLabels[i]) {
/*                    bat "echo executing on node: ${nodeLabels[i]}"
                    bat "xcopy /s /v /z /y /i \"$MSI_STAGING_DIR\\*.*\" . "*/
                }
            }

        } else {
            ///node(nodeLabels[0]) {
            //   bat "echo executing on node: ${nodeLabels[0]}"
            //   bat "xcopy /s /v /z /y /i \"$MSI_STAGING_DIR\\*.*\" . "
            //}
        }
    }

    stage 'Uninstall MSI'
    runStep nodeLabels, envVars, broadcast, {
        println "broadcast: ${broadcast}"

        if (broadcast) {
            println nodeLabels.size()

            for (int i=0; i< nodeLabels.size(); i++){

                println "name of node: ${nodeLabels[i]}"
                //println envVars["repo"]

                node(nodeLabels[i]) {
                    //bat "echo executing on node: ${nodeLabels[i]}"
                    //bat "$PERL $bf_scripts\\uninstall_pp_msi.pl "
                }
            }

        } else {
            node(nodeLabels[0]) {
                bat "echo executing on node: ${nodeLabels[0]}"
                //bat "$PERL $bf_scripts\\uninstall_pp_msi.pl"
            }
        }
    }

    stage 'Extract'
    runStep nodeLabels, envVars, broadcast, {
        println "broadcast: ${broadcast}"

        if (broadcast) {
            println nodeLabels.size()

            for (int i=0; i< nodeLabels.size(); i++){

                println "name of node: ${nodeLabels[i]}"
                //println envVars["repo"]

                node(nodeLabels[i]) {
                    /**
                     bat "echo executing on node: ${nodeLabels[i]}"
                     bat "echo $shell=new-object -com shell.application  > $PS_UNZIP_SCRIPT"
                     bat "echo $dest=$shell.namespace("${BF_ROOT}") >> $PS_UNZIP_SCRIPT"
                     bat "echo $zips = get-childitem ${BF_ROOT}\\*.zip >> $PS_UNZIP_SCRIPT"
                     bat "echo foreach ($zip in $zips) >> $PS_UNZIP_SCRIPT"
                     bat "echo { >> $PS_UNZIP_SCRIPT"
                     bat "echo Write-Host $zip.fullname >> $PS_UNZIP_SCRIPT"
                     bat "echo $zipObj = $shell.namespace($zip.fullname) >> $PS_UNZIP_SCRIPT"
                     bat "echo $dest.Copyhere($zipObj.items(),16) >> $PS_UNZIP_SCRIPT"
                     bat "echo } >> $PS_UNZIP_SCRIPT"
                     bat "$PS_EXE $BF_ROOT\\$PS_UNZIP_SCRIPT"
                     **/
/*                    bat "unzip -o -v *.zip"
                    bat "dir /S /B"*/
                }
            }

        } else {
            node(nodeLabels[0]) {

            }
        }
    }

    stage 'Execute Install'
    runStep nodeLabels, envVars, broadcast, {
        println "broadcast: ${broadcast}"

        if (broadcast) {
            println nodeLabels.size()

            for (int i=0; i< nodeLabels.size(); i++){

                println "name of node: ${nodeLabels[i]}"
                //println envVars["repo"]

                node(nodeLabels[i]) {
/*                    bat "echo executing on node: ${nodeLabels[i]}"
                    bat "$PS_EXE $PS_DEPLOY_SCRIPT"*/
                }
            }

        } else {
            node(nodeLabels[0]) {
/*                bat "echo executing on node: ${nodeLabels[0]}"
                bat "$PS_EXE $PS_DEPLOY_SCRIPT"*/
            }
        }
    }

    stage 'Cleanup'
    runStep nodeLabels, envVars, broadcast, {
        println "broadcast: ${broadcast}"

        if (broadcast) {
            println nodeLabels.size()

            for (int i=0; i< nodeLabels.size(); i++){

                println "name of node: ${nodeLabels[i]}"
                //println envVars["repo"]

                node(nodeLabels[i]) {
/*                    bat "echo executing on node: ${nodeLabels[i]}"
                    bat "rm -rf $env.WORKSPACE*//*"
                    bat "exit 0"*/
                }
            }

        } else {
            node(nodeLabels[0]) {
/*                bat "echo executing on node: ${nodeLabels[0]}"
                bat "rm -rf $env.WORKSPACE*//*"
                bat "exit 0"*/
            }
        }
    }
}

def getNodesFromSelectors(def selector) {

    List nodeLabels = ["master", "master", "master"]
    //List nodeLabels = ["dps_cats_int_web", "dps_cats_int_web-sideA"]


    return nodeLabels
}


def runStep(nodeLabels, envVars, broadcast, cl) {

    println nodeLabels;
    println envVars;
    cl();
}

return this;