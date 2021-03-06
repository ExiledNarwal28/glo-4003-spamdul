package ca.ulaval.glo4003.communications.helpers;

import ca.ulaval.glo4003.communications.domain.EmailAddress;
import com.github.javafaker.Faker;

public class EmailMother {
  public static String createEmailHost() {
    return Faker.instance().internet().domainName();
  }

  public static EmailAddress createEmailAddress() {
    String address = Faker.instance().internet().emailAddress();
    return new EmailAddress(address);
  }

  public static String createEmailPassword() {
    return Faker.instance().internet().password();
  }

  public static String createEmailSubject() {
    return Faker.instance().pokemon().name();
  }

  public static String createEmailContent() {
    return Faker.instance().pokemon().location();
  }
}
