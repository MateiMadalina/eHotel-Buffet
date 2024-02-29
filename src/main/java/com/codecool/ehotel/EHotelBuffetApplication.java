package com.codecool.ehotel;

import com.codecool.ehotel.service.buffet.BreakfastManagerImpl;
import com.codecool.ehotel.service.buffet.BuffetService;
import com.codecool.ehotel.service.buffet.BuffetServiceImpl;
import com.codecool.ehotel.service.date.SeasonDates;
import com.codecool.ehotel.service.date.SeasonDatesImpl;
import com.codecool.ehotel.service.guest.GuestCreator;
import com.codecool.ehotel.service.guest.GuestCreatorImpl;
import com.codecool.ehotel.service.guest.GuestService;
import com.codecool.ehotel.service.guest.GuestServiceImpl;
import com.codecool.ehotel.service.logger.ConsoleLogger;
import com.codecool.ehotel.service.logger.Logger;
import com.codecool.ehotel.service.scanner.ScannerImpl;
import com.codecool.ehotel.service.scanner.ScannerInterface;
import com.codecool.ehotel.ui.EHotelBuffetUi;

import java.util.Random;

public class EHotelBuffetApplication {

    private final static int hotelGuestNum = 1000;

    public static void main(String[] args) {
        Random rand = new Random();
        ScannerInterface scanner = new ScannerImpl();
        Logger consoleLogger = new ConsoleLogger();

        SeasonDates seasonDates = new SeasonDatesImpl(scanner, consoleLogger);
        GuestCreator guestCreator = new GuestCreatorImpl(rand);
        GuestService guestService = new GuestServiceImpl(guestCreator);
        BuffetService buffetService = new BuffetServiceImpl(rand);

        BreakfastManagerImpl breakfastManagerImpl = new BreakfastManagerImpl(buffetService, guestService, consoleLogger);
        EHotelBuffetUi eHotelBuffetUi = new EHotelBuffetUi(consoleLogger, seasonDates, guestService, breakfastManagerImpl);

        eHotelBuffetUi.run(hotelGuestNum);
    }
}