package ca.ulaval.glo4003.funds.domain;

import com.google.common.truth.Truth;
import org.junit.Test;

public class MoneyTest {

  @Test
  public void whenAddingMoney_thenReturnAddedMoney() {
    Money money = Money.fromDouble(5);
    Money addedMoney = Money.fromDouble(10);
    Money expectedMoney = Money.fromDouble(15);

    Money actualMoney = money.plus(addedMoney);

    Truth.assertThat(actualMoney).isEqualTo(expectedMoney);
  }
}
