package com.codecool.ehotel.ui;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.MealType;
import com.codecool.ehotel.service.buffet.BreakfastManager;
import com.codecool.ehotel.service.guest.GuestService;
import com.codecool.ehotel.service.logger.Logger;
import com.codecool.ehotel.service.scanner.ScannerImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

public class EHotelBuffetUi {

    private Logger logger;
    private ScannerImpl scanner;
    private GuestService guestService;
    private BreakfastManager breakfastManager;

    public EHotelBuffetUi(Logger logger, ScannerImpl scanner, GuestService guestService,
                          BreakfastManager breakfastManager) {
        this.logger = logger;
        this.scanner = scanner;
        this.guestService = guestService;
        this.breakfastManager = breakfastManager;
    }

    public void run() {
        welcomeMessage();

        LocalDate startDate = getStartDate();
        LocalDate endDate = getEndDate(startDate);
        LocalDate chosenDate = getChosenDate(startDate, endDate);
        List<Guest> guests = generateGuestsForSeason(startDate, endDate, chosenDate);

        logger.logInfo(guests.toString());
        Buffet buffet = new Buffet(generatePortions(chosenDate));
        logger.logInfo("----------------------------------------------------------");
        logger.logInfo("Initial spread: " + buffet);
        breakfastManager.serve(guests, buffet, chosenDate);
    }

    private List<Guest> generateGuestsForSeason(LocalDate startDate, LocalDate endDate, LocalDate chosenDate) {
        final int hotelGuestsNr = 1000;

        logger.logInfo("----------------------------------------------------------");
        logger.logInfo("Between " + startDate + " and " + endDate + ", a total of " + hotelGuestsNr + " guests was expected.");
        logger.logInfo("On " + chosenDate + " we simulated a breakfast buffet:");

        List<Guest> guests = new ArrayList<>();
        for (int i = 0; i < hotelGuestsNr; i++) {
            guests.add(guestService.generateRandomGuest(startDate, endDate));
        }

        return guests;
    }

    private Map<MealType, List<LocalDateTime>> generatePortions(LocalDate chosenDate) {
        LocalDateTime startDateTime = LocalDateTime.of(chosenDate.getYear(), chosenDate.getMonth(), chosenDate.getDayOfMonth(), 6, 0, 0);
        Map<MealType, List<LocalDateTime>> portions = new HashMap<>();
        for (int i = 0; i < MealType.values().length; i++) {
            List<LocalDateTime> times = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                times.add(startDateTime);
            }
            portions.put(MealType.values()[i], times);
        }
        return portions;
    }

    private void welcomeMessage() {
        logger.logInfo("Welcome to the EHotel Buffet simulation!");
        logger.logInfo("Select a period of maximum 1 month.");
    }

    private LocalDate getStartDate() {
        logger.logInfo("Pick a start date: ");
        while (true) {
            String answer = scanner.getStringInput();
            try {
                return LocalDate.parse(answer);
            } catch (Exception e) {
                logger.logError("Please pick a valid date: ");
            }
        }
    }

    private  LocalDate getEndDate(LocalDate startDate) {
        logger.logInfo("Pick an end date: ");
        while(true) {
            String answer = scanner.getStringInput();
            try {
                LocalDate dateAnswer = LocalDate.parse(answer);
                Period period = Period.between(startDate, dateAnswer);
                int months = (int) period.toTotalMonths();
                int days = period.getDays();
                if(months == 0 && days >= 0 || months == 1 && days <= 0) {
                    return dateAnswer;
                } else {
                    logger.logError("Pick an interval within 1 month");
                }
            } catch (Exception e) {
                logger.logError("Pick a valid date");
            }
        }
    }

    private LocalDate getChosenDate(LocalDate startDate, LocalDate endDate) {
        logger.logInfo("Pick a date between " + startDate + " and " + endDate);
        while (true) {
            String answer = scanner.getStringInput();
            try {
                LocalDate dateAnswer = LocalDate.parse(answer);
                if(dateAnswer.isBefore(startDate) || dateAnswer.isAfter(endDate)) {
                    logger.logError("Pick a date within the chosen season");
                } else {
                    return dateAnswer;
                }
            } catch (Exception e) {
                logger.logError("Please pick a valid date: ");
            }
        }
    }
}