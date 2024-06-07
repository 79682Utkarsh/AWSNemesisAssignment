package com.nemesis.aws_assignment.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nemesis.aws_assignment.model.S3BucketObject;

public interface S3BucketObjectRepository extends JpaRepository<S3BucketObject, Long>{
	
	
	List<S3BucketObject> findByBucketNameAndObjectNameLike(String bucketName, String pattern);
	Long countByBucketName(String bucketName);
}
