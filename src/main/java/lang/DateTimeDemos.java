package lang;

import java.time.*;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateTimeDemos {

    public static void main(String[] args) {
        // Heutiges Datum abrufen
        LocalDateTime now = LocalDateTime.now();    // Datum und Uhrzeit
        LocalDate dateNow = LocalDate.now();        // nur Datum
        LocalTime timeNow = LocalTime.now();        // nur Uhrzeit
        System.out.println("Heute ist der " + now.getDayOfMonth() +
                ". " + now.getMonth().getDisplayName(TextStyle.FULL, Locale.JAPAN) +
                " " + now.getYear());
        System.out.println("Es ist jetzt " + now.getHour() + ":" + now.getMinute() + "." + now.getSecond());

        // Spezielles Datum/Zeit einstellen
        LocalDate dateOfBirth = LocalDate.of(1976, Month.JUNE, 3);
        System.out.println("Du wurdest an einem " + dateOfBirth.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.GERMANY) + " geboren.");
        LocalTime timeUE1 = LocalTime.of(8, 15);
        System.out.println("Unterrichtsbeginn ist um " + timeUE1.getHour() + ":" + timeUE1.getMinute());
        Period schuljahr = Period.ofWeeks(43);

        // Mit Datum und Zeit rechnen
        LocalDate date40er = dateOfBirth.plusYears(40);
        System.out.println("Dein 40. Geburtstag war an einem " + date40er.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.GERMANY));
        LocalTime timeUE2 = timeUE1.plusMinutes(55);
        System.out.println("Die 2.UE beginnt um " + timeUE2.getHour() + ":" + timeUE2.getMinute());

        // Datum/Zeitdifferenzen berechnen
        Period lifeTime = dateOfBirth.until(dateNow);
        System.out.println("Zwischen deinem Geburtstag und jetzt liegen " +
                lifeTime.getYears() + " Jahre, " +
                lifeTime.getMonths() + " Monate und " +
                lifeTime.getDays() + " Tage.");
    }
}
