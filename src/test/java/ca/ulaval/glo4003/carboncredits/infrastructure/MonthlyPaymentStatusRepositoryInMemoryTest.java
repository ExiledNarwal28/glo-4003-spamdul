package ca.ulaval.glo4003.carboncredits.infrastructure;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.carboncredits.domain.MonthlyPaymentStatus;
import ca.ulaval.glo4003.carboncredits.domain.MonthlyPaymentStatusRepository;
import org.junit.Before;
import org.junit.Test;

public class MonthlyPaymentStatusRepositoryInMemoryTest {
  private MonthlyPaymentStatusRepository monthlyPaymentStatusRepository;

  @Before
  public void setUp() {
    monthlyPaymentStatusRepository = new MonthlyPaymentStatusRepositoryInMemory();
  }

  @Test
  public void whenGetting_thenReturnEnable() {
    MonthlyPaymentStatus monthlyPaymentStatus = monthlyPaymentStatusRepository.get();

    assertThat(monthlyPaymentStatus).isEqualTo(MonthlyPaymentStatus.ENABLE);
  }

  @Test
  public void givenSavedMonthlyPaymentStatus_whenGetting_thenReturnNewlySavedStatus() {
    MonthlyPaymentStatus monthlyPaymentStatus = MonthlyPaymentStatus.DISABLE;

    monthlyPaymentStatusRepository.save(monthlyPaymentStatus);
    MonthlyPaymentStatus savedMonthlyPaymentStatus = monthlyPaymentStatusRepository.get();

    assertThat(savedMonthlyPaymentStatus).isEqualTo(monthlyPaymentStatus);
  }
}
