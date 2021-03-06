package ca.ulaval.glo4003.carboncredits.infrastructure;

import ca.ulaval.glo4003.carboncredits.domain.CarbonCredit;
import ca.ulaval.glo4003.carboncredits.domain.CarbonCreditRepository;

public class CarbonCreditRepositoryInMemory implements CarbonCreditRepository {
  private CarbonCredit carbonCreditAmount = CarbonCredit.zero();

  @Override
  public void add(CarbonCredit carbonCredit) {
    this.carbonCreditAmount = carbonCreditAmount.plus(carbonCredit);
  }

  @Override
  public CarbonCredit get() {
    return carbonCreditAmount;
  }
}
