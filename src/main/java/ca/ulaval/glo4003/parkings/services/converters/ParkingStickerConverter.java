package ca.ulaval.glo4003.parkings.services.converters;

import ca.ulaval.glo4003.accounts.domain.AccountId;
import ca.ulaval.glo4003.accounts.services.converters.AccountIdConverter;
import ca.ulaval.glo4003.communications.domain.EmailAddress;
import ca.ulaval.glo4003.communications.domain.PostalCode;
import ca.ulaval.glo4003.communications.domain.ReceptionMethod;
import ca.ulaval.glo4003.communications.domain.exceptions.MissingEmailException;
import ca.ulaval.glo4003.communications.domain.exceptions.MissingPostalCodeException;
import ca.ulaval.glo4003.communications.services.converters.EmailAddressConverter;
import ca.ulaval.glo4003.communications.services.converters.PostalCodeConverter;
import ca.ulaval.glo4003.parkings.domain.ParkingAreaCode;
import ca.ulaval.glo4003.parkings.domain.ParkingPeriod;
import ca.ulaval.glo4003.parkings.domain.ParkingSticker;
import ca.ulaval.glo4003.parkings.services.assemblers.ParkingAreaCodeAssembler;
import ca.ulaval.glo4003.parkings.services.assemblers.ParkingPeriodAssembler;
import ca.ulaval.glo4003.parkings.services.dto.ParkingStickerDto;

public class ParkingStickerConverter {
  private final ParkingAreaCodeAssembler parkingAreaCodeAssembler;
  private final AccountIdConverter accountIdConverter;
  private final PostalCodeConverter postalCodeConverter;
  private final EmailAddressConverter emailAddressConverter;
  private final ParkingPeriodAssembler parkingPeriodAssembler;

  public ParkingStickerConverter() {
    this(
        new ParkingAreaCodeAssembler(),
        new AccountIdConverter(),
        new PostalCodeConverter(),
        new EmailAddressConverter(),
        new ParkingPeriodAssembler());
  }

  public ParkingStickerConverter(
      ParkingAreaCodeAssembler parkingAreaCodeAssembler,
      AccountIdConverter accountIdConverter,
      PostalCodeConverter postalCodeConverter,
      EmailAddressConverter emailAddressConverter,
      ParkingPeriodAssembler parkingPeriodAssembler) {
    this.parkingAreaCodeAssembler = parkingAreaCodeAssembler;
    this.accountIdConverter = accountIdConverter;
    this.postalCodeConverter = postalCodeConverter;
    this.emailAddressConverter = emailAddressConverter;
    this.parkingPeriodAssembler = parkingPeriodAssembler;
  }

  public ParkingSticker convert(ParkingStickerDto parkingStickerDto, String stringAccountId) {
    AccountId accountId = accountIdConverter.convert(stringAccountId);
    ParkingAreaCode parkingAreaCode =
        parkingAreaCodeAssembler.assemble(parkingStickerDto.parkingArea);

    ParkingPeriod parkingPeriod = parkingPeriodAssembler.assemble(parkingStickerDto.parkingPeriod);
    ReceptionMethod receptionMethod = ReceptionMethod.get(parkingStickerDto.receptionMethod);

    switch (receptionMethod) {
      case POSTAL:
        if (parkingStickerDto.postalCode == null) {
          throw new MissingPostalCodeException();
        } else {
          PostalCode postalCode = postalCodeConverter.convert(parkingStickerDto.postalCode);
          return new ParkingSticker(
              accountId, parkingAreaCode, receptionMethod, postalCode, parkingPeriod);
        }
      case SSP:
        return new ParkingSticker(accountId, parkingAreaCode, receptionMethod, parkingPeriod);
      default:
      case EMAIL:
        if (parkingStickerDto.email == null) {
          throw new MissingEmailException();
        } else {
          EmailAddress emailAddress = emailAddressConverter.convert(parkingStickerDto.email);
          return new ParkingSticker(
              accountId, parkingAreaCode, receptionMethod, emailAddress, parkingPeriod);
        }
    }
  }
}
