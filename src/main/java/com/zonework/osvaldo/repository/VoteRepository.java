package com.zonework.osvaldo.repository;

import com.zonework.osvaldo.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Application, Long> {

}
