package ca.ulaval.glo4003.reports.infrastructure.aggregatefunctions;

import ca.ulaval.glo4003.reports.domain.ReportPeriod;
import ca.ulaval.glo4003.reports.domain.metrics.ReportMetricType;
import java.util.List;
import java.util.stream.Collectors;

public class MaximumAggregateFunctionInMemory extends ReportAggregateFunctionInMemory {

  @Override
  public List<ReportPeriod> aggregate(
      List<ReportPeriod> periods, List<ReportMetricType> metricTypes) {
    return metricTypes.stream()
        .map(metricType -> aggregate(periods, metricType))
        .collect(Collectors.toList());
  }

  private ReportPeriod aggregate(List<ReportPeriod> periods, ReportMetricType metricType) {
    ReportPeriod highestPeriod = null;
    double highestValue = Double.MIN_VALUE;

    for (ReportPeriod period : periods) {
      if (hasMetricsOfType(period, metricType)) {
        double totalValueForPeriod = getTotalValueForPeriod(period, metricType);

        if (highestValue == Double.MIN_VALUE || highestValue < totalValueForPeriod) {
          highestValue = totalValueForPeriod;
          highestPeriod = period;
        }
      }
    }

    if (highestValue == Double.MIN_VALUE) highestValue = 0d;

    return buildReportPeriod(formatMaximumPeriodName(highestPeriod), metricType, highestValue);
  }

  private String formatMaximumPeriodName(ReportPeriod period) {
    return String.format("%s (maximum)", period == null ? "no period" : period.getName());
  }
}
