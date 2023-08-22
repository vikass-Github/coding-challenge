package com.coding.Java.Coding.Challenge2.repository;

import com.coding.Java.Coding.Challenge2.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserData,Long> {
}
