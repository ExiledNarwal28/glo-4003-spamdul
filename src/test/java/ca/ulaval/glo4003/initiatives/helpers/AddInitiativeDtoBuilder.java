package ca.ulaval.glo4003.initiatives.helpers;

import static ca.ulaval.glo4003.funds.helpers.MoneyMother.createMoney;
import static ca.ulaval.glo4003.initiatives.helpers.InitiativeMother.*;

import ca.ulaval.glo4003.initiatives.services.dto.AddInitiativeDto;

public class AddInitiativeDtoBuilder {
  private String name = createInitiativeName();
  private Double amount = createMoney().toDouble();

  private AddInitiativeDtoBuilder() {}

  public static AddInitiativeDtoBuilder anAddInitiativeDto() {
    return new AddInitiativeDtoBuilder();
  }

  public AddInitiativeDtoBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public AddInitiativeDto build() {
    AddInitiativeDto addInitiativeDto = new AddInitiativeDto();
    addInitiativeDto.name = name;
    addInitiativeDto.amount = amount;
    return addInitiativeDto;
  }
}
