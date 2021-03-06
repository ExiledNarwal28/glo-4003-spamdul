package ca.ulaval.glo4003.accesspasses.helpers;

import static ca.ulaval.glo4003.cars.helpers.LicensePlateMother.createLicensePlate;
import static ca.ulaval.glo4003.parkings.helpers.ParkingAreaMother.createParkingAreaCode;
import static ca.ulaval.glo4003.times.helpers.DayOfWeekMother.createDayOfWeek;

import ca.ulaval.glo4003.accesspasses.domain.AccessPeriod;
import ca.ulaval.glo4003.accesspasses.services.dto.AccessPassDto;

public class AccessPassDtoBuilder {
  private String accessDay = createDayOfWeek().toString();
  private String licensePlate = createLicensePlate().toString();
  private String accessPeriod = AccessPeriod.ONE_SEMESTER.toString();
  private String[] semesters;
  private String parkingArea = createParkingAreaCode().toString();

  public static AccessPassDtoBuilder anAccessPassDto() {
    return new AccessPassDtoBuilder();
  }

  public AccessPassDtoBuilder withAccessDay(String accessDay) {
    this.accessDay = accessDay;
    return this;
  }

  public AccessPassDtoBuilder withLicensePlate(String licensePlate) {
    this.licensePlate = licensePlate;
    return this;
  }

  public AccessPassDtoBuilder withAccessPeriod(String accessPeriod) {
    this.accessPeriod = accessPeriod;
    return this;
  }

  public AccessPassDtoBuilder withSemesters(String[] semesters) {
    this.semesters = semesters;
    return this;
  }

  public AccessPassDtoBuilder withParkingAea(String parkingArea) {
    this.parkingArea = parkingArea;
    return this;
  }

  public AccessPassDto build() {
    AccessPassDto accessPassDto = new AccessPassDto();
    accessPassDto.accessDay = accessDay;
    accessPassDto.licensePlate = licensePlate;
    accessPassDto.period = accessPeriod;
    accessPassDto.semesters = semesters;
    accessPassDto.parkingArea = parkingArea;
    return accessPassDto;
  }
}
