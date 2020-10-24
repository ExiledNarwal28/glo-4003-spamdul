package ca.ulaval.glo4003.initiative.assembler;

import ca.ulaval.glo4003.funds.assemblers.MoneyAssembler;
import ca.ulaval.glo4003.funds.domain.Money;
import ca.ulaval.glo4003.initiative.api.dto.InitiativeAddAllocatedAmountDto;

public class InitiativeAddAllocatedAmountAssembler {

  private MoneyAssembler moneyAssembler;

  public InitiativeAddAllocatedAmountAssembler(MoneyAssembler moneyAssembler) {
    this.moneyAssembler = moneyAssembler;
  }

  public Money assemble(InitiativeAddAllocatedAmountDto InitiativeAddAllocatedAmount) {
    return moneyAssembler.assemble(InitiativeAddAllocatedAmount.amountToAdd);
  }
}
