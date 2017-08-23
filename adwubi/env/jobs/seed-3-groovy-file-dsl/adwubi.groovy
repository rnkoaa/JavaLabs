String basePath = 'adwubi'
String repo = 'rnkoaa/adwubi'

job('Adwubi build'){
    description('A basic build of My Adwubi Item')
    //keepDependencies('false')
    scm {
        github repo
    }
    steps {
        ant() {
            target 'main'
            buildFile '${WORKSPACE}/build.xml'
            //antInstallation('Ant 1.7.0')
            //javaOpts(['-Xmx1024m'])
            //antInstallation 'Ant 1.7.0'
            //prop 'tomcat.home', '${WORKSPACE}/tomcat7'
        }
    }
}