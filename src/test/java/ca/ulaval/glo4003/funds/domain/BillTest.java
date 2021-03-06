package ca.ulaval.glo4003.funds.domain;

import static ca.ulaval.glo4003.funds.helpers.BillMother.*;
import static ca.ulaval.glo4003.funds.helpers.MoneyMother.createMoney;
import static ca.ulaval.glo4003.times.helpers.CustomDateTimeMother.createDateTime;

import ca.ulaval.glo4003.funds.domain.exceptions.AmountDueExceededException;
import ca.ulaval.glo4003.times.domain.CustomDateTime;
import com.google.common.truth.Truth;
import org.junit.Test;

public class BillTest {

  private final BillId billId = createBillId();
  private final String description = createDescription();
  private final Money amountDue = createMoney();
  private final BillType billType = createBillType();
  private final CustomDateTime customDateTime = createDateTime();

  @Test
  public void whenConstructing_setAmountPaidToZero() {
    Money expectedAmountPaid = Money.zero();

    Bill bill = new Bill(billId, billType, description, amountDue, customDateTime);

    Truth.assertThat(bill.getAmountPaid()).isEqualTo(expectedAmountPaid);
  }

  @Test
  public void whenPaying_thenAmountIsPaid() {
    Bill bill = new Bill(billId, billType, description, amountDue, customDateTime);

    bill.pay(amountDue);

    Truth.assertThat(bill.getAmountDue()).isEqualTo(Money.zero());
    Truth.assertThat(bill.getAmountPaid()).isEqualTo(amountDue);
  }

  @Test(expected = AmountDueExceededException.class)
  public void whenPayingMoreThanAmountDue_thenThrowTooMuchMoney() {
    Bill bill = new Bill(billId, billType, description, amountDue, customDateTime);

    bill.pay(amountDue.plus(Money.fromDouble(10)));
  }
}
