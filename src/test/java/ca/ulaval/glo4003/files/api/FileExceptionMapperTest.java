package ca.ulaval.glo4003.files.api;

import ca.ulaval.glo4003.files.domain.exceptions.FileException;
import ca.ulaval.glo4003.files.domain.exceptions.InvalidFileException;
import com.google.common.truth.Truth;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;

public class FileExceptionMapperTest {
  private FileExceptionMapper fileExceptionMapper;

  @Before
  public void setUp() {
    fileExceptionMapper = new FileExceptionMapper();
  }

  @Test
  public void givenInvalidTimeException_whenResponding_thenStatusIsBadRequest() {
    FileException fileException = new InvalidFileException();

    Response response = fileExceptionMapper.toResponse(fileException);

    Truth.assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
  }
}
