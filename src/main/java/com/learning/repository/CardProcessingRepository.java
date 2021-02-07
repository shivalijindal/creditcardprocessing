package com.learning.repository;

import com.learning.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface CardProcessingRepository extends JpaRepository<CreditCard, Long> {

    @Query("SELECT t FROM CreditCard t where t.cardNumber = :cardNumber")
    Optional<CreditCard> findByCardNumber(BigInteger cardNumber);

}
