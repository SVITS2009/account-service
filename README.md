Account-service Spring Boot Application

MS Access(DB) + Spring Boot + JdbcTemplate

To the setup account-service application locally
* JDK 11
* Maven 3

* Clone the project - git clone https://github.com/SVITS2009/account-service.git
  Go to project directory and run the below commands.

* Clean - mvn clean
* Run the test cases - mvn test
* Run install - mvn install

* Running the application locally

  There are several ways to run a Spring Boot application on your local machine. One way is to
  
  1.) Run the main method in the com.nagarro.account.AccountServiceApplication class from your IDE.
  
  2.) Alternatively you can use the Spring Boot Maven plugin like so:
  
      mvn spring-boot:run

* Optional Information -

- To update session timeout
  src/main/resources/application.properties
    * server.servlet.session.timeout= 300s
      Currently 5 minutes is the session timeout. if we want, we can update above properties based on requirenment.

- To update accessDB (.accdb) file path
    * src/main/resources/application.properties
      spring.datasource.url=jdbc:ucanaccess://DB/accountsdb.accdb;openExclusive=false;ignoreCase=true

  Currently, reading ms access file from DB folder which is in project directory. if we want, we can update above properties based on requirenment.
  Example - .accdb file is available in the path - D:\accessdb\accountsdb.accdb, and we want to connect with that. we have to update above properties.

  spring.datasource.url=jdbc:ucanaccess://D:/accessdb/accountsdb.accdb;openExclusive=false;ignoreCase=true
  Replaced //DB/accountsdb.accdb path with //D:/accessdb/accountsdb.accdb

- User information -
  Currently Application supports 2 user.
    * User1 - admin/admin (who can perform all the activity like filter based on Date and amount)
    * User2 - user/user (who can just view last 3 months statements, No filter applicable)

- Authentication mechanism
    * Form based Login
  
    




