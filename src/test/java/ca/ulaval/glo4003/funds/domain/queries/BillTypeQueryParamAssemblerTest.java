package ca.ulaval.glo4003.funds.domain.queries;

import static ca.ulaval.glo4003.funds.domain.queries.BillTypeQueryParamAssembler.BILL_TYPE_PARAM;
import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.funds.domain.BillQueryBuilder;
import ca.ulaval.glo4003.funds.domain.BillType;
import ca.ulaval.glo4003.funds.exception.InvalidBillTypeException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BillTypeQueryParamAssemblerTest {

  private static BillQueryParamAssembler queryAssembler;
  @Mock private static BillQueryBuilder queryBuilder;
  @Mock private static BillQueryBuilder assembledQueryBuilder;

  private BillType billType = BillType.OFFENSE;
  private Map<String, List<String>> params = new HashMap<>();

  @Before
  public void setUpMocks() {
    queryAssembler = new BillTypeQueryParamAssembler();
    when(queryBuilder.withBillType(billType)).thenReturn(assembledQueryBuilder);
  }

  @Test
  public void assemble_withoutBillType_shouldReturnSameBuilder() {
    BillQueryBuilder actualQueryBuilder = queryAssembler.assemble(queryBuilder, params);

    assertThat(queryBuilder).isEqualTo(actualQueryBuilder);
  }

  @Test
  public void assemble_withBillType_shouldAssembleBuilder() {
    params.put(BILL_TYPE_PARAM, Collections.singletonList(billType.toString()));

    BillQueryBuilder actualQueryBuilder = queryAssembler.assemble(queryBuilder, params);

    assertThat(assembledQueryBuilder).isEqualTo(actualQueryBuilder);
  }

  @Test(expected = InvalidBillTypeException.class)
  public void assemble_withInvalidBillType_shouldThrowInvalidPackageException() {
    params.put(BILL_TYPE_PARAM, Collections.singletonList("invalidBillType"));

    queryAssembler.assemble(queryBuilder, params);
  }
}
