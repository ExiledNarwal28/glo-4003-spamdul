package ca.ulaval.glo4003.funds.domain;

import static ca.ulaval.glo4003.accesspasses.helpers.AccessPassBuilder.anAccessPass;
import static ca.ulaval.glo4003.cars.helpers.CarMother.createConsumptionType;
import static ca.ulaval.glo4003.funds.helpers.BillMother.createBillId;
import static ca.ulaval.glo4003.funds.helpers.MoneyMother.createMoney;
import static ca.ulaval.glo4003.offenses.helpers.OffenseTypeMother.createOffenseCode;
import static ca.ulaval.glo4003.parkings.helpers.ParkingStickerMother.createParkingStickerCode;
import static ca.ulaval.glo4003.parkings.helpers.ParkingStickerMother.createReceptionMethod;
import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.accesspasses.domain.AccessPass;
import ca.ulaval.glo4003.cars.domain.ConsumptionType;
import ca.ulaval.glo4003.communications.domain.ReceptionMethod;
import ca.ulaval.glo4003.offenses.domain.OffenseCode;
import ca.ulaval.glo4003.parkings.domain.ParkingStickerCode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BillFactoryTest {

  @Mock BillIdGenerator billIdGenerator;

  private BillFactory billFactory;

  private final BillId billId = createBillId();
  private final Money fee = createMoney();
  private final ParkingStickerCode parkingStickerCode = createParkingStickerCode();
  private final AccessPass accessPass = anAccessPass().build();
  private final ReceptionMethod receptionMethod = createReceptionMethod();
  private final OffenseCode offenseCode = createOffenseCode();
  private final ConsumptionType consumptionType = createConsumptionType();

  @Before
  public void setUp() {
    billFactory = new BillFactory(billIdGenerator);

    when(billIdGenerator.generate()).thenReturn(billId);
  }

  @Test
  public void whenCreatingForParkingSticker_thenReturnBillWithId() {
    Bill bill = billFactory.createForParkingSticker(fee, parkingStickerCode, receptionMethod);

    assertThat(bill.getId()).isSameInstanceAs(billId);
  }

  @Test
  public void
      whenCreatingForParkingSticker_thenReturnBillWithDescriptionContainingParkingStickerCode() {
    Bill bill = billFactory.createForParkingSticker(fee, parkingStickerCode, receptionMethod);

    assertThat(bill.getDescription()).contains(parkingStickerCode.toString());
  }

  @Test
  public void
      givenEmailReceptionMethod_whenCreatingForParkingSticker_thenReturnBillWithAmountEqualToFee() {
    ReceptionMethod emailReceptionMethod = ReceptionMethod.EMAIL;

    Bill bill = billFactory.createForParkingSticker(fee, parkingStickerCode, emailReceptionMethod);

    assertThat(bill.getAmountDue()).isEqualTo(fee);
  }

  @Test
  public void
      givenPostalReceptionMethod_whenCreatingForParkingSticker_thenReturnBillWithAmountEqualToFeePlusFive() {
    Money expectedAmount = fee.plus(Money.fromDouble(5));
    ReceptionMethod postalReceptionMethod = ReceptionMethod.POSTAL;

    Bill bill = billFactory.createForParkingSticker(fee, parkingStickerCode, postalReceptionMethod);

    assertThat(bill.getAmountDue()).isEqualTo(expectedAmount);
  }

  @Test
  public void whenCreatingForParkingSticker_thenReturnBillWithParkingStickerType() {
    Bill bill = billFactory.createForParkingSticker(fee, parkingStickerCode, receptionMethod);

    assertThat(bill.getBillType()).isEqualTo(BillType.PARKING_STICKER);
  }

  @Test
  public void whenCreatingForAccessPass_thenReturnBillWithId() {
    Bill bill =
        billFactory.createForAccessPass(
            fee, accessPass.getCode(), consumptionType, accessPass.getReceptionMethod());

    assertThat(bill.getId()).isSameInstanceAs(billId);
  }

  @Test
  public void whenCreatingForAccessPass_thenReturnBillWithDescriptionContainingAccessPassCode() {
    Bill bill =
        billFactory.createForAccessPass(
            fee, accessPass.getCode(), consumptionType, accessPass.getReceptionMethod());

    assertThat(bill.getDescription()).contains(accessPass.getCode().toString());
  }

  @Test
  public void
      givenPostalReceptionMethod_whenCreatingForAccessPass_thenReturnBillWithAmountEqualToFeePlusFive() {
    Money expectedFee = fee.plus(Money.fromDouble(5));

    Bill bill =
        billFactory.createForAccessPass(
            fee, accessPass.getCode(), consumptionType, ReceptionMethod.POSTAL);

    assertThat(bill.getAmountDue()).isEqualTo(expectedFee);
  }

  @Test
  public void
      givenEmailPostalReceptionMethod_whenCreatingForAccessPass_thenReturnBillWithAmountEqualToFee() {
    Bill bill =
        billFactory.createForAccessPass(
            fee, accessPass.getCode(), consumptionType, ReceptionMethod.EMAIL);

    assertThat(bill.getAmountDue()).isEqualTo(fee);
  }

  @Test
  public void
      givenSspReceptionMethod_whenCreatingForAccessPass_thenReturnBillWithAmountEqualToFee() {
    Bill bill =
        billFactory.createForAccessPass(
            fee, accessPass.getCode(), consumptionType, ReceptionMethod.SSP);

    assertThat(bill.getAmountDue()).isEqualTo(fee);
  }

  @Test
  public void whenCreatingForAccessPass_thenReturnBillWithAccessPassType() {
    Bill bill =
        billFactory.createForAccessPass(
            fee, accessPass.getCode(), consumptionType, accessPass.getReceptionMethod());

    assertThat(bill.getBillType()).isEqualTo(BillType.ACCESS_PASS);
  }

  @Test
  public void whenCreatingForOffense_thenReturnBillWithId() {
    Bill bill = billFactory.createForOffense(fee, offenseCode);

    assertThat(bill.getId()).isSameInstanceAs(billId);
  }

  @Test
  public void whenCreatingForOffense_thenReturnBillWithDescriptionContainingOffenseCode() {
    Bill bill = billFactory.createForOffense(fee, offenseCode);

    assertThat(bill.getDescription()).contains(offenseCode.toString());
  }

  @Test
  public void whenCreatingForOffense_thenReturnBillWithAmountEqualToFee() {
    Bill bill = billFactory.createForOffense(fee, offenseCode);

    assertThat(bill.getAmountDue()).isEqualTo(fee);
  }

  @Test
  public void whenCreatingForOffense_thenReturnBillWithAccessPassType() {
    Bill bill = billFactory.createForOffense(fee, offenseCode);

    assertThat(bill.getBillType()).isEqualTo(BillType.OFFENSE);
  }
}
