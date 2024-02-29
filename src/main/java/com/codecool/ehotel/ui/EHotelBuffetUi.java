package com.codecool.ehotel.ui;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.service.buffet.BreakfastManagerImpl;
import com.codecool.ehotel.service.date.SeasonDates;
import com.codecool.ehotel.service.guest.GuestService;
import com.codecool.ehotel.service.logger.Logger;

import java.time.LocalDate;
import java.util.*;

public class EHotelBuffetUi {

    private final Logger logger;
    private final SeasonDates seasonDates;
    private final GuestService guestService;
    private final BreakfastManagerImpl breakfastManagerImpl;

    public EHotelBuffetUi(Logger logger, SeasonDates seasonDates, GuestService guestService,
                          BreakfastManagerImpl breakfastManagerImpl) {
        this.logger = logger;
        this.seasonDates = seasonDates;
        this.guestService = guestService;
        this.breakfastManagerImpl = breakfastManagerImpl;
    }

    public void run(int hotelGuestNum) {
        welcomeMessage();

        LocalDate startDate = seasonDates.getStartDate();
        LocalDate endDate = seasonDates.getEndDate(startDate);
        LocalDate chosenDate = seasonDates.getChosenDate(startDate, endDate);

        List<Guest> guests = guestService.generateGuestsForSeason(startDate, endDate, hotelGuestNum);

        logger.logInfo("----------------------------------------------------------");
        logger.logInfo("Between " + startDate + " and " + endDate + ", a total of " + hotelGuestNum + " guests was expected.");
        logger.logInfo("On " + chosenDate + " we simulated a breakfast buffet:");

        Set<Guest> guestsForTheDay = guestService.getGuestsForDay(guests, chosenDate);

        logger.logInfo("Number of guests on that day: " + guestsForTheDay.size());
        logger.logInfo("----------------------------------------------------------");

        breakfastManagerImpl.serve(guestsForTheDay, chosenDate);
    }

    private void welcomeMessage() {
        logger.logInfo("Welcome to the EHotel Buffet simulation!");
        logger.logInfo("Select a period of maximum 1 month.");
    }
}