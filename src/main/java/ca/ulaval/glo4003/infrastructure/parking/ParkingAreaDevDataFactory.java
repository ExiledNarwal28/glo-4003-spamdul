package ca.ulaval.glo4003.infrastructure.parking;

import ca.ulaval.glo4003.domain.parking.ParkingArea;
import ca.ulaval.glo4003.domain.parking.ParkingAreaCode;
import java.util.List;
import jersey.repackaged.com.google.common.collect.Lists;

public class ParkingAreaDevDataFactory {

  public List<ParkingArea> createMockData() {
    List<ParkingArea> parkingAreas = Lists.newArrayList();

    ParkingArea desjardins =
        new ParkingArea(new ParkingAreaCode("1ef66d3c-fd39-11ea-adc1-0242ac120002"));
    parkingAreas.add(desjardins);

    ParkingArea pouliot =
        new ParkingArea(new ParkingAreaCode("1ef66fd0-fd39-11ea-adc1-0242ac120002"));
    parkingAreas.add(pouliot);

    ParkingArea peps = new ParkingArea(new ParkingAreaCode("1ef670ca-fd39-11ea-adc1-0242ac120002"));
    parkingAreas.add(peps);

    return parkingAreas;
  }
}
