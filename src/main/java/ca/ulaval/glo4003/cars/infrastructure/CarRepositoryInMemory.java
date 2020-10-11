package ca.ulaval.glo4003.cars.infrastructure;

import ca.ulaval.glo4003.cars.domain.Car;
import ca.ulaval.glo4003.cars.domain.CarRepository;
import ca.ulaval.glo4003.cars.domain.LicensePlate;
import ca.ulaval.glo4003.cars.exceptions.NotFoundLicensePlate;
import java.util.HashMap;
import java.util.Map;

public class CarRepositoryInMemory implements CarRepository {
  private final Map<LicensePlate, Car> cars = new HashMap<>();

  @Override
  public LicensePlate save(Car car) {
    cars.put(car.getLicensePlate(), car);
    return car.getLicensePlate();
  }

  @Override
  public Car getCarByLicensePlate(LicensePlate licensePlate) {
    Car car = cars.get(licensePlate);

    if (car == null) throw new NotFoundLicensePlate();

    return car;
  }
}
