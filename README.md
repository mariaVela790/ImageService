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

## Improvements For Future
1. Finish error handling and create custom exceptions for bad data and empty sql result sets
2. Finish moving logic to be in service layer 
3. Add unit testing
4. Save images by integrating with S3

## Example Requests and Response

### Request GET
`http://localhost:8080/images/?objects="bird, feather"`

### Response
`{
    "images": [
            {
                "imageId": 1,
                "label": "bird floof",
                "annotations": [
                    {
                    "object": "Bird",
                    "score": 0.960077,
                    "topicality": 0.960077
                    },
                    {
                    "object": "Feather",
                    "score": 0.806098,
                    "topicality": 0.806098
                    },
                ]
            }
        ]
}`

### Request GET
`http://localhost:8080/images/`

### Response
`{
    "images": [
            {
                "imageId": 1,
                "label": "bird floof",
                "annotations": [
                    {
                    "object": "Bird",
                    "score": 0.960077,
                    "topicality": 0.960077
                    },
                    {
                    "object": "Feather",
                    "score": 0.806098,
                    "topicality": 0.806098
                    },
                ]
            }
        ]
}`

### Request POST 
`{
"filePath": "/Users/montserratvela/IdeaProjects/hebinterview/src/main/resources/images/flower.jpeg",
"label": "",
"enableDetection": true
}`

### Response
`{
"images": [
    {
        "imageId": 3,
        "label": "7y74H_[Mon, Nov, 21, 00:11:55, CST, 2022]",
        "annotations": [
            {
                "object": "Flower",
                "score": 0.9710453,
                "topicality": 0.9710453
            },
            {
                "object": "Plant",
                "score": 0.958386,
                "topicality": 0.958386
            },
            {
                "object": "Petal",
                "score": 0.90035725,
                "topicality": 0.90035725
            },
            {
                "object": "Annual plant",
                "score": 0.74014735,
                "topicality": 0.74014735
            },
            {
                "object": "Flowering plant",
                "score": 0.7377718,
                "topicality": 0.7377718
            },
            {
                "object": "Terrestrial plant",
                "score": 0.7356099,
                "topicality": 0.7356099
            },
            {
                "object": "Close-up",
                "score": 0.7152714,
                "topicality": 0.7152714
            },
            {
                "object": "Forb",
                "score": 0.68906724,
                "topicality": 0.68906724
            },
            {
                "object": "Pollen",
                "score": 0.68214023,
                "topicality": 0.68214023
            },
            {
                "object": "Macro photography",
                "score": 0.68012255,
                "topicality": 0.68012255
            }
        ]
        }
    ]
}`
