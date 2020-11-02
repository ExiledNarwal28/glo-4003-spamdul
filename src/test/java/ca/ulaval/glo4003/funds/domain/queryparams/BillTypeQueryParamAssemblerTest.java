package ca.ulaval.glo4003.funds.domain.queryparams;

import static ca.ulaval.glo4003.funds.domain.queryparams.BillTypeQueryParamAssembler.BILL_TYPE_PARAM;
import static ca.ulaval.glo4003.funds.helpers.BillMother.createBillType;
import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.funds.domain.BillQueryBuilder;
import ca.ulaval.glo4003.funds.domain.BillType;
import ca.ulaval.glo4003.funds.exception.InvalidBillTypeException;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BillTypeQueryParamAssemblerTest {

  private static BillQueryParamAssembler queryAssembler;
  @Mock private BillQueryBuilder queryBuilder;
  @Mock private BillQueryBuilder assembledQueryBuilder;

  private BillType billType = createBillType();
  private BillQueryParams params = new BillQueryParams();

  @Before
  public void setUp() {
    queryAssembler = new BillTypeQueryParamAssembler();
    when(queryBuilder.withBillType(billType)).thenReturn(assembledQueryBuilder);
  }

  @Test
  public void whenAssemblingWithoutBillType_thenShouldReturnSameBuilder() {
    BillQueryBuilder actualQueryBuilder = queryAssembler.assemble(queryBuilder, params);

    assertThat(queryBuilder).isEqualTo(actualQueryBuilder);
  }

  @Test
  public void whenAssemblingWithBillType_thenShouldAssembleBuilder() {
    params.add(BILL_TYPE_PARAM, Collections.singletonList(billType.toString()));

    BillQueryBuilder actualQueryBuilder = queryAssembler.assemble(queryBuilder, params);

    assertThat(assembledQueryBuilder).isEqualTo(actualQueryBuilder);
  }

  @Test(expected = InvalidBillTypeException.class)
  public void whenAssemblingWithInvalidBillType_thenShouldThrowInvalidPackageException() {
    params.add(BILL_TYPE_PARAM, Collections.singletonList("invalidBillType"));

    queryAssembler.assemble(queryBuilder, params);
  }
}