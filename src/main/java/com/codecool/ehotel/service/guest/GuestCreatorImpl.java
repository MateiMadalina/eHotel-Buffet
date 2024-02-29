package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.GuestType;

import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.Random;

public class GuestCreatorImpl implements GuestCreator {

    private final Random rand;
    private final static int maxSeasonStay = 7;
    private int actualStay;
    private int numberForFirstDay;
    private int numberOfLastDay;

    public GuestCreatorImpl(Random rand) {
        this.rand = rand;

        this.actualStay = 0;
        this.numberForFirstDay = 0;
        this.numberOfLastDay = 0;
    }

    private final String[] firstNames = {
            "Tomas", "Samuel", "George", "Noelle", "Lily",
            "Rosa", "Hugh", "Jack", "Will", "Rachel", "Nadia",
            "Chloe"
    };
    private final String[] lastNames = {
            "Rutherford", "Hammond", "Nelson", "Dobson",
            "Verne", "Hunting", "Boston", "Sparrow", "Turner",
            "Greene", "Hamilton", "Tucker"};

    @Override
    public String getGuestName() {
        return firstNames[rand.nextInt(firstNames.length)] + " " + lastNames[rand.nextInt(lastNames.length)];
    }

    @Override
    public GuestType getGuestType() {
        return GuestType.values()[rand.nextInt(GuestType.values().length)];
    }

    @Override
    public LocalDate getCheckIn(LocalDate seasonStart, LocalDate seasonEnd) {
        actualStay = getActualStay(seasonStart, seasonEnd);
        int seasonDaysDifference = getSeasonDaysDifference(seasonStart, seasonEnd, actualStay);
        numberForFirstDay = getNumberForFirstDay(seasonDaysDifference);

        return seasonStart.plusDays(numberForFirstDay);
    }

    @Override
    public LocalDate getCheckOut(LocalDate seasonStart) {
        numberOfLastDay = getNumberOfLastDay(numberForFirstDay, actualStay);

        return seasonStart.plusDays(numberOfLastDay);
    }

    private int getActualStay(LocalDate seasonStart, LocalDate seasonEnd) {
        int seasonLength = (int) ChronoUnit.DAYS.between(seasonStart, seasonEnd);
        int maxPeriod = Math.min(seasonLength, maxSeasonStay);

        return rand.nextInt(maxPeriod) + 1;
    }

    private int getSeasonDaysDifference(LocalDate seasonStart, LocalDate seasonEnd, int actualStay) {
        return (int) ((seasonEnd.toEpochDay() - seasonStart.toEpochDay()) - actualStay);
    }

    private int getNumberForFirstDay(int seasonDaysDifference) {
        return rand.nextInt(seasonDaysDifference + 1);
    }

    private int getNumberOfLastDay(int numberForFirstDay, int actualStay) {
        return numberForFirstDay + actualStay;
    }
}