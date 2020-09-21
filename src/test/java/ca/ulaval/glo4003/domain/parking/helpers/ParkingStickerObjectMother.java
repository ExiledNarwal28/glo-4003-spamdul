package ca.ulaval.glo4003.domain.parking.helpers;

import static ca.ulaval.glo4003.interfaces.Randomizer.randomEnum;

import ca.ulaval.glo4003.domain.parking.ParkingStickerCode;
import ca.ulaval.glo4003.domain.parking.ReceptionMethods;
import com.github.javafaker.Faker;

public class ParkingStickerObjectMother {
  public static ParkingStickerCode createParkingStickerCode() {
    return new ParkingStickerCode(Faker.instance().color().toString());
  }

  public static ReceptionMethods createReceptionMethod() {
    return randomEnum(ReceptionMethods.class);
  }

  public static String createAddress() {
    return Faker.instance().address().fullAddress();
  }
}
