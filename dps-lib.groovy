def invoke(def selector){

echo " Selector : ${selector} "   
   
node{   
   
   stage 'Copy Scripts'
    checkpoint 'Copy Scripts'   
    sh 'echo step-1.1'
    sh "echo step-1.2"
    sh '''
    # the step below uses an output filter
    # job fails if this expression is matched: ^0 File\\(s\\) copied
    # xcopy /s /v /z /y /i "${MSI_STAGING_DIR}\\*.*" .    
    '''    
  
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

return this;

