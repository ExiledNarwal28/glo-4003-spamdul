package ca.ulaval.glo4003.users.services;

import ca.ulaval.glo4003.accounts.domain.Account;
import ca.ulaval.glo4003.accounts.domain.AccountFactory;
import ca.ulaval.glo4003.accounts.domain.AccountId;
import ca.ulaval.glo4003.accounts.domain.AccountRepository;
import ca.ulaval.glo4003.accounts.services.assemblers.AccountIdAssembler;
import ca.ulaval.glo4003.accounts.services.converters.AccountIdConverter;
import ca.ulaval.glo4003.users.domain.User;
import ca.ulaval.glo4003.users.services.assemblers.UserAssembler;
import ca.ulaval.glo4003.users.services.converters.UserConverter;
import ca.ulaval.glo4003.users.services.dto.AccountIdDto;
import ca.ulaval.glo4003.users.services.dto.UserDto;

public class UserService {
  private final AccountRepository accountRepository;
  private final AccountFactory accountFactory;
  private final AccountIdConverter accountIdConverter;
  private final AccountIdAssembler accountIdAssembler;
  private final UserConverter userConverter;
  private final UserAssembler userAssembler;

  public UserService(AccountRepository accountRepository, AccountFactory accountFactory) {
    this(
        accountRepository,
        accountFactory,
        new AccountIdConverter(),
        new AccountIdAssembler(),
        new UserConverter(),
        new UserAssembler());
  }

  public UserService(
      AccountRepository accountRepository,
      AccountFactory accountFactory,
      AccountIdConverter accountIdConverter,
      AccountIdAssembler accountIdAssembler,
      UserConverter userConverter,
      UserAssembler userAssembler) {
    this.accountRepository = accountRepository;
    this.accountFactory = accountFactory;
    this.accountIdConverter = accountIdConverter;
    this.accountIdAssembler = accountIdAssembler;
    this.userConverter = userConverter;
    this.userAssembler = userAssembler;
  }

  public AccountIdDto addUser(UserDto userDto) {
    User user = userConverter.convert(userDto);
    Account account = accountFactory.createAccount(user);

    AccountId accountId = accountRepository.save(account);

    return accountIdAssembler.assemble(accountId);
  }

  public UserDto getUser(String stringId) {
    AccountId accountId = accountIdConverter.convert(stringId);
    Account account = accountRepository.get(accountId);

    return userAssembler.assemble(account.getUser());
  }
}
