package ca.ulaval.glo4003.domain.parking.exception;

public class MissingPostalCodeException extends ParkingException {
  private static final String ERROR = "Missing property : postalCode";
  private static final String DESCRIPTION = "Property postalCode is missing";

  public MissingPostalCodeException() {
    super(ERROR, DESCRIPTION);
  }
}