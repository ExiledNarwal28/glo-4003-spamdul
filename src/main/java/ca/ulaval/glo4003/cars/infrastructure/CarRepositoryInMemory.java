package ca.ulaval.glo4003.cars.infrastructure;

import ca.ulaval.glo4003.cars.domain.Car;
import ca.ulaval.glo4003.cars.domain.CarRepository;
import ca.ulaval.glo4003.cars.domain.LicensePlate;
import ca.ulaval.glo4003.cars.exceptions.AlreadyExistingLicensePlateException;
import ca.ulaval.glo4003.cars.exceptions.NotFoundLicensePlateException;
import java.util.HashMap;
import java.util.Map;

public class CarRepositoryInMemory implements CarRepository {
  private final Map<LicensePlate, Car> cars = new HashMap<>();

  @Override
  public LicensePlate save(Car car) {
    if (cars.containsKey(car.getLicensePlate())) {
      throw new AlreadyExistingLicensePlateException();
    }
    cars.put(car.getLicensePlate(), car);
    return car.getLicensePlate();
  }

  @Override
  public Car get(LicensePlate licensePlate) {
    Car car = cars.get(licensePlate);

    if (car == null) throw new NotFoundLicensePlateException();

    return car;
  }
}
