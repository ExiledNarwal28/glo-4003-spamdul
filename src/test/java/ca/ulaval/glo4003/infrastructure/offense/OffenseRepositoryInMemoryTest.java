package ca.ulaval.glo4003.infrastructure.offense;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.domain.offense.Offense;
import ca.ulaval.glo4003.domain.offense.OffenseCodes;
import ca.ulaval.glo4003.domain.offense.OffenseRepository;
import ca.ulaval.glo4003.domain.offense.exceptions.OffenseNotFoundException;
import org.junit.Before;
import org.junit.Test;

public class OffenseRepositoryInMemoryTest {

  private static final String A_REASON = "Not enough chips";
  private static final String A_CODE = OffenseCodes.VIG_01.toString();
  private static final int AN_AMOUNT = 420;

  private OffenseRepository offenseRepository;

  @Before
  public void setup() {
    offenseRepository = new OffenseRepositoryInMemory();
  }

  @Test
  public void whenSaving_shouldAddOffenseToMap() {
    Offense offense = new Offense(A_REASON, A_CODE, AN_AMOUNT);

    offenseRepository.save(offense);

    assertThat(offenseRepository.findByCode(OffenseCodes.get(A_CODE)) == offense);
  }

  @Test(expected = OffenseNotFoundException.class)
  public void givenCodeWithNoOffense_whenFindingByCode_shouldThrowException() {
    offenseRepository.findByCode(OffenseCodes.ZONE_03);
  }
}
