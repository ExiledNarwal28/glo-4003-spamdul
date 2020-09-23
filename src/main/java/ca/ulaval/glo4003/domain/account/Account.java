package ca.ulaval.glo4003.domain.account;

import ca.ulaval.glo4003.domain.parking.ParkingSticker;
import ca.ulaval.glo4003.domain.user.User;
import java.util.List;
import jersey.repackaged.com.google.common.collect.Lists;

public class Account {
  private AccountId id;
  private User user;
  private List<ParkingSticker> parkingStickers = Lists.newArrayList();

  public Account(AccountId id, User user) {
    this.id = id;
    this.user = user;
  }

  public AccountId getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public List<ParkingSticker> getParkingStickers() {
    return parkingStickers;
  }

  public void addParkingSticker(ParkingSticker parkingSticker) {
    parkingStickers.add(parkingSticker);
  }
}
