course link: https://learning.oreilly.com/videos/learning-apache-maven/9781771373661/

In eclipse > show view > terminal > terminal (not remote > terminal)
Open where you installed maven-bin directory, > go inside lib folder > extract maven-model-builder-3.8.2 > org.apache.maven.model>pom-4.0.0.xml(This is super pom)

Run `mvn help:effective-pom` to get effective-pom in console


Run mvn clean package to clear out the build target directory, build the classes/jar in that directory
Use `mvn -Pproduction clean package` for production profile

mvn archetype `plugin` and generate `goal`, will give 3000+ types, just hit enter, it will set default, and then version > pick latest,
 and then groupId, and then version : 1.0.0-SNAPSHOT > pick any 1.0 or something and then package (if it is different than groupId)
`mvn archetype:generate`

Use `mvn eclipse:eclipse` to generate files for eclipse project, eclipse plugin anf eclipse goal. Later you can import into eclipse or STS
Use `mvn idea:idea`


Run `mvn dependency:copy-dependencies` to pull down only dependencies to local .m2 and copy to target folder

transitive dependencies - dependencies of a dependency, e.g, junit has hamcrest dependency in its pom

Remote repositories - where to pull dependencies from?
go to maven installed directory > conf directory > settings.xml
```
 <profile>
      <id>spring_remote</id>
      <repositories>
        <repository>
          <id>spring_repository</id>
          <url>http://repo.spring.io/release</url>
        </repository>
      </repositories>
    </profile>
  </profiles>
  <activeProfiles>
    <activeProfile>spring_remote</activeProfile>
  </activeProfiles>
```

Scope for dependencies
default is compile
compile - available during build, run, test phases
`mvn -X compile` X for debug, you will see dependencies in the classpath in logs
`mvn -X test`, see dependencies in test classpath
`import` scope is used for poms
`provided` - expect the dependency to be provided by JDK or the container you are using
When the dependency is available during build and test phases, but not available during deploy in webapps.
servlet is provided by tomcat
`runtime` - needed when we execute or when we test the application, but not during compilation e.g jdbc-driver - runtime
`system` - it says the dependency will be provided by the filesystem(not recommended). Basically placing jars in some folder and specifying paths for it

