# ImageService
Toy API to take images, determine objects in them and save to a database

## Project Set Up
This project uses the GCD Vision API when enableDetection is true to detect objects in the image.
In order to use GCD you will need to set up the Application Default Credentials using the following guide: 
https://cloud.google.com/docs/authentication/provide-credentials-adc#local-dev

## DB Setup
1. Create a free account with AWS
2. Under IAM section create a security group name `image-db-security-group` with inbound and outbound rules set to allow traffic from anywhere 
3. Continue to the RDS (Relational Database Service) create a database of type MySQL, check to allow public access, assign the security group `image-db-security-group`, and create an admin user login
4. Once database is created, test connection using MySQL WorkBench
5. Create database `images` with tables using commands in `resources/db/db.ddl`
6. Use credentials to create application.properties as follows
```
spring.datasource.url=jdbc:mysql://your-aws-endpoint.com:3306/dbName
spring.datasource.username=user
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.jdbc.Driver

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
spring.jpa.show-sql=false
spring.jpa.hibernate.ddl-auto=create
```

## Design
The following is the design for this API. 
<img width="1225" alt="Screen Shot 2022-11-14 at 9 44 27 PM" src="https://user-images.githubusercontent.com/43353944/202354768-44c20559-148c-4803-bb16-1aad2dc945ee.png">

