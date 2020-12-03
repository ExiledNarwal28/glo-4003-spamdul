package ca.ulaval.glo4003.accounts.infrastructure;

import ca.ulaval.glo4003.accesspasses.domain.AccessPass;
import ca.ulaval.glo4003.accesspasses.domain.AccessPassCode;
import ca.ulaval.glo4003.accesspasses.exceptions.NotFoundAccessPassException;
import ca.ulaval.glo4003.accounts.domain.Account;
import ca.ulaval.glo4003.accounts.domain.AccountId;
import ca.ulaval.glo4003.accounts.domain.AccountRepository;
import ca.ulaval.glo4003.accounts.exceptions.NotFoundAccountException;
import ca.ulaval.glo4003.cars.domain.LicensePlate;
import ca.ulaval.glo4003.parkings.domain.ParkingStickerCode;
import java.util.*;
import java.util.stream.Collectors;

public class AccountRepositoryInMemory implements AccountRepository {
  private final Map<AccountId, Account> accounts = new HashMap<>();

  @Override
  public AccountId save(Account account) {
    accounts.put(account.getId(), account);
    return account.getId();
  }

  @Override
  public Account get(AccountId accountId) {
    Account foundAccount = accounts.get(accountId);

    if (foundAccount == null) throw new NotFoundAccountException();

    return foundAccount;
  }

  @Override
  public Account get(ParkingStickerCode parkingStickerCode) {
    for (Account account : accounts.values()) {
      if (account.getParkingSticker(parkingStickerCode) != null) {
        return account;
      }
    }
    throw new NotFoundAccountException();
  }

  @Override
  public AccessPass getAccessPass(AccessPassCode accessPassCode) {
    Optional<AccessPass> accessPass =
        accounts.values().stream()
            .map(account -> account.getAccessPass(accessPassCode))
            .filter(Objects::nonNull)
            .findFirst();

    if (accessPass.isPresent()) {
      return accessPass.get();
    } else {
      throw new NotFoundAccessPassException();
    }
  }

  @Override
  public List<AccessPass> getAccessPasses(LicensePlate licensePlate) {
    List<AccessPass> accessPasses =
        accounts.values().stream()
            .flatMap(account -> account.getAccessPasses(licensePlate).stream())
            .collect(Collectors.toList());

    if (accessPasses.isEmpty()) throw new NotFoundAccessPassException();

    return accessPasses;
  }

  @Override
  public void update(Account account) {
    Account foundAccount = get(account.getId());

    accounts.put(foundAccount.getId(), account);
  }

  @Override
  public void update(AccessPass accessPass) {
    Account account = get(accessPass.getCode());

    account.addAccessPass(accessPass);

    accounts.put(account.getId(), account);
  }

  private Account get(AccessPassCode accessPassCode) {
    Optional<Account> foundAccount =
        accounts.values().stream()
            .filter(account -> account.getAccessPass(accessPassCode) != null)
            .findFirst();

    if (foundAccount.isPresent()) {
      return foundAccount.get();
    } else {
      throw new NotFoundAccessPassException();
    }
  }
}
