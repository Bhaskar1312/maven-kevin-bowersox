course link: https://learning.oreilly.com/videos/learning-apache-maven/9781771373661/
repo https://resources.oreilly.com/examples/0636920040729/-/tree/master/Learning%20Apache%20Maven%20-%20Working%20Files

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

Conflict Resolution
maven-conflict-example-1 has org.apache.commons:commons-lang3 of 3.11
maven-conflict-example-2 has org.apache.commons:commons-lang3 of 3.12.0
more-maven-projects has maven-conflict-example-1, maven-conflict-example-2 as dependencies
When running `mvn -X compile`, it only placed one file of commons-lang jar(should have be 3.12.0, but 3.11 may be due to decimal inconsistency)
[DEBUG]   (f) compilePath = [C:\Users\Bhaskar\Downloads\Code\maven\maven-kevin-bowersox\more-maven-examples\target\classes,
 C:\Users\Bhaskar\.m2\repository\com\company\maven\maven-conflict-example-1\1.0-SNAPSHOT\maven-conflict-example-1-1.0-SNAPSHOT.jar, 
 C:\Users\Bhaskar\.m2\repository\org\apache\commons\commons-lang3\3.11\commons-lang3-3.11.jar,
 C:\Users\Bhaskar\.m2\repository\com\company\maven\maven-conflict-example-2\1.0-SNAPSHOT\maven-conflict-example-2-1.0-SNAPSHOT.jar]

If we want to exclude 3.11 explicitly and use 3.12.0
    <dependency>
      <groupId>com.company.maven</groupId>
      <artifactId>maven-conflict-example-1</artifactId>
      <version>1.0-SNAPSHOT</version>
      <exclusions>
        <exclusion>
          <groupId>org.apache.commons</groupId>
          <artifactId>commons-lang3</artifactId>
<!--          <version>3.11</version>-->
        </exclusion>
      </exclusions>
      
This time
 compilePath = [C:\Users\Bhaskar\Downloads\Code\maven\maven-kevin-bowersox\more-maven-examples\target\classes,
 C:\Users\Bhaskar\.m2\repository\com\company\maven\maven-conflict-example-1\1.0-SNAPSHOT\maven-conflict-example-1-1.0-SNAPSHOT.jar,
 C:\Users\Bhaskar\.m2\repository\com\company\maven\maven-conflict-example-2\1.0-SNAPSHOT\maven-conflict-example-2-1.0-SNAPSHOT.jar,
 C:\Users\Bhaskar\.m2\repository\org\apache\commons\commons-lang3\3.12.0\commons-lang3-3.12.0.jar]
 
 Lifecycle of maven 
 old - default, clean, site
 new - https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html#setting-up-your-project-to-use-the-build-lifecycle

Phase	Handles	Description
prepare-resources	resource copying	Resource copying can be customized in this phase.
validate	Validating the information	Validates if the project is correct and if all necessary information is available.
compile	compilation	Source code compilation is done in this phase.
Test	Testing	Tests the compiled source code suitable for testing framework.
package	packaging	This phase creates the JAR/WAR package as mentioned in the packaging in POM.xml.
install	installation	This phase installs the package in local/remote maven repository.
Deploy	Deploying	Copies the final package to the remote repository.
`mvn help:describe -Dcmd=clean`

important phases (6 of 23 phases)
compile, test-compile, test, package, install, deploy
compile - compile src/main/java into class for JVM
test-compile - compile test/main/java into class
test - run tests using suitable unit test framework, doesn't require package (can turn dont fail build on test fail)
<build>
<plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-surefire-plugin</artifactId>
          <version>2.22.1</version>
          <configuration>
            <testFailureIgnore>true</testFailureIgnore>
          </configuration>
        </plugin>
</build>
 
package - take the compiled code and package in distributable format e.g, jar, war, ear 
install - install the package into the local repository, for use as a dependency in other projects locally
deploy - done in an integration or release environment, copies the final package to the remote repository for sharing with other developers/projects

goals
`mvn compiler:compile`
`mvn help:describe -Dplugin=compiler`
`mvn compiler:help -Ddetail=true -Dgoal=compile`

Plugin properties
`mvn help:describe -Dcmd=compiler:compile -Ddetail`
 verbose (Default: false)
      User property: maven.compiler.verbose
      Set to true to show messages about what the compiler is doing.
 `mvn compiler:compile -Dmaven.compiler.verbose=true`
 or
<plugin>
          <artifactId>maven-compiler-plugin</artifactId>
          <version>3.8.0</version>
          <configuration>
            <verbose>true</verbose>
          </configuration>
        </plugin>
and run `mvn compile` or `mvn compiler:compile`
