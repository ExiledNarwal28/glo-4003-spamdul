package ca.ulaval.glo4003.users.services.dto;

public class AccountIdDto {
  public String accountId;

  @Override
  public String toString() {
    return String.format("AccountIdDto{accountId='%s'}", accountId);
  }
}
