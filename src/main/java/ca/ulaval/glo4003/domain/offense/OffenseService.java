package ca.ulaval.glo4003.domain.offense;

import ca.ulaval.glo4003.api.offense.dto.OffenseDto;
import ca.ulaval.glo4003.api.offense.dto.OffenseValidationDto;
import ca.ulaval.glo4003.domain.parking.ParkingSticker;
import ca.ulaval.glo4003.domain.parking.ParkingStickerRepository;
import ca.ulaval.glo4003.domain.parking.exception.NotFoundParkingStickerException;
import java.util.ArrayList;
import java.util.List;

public class OffenseService {
  private final ParkingStickerRepository parkingStickerRepository;
  private final OffenseValidationAssembler offenseValidationAssembler;
  private final OffenseAssembler offenseAssembler;

  public OffenseService(
      ParkingStickerRepository parkingStickerRepository,
      OffenseValidationAssembler offenseValidationAssembler) {
    this.parkingStickerRepository = parkingStickerRepository;
    this.offenseValidationAssembler = offenseValidationAssembler;
    this.offenseAssembler = new OffenseAssembler();
  }

  public List<OffenseDto> getAllOffenses() {
    // TODO This is all hard-coded for now, eventually there should be file thingies going on
    // instead
    ArrayList<Offense> offenses = new ArrayList<>();
    // Those will be reused far more often
    offenses.add(createWrongZoneOffense());
    offenses.add(createWrongDayOffense());
    offenses.add(createInvalidStickerOffense());
    // Those have to be treated "manually" by the officer
    offenses.add(new Offense("temps dépassé", "TEMPS_01", 47));
    offenses.add(new Offense("stationnement réservé pour voiture électrique", "ZONE_02", 55));
    offenses.add(new Offense("pas de vignette", "VIG_03", 55));
    offenses.add(new Offense("la vignette et la plaque ne sont pas associées", "VIG_04", 42));
    offenses.add(
        new Offense("stationnement réservé pour voiture électrique branchée", "ZONE_03", 55));
    return offenseAssembler.assembleMany(offenses);
  }

  public OffenseDto isOffenseNeeded(OffenseValidationDto offenseValidationDto) {
    // TODO On fait comment pour check si la vignette a accès pour la journée?
    ParkingSticker parkingSticker;

    OffenseValidation offenseValidation = offenseValidationAssembler.assemble(offenseValidationDto);

    try {
      parkingSticker =
          parkingStickerRepository.findByCode(offenseValidation.getParkingStickerCode());
    } catch (NotFoundParkingStickerException e) {
      return offenseAssembler.assemble(createInvalidStickerOffense());
    }

    if (!parkingSticker.validateParkingStickerAreaCode(offenseValidation.getParkingAreaCode())) {
      return offenseAssembler.assemble(createWrongZoneOffense());
    }

    return offenseAssembler.assemble(createNoOffense());
  }

  private Offense createNoOffense() {
    return new Offense("Aucune infraction signalée", "000", 0);
  }

  private Offense createWrongZoneOffense() {
    return new Offense("mauvaise zone", "ZONE_01", 55);
  }

  private Offense createWrongDayOffense() {
    return new Offense("vignette pas admissible pour la journée", "VIG_01", 22);
  }

  private Offense createInvalidStickerOffense() {
    return new Offense("vignette invalide", "VIG_02", 45);
  }
}
