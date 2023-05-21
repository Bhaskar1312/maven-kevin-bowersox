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

https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html#setting-up-your-project-to-use-the-build-lifecycle

Custom Plugins
`mvn archetype:generate -DgroupId=com.company.maven -DartifactId=first-custom -Dversion=1.0.0-SNAPSHOT  -DarchetypeArtifactId=maven-archetype-mojo -DarchetypeGroupId=org.apache.maven.archetypes`

https://www.baeldung.com/maven-plugin

go to more-maven-examples folder > `mvn com.company.maven:first-custom:touch` This is long way of specifying goal.

```
<plugin>
          <groupId>com.company.maven</groupId>
          <artifactId>first-custom</artifactId>
          <version>1.0.0-SNAPSHOT</version>
          <executions>
            <execution>
              <id>first-custom-compile</id>
              <phase>compile</phase>
              <goals>
                <goal>touch</goal>
              </goals>
            </execution>
          </executions>
        </plugin>
 ```       
 `mvn fCustom:touch`
 or `mvn package` as we tied it to 'compile' phase
 
 https://maven.apache.org/plugins/index.html
 
 Goals and Plugins
 maven package
  or 
 jar plugin:jar goal
mvn jar:jar 
mvn jar:jar -Djar.finalName=test -Djar.forceCreation=true #create jar file everytime
mvn clean compile jar:jar 

https://stackoverflow.com/questions/2619598/differences-between-dependencymanagement-and-dependencies-in-maven

mvn javadoc:javadoc (plugin:goal), generated in target/site/index.html
mvn javadoc:javadoc -Dheader=Company -Dfooter=Copyright  
#couldn't see footer?
mvn help:describe -Dcmd=javadoc:javadoc -Ddetail
 <build>
     <plugins>
      <plugin>
        <artifactId>maven-javadoc-plugin</artifactId>
        <version>3.4.1</version>
        <groupId>org.apache.maven.plugins</groupId>
      </plugin>
    </plugins>
    
    <!-- plugin Management -->
</build>

why did we specify javadoc in plugin and not in pluginManagement?
because, javadoc plugin is not mentioned/declared in super-pom/parent-pom
compiler plugin is declared, so we can just place it in pluginManagement section. The configurations in pluginManagement will be inherited by any child project
<pluginManagement><!-- lock down plugins versions to avoid using Maven defaults (may be moved to parent pom) -->
      <plugins>
        <plugin>
          <artifactId>maven-javadoc-plugin</artifactId>
          <groupId>org.apache.maven.plugins</groupId>
          <configuration>
            <footer>This is the new configuration </footer>
          </configuration>
          <executions>
            <execution>
<!--              <id></id>-->
              <phase>compile</phase>
              <goals>
                <goal>jar</goal>
              </goals>
            </execution>
          </executions>
        </plugin>
</plugins>
 </pluginManagement>
 
 we generated all javadoc and placed them in the jar file
 
 Install 
 takes artifact and installs it into local repository
 mvn install:install doesn;t make sense as the project needs to be compiled first
 just use `mvn install` phase 
 `mvn deploy` to upload to remote repository
     <distributionManagement>
        <repository>
            <id>bhaskar</id>
            <name>bhaskar-releases</name>
            <!-- This url doesn't work -->
            <url>http://bhaskar.artifactoryonline.com/bhaskar/ext-releases-local</url>
        </repository>
    </distributionManagement>
    
mvn test
mvn install -Dmaven.test.skip=true 
    <pluginManagement><!-- lock down plugins versions to avoid using Maven defaults (may be moved to parent pom) -->
      <plugins>
          <plugin>
              <groupId>org.apache.maven.plugins</groupId>
              <artifactId>maven-surefire-plugin</artifactId>
              <version>2.22.1</version>
              <configuration>
                  <testFailureIgnore>true</testFailureIgnore>
              </configuration>
          </plugin>
       </pluginManagement>   
==
mvn eclipse:eclipse
Now in eclipse, you can use File > Import > Existing project (instead of Maven import project)
===
Run mvn archetype:generate
and select maven-archetype-webapp filter 

<build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-war-plugin</artifactId>
        <version>3.3.2</version>
        <configuration>
          <warName>mavenWeb</warName>
        </configuration>
      </plugin>
    </plugins>
</build>

mvn war:war 

===
Archetypes
mvn archetype:generate > Filter spring-data-basic(published by kevin bowersox)

From inside repo root, Run 'mvn archetype:create-from-project'
Now inside target > generated-sources > archetype > 
```
  <groupId>${groupId}</groupId>
  <artifactId>${artifactId}</artifactId>
  <version>${version}</version>
<!-- packing maven-archetype is not present -->
  <name>${artifactId}</name>
 ```
 Now run 'mvn install' (or deploy for company's remote repo) inside inside target > generated-sources > archetype > 
 To upload to local repository
 Now run, mvn archetype:generate and select the local 
 
 maven multimodules
 ```
   <packaging>pom</packaging> <!-- pom - it will only contain configuration-->

    <modules>
        <module>../maven-multimodule-module</module>
        <module>../maven-multimodule-module2</module>
    </modules>
 ```
 Run mvn clean install inside base-module
 
 ##
 Install/extract tomcat, place it in path > click on startup.bat inside bin folder 
 
 go to tomcat localhost:8080/ and click on Manager app, click on cancel, now copy role into `conf/tomcat-users.xml` like it says
 click on shutdown.bat to stop
 
 For tomcat in tomcat-users.xml, inside tomcat-users section
<role rolename="manager-gui"/>
<user username="tomcat" password="s3cret" roles="manager-gui"/>
Now add tomcat configuration in maven settings.xml file > servers section
===
    Add it in build > plugins 
    <plugins>
      <plugin>
        <groupId>org.apache.tomcat.maven</groupId>
        <artifactId>tomcat-maven-plugin</artifactId>
        <version>2.2</version>
        <configuration>
          <url>http://localhost:8080/manager/text</url>
          <server>tomcat-server</server>
        </configuration>
      </plugin>
    </plugins>
    
mvn tomcat7:deploy
mvn tomcat:deploy
mvn clean package tomcat:deploy
These commands are not working but you can manually upload war file, check again

Encrypting passwords
Encrypt master password
webapp> mvn -emp test 
{something+something=}
go to ~/.m2/folder and create settings-security.xml
<settingsSecurity>
<master>{something+something=}</master>
</settingsSecurity>

Encrypt password
Now run mvn -ep s3cret
Encrypt password
$ mvn -ep s3cret
{Ali/ali+Y=}
Now replace with
    <server>
      <id>tomcat-server</id>
      <username>tomcat</username>
      <password>{Ali/ali+Y=}</password>
    </server>
    
 Debugging
 mvn -h --help
 mvn -v --version
 mvn compiler:help
 
 mvn -q compile  
 show if there is an error
 
 mvn -X .... for debugging
 mvn -e ... to debug maven itself for filing bugs