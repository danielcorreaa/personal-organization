package com.personal_organization.domain.valueobject;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void shouldAddMoneyCorrectly() {
        Money m1 = new Money(new BigDecimal("100"));
        Money m2 = new Money(new BigDecimal("50"));

        Money result = m1.add(m2);

        assertEquals(new BigDecimal("150"), result.getAmount());
    }

    @Test
    void shouldAllowZeroMoney() {
        Money money = new Money(new BigDecimal("0"));
        assertEquals(new BigDecimal("0"), money.getAmount());
    }
}