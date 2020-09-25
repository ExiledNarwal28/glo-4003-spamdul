package ca.ulaval.glo4003.domain.parking;

import ca.ulaval.glo4003.api.parking.dto.ParkingStickerDto;
import ca.ulaval.glo4003.domain.account.AccountId;
import ca.ulaval.glo4003.domain.account.AccountIdAssembler;
import ca.ulaval.glo4003.domain.parking.exception.MissingAddressException;

import javax.inject.Inject;

public class ParkingStickerAssembler {
  private final AccountIdAssembler accountIdAssembler;

  @Inject
  public ParkingStickerAssembler(AccountIdAssembler accountIdAssembler) {
    this.accountIdAssembler = accountIdAssembler;
  }

  public ParkingSticker assemble(ParkingStickerDto parkingStickerDto) {
    ReceptionMethods receptionMethod = ReceptionMethods.get(parkingStickerDto.receptionMethod);
    validateReceptionMethod(receptionMethod, parkingStickerDto.address);

    AccountId accountId = accountIdAssembler.assemble(parkingStickerDto.accountId);

    return new ParkingSticker(
        accountId, new ParkingAreaCode(parkingStickerDto.parkingArea), receptionMethod);
  }

  private void validateReceptionMethod(ReceptionMethods receptionMethod, String address) {
    if (receptionMethod.equals(ReceptionMethods.POSTAL) && address == null) {
      throw new MissingAddressException();
    }
  }
}
