package ca.ulaval.glo4003.parkings.domain;

import ca.ulaval.glo4003.funds.domain.Money;
import java.util.Map;

public class ParkingArea {
  private final ParkingAreaCode code;
  private final Map<ParkingPeriod, Money> feePerPeriod;

  public ParkingArea(ParkingAreaCode code, Map<ParkingPeriod, Money> feePerPeriod) {
    this.code = code;
    this.feePerPeriod = feePerPeriod;
  }

  public ParkingAreaCode getCode() {
    return code;
  }

  public Money getFeeForPeriod(ParkingPeriod period) {
    return feePerPeriod.get(period);
  }

  public Map<ParkingPeriod, Money> getFeeForAllPeriod() {
    return this.feePerPeriod;
  }
}
