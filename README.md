course link: https://learning.oreilly.com/videos/learning-apache-maven/9781771373661/

In eclipse > show view > terminal > terminal (not remote > terminal)
Open where you installed maven-bin directory, > go inside lib folder > extract maven-model-builder-3.8.2 > org.apache.maven.model>pom-4.0.0.xml(This is super pom)

Run `mvn help:effective-pom` to get effective-pom in console


Run mvn clean package to clear out the build target directory, build the classes/jar in that directory
Use `mvn -Pproduction clean package` for prodcution profile