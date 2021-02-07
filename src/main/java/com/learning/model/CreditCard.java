package com.learning.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * DB Entity Class maps to the Table credit_card_details
 */
@Entity
@Table(name = "credit_card_details")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "card_name")
    private String name;

    @Column(name = "card_number")
    private BigInteger cardNumber;

    @Column(name = "card_limit")
    private BigDecimal limit;

    @Column(name = "card_balance")
    private BigDecimal balance;

    CreditCard(final Long id, final String name, final BigInteger cardNumber, final BigDecimal limit, final BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.cardNumber = cardNumber;
        this.limit = limit;
        this.balance = balance;
    }

    /** Used by spring JPA to populate data from the database*/
        public CreditCard() {
        // default constructor
    }

    public String getName() {
        return name;
    }

    public BigInteger getCardNumber() {
        return cardNumber;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public static CreditCard.CreditCardBuilder builder() {
        return new CreditCard.CreditCardBuilder();
    }

    public static class CreditCardBuilder {
        private Long id;
        private String name;
        private BigInteger cardNumber;
        private BigDecimal limit;
        private BigDecimal balance;

        CreditCardBuilder() {
        }

        public CreditCard.CreditCardBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public CreditCard.CreditCardBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public CreditCard.CreditCardBuilder cardNumber(final BigInteger cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public CreditCard.CreditCardBuilder limit(final BigDecimal limit) {
            this.limit = limit;
            return this;
        }

        public CreditCard.CreditCardBuilder balance(final BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public CreditCard build() {
            return new CreditCard(this.id, this.name, this.cardNumber, this.limit, this.balance);
        }
    }
}
