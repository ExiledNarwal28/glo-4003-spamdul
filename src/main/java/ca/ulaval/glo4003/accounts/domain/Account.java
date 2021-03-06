package ca.ulaval.glo4003.accounts.domain;

import ca.ulaval.glo4003.accesspasses.domain.AccessPass;
import ca.ulaval.glo4003.accesspasses.domain.AccessPassCode;
import ca.ulaval.glo4003.cars.domain.Car;
import ca.ulaval.glo4003.cars.domain.LicensePlate;
import ca.ulaval.glo4003.cars.domain.exceptions.AlreadyExistingCarException;
import ca.ulaval.glo4003.funds.domain.Bill;
import ca.ulaval.glo4003.funds.domain.BillId;
import ca.ulaval.glo4003.parkings.domain.ParkingSticker;
import ca.ulaval.glo4003.parkings.domain.ParkingStickerCode;
import ca.ulaval.glo4003.users.domain.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Account {
  private final AccountId id;
  private final User user;
  private final Map<AccessPassCode, AccessPass> accessPasses = new HashMap<>();
  private final Map<LicensePlate, Car> cars = new HashMap<>();
  private final Map<ParkingStickerCode, ParkingSticker> parkingStickers = new HashMap<>();
  private final Map<BillId, Bill> bills = new HashMap<>();

  public Account(AccountId id, User user) {
    this.id = id;
    this.user = user;
  }

  public AccountId getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public AccessPass getAccessPass(AccessPassCode accessPassCode) {
    return accessPasses.get(accessPassCode);
  }

  public List<AccessPass> getAccessPasses(LicensePlate licensePlate) {
    return accessPasses.values().stream()
        .filter(accessPass -> licensePlate.equals(accessPass.getLicensePlate()))
        .collect(Collectors.toList());
  }

  public Car getCar(LicensePlate licensePlate) {
    return cars.get(licensePlate);
  }

  public List<Car> getCars() {
    return new ArrayList<>(cars.values());
  }

  public Bill getBill(BillId billId) {
    return bills.get(billId);
  }

  public List<Bill> getBills() {
    return new ArrayList<>(bills.values());
  }

  public ParkingSticker getParkingSticker(ParkingStickerCode parkingStickerCode) {
    return parkingStickers.get(parkingStickerCode);
  }

  public void addAccessPass(AccessPass accessPass) {
    accessPasses.put(accessPass.getCode(), accessPass);
  }

  public void saveCar(Car car) {
    if (cars.get(car.getLicensePlate()) != null) throw new AlreadyExistingCarException();

    cars.put(car.getLicensePlate(), car);
  }

  public void addParkingSticker(ParkingSticker parkingSticker) {
    parkingStickers.put(parkingSticker.getCode(), parkingSticker);
  }

  public void addBill(Bill bill) {
    bills.put(bill.getId(), bill);
  }
}
