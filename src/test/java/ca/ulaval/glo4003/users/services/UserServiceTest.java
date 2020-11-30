package ca.ulaval.glo4003.users.services;

import static ca.ulaval.glo4003.accounts.helpers.AccountBuilder.anAccount;
import static ca.ulaval.glo4003.users.helpers.UserBuilder.aUser;
import static ca.ulaval.glo4003.users.helpers.UserDtoBuilder.aUserDto;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.accounts.domain.Account;
import ca.ulaval.glo4003.accounts.domain.AccountFactory;
import ca.ulaval.glo4003.accounts.domain.AccountRepository;
import ca.ulaval.glo4003.accounts.services.converters.AccountIdConverter;
import ca.ulaval.glo4003.users.domain.User;
import ca.ulaval.glo4003.users.services.assemblers.UserAssembler;
import ca.ulaval.glo4003.users.services.dto.AccountIdDto;
import ca.ulaval.glo4003.users.services.dto.UserDto;
import com.google.common.truth.Truth;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
  @Mock private AccountRepository accountRepository;
  @Mock private AccountFactory accountFactory;
  @Mock private AccountIdConverter accountIdConverter;
  @Mock private UserAssembler userAssembler;
  @Mock private AccountIdDto accountIdDto;

  private Account account;
  private User user;
  private UserDto userDto;

  private UserService userService;

  @Before
  public void setUp() {
    userService =
        new UserService(accountRepository, accountFactory, accountIdConverter, userAssembler);

    account = anAccount().build();
    user = aUser().build();
    userDto = aUserDto().build();

    when(userAssembler.assemble(userDto)).thenReturn(user);
    when(accountFactory.createAccount(user)).thenReturn(account);
    when(accountRepository.save(account)).thenReturn(account.getId());
    when(accountIdConverter.convert(account.getId())).thenReturn(accountIdDto);

    when(accountIdConverter.convert(account.getId().toString())).thenReturn(account.getId());
    when(accountRepository.get(account.getId())).thenReturn(account);
    when(userAssembler.assemble(account.getUser())).thenReturn(userDto);
  }

  @Test
  public void whenAddingUser_thenReturnAccountIdDto() {
    AccountIdDto receivedAccountIdDto = userService.addUser(userDto);

    Truth.assertThat(receivedAccountIdDto).isSameInstanceAs(accountIdDto);
  }

  @Test
  public void whenGettingUser_thenReturnUserDto() {
    UserDto receivedUserDto = userService.getUser(account.getId().toString());

    Truth.assertThat(receivedUserDto).isSameInstanceAs(userDto);
  }
}
