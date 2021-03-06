package ca.ulaval.glo4003.errors.domain.exceptions;

import ca.ulaval.glo4003.errors.domain.ErrorCode;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class ApplicationException extends RuntimeException {
  private final String error;
  private final String description;
  private final ErrorCode code;

  protected ApplicationException(String error, String description, ErrorCode errorCode) {
    super(error);
    this.error = error;
    this.description = description;
    this.code = errorCode;
  }

  public String getError() {
    return error;
  }

  public String getDescription() {
    return description;
  }

  public ErrorCode getCode() {
    return code;
  }

  protected <T extends Enum<?>> String enumerateValues(Class<T> enumToEnumerate) {
    List<String> values =
        Stream.of(enumToEnumerate.getEnumConstants())
            .map(Enum::toString)
            .collect(Collectors.toList());

    return enumerateStrings(values);
  }

  protected String enumerateStrings(List<String> values) {
    int lastIndex = values.size() - 1;
    return String.join(
        " or ", String.join(", ", values.subList(0, lastIndex)), values.get(lastIndex));
  }
}
