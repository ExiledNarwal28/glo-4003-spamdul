package ca.ulaval.glo4003.parkings.api.dto;

public class AccessStatusDto {
  public String accessStatus;

  @Override
  public String toString() {
    return String.format("accessStatus='%s'", accessStatus);
  }
}