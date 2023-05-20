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