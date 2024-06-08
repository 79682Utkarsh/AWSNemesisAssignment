package com.nemesis.aws_assignment.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nemesis.aws_assignment.model.Job;
import com.nemesis.aws_assignment.repo.JobRepository;
import com.nemesis.aws_assignment.service.AwsService;

import java.util.List;

@RestController
@RequestMapping("/aws")
public class AwsController {

    @Autowired
    private AwsService awsService;

    @Autowired
    private JobRepository jobRepository;

      @PostMapping("/discover")
    public ResponseEntity<Long> discoverServices(@RequestBody List<String> services) {
        Job job = new Job();
        job.setStatus("IN_PROGRESS");
        job.setServiceName("DISCOVERY");
        jobRepository.save(job);

        Long jobId = job.getId();
        for (String service : services) {
            if (service.equals("EC2")) {
                awsService.discoverEC2Instances(jobId);
            } else if (service.equals("S3")) {
                awsService.discoverS3Buckets(jobId);
            }
        }

        return ResponseEntity.ok(jobId);
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<String> getJobStatus(@PathVariable Long jobId) {
        Job job = jobRepository.findById(jobId).orElseThrow();
        return ResponseEntity.ok(job.getStatus());
    }

    @GetMapping("/discovery/{service}")
    public ResponseEntity<?> getDiscoveryResult(@PathVariable String service) {
        if (service.equalsIgnoreCase("EC2")) {
            return ResponseEntity.ok(awsService.getEC2Instances());
        } else if (service.equalsIgnoreCase("S3")) {
            return ResponseEntity.ok(awsService.getS3Buckets());
        } else {
            return ResponseEntity.badRequest().body("Invalid service name");
        }
    }

    @PostMapping("/s3/{bucketName}")
    public ResponseEntity<Long> getS3BucketObjects(@PathVariable String bucketName) {
        Job job = new Job();
        job.setStatus("IN_PROGRESS");
        job.setServiceName("S3");
        jobRepository.save(job);

        Long jobId = job.getId();
        awsService.discoverS3BucketObjects(jobId, bucketName);

        return ResponseEntity.ok(jobId);
    }

    @GetMapping("/s3/{bucketName}/count")
    public ResponseEntity<Long> getS3BucketObjectCount(@PathVariable String bucketName) {
        return ResponseEntity.ok(awsService.countS3BucketObjects(bucketName));
    }

    @GetMapping("/s3/{bucketName}/objects")
    public ResponseEntity<List<String>> getS3BucketObjectLike(@PathVariable String bucketName, @RequestParam String pattern) {
        return ResponseEntity.ok(awsService.getS3BucketObjectsLike(bucketName, pattern));
    }
}
