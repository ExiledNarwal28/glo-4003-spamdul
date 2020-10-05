package ca.ulaval.glo4003.funds.helpers;

import ca.ulaval.glo4003.funds.domain.Money;
import com.github.javafaker.Faker;

public class MoneyMother {
  public static Money createMoney() {
    double amount = Faker.instance().number().numberBetween(1, 200);
    return new Money(amount);
  }
}
