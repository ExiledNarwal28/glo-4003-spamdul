package ca.ulaval.glo4003;

import static ca.ulaval.glo4003.schedulers.systemtime.SchedulerBuilder.newScheduler;

import ca.ulaval.glo4003.accesspasses.AccessPassInjector;
import ca.ulaval.glo4003.accesspasses.domain.AccessPassCreationObserver;
import ca.ulaval.glo4003.accounts.AccountInjector;
import ca.ulaval.glo4003.carboncredits.CarbonCreditInjector;
import ca.ulaval.glo4003.carboncredits.api.CarbonCreditResource;
import ca.ulaval.glo4003.cars.CarInjector;
import ca.ulaval.glo4003.communications.CommunicationInjector;
import ca.ulaval.glo4003.errors.ErrorInjector;
import ca.ulaval.glo4003.funds.FundInjector;
import ca.ulaval.glo4003.funds.services.BillService;
import ca.ulaval.glo4003.gates.GateInjector;
import ca.ulaval.glo4003.gates.api.GateResource;
import ca.ulaval.glo4003.initiatives.InitiativeInjector;
import ca.ulaval.glo4003.initiatives.api.InitiativeResource;
import ca.ulaval.glo4003.initiatives.domain.InitiativeAddedAllocatedAmountObserver;
import ca.ulaval.glo4003.offenses.OffenseInjector;
import ca.ulaval.glo4003.offenses.api.OffenseResource;
import ca.ulaval.glo4003.parkings.ParkingInjector;
import ca.ulaval.glo4003.parkings.api.ParkingAreaResource;
import ca.ulaval.glo4003.parkings.domain.ParkingStickerCreationObserver;
import ca.ulaval.glo4003.reports.ReportInjector;
import ca.ulaval.glo4003.reports.api.ReportParkingAreaResource;
import ca.ulaval.glo4003.reports.api.ReportProfitResource;
import ca.ulaval.glo4003.times.TimeInjector;
import ca.ulaval.glo4003.users.UserInjector;
import ca.ulaval.glo4003.users.api.UserResource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.ws.rs.ext.ExceptionMapper;
import org.quartz.Scheduler;

public class ApplicationInjector {

  private static final boolean IS_DEV = true;

  private static final AccessPassInjector ACCESS_PASS_INJECTOR = new AccessPassInjector();
  private static final AccountInjector ACCOUNT_INJECTOR = new AccountInjector();
  private static final CarInjector CAR_INJECTOR = new CarInjector();
  private static final CommunicationInjector COMMUNICATION_INJECTOR = new CommunicationInjector();
  private static final GateInjector GATE_INJECTOR = new GateInjector();
  private static final FundInjector FUND_INJECTOR = new FundInjector();
  private static final OffenseInjector OFFENSE_INJECTOR = new OffenseInjector();
  private static final ParkingInjector PARKING_INJECTOR = new ParkingInjector();
  private static final TimeInjector TIME_INJECTOR = new TimeInjector();
  private static final UserInjector USER_INJECTOR = new UserInjector();
  private static final CarbonCreditInjector CARBON_CREDIT_INJECTOR = new CarbonCreditInjector();
  private static final InitiativeInjector INITIATIVE_INJECTOR = new InitiativeInjector();
  private static final ReportInjector REPORT_INJECTOR = new ReportInjector();
  private static final ErrorInjector ERROR_INJECTOR = new ErrorInjector();

  public UserResource createUserResource() {
    List<ParkingStickerCreationObserver> parkingStickerCreationObservers =
        Arrays.asList(
            COMMUNICATION_INJECTOR.createEmailSender(),
            COMMUNICATION_INJECTOR.createPostalCodeSender(),
            COMMUNICATION_INJECTOR.createSspSender());
    List<AccessPassCreationObserver> accessPassCreationObservers =
        Arrays.asList(
            COMMUNICATION_INJECTOR.createEmailSender(),
            COMMUNICATION_INJECTOR.createPostalCodeSender(),
            COMMUNICATION_INJECTOR.createSspSender());

    BillService billService =
        FUND_INJECTOR.createBillService(
            REPORT_INJECTOR.createReportEventService(),
            ACCOUNT_INJECTOR.createAccountService(),
            INITIATIVE_INJECTOR.getInitiativeFundCollector());

    return USER_INJECTOR.createUserResource(
        ACCOUNT_INJECTOR.getAccountRepository(),
        ACCOUNT_INJECTOR.createAccountFactory(),
        ACCESS_PASS_INJECTOR.createAccessPassService(
            CAR_INJECTOR.createCarService(ACCOUNT_INJECTOR.createAccountService()),
            PARKING_INJECTOR.createParkingAreaService(),
            ACCOUNT_INJECTOR.createAccountService(),
            billService,
            TIME_INJECTOR.createSemesterService(),
            accessPassCreationObservers),
        CAR_INJECTOR.createCarService(ACCOUNT_INJECTOR.createAccountService()),
        ACCOUNT_INJECTOR.createAccountService(),
        PARKING_INJECTOR.createParkingStickerService(
            IS_DEV,
            ACCOUNT_INJECTOR.createAccountService(),
            parkingStickerCreationObservers,
            billService),
        billService);
  }

