package ca.ulaval.glo4003.reports.infrastructure.metrics;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4003.reports.domain.metrics.ReportMetric;
import ca.ulaval.glo4003.reports.domain.metrics.ReportMetricType;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class InMemoryReportMetricBuilderTest {

  private InMemoryReportMetricBuilder reportMetricBuilder;

  @Before
  public void setUp() {
    reportMetricBuilder = new InMemoryReportMetricBuilder();
  }

  @Test
  public void givenNoMetricType_whenBuildingMany_thenReturnNoMetric() {
    List<ReportMetric> metrics = reportMetricBuilder.someMetrics().buildMany();

    assertThat(metrics).hasSize(0);
  }

  @Test
  public void givenProfitsMetricType_whenBuildingMany_thenReturnProfitsMetric() {
    List<ReportMetricType> metricTypes = Collections.singletonList(ReportMetricType.PROFITS);

    List<ReportMetric> metrics =
        reportMetricBuilder.someMetrics().withTypes(metricTypes).buildMany();

    assertThat(metrics).hasSize(1);
    assertThat(metrics.get(0)).isInstanceOf(InMemoryProfitsMetric.class);
  }
}
