package ca.ulaval.glo4003.initiative.helpers;

import static ca.ulaval.glo4003.funds.helpers.MoneyMother.createMoney;

import ca.ulaval.glo4003.initiative.api.dto.InitiativeAvailableAmountDto;

public class InitiativeAvailableAmountDtoBuilder {
  private double availableAmount = createMoney().toDouble();

  private InitiativeAvailableAmountDtoBuilder() {}

  public static InitiativeAvailableAmountDtoBuilder aInitiativeAvailableAmountDto() {
    return new InitiativeAvailableAmountDtoBuilder();
  }

  public InitiativeAvailableAmountDtoBuilder withAvailableAmount(Double availableAmount) {
    this.availableAmount = availableAmount;
    return this;
  }

  public InitiativeAvailableAmountDto build() {
    InitiativeAvailableAmountDto initiativeAvailableAmountDto = new InitiativeAvailableAmountDto();
    initiativeAvailableAmountDto.availableAmount = availableAmount;
    return initiativeAvailableAmountDto;
  }
}
