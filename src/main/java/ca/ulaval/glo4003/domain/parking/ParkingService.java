package ca.ulaval.glo4003.domain.parking;

import ca.ulaval.glo4003.api.parking.dto.ParkingStickerCodeDto;
import ca.ulaval.glo4003.api.parking.dto.ParkingStickerDto;
import ca.ulaval.glo4003.domain.account.Account;
import ca.ulaval.glo4003.domain.account.AccountRepository;
import java.util.logging.Logger;
import javax.inject.Inject;

public class ParkingService {
  private final Logger logger = Logger.getLogger(ParkingService.class.getName());
  private final ParkingStickerAssembler parkingStickerAssembler;
  private final ParkingStickerCodeAssembler parkingStickerCodeAssembler;
  private final ParkingStickerFactory parkingStickerFactory;
  private final AccountRepository accountRepository;
  private final ParkingAreaRepository parkingAreaRepository;
  private final ParkingStickerRepository parkingStickerRepository;

  @Inject
  public ParkingService(
      ParkingStickerAssembler parkingStickerAssembler,
      ParkingStickerCodeAssembler parkingStickerCodeAssembler,
      ParkingStickerFactory parkingStickerFactory,
      AccountRepository accountRepository,
      ParkingAreaRepository parkingAreaRepository,
      ParkingStickerRepository parkingStickerRepository) {
    this.parkingStickerAssembler = parkingStickerAssembler;
    this.parkingStickerCodeAssembler = parkingStickerCodeAssembler;
    this.parkingStickerFactory = parkingStickerFactory;
    this.accountRepository = accountRepository;
    this.parkingAreaRepository = parkingAreaRepository;
    this.parkingStickerRepository = parkingStickerRepository;
  }

  public ParkingStickerCodeDto addParkingSticker(ParkingStickerDto parkingStickerDto) {
    logger.info(String.format("Add new parking sticker %s", parkingStickerDto));

    ParkingSticker parkingSticker = parkingStickerAssembler.assemble(parkingStickerDto);

    Account account = accountRepository.findById(parkingSticker.getAccountId());
    parkingAreaRepository.findByCode(parkingSticker.getParkingAreaCode());

    parkingSticker = parkingStickerFactory.create(parkingSticker);

    account.addParkingStickerCode(parkingSticker.getCode());
    accountRepository.update(account);

    parkingStickerRepository.save(parkingSticker);

    return parkingStickerCodeAssembler.assemble(parkingSticker.getCode());
  }
}
