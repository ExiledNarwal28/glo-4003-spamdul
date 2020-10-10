package ca.ulaval.glo4003.funds;

import ca.ulaval.glo4003.funds.assemblers.MoneyAssembler;
import ca.ulaval.glo4003.funds.services.BillService;
import com.google.common.truth.Truth;
import org.junit.Before;
import org.junit.Test;

public class FundInjectorTest {

  private FundInjector fundInjector;

  @Before
  public void setUp() {
    fundInjector = new FundInjector();
  }

  @Test
  public void whenCreatingBillService_thenReturnIt() {
    BillService billService = fundInjector.createBillService();

    Truth.assertThat(billService).isNotNull();
  }

  @Test
  public void whenCreatingMoneyAssembler_thenReturnIt() {
    MoneyAssembler moneyAssembler = fundInjector.createMoneyAssembler();

    Truth.assertThat(moneyAssembler).isNotNull();
  }
}
