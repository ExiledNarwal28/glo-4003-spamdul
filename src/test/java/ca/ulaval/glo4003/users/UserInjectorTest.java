package ca.ulaval.glo4003.users;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.accesspasses.services.AccessPassService;
import ca.ulaval.glo4003.accounts.domain.AccountFactory;
import ca.ulaval.glo4003.accounts.domain.AccountRepository;
import ca.ulaval.glo4003.accounts.services.AccountService;
import ca.ulaval.glo4003.cars.services.CarService;
import ca.ulaval.glo4003.funds.services.BillService;
import ca.ulaval.glo4003.parkings.services.ParkingStickerService;
import ca.ulaval.glo4003.users.api.UserResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserInjectorTest {

  @Mock private AccountRepository accountRepository;
  @Mock private AccountFactory accountFactory;
  @Mock private AccessPassService accessPassService;
  @Mock private CarService carService;
  @Mock private AccountService accountService;
  @Mock private ParkingStickerService parkingStickerService;
  @Mock private BillService billService;

  private UserInjector userInjector;

  @Before
  public void setUp() {
    userInjector = new UserInjector();
  }

  @Test
  public void whenCreatingUserResource_thenReturnIt() {
    UserResource userResource =
        userInjector.createUserResource(
            accountRepository,
            accountFactory,
            accessPassService,
            carService,
            accountService,
            parkingStickerService,
            billService);

    assertThat(userResource).isNotNull();
  }
}
