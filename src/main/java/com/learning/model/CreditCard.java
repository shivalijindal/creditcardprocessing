package com.learning.model;

import javax.persistence.*;

@Entity
@Table(name = "credit_card_details")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "card_name")
    private String name;

    @Column(name = "card_number")
    private Long cardNumber;

    @Column(name = "card_limit")
    private Double limit;

    @Column(name = "card_balance")
    private Double balance;

    CreditCard(final Long id, final String name, final Long cardNumber, final Double limit, final Double balance) {
        this.id = id;
        this.name = name;
        this.cardNumber = cardNumber;
        this.limit = limit;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public Double getLimit() {
        return limit;
    }

    public Double getBalance() {
        return balance;
    }

    public static CreditCard.CreditCardBuilder builder() {
        return new CreditCard.CreditCardBuilder();
    }

    public static class CreditCardBuilder {
        private Long id;
        private String name;
        private Long cardNumber;
        private Double limit;
        private Double balance;

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

        public CreditCard.CreditCardBuilder cardNumber(final Long cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public CreditCard.CreditCardBuilder limit(final Double limit) {
            this.limit = limit;
            return this;
        }

        public CreditCard.CreditCardBuilder balance(final Double balance) {
            this.balance = balance;
            return this;
        }

        public CreditCard build() {
            return new CreditCard(this.id, this.name, this.cardNumber, this.limit, this.balance);
        }
    }
}
