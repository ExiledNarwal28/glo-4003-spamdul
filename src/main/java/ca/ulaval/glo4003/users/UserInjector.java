package ca.ulaval.glo4003.users;

import ca.ulaval.glo4003.accesspasses.services.AccessPassService;
import ca.ulaval.glo4003.accounts.domain.AccountFactory;
import ca.ulaval.glo4003.accounts.domain.AccountRepository;
import ca.ulaval.glo4003.accounts.services.AccountService;
import ca.ulaval.glo4003.accounts.services.converters.AccountIdConverter;
import ca.ulaval.glo4003.cars.services.CarService;
import ca.ulaval.glo4003.parkings.services.ParkingStickerService;
import ca.ulaval.glo4003.times.services.assemblers.CustomDateAssembler;
import ca.ulaval.glo4003.users.api.UserResource;
import ca.ulaval.glo4003.users.services.UserService;
import ca.ulaval.glo4003.users.services.assemblers.UserAssembler;

public class UserInjector {

  public UserResource createUserResource(
      AccountRepository accountRepository,
      AccountFactory accountFactory,
      AccountIdConverter accountIdConverter,
      CustomDateAssembler customDateAssembler,
      AccessPassService accessPassService,
      CarService carService,
      AccountService accountService,
      ParkingStickerService parkingStickerService) {
    UserAssembler userAssembler = new UserAssembler(customDateAssembler);

    UserService userService =
        new UserService(accountRepository, accountFactory, accountIdConverter, userAssembler);

    return new UserResource(
        userService, accessPassService, carService, accountService, parkingStickerService);
  }
}
