package com.learning.repository;

import com.learning.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardProcessingRepository extends JpaRepository<CreditCard, Long> {

}
