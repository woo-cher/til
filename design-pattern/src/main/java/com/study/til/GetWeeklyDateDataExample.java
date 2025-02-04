package com.study.til;

import java.time.DayOfWeek;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class GetWeeklyDateDataExample {
  public static void main(String[] args) {
    final var START_DAY = DayOfWeek.THURSDAY;
    final var END_DAY = DayOfWeek.WEDNESDAY;

    var currentDate = ZonedDateTime.now(ZoneOffset.UTC);
    var formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    final var endYmd = currentDate.with(
        TemporalAdjusters.previousOrSame(END_DAY)
    ).format(formatter);

    final var startYmd = currentDate.with(
        TemporalAdjusters.previousOrSame(START_DAY)
    ).minusWeeks(1).format(formatter);

    System.out.println(currentDate);
    System.out.println(endYmd);
    System.out.println(startYmd);
  }
}
