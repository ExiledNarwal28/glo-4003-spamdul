package ca.ulaval.glo4003.reports.services;

import static ca.ulaval.glo4003.cars.helpers.CarMother.createConsumptionType;
import static ca.ulaval.glo4003.funds.helpers.MoneyMother.createMoney;
import static ca.ulaval.glo4003.parkings.helpers.ParkingAreaMother.createParkingAreaCode;
import static ca.ulaval.glo4003.reports.helpers.ReportEventBuilder.aReportEvent;
import static ca.ulaval.glo4003.times.helpers.CustomDateTimeMother.createDateTime;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.cars.domain.ConsumptionType;
import ca.ulaval.glo4003.funds.domain.Money;
import ca.ulaval.glo4003.parkings.domain.ParkingAreaCode;
import ca.ulaval.glo4003.reports.domain.ReportEvent;
import ca.ulaval.glo4003.reports.domain.ReportEventFactory;
import ca.ulaval.glo4003.reports.domain.ReportEventType;
import ca.ulaval.glo4003.reports.domain.ReportRepository;
import ca.ulaval.glo4003.times.domain.CustomDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ReportEventServiceTest {
  private final Money profits = createMoney();

  @Mock private ReportRepository reportRepository;
  @Mock private ReportEventFactory reportEventFactory;

  private ReportEventService reportEventService;
  private final ConsumptionType consumptionType = createConsumptionType();
  private final ReportEvent billPaidForParkingStickerEvent = aReportEvent().build();
  private final ReportEvent billPaidForAccessPassEvent = aReportEvent().build();
  private final ReportEvent billPaidForOffenseEvent = aReportEvent().build();
  private final ReportEvent accessAreaCodeEvent = aReportEvent().build();
  private final CustomDateTime dateTime = createDateTime();
  private final ParkingAreaCode parkingAreaCode = createParkingAreaCode();

  @Before
  public void setUp() {
    reportEventService = new ReportEventService(reportRepository, reportEventFactory);

    when(reportEventFactory.create(ReportEventType.BILL_PAID_FOR_PARKING_STICKER, profits))
        .thenReturn(billPaidForParkingStickerEvent);
    when(reportEventFactory.create(
            ReportEventType.BILL_PAID_FOR_ACCESS_PASS, profits, consumptionType))
        .thenReturn(billPaidForAccessPassEvent);
    when(reportEventFactory.create(ReportEventType.BILL_PAID_FOR_OFFENSE, profits))
        .thenReturn(billPaidForOffenseEvent);
    when(reportEventFactory.create(ReportEventType.GATE_ENTERED, dateTime, parkingAreaCode))
        .thenReturn(accessAreaCodeEvent);
  }

  @Test
  public void whenAddingBillPaidForParkingStickerEvent_thenAddReportEventToRepository() {
    reportEventService.addBillPaidForParkingStickerEvent(profits);

    verify(reportRepository).addEvent(billPaidForParkingStickerEvent);
  }

  @Test
  public void whenAddingBillPaidForAccessPassEvent_thenAddReportEventToRepository() {
    reportEventService.addBillPaidForAccessPassEvent(profits, consumptionType);

    verify(reportRepository).addEvent(billPaidForAccessPassEvent);
  }

  @Test
  public void whenAddingBillPaidForOffenseEvent_thenAddReportEventToRepository() {
    reportEventService.addBillPaidForOffenseEvent(profits);

    verify(reportRepository).addEvent(billPaidForOffenseEvent);
  }

  @Test
  public void whenAddingGateEnteredEvent_thenAddReportEventToRepository() {
    reportEventService.addGateEnteredEvent(dateTime, parkingAreaCode);

    verify(reportRepository).addEvent(accessAreaCodeEvent);
  }
}
