package ca.ulaval.glo4003.parkings;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.accounts.services.AccountService;
import ca.ulaval.glo4003.funds.services.BillService;
import ca.ulaval.glo4003.parkings.api.ParkingAreaResource;
import ca.ulaval.glo4003.parkings.domain.ParkingAreaRepository;
import ca.ulaval.glo4003.parkings.domain.ParkingStickerCreationObserver;
import ca.ulaval.glo4003.parkings.infrastructure.ParkingAreaRepositoryInMemory;
import ca.ulaval.glo4003.parkings.services.ParkingStickerService;
import ca.ulaval.glo4003.parkings.services.assemblers.ParkingAreaCodeAssembler;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ParkingInjectorTest {

  @Mock private AccountService accountService;
  @Mock private ParkingStickerCreationObserver parkingStickerCreationObserver;
  @Mock private BillService billService;

  private ParkingInjector parkingInjector;

  @Before
  public void setUp() {
    parkingInjector = new ParkingInjector();
  }

  @Test
  public void whenGettingParkingAreaRepository_thenReturnInMemoryParkingAreaRepository() {
    ParkingAreaRepository parkingAreaRepository = parkingInjector.getParkingAreaRepository();

    assertThat(parkingAreaRepository).isInstanceOf(ParkingAreaRepositoryInMemory.class);
  }

  @Test
  public void whenCreatingParkingAreaCodeAssembler_thenReturnIt() {
    ParkingAreaCodeAssembler parkingAreaCodeAssembler =
        parkingInjector.createParkingAreaCodeAssembler();

    assertThat(parkingAreaCodeAssembler).isNotNull();
  }

  @Test
  public void whenCreatingParkingStickerService_thenReturnIt() {
    ParkingStickerService parkingStickerService =
        parkingInjector.createParkingStickerService(
            true,
            accountService,
            Collections.singletonList(parkingStickerCreationObserver),
            billService);

    assertThat(parkingStickerService).isNotNull();
  }

  @Test
  public void whenCreatingParkingAreaResource_thenReturnIt() {
    ParkingAreaResource parkingAreaResource = parkingInjector.createParkingAreaResource();

    assertThat(parkingAreaResource).isNotNull();
  }
}
