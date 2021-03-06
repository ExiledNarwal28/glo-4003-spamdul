package ca.ulaval.glo4003.initiatives.domain.exceptions;

import ca.ulaval.glo4003.errors.domain.ErrorCode;
import ca.ulaval.glo4003.errors.domain.exceptions.ApplicationException;

public class InvalidInitiativeNameException extends ApplicationException {
  private static final String ERROR = "Invalid initiative name";
  private static final String DESCRIPTION = "Initiative name cannot be null";
  private static final ErrorCode CODE = ErrorCode.INVALID_REQUEST;

  public InvalidInitiativeNameException() {
    super(ERROR, DESCRIPTION, CODE);
  }
}
