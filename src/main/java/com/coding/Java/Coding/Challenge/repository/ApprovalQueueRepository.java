package com.coding.Java.Coding.Challenge.repository;

import com.coding.Java.Coding.Challenge.entity.ApprovalQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApprovalQueueRepository extends JpaRepository<ApprovalQueue,Long> {

    public Optional<ApprovalQueue> findApprovalQueueByProductId(Long productId);
}
