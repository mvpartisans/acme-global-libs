/**
 * Created by iansari on 5/17/16.
 */
//stash a file for later use in workflow
node('one') {
    stage 'one'
    // First set up a shared Maven repo so we don't need to download all dependencies on every build.
    writeFile file: 'settings.xml', text: '<settings><localRepository>/m2repo</localRepository></settings>'
    stash name: 'mvnSettings', includes: 'settings.xml'
}
node('two') {
    //checkpoint allows restarting workflow at a specific point
    checkpoint 'checkpoint one'
    stage 'two'
    unstash 'mvnSettings'
    def unstashedSuccess = fileExists 'settings.xml'
    echo 'successfully unstashed file: ' + unstashedSuccess
}