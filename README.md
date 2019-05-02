# newspaper-scraper

## About

This is a project for displaying the idea of web scraping application for a speific newpaper site.

It was made using **Spring Boot**, **Spring Data JPA**,**Spring Data REST**, **Mockito**, **JUNIT** and **HtmlUnit**. Database is in memory **MYSql**.

**HTMLUnit** allows to simulate browser events such as clicking and forms submission when scraping and it also has JavaScript support. This enhances the automation process. It also supports XPath based parsing. It can also be used for web application unit testing.

## Configuration

### Configuration Files

Folder **src/resources/** contains config files for **newspaper-scraper** Spring Boot application.

* **src/resources/application.properties** - main configuration file. 
Here it is possible to change the port number and password and username related.

## How to run

You can run it from the command line with included Maven. 

Once the app starts, go to any rest client like Postman and access `http://localhost:8099/scrapper/`
```
#### Using Executable Jar

Or you can build the JAR file with bash
$ scripts/mvnw clean package
``` 

Then you can run the JAR file:
$ java -jar target/web-scraper-0.0.1-SNAPSHOT.jar
```
### Maven

Open a terminal and run the following commands to ensure that you have valid versions of Java and Maven installed:

$ java -version
java version "1.8.0_201"
Java(TM) SE Runtime Environment (build 1.8.0_201-b09)
Java HotSpot(TM) 64-Bit Server VM (build 25.201-b09, mixed mode)

$ mvn -v
Apache Maven 3.6.0 (97c98ec64a1fdfee7767ce5ffb20918da4f719f3; 2018-10-25T00:11:47+05:30)
Maven home: /home/Softwares/apache-maven-3.6.0
Java version: 1.8.0_201, vendor: Oracle Corporation, runtime: /home/Softwares/jdk1.8.0_201/jre
Default locale: en_IN, platform encoding: UTF-8
OS name: "linux", version: "4.15.0-47-generic", arch: "amd64", family: "unix"
```

#### Using the Maven Plugin

The Spring Boot Maven plugin includes a run goal that can be used to quickly compile and run your application. 
Applications run in an exploded form, as they do in your IDE. 
The following example shows a typical Maven command to run a Spring Boot application:
 
```bash
$ mvn spring-boot:run
``` 

#### Using Executable Jar

To create an executable jar run:

```bash
$ mvn clean package
``` 

To run that application, use the java -jar command, as follows:

```bash
$ java -jar target/webscrapper-0.0.1-SNAPSHOT.jar
```

To exit the application, press **ctrl-c**.

## Tests

Tests can be run by executing following command from the root of the project:

```bash
$ mvn test
```

In case of using different datasource `/src/main/resources/application.properties` file it is possible to change both
web interface url path, as well as the datasource url.

