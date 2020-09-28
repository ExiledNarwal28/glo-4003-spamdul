package ca.ulaval.glo4003.api.car;

import ca.ulaval.glo4003.api.car.dto.CarDTO;
import ca.ulaval.glo4003.domain.car.CarService;
import javax.ws.rs.core.Response;

public class CarResourceImplementation implements CarResource {

  private final CarService carService;

  public CarResourceImplementation(CarService carService) {
    this.carService = carService;
  }

  @Override
  public Response addCar(int userId, CarDTO carDTO) {
    carService.addCar(userId, carDTO);
    return Response.status(Response.Status.CREATED).build();
  }
}
