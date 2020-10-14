package ca.ulaval.glo4003.funds.api.dto;

public class BillDto {
  public String billId;
  public String billType;
  public String description;
  public String amountDue;
  public String amountPaid;

  @Override
  public String toString() {
    return String.format(
        "BillDto{billId='%s', billType='%s', description='%s', amountDue='%s', amountPaid='%s'}",
        billId, billType, description, amountDue, amountPaid);
  }
}
