def lib1
node {
    git 'https://github.com/mvpartisans/acme-global-libs'
    lib1 = load 'refimpl/DpsLib.groovy' 
    
    stage 'Pre Step'
    sh '''
    echo 'echo Pre Step'
    '''
    
    lib1.init(selector, broadcast.toBoolean())
    lib1.buildSteps {
    [
            "stage 1": {
                println "executing Stage1"
                println ""

            },
            "stage 2": {
                println "executing Stage2"
                sh 'echo printing env vars from remote $REPO'
                    withEnv(["folder_foo=imran", "name=homer", "REPO=Nexus"]) {
                        sh 'echo $folder_foo'
                        sh 'echo $name'
                        sh 'echo printing overridden env var $REPO'                        
                    } 
                sh '''
                    echo "hello from shell stag-2...!"
                '''                
            },
            "stage 3": {
                println "hello from stage 3"
            }
    ]
}
    
    stage 'Post Step'
    sh 'echo Post Step'
}
