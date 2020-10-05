package ca.ulaval.glo4003.parkings;

import ca.ulaval.glo4003.accounts.assemblers.AccountIdAssembler;
import ca.ulaval.glo4003.accounts.domain.AccountRepository;
import ca.ulaval.glo4003.communications.assemblers.EmailAddressAssembler;
import ca.ulaval.glo4003.communications.domain.EmailSender;
import ca.ulaval.glo4003.files.domain.StringMatrixFileHelper;
import ca.ulaval.glo4003.files.filesystem.CsvHelper;
import ca.ulaval.glo4003.funds.filesystem.ZoneFeesFileHelper;
import ca.ulaval.glo4003.funds.services.BillService;
import ca.ulaval.glo4003.locations.assemblers.PostalCodeAssembler;
import ca.ulaval.glo4003.locations.domain.PostalSender;
import ca.ulaval.glo4003.parkings.api.ParkingResource;
import ca.ulaval.glo4003.parkings.api.ParkingResourceImplementation;
import ca.ulaval.glo4003.parkings.assemblers.AccessStatusAssembler;
import ca.ulaval.glo4003.parkings.assemblers.ParkingAreaCodeAssembler;
import ca.ulaval.glo4003.parkings.assemblers.ParkingStickerAssembler;
import ca.ulaval.glo4003.parkings.assemblers.ParkingStickerCodeAssembler;
import ca.ulaval.glo4003.parkings.domain.*;
import ca.ulaval.glo4003.parkings.infrastructure.ParkingAreaRepositoryInMemory;
import ca.ulaval.glo4003.parkings.infrastructure.ParkingStickerRepositoryInMemory;
import ca.ulaval.glo4003.parkings.services.ParkingService;
import java.util.List;
import java.util.stream.Collectors;

public class ParkingInjector {

  private final ParkingStickerCodeGenerator parkingStickerCodeGenerator;
  private final ParkingAreaRepository parkingAreaRepository;
  private final ParkingStickerRepository parkingStickerRepository;

  public ParkingInjector() {
    parkingStickerCodeGenerator = new ParkingStickerCodeGenerator();
    parkingAreaRepository = new ParkingAreaRepositoryInMemory();
    parkingStickerRepository = new ParkingStickerRepositoryInMemory();
  }

  public ParkingResource createParkingResource(
      boolean isDev,
      AccountIdAssembler accountIdAssembler,
      PostalCodeAssembler postalCodeAssembler,
      EmailAddressAssembler emailAddressAssembler,
      EmailSender emailSender,
      PostalSender postalSender,
      AccountRepository accountRepository,
      BillService billService) {
    ParkingAreaCodeAssembler parkingAreaCodeAssembler = new ParkingAreaCodeAssembler();

    if (isDev) {
      StringMatrixFileHelper fileHelper = new CsvHelper();
      ZoneFeesFileHelper zoneFeesFileHelper = new ZoneFeesFileHelper(fileHelper);
      List<String> zones = zoneFeesFileHelper.getAllZones();
      List<ParkingArea> parkingAreas =
          zones.stream()
              .map(zone -> new ParkingArea(parkingAreaCodeAssembler.assemble(zone)))
              .collect(Collectors.toList());
      parkingAreas.forEach(parkingAreaRepository::save);
    }

    ParkingStickerAssembler parkingStickerAssembler =
        new ParkingStickerAssembler(
            parkingAreaCodeAssembler,
            accountIdAssembler,
            postalCodeAssembler,
            emailAddressAssembler);
    ParkingStickerCodeAssembler parkingStickerCodeAssembler = new ParkingStickerCodeAssembler();
    AccessStatusAssembler accessStatusAssembler = new AccessStatusAssembler();

    ParkingStickerFactory parkingStickerFactory =
        new ParkingStickerFactory(parkingStickerCodeGenerator);

    ParkingService parkingService =
        new ParkingService(
            parkingStickerAssembler,
            parkingStickerCodeAssembler,
            parkingStickerFactory,
            accountRepository,
            parkingAreaRepository,
            parkingStickerRepository,
            accessStatusAssembler,
            emailSender,
            postalSender,
            billService);

    return new ParkingResourceImplementation(parkingService);
  }

  public ParkingStickerRepository getParkingStickerRepository() {
    return parkingStickerRepository;
  }

  public ParkingStickerCodeAssembler createParkingStickerCodeAssembler() {
    return new ParkingStickerCodeAssembler();
  }

  public ParkingAreaCodeAssembler createParkingAreaCodeAssembler() {
    return new ParkingAreaCodeAssembler();
  }
}
