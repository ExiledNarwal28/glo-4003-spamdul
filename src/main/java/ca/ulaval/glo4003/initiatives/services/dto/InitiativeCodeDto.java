package ca.ulaval.glo4003.initiatives.services.dto;

public class InitiativeCodeDto {
  public String initiativeCode;

  @Override
  public String toString() {
    return String.format("InitiativeCodeDto{initiativeCode='%s'}", initiativeCode);
  }
}
