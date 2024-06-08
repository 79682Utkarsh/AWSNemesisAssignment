Aws Assignment Nemesis

###Spring-boot-based application which will communicate with AWS cloud
services Ec2 and S3

##Prerequisites

--Java 8
-- Maven
-- MariaDB
--Aws account with access to EC2 and S3
--Postman to test APIs

##Database Schema

--Create Database nemesis_assignment_db;

--CREATE TABLE job (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  status VARCHAR(255),
  service_name VARCHAR(255)
);

--CREATE TABLE ec2instance (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  instance_id VARCHAR(255)
);

--CREATE TABLE s3bucket (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  bucket_name VARCHAR(255)
);

--CREATE TABLE s3bucket_object (
  id BIGINTjob AUTO_INCREMENT PRIMARY KEY,
  bucket_name VARCHAR(255),
  object_name VARCHAR(255)
);

##Apis Output

1. DiscoverServices(List<String> services)

   --Controller Name:- http://localhost:8080/aws/discover
   --Method:- POST
   --Input:- ["EC2", "S3"]
   --Output:- 1 or 2 or 3 so on

2. GetJobResult(Jobid)

   --Controller Name:- http://localhost:8080/aws/job/2
   --Method:- GET
   --Input:- 2
   --Output:- Success

3. GetDiscoveryResult(String Service)

   --Controller Name:- http://localhost:8080/aws/discovery/S3
   --Method:- GET
   --Input:- Service Name S3 or EC2
   --Output:-For S3
   
      [
    {
        "id": 1,
        "bucketName": "nimesaassignmentbucket1"
    },
    {
        "id": 2,
        "bucketName": "nimesaassignmentbucket2"
    },
    {
        "id": 3,
        "bucketName": "nimesaassignmentbucket3"
    }

  ]
      For EC2

      [
    {
        "id": 1,
        "instanceId": "i-017e6519856f51d35"
    },
    {
        "id": 2,
        "instanceId": "i-0d84e2f42c1069be6"
    }
    ]

  4. GetS3BucketObjects(String BucketName)

     --Controller Name:- http://localhost:8080/aws/s3/nimesaassignmentbucket4
     --Method:- POST
     --Input:- Bucket Name (nimesaassignmentbucket4)
     --Output:- 19

  5. GetS3BucketObjectCount(String BucketName)

     --Controller Name:- http://localhost:8080/aws/s3/nimesaassignmentbucket1/count
     --Method:- GET
     --Input:- Bucket Name (nimesaassignmentbucket1)
     --Output:- 6

  6. GetS3BucketObjectlike(String BucketName, String Pattern)

     --Controller Name:- http://localhost:8080/aws/s3/nimesaassignmentbucket1/objects?pattern=Test-File
     --Method:- GET
     --Input:- Bucket Name (nimesaassignmentbucket10)
               And inside Query Param Key:-pattern Value:- Test-File

     --Output:- [
    "New folder/Test-File - 2.txt",
    "New folder/Test-File - 3.txt",
    "New folder/Test-File - 4.txt",
    "New folder/Test-File.txt"
]


##Database Configuration

spring.application.name=aws-assignment
spring.datasource.url=jdbc:mysql://localhost:3306/nemesis_assignment_db
spring.datasource.username=root
spring.datasource.password=aabmatica@12345
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.MariaDBDialect




   
 



  
  

   

