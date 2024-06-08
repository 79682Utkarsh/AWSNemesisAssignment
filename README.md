Aws Assignment Nemesis

###Spring-boot-based application which will communicate with AWS cloud
services Ec2 and S3

##Prerequisites

--Java 8
-- Maven
-- MariaDb
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

   

