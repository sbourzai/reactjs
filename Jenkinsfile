# stagelibrary variable will be used later to contain old_stagelibraries and is filled in # stage ('Old stage')
def oldstagelibrary

# newstagelibrary variable will contain path of your new sharedlibrary
def newstagelibrary
stage('Old stage') {   
            steps {
              script {
                        // Load Shared library Groovy file old_stagelibraries.Give your path of old_stagelibraries file which is created
                        oldstagelibrary = load 'C:\\Jenkins\\old_stagelibraries'
                        // Execute your function available in old_stagelibraries.groovy file.
                        oldstagelibrary.MyOld_library()       
                      }               
                  }
        }
# Add your new stage in the Jenkinsfile and use your new_stagelibraries file that is created
stage('New stage') {   
            steps {
              script {
                        // Load Shared library Groovy file new_stagelibraries which will contain your new functions.Give your path of new_stagelibraries file which is created
                        newstagelibrary = load 'C:\\Jenkins\\new_stagelibraries'
                        // Execute your function MyNew_library available in new_stagelibraries.groovy file.
                        newstagelibrary.MyNew_library()       
                      }               
                  }
        }
