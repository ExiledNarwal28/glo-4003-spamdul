package ca.ulaval.glo4003.reports.exceptions;

// TODO : Add ReportExceptionMapper
public abstract class ReportException extends RuntimeException {
  public String error;
  public String description;

  ReportException(String error, String description) {
    this.error = error;
    this.description = description;
  }
}
