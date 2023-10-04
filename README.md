Reproduction of JDK-8013722
===========================

To reproduce run:

    $ ./mvnw clean install

to generate a Maven test-project with 100 modules and then run either

    $ ./mvnw -f test-project/pom.xml -T1C clean install

or the basic Java program that also demonstrates the problem (when run from a JAR file):

    $ java -cp build-tool/target/build-tool-1.0-SNAPSHOT.jar org.example.MultiThreadResourceLoader
