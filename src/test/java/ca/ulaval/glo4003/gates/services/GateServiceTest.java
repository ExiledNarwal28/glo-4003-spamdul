package ca.ulaval.glo4003.gates.services;

import static ca.ulaval.glo4003.accesspasses.helpers.AccessPassMother.createAccessPassCode;
import static ca.ulaval.glo4003.cars.helpers.LicensePlateMother.createLicensePlate;
import static ca.ulaval.glo4003.gates.helpers.AccessStatusDtoBuilder.anAccessStatusDto;
import static ca.ulaval.glo4003.gates.helpers.DateTimeDtoBuilder.aDateTimeDto;
import static ca.ulaval.glo4003.times.helpers.CustomDateTimeBuilder.aDateTime;
import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.accesspasses.domain.AccessPass;
import ca.ulaval.glo4003.accesspasses.domain.exceptions.InvalidAccessPassExitException;
import ca.ulaval.glo4003.accesspasses.services.AccessPassService;
import ca.ulaval.glo4003.cars.domain.LicensePlate;
import ca.ulaval.glo4003.cars.services.converters.LicensePlateConverter;
import ca.ulaval.glo4003.gates.services.dto.AccessStatusDto;
import ca.ulaval.glo4003.parkings.domain.AccessStatus;
import ca.ulaval.glo4003.parkings.services.assemblers.AccessStatusAssembler;
import ca.ulaval.glo4003.reports.services.ReportEventService;
import ca.ulaval.glo4003.times.domain.CustomDateTime;
import ca.ulaval.glo4003.times.services.converters.CustomDateTimeConverter;
import ca.ulaval.glo4003.times.services.dto.DateTimeDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GateServiceTest {
  @Mock private AccessPassService accessPassService;
  @Mock private CustomDateTimeConverter customDateTimeConverter;
  @Mock private AccessStatusAssembler accessStatusAssembler;
  @Mock private AccessPass accessPass;
  @Mock private LicensePlateConverter licensePlateConverter;
  @Mock private ReportEventService reportEventService;

  private GateService gateService;

  private final DateTimeDto dateTimeDto = aDateTimeDto().build();
  private final CustomDateTime dateTime = aDateTime().build();
  private final String accessPassCode = createAccessPassCode().toString();
  private final AccessStatusDto grantedAccessStatusDto = anAccessStatusDto().build();
  private final AccessStatusDto refusedAccessStatusDto = anAccessStatusDto().build();
  private final String accessPassLicensePlate = createLicensePlate().toString();
  private final LicensePlate LICENSE_PLATE = new LicensePlate(accessPassLicensePlate);
  private final List<AccessPass> accessPasses = new ArrayList<>();

  @Before
  public void setUp() {
    gateService =
        new GateService(
            accessPassService,
            customDateTimeConverter,
            accessStatusAssembler,
            licensePlateConverter,
            reportEventService);

    accessPasses.add(accessPass);

    when(customDateTimeConverter.convert(dateTimeDto)).thenReturn(dateTime);
    when(accessPassService.getAccessPass(accessPassCode)).thenReturn(accessPass);
    when(accessStatusAssembler.assemble(AccessStatus.ACCESS_GRANTED))
        .thenReturn(grantedAccessStatusDto);
    when(accessStatusAssembler.assemble(AccessStatus.ACCESS_REFUSED))
        .thenReturn(refusedAccessStatusDto);
    when(licensePlateConverter.convert(accessPassLicensePlate)).thenReturn(LICENSE_PLATE);
    when(accessPassService.getAccessPasses(LICENSE_PLATE)).thenReturn(accessPasses);
  }

  @Test
  public void givenValidAccessDay_whenValidatingAccessPassEntryWithCode_thenReturnAccessGranted() {
    when(accessPass.validateAccess(dateTime)).thenReturn(true);

    AccessStatusDto accessStatusDto =
        gateService.validateAccessPassEntryWithCode(dateTimeDto, accessPassCode);

    assertThat(accessStatusDto).isSameInstanceAs(grantedAccessStatusDto);
  }

  @Test
  public void givenValidAccessDay_whenValidatingAccessPassEntryWithCode_theShouldAdmitOnCampus() {
    when(accessPass.validateAccess(dateTime)).thenReturn(true);

    gateService.validateAccessPassEntryWithCode(dateTimeDto, accessPassCode);

    verify(accessPassService).enterCampus(accessPass);
  }

  @Test
  public void
      givenInvalidAccessDay_whenValidatingAccessPassEntryWithCode_thenReturnAccessRefused() {
    when(accessPass.validateAccess(dateTime)).thenReturn(false);

    AccessStatusDto accessStatusDto =
        gateService.validateAccessPassEntryWithCode(dateTimeDto, accessPassCode);

    assertThat(accessStatusDto).isSameInstanceAs(refusedAccessStatusDto);
  }

  @Test
  public void
      givenValidAccessDay_whenValidatingAccessPassEntryWithLicensePlate_thenReturnAccessGranted() {
    when(accessPass.validateAccess(dateTime)).thenReturn(true);

    AccessStatusDto accessStatusDto =
        gateService.validateAccessPassEntryWithLicensePlate(dateTimeDto, accessPassLicensePlate);

    assertThat(accessStatusDto).isSameInstanceAs(grantedAccessStatusDto);
  }

  @Test
  public void
      givenValidAccessDay_whenValidatingAccessPassEntryWithLicensePlate_theShouldAdmitOnCampus() {
    when(accessPass.validateAccess(dateTime)).thenReturn(true);

    gateService.validateAccessPassEntryWithLicensePlate(dateTimeDto, accessPassLicensePlate);

    verify(accessPassService).enterCampus(accessPass);
  }

  @Test
  public void
      givenInvalidAccessDay_whenValidatingAccessPassEntryWithLicensePlate_thenReturnAccessRefused() {
    when(accessPass.validateAccess(dateTime)).thenReturn(false);

    AccessStatusDto accessStatusDto =
        gateService.validateAccessPassEntryWithLicensePlate(dateTimeDto, accessPassLicensePlate);

    assertThat(accessStatusDto).isSameInstanceAs(refusedAccessStatusDto);
  }

  @Test
  public void whenValidateAccessPassExitWithCode_thenExitCampusHasBeenCalled() {
    gateService.validateAccessPassExitWithCode(accessPassCode);

    verify(accessPassService).exitCampus(accessPass);
  }

  @Test
  public void
      givenAccessPassAdmittedOnCampus_whenValidateAccessPassExitWithLicensePlate_thenExitCampusHasBeenCalled() {
    when(accessPass.isAdmittedOnCampus()).thenReturn(true);

    gateService.validateAccessPassExitWithLicensePlate(accessPassLicensePlate);

    verify(accessPassService).exitCampus(accessPass);
  }

  @Test(expected = InvalidAccessPassExitException.class)
  public void
      givenAccessPassNotAdmittedOnCampus_whenValidateAccessPassExitWithLicensePlate_thenThrowInvalidAccessPassExitException() {
    when(accessPass.isAdmittedOnCampus()).thenReturn(false);

    gateService.validateAccessPassExitWithLicensePlate(accessPassLicensePlate);
  }

  @Test
  public void whenValidateAccessPassEntryWithLicensePlate_thenAddReportEvent() {
    when(accessPass.validateAccess(dateTime)).thenReturn(true);

    gateService.validateAccessPassEntryWithLicensePlate(dateTimeDto, accessPassLicensePlate);

    verify(reportEventService).addGateEnteredEvent(dateTime, accessPass.getParkingAreaCode());
  }

  @Test
  public void whenValidateAccessPassEntryWithCode_thenAddReportEvent() {
    when(accessPass.validateAccess(dateTime)).thenReturn(true);

    gateService.validateAccessPassEntryWithCode(dateTimeDto, accessPassCode);

    verify(reportEventService).addGateEnteredEvent(dateTime, accessPass.getParkingAreaCode());
  }
}
