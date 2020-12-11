package ca.ulaval.glo4003.errors.api;

import ca.ulaval.glo4003.errors.domain.ErrorCode;
import javax.ws.rs.core.Response.Status;

public class ErrorResponseStatusConverter {

  // TODO #305 : Test this
  public Status convert(ErrorCode errorCode) {
    switch (errorCode) {
      case NOT_FOUND:
        return Status.NOT_FOUND;
      case ALREADY_EXISTING:
        return Status.CONFLICT; // TODO : Change Postman and doc API
      case UNSUPPORTED_OPERATION:
        return Status.NOT_IMPLEMENTED;
      case APPLICATION_FAILURE:
        return Status.INTERNAL_SERVER_ERROR;
      default:
      case INVALID_REQUEST:
        return Status.BAD_REQUEST;
    }
  }
}
