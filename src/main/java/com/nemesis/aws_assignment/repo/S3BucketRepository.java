package com.nemesis.aws_assignment.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nemesis.aws_assignment.model.S3Bucket;

public interface S3BucketRepository extends JpaRepository<S3Bucket, Long>{

}