  public OffenseResource createOffenseResource() {
    return OFFENSE_INJECTOR.createOffenseResource(
        PARKING_INJECTOR.getParkingAreaRepository(),
        FUND_INJECTOR.createMoneyConverter(),
        FUND_INJECTOR.createBillService(
            REPORT_INJECTOR.createReportEventService(),
            ACCOUNT_INJECTOR.createAccountService(),
            INITIATIVE_INJECTOR.getInitiativeFundCollector()),
        ACCOUNT_INJECTOR.createAccountService());
  }

  public GateResource createGateResource() {
    List<AccessPassCreationObserver> accessPassCreationObservers =
        Arrays.asList(
            COMMUNICATION_INJECTOR.createEmailSender(),
            COMMUNICATION_INJECTOR.createPostalCodeSender(),
            COMMUNICATION_INJECTOR.createSspSender());

    return GATE_INJECTOR.createGateResource(
        ACCESS_PASS_INJECTOR.createAccessPassService(
            CAR_INJECTOR.createCarService(ACCOUNT_INJECTOR.createAccountService()),
            PARKING_INJECTOR.createParkingAreaService(),
            ACCOUNT_INJECTOR.createAccountService(),
            FUND_INJECTOR.createBillService(
                REPORT_INJECTOR.createReportEventService(),
                ACCOUNT_INJECTOR.createAccountService(),
                INITIATIVE_INJECTOR.getInitiativeFundCollector()),
            TIME_INJECTOR.createSemesterService(),
            accessPassCreationObservers),
        REPORT_INJECTOR.createReportEventService());
  }

  public CarbonCreditResource createCarbonCreditResource() {
    return CARBON_CREDIT_INJECTOR.createCarbonCreditResource(
        INITIATIVE_INJECTOR.createService(getInitiativeAddedAllocatedAmountObservers()),
        INITIATIVE_INJECTOR.getInitiativeRepository());
  }

  public ParkingAreaResource createParkingAreaResource() {
    return PARKING_INJECTOR.createParkingAreaResource();
  }

  public InitiativeResource createInitiativeResource() {
    List<InitiativeAddedAllocatedAmountObserver> initiativeAddedAllocatedAmountObservers =
        getInitiativeAddedAllocatedAmountObservers();

    return INITIATIVE_INJECTOR.createInitiativeResource(
        INITIATIVE_INJECTOR.createService(initiativeAddedAllocatedAmountObservers));
  }

  public ReportProfitResource createReportProfitResource() {
    return REPORT_INJECTOR.createReportProfitResource();
  }

  public ReportParkingAreaResource createReportParkingAreaResource() {
    return REPORT_INJECTOR.createReportParkingAreaResource(
        PARKING_INJECTOR.createParkingAreaService());
  }

  public List<ExceptionMapper> createExceptionMappers() {
    return ERROR_INJECTOR.createExceptionMappers();
  }

  public Scheduler createScheduler() {
    List<InitiativeAddedAllocatedAmountObserver> initiativeAddedAllocatedAmountObservers =
        getInitiativeAddedAllocatedAmountObservers();

    return newScheduler()
        .withJobHandlers(
            Collections.singletonList(
                CARBON_CREDIT_INJECTOR.createConvertCarbonCreditHandler(
                    INITIATIVE_INJECTOR.createService(initiativeAddedAllocatedAmountObservers),
                    INITIATIVE_INJECTOR.getInitiativeRepository())))
        .build();
  }

  private List<InitiativeAddedAllocatedAmountObserver>
      getInitiativeAddedAllocatedAmountObservers() {
    return Collections.singletonList(
        CARBON_CREDIT_INJECTOR.createCarbonCreditService(
            INITIATIVE_INJECTOR.createService(Collections.emptyList()),
            INITIATIVE_INJECTOR.getInitiativeRepository()));
  }
}
