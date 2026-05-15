package com.pinank.goldpredictor;

public class TradingCalendar {
    public static Day normalizeToTradingDay(Day day) {
        if (day == Day.SATURDAY || day == Day.SUNDAY) {
            return Day.MONDAY;
        }
        return day;
    }

    public static int getRemainingTradingDays(Day day) {
        day = normalizeToTradingDay(day);

        return switch (day) {
            case MONDAY -> 5;
            case TUESDAY -> 4;
            case WEDNESDAY -> 3;
            case THURSDAY -> 2;
            case FRIDAY -> 1;
            default -> 5;
        };
    }
}
