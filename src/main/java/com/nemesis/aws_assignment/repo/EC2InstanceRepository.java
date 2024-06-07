package com.nemesis.aws_assignment.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nemesis.aws_assignment.model.EC2Instance;

public interface EC2InstanceRepository extends JpaRepository<EC2Instance, Long>{

}
