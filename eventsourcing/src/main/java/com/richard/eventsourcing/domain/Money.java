package com.richard.eventsourcing.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.richard.eventsourcing.MoneySerializer;
import java.math.BigDecimal;

//@JsonDeserialize(using = MoneyDeserializer.class)
@JsonSerialize(using = MoneySerializer.class)
public record Money(BigDecimal amount, String currencyCode) {

  private static final int DEFAULT_DECIMAL_PLACES = 2;

  public Money {
//    if (currencyLookup == null) {
//      currencyLookup = new DefaultCurrencyLookup();
//    }
//    if (currencyCode == null || currencyCode.isEmpty()) {
//      throw new IllegalArgumentException("currency code must be specified");
//    }
//
//    var currency = currencyLookup.findCurrency(currencyCode);
//    if (!currency.inUse()) {
//      throw new IllegalArgumentException("Currency " + currencyCode + " is not valid");
//    }
//
//    BigDecimal roundedAmount = amount.setScale(currency.decimalPlaces(), RoundingMode.CEILING);
//    if (!roundedAmount.equals(amount)) {
//      throw new IllegalArgumentException(String.format("Amount in %s cannot have more than %d decimals",
//          currencyCode, currency.decimalPlaces()));
//    }
  }

  public static Money fromDecimal(double amount, String currencyCode) {
//    var amt = BigDecimal.valueOf(amount)
//        .setScale(DEFAULT_DECIMAL_PLACES, RoundingMode.CEILING);
//    return new Money(amt, currencyCode, CurrencyLookupContext.defaultCurrencyLookup());
    return null;
  }

//  public static Money fromDecimal(double amount, String currencyCode, CurrencyLookup currencyLookup) {
////    var amt = BigDecimal.valueOf(amount)
////        .setScale(DEFAULT_DECIMAL_PLACES, RoundingMode.CEILING);
////    return new Money(amt, currencyCode, currencyLookup);
//  }

//  public Money(String amount, String currency) {
//    this(new BigDecimal(amount), currency, CurrencyLookupContext.defaultCurrencyLookup());
//  }
//
//  public Money(BigDecimal amount, String currency) {
//    this(amount, currency, new DefaultCurrencyLookup());
//  }
//
//  public static Money fromString(String amount, String currencyCode, CurrencyLookup currencyLookup) {
//    return new Money(new BigDecimal(amount), currencyCode, currencyLookup);
//  }
//
//  @Override
//  public boolean equals(Object o) {
//    if (this == o) {
//      return true;
//    }
//    if (o == null || getClass() != o.getClass()) {
//      return false;
//    }
//    Money money = (Money) o;
//    return Objects.equals(amount, money.amount) &&
//        Objects.equals(currencyCode, money.currencyCode);
//  }
//
//  @Override
//  public int hashCode() {
//    return Objects.hash(amount, currencyCode);
//  }
//
//  @Override
//  public String toString() {
//    return String.format("%s %s", currencyCode, amount);
//  }
//
//  public Money subtract(Money subtrahend) {
//    if (!currencyCode.equals(subtrahend.currencyCode)) {
//      throw new CurrencyMismatchException("Cannot subtract amounts with different currencies");
//    }
//
//    return new Money(amount.subtract(subtrahend.amount), subtrahend.currencyCode);
//  }
//
//  public Money add(Money summand) {
//    if (!currencyCode.equals(summand.currencyCode)) {
//      throw new CurrencyMismatchException(
//          "Cannot sum amounts with different currencies");
//    }
//
//    return new Money(amount.add(summand.amount), summand.currencyCode);
//  }

}
