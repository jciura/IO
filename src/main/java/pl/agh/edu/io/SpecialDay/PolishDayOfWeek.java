package pl.agh.edu.io.SpecialDay;

import java.time.DayOfWeek;

public enum PolishDayOfWeek {
    MONDAY("Poniedziałek"),
    TUESDAY("Wtorek"),
    WEDNESDAY("Środa"),
    THURSDAY("Czwartek"),
    FRIDAY("Piątek"),
    SATURDAY("Sobota"),
    SUNDAY("Niedziela");

    private final String polishName;

    PolishDayOfWeek(String polishName) {
        this.polishName = polishName;
    }

    public static String fromDayOfWeek(DayOfWeek dayOfWeek) {
        return PolishDayOfWeek.valueOf(dayOfWeek.name()).getPolishName();
    }

    public String getPolishName() {
        return polishName;
    }
}
