package com.nemesis.aws_assignment.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.nemesis.aws_assignment.model.EC2Instance;
import com.nemesis.aws_assignment.model.Job;
import com.nemesis.aws_assignment.model.S3Bucket;
import com.nemesis.aws_assignment.model.S3BucketObject;
import com.nemesis.aws_assignment.repo.EC2InstanceRepository;
import com.nemesis.aws_assignment.repo.JobRepository;
import com.nemesis.aws_assignment.repo.S3BucketObjectRepository;
import com.nemesis.aws_assignment.repo.S3BucketRepository;

import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Instance;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class AwsService {

    @Autowired
    private S3Client s3Client;

    @Autowired
    private Ec2Client ec2Client;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private EC2InstanceRepository ec2InstanceRepository;

    @Autowired
    private S3BucketRepository s3BucketRepository;

    @Autowired
    private S3BucketObjectRepository s3BucketObjectRepository;

    @Async
    public CompletableFuture<Void> discoverEC2Instances(Long jobId) {
        Job job = jobRepository.findById(jobId).orElseThrow();
        try {
            DescribeInstancesResponse response = ec2Client.describeInstances();
            List<Instance> instances = response.reservations().stream()
                    .flatMap(r -> r.instances().stream())
                    .collect(Collectors.toList());

            List<EC2Instance> ec2Instances = instances.stream()
                    .map(i -> {
                        EC2Instance ec2Instance = new EC2Instance();
                        ec2Instance.setInstanceId(i.instanceId());
                        return ec2Instance;
                    }).collect(Collectors.toList());

            ec2InstanceRepository.saveAll(ec2Instances);
            job.setStatus("SUCCESS");
        } catch (Exception e) {
            job.setStatus("FAILED");
        } finally {
            jobRepository.save(job);
        }
        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<Void> discoverS3Buckets(Long jobId) {
        Job job = jobRepository.findById(jobId).orElseThrow();
        try {
            ListBucketsResponse response = s3Client.listBuckets();
            List<Bucket> buckets = response.buckets();

            List<S3Bucket> s3Buckets = buckets.stream()
                    .map(b -> {
                        S3Bucket s3Bucket = new S3Bucket();
                        s3Bucket.setBucketName(b.name());
                        return s3Bucket;
                    }).collect(Collectors.toList());

            s3BucketRepository.saveAll(s3Buckets);
            job.setStatus("SUCCESS");
        } catch (Exception e) {
            job.setStatus("FAILED");
        } finally {
            jobRepository.save(job);
        }
        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<Void> discoverS3BucketObjects(Long jobId, String bucketName) {
        Job job = jobRepository.findById(jobId).orElseThrow();
        try {
            ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(bucketName).build();
            ListObjectsV2Response result = s3Client.listObjectsV2(request);

            List<S3BucketObject> objects = result.contents().stream()
                    .map(s3Object -> {
                        S3BucketObject object = new S3BucketObject();
                        object.setBucketName(bucketName);
                        object.setObjectName(s3Object.key());
                        return object;
                    }).collect(Collectors.toList());

            s3BucketObjectRepository.saveAll(objects);
            job.setStatus("SUCCESS");
        } catch (Exception e) {
            job.setStatus("FAILED");
        } finally {
            jobRepository.save(job);
        }
        return CompletableFuture.completedFuture(null);
    }

    public List<EC2Instance> getEC2Instances() {
        return ec2InstanceRepository.findAll();
    }

    public List<S3Bucket> getS3Buckets() {
        return s3BucketRepository.findAll();
    }

    public Long countS3BucketObjects(String bucketName) {
        return s3BucketObjectRepository.countByBucketName(bucketName);
    }

    public List<String> getS3BucketObjectsLike(String bucketName, String pattern) {
        return s3BucketObjectRepository.findByBucketNameAndObjectNameLike(bucketName, "%" + pattern + "%")
                .stream().map(S3BucketObject::getObjectName).collect(Collectors.toList());
    }
}