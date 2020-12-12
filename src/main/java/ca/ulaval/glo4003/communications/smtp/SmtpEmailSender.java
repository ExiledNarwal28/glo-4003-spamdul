package ca.ulaval.glo4003.communications.smtp;

import ca.ulaval.glo4003.communications.domain.EmailSender;
import ca.ulaval.glo4003.communications.domain.exceptions.EmailSendingFailedException;
import ca.ulaval.glo4003.parkings.domain.ParkingSticker;
import ca.ulaval.glo4003.parkings.domain.ReceptionMethod;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SmtpEmailSender implements EmailSender {
  private static final String PARKING_STICKER_CREATION_SUBJECT = "Your SPAMD-UL parking sticker";
  private static final String PARKING_STICKER_CREATION_TEXT =
      "Your SPAMD-UL parking sticker code is %s";

  private final SmtpClient smtpClient;

  public SmtpEmailSender() {
    this(new SmtpClient());
  }

  public SmtpEmailSender(SmtpClient smtpClient) {
    this.smtpClient = smtpClient;
  }

  @Override
  public void sendEmail(String emailAddress, String emailSubject, String emailContent) {
    MimeMessage message = smtpClient.createMessage();

    try {
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
      message.setSubject(emailSubject);
      message.setText(emailContent);
    } catch (MessagingException exception) {
      throw new EmailSendingFailedException();
    }

    smtpClient.sendMessage(message);
  }

  @Override
  public void listenParkingStickerCreated(ParkingSticker parkingSticker) {
    if (parkingSticker.getReceptionMethod().equals(ReceptionMethod.EMAIL)) {
      sendEmail(
          parkingSticker.getEmailAddress().toString(),
          PARKING_STICKER_CREATION_SUBJECT,
          String.format(PARKING_STICKER_CREATION_TEXT, parkingSticker.getCode().toString()));
    }
  }
}
