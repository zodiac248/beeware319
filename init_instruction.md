Run the following commands in mqsql to initialize the database
mysql> create database db_panpal
mysql> create user 'ppuser' identified by 'Password';
mysql> grant all on db_panpal.* to 'ppuser';

Run the application using:
./mvnw spring-boot:run

Or build the executable JAR file using:
./mvnw clean package
and run the JAR file as follows:
java -jar target/panpal-backend-0.1.0.jar