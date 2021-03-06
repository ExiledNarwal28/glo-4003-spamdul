package ca.ulaval.glo4003.times.domain;

import java.time.Year;
import java.util.Calendar;

public class TimeYear extends TimeCalendar {

  public TimeYear(CustomDateTime customDateTime) {
    super(customDateTime);
  }

  public TimeYear(int year) {
    super(year);
  }

  @Override
  protected CustomDateTime firstDateTime() {
    Calendar year = thatYear();
    int firstDayOfYear = calendar.getActualMinimum(Calendar.DAY_OF_YEAR);
    year.set(Calendar.DAY_OF_YEAR, firstDayOfYear);
    setAtMinimumTime(year);
    return toDateTime(year);
  }

  @Override
  protected CustomDateTime lastDateTime() {
    Calendar year = thatYear();
    int lastDayOfYear = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
    year.set(Calendar.DAY_OF_YEAR, lastDayOfYear);
    setAtMaximumTime(year);
    return toDateTime(year);
  }

  private Calendar thatYear() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, getYear());
    return calendar;
  }

  public static TimeYear now() {
    return new TimeYear(Year.now().getValue());
  }

  @Override
  public String toString() {
    return Integer.toString(calendar.get(Calendar.YEAR));
  }

  @Override
  public int compareTo(TimeCalendar other) {
    return getYear() - other.getYear();
  }
}
