package ca.ulaval.glo4003.communications;

import ca.ulaval.glo4003.communications.assemblers.EmailAddressAssembler;
import ca.ulaval.glo4003.communications.domain.EmailSender;
import ca.ulaval.glo4003.communications.smtp.GmailSmtpClient;
import ca.ulaval.glo4003.communications.smtp.GmailSmtpEmailClient;

public class CommunicationInjector {

  public EmailAddressAssembler createEmailAddressAssembler() {
    return new EmailAddressAssembler();
  }

  public EmailSender createEmailSender() {
    GmailSmtpClient gmailSmtpClient = new GmailSmtpClient();
    return new GmailSmtpEmailClient(gmailSmtpClient);
  }
}
