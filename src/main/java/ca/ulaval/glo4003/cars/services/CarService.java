package ca.ulaval.glo4003.cars.services;

import ca.ulaval.glo4003.accounts.services.AccountService;
import ca.ulaval.glo4003.cars.api.dto.CarDto;
import ca.ulaval.glo4003.cars.assemblers.CarAssembler;
import ca.ulaval.glo4003.cars.domain.Car;
import ca.ulaval.glo4003.cars.domain.CarRepository;
import ca.ulaval.glo4003.cars.domain.LicensePlate;

public class CarService {

  private final CarAssembler carAssembler;
  private final CarRepository carRepository;
  private final AccountService accountService;

  public CarService(
      CarAssembler carAssembler, CarRepository carRepository, AccountService accountService) {
    this.carAssembler = carAssembler;
    this.carRepository = carRepository;
    this.accountService = accountService;
  }

  public void addCar(CarDto carDto, String accountId) {
    Car car = carAssembler.assemble(carDto, accountId);

    accountService.addLicensePlateToAccount(car.getAccountId(), car.getLicensePlate());

    carRepository.save(car);
  }

  public Car getCar(LicensePlate licensePlate) {
    return carRepository.get(licensePlate);
  }
}
