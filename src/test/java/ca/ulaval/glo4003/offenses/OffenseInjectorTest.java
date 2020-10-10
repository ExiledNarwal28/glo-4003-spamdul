package ca.ulaval.glo4003.offenses;

import ca.ulaval.glo4003.files.domain.StringFileReader;
import ca.ulaval.glo4003.files.filesystem.JsonFileReader;
import ca.ulaval.glo4003.funds.assemblers.MoneyAssembler;
import ca.ulaval.glo4003.offenses.api.OffenseResource;
import ca.ulaval.glo4003.parkings.assemblers.ParkingAreaCodeAssembler;
import ca.ulaval.glo4003.parkings.assemblers.ParkingStickerCodeAssembler;
import ca.ulaval.glo4003.parkings.domain.ParkingStickerRepository;
import ca.ulaval.glo4003.times.assemblers.TimeOfDayAssembler;
import com.google.common.truth.Truth;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OffenseInjectorTest {

  @Mock private ParkingStickerRepository parkingStickerRepository;
  @Mock private ParkingStickerCodeAssembler parkingStickerCodeAssembler;
  @Mock private ParkingAreaCodeAssembler parkingAreaCodeAssembler;
  @Mock private TimeOfDayAssembler timeOfDayAssembler;
  @Mock private MoneyAssembler moneyAssembler;

  private OffenseInjector offenseInjector;

  private final StringFileReader jsonHelper =
      new JsonFileReader(); // TODO : If we would not fill offense type repository at injection,
  // this
  // would not have to happen

  @Before
  public void setUp() {
    offenseInjector = new OffenseInjector();
  }

  @Test
  public void whenCreatingOffenseResource_thenReturnIt() {
    OffenseResource offenseResource =
        offenseInjector.createOffenseResource(
            parkingStickerRepository,
            parkingStickerCodeAssembler,
            parkingAreaCodeAssembler,
            timeOfDayAssembler,
            jsonHelper,
            moneyAssembler);

    Truth.assertThat(offenseResource).isNotNull();
  }
}