package ca.ulaval.glo4003.reports.domain.metrics;

import ca.ulaval.glo4003.reports.domain.ReportPeriodData;

// TODO : Make sure all is used in ReportMetric
public abstract class ReportMetric {

  public abstract ReportMetricType getType();

  public abstract void calculate(ReportPeriodData data);

  protected ReportMetricData toMetricData(int value) {
    return new ReportMetricData() {
      @Override
      public ReportMetricType getType() {
        return ReportMetric.this.getType();
      }

      @Override
      public int getValue() {
        return value;
      }
    };
  }
}