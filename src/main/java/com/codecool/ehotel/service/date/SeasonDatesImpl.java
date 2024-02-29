package com.codecool.ehotel.service.date;

import com.codecool.ehotel.service.logger.Logger;
import com.codecool.ehotel.service.scanner.ScannerInterface;

import java.time.LocalDate;
import java.time.Period;

public class SeasonDatesImpl implements SeasonDates{

    private final ScannerInterface scanner;
    private final Logger logger;

    public SeasonDatesImpl(ScannerInterface scanner, Logger logger) {
        this.scanner = scanner;
        this.logger = logger;
    }

    @Override
    public LocalDate getStartDate() {
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

    @Override
    public LocalDate getEndDate(LocalDate startDate) {
        logger.logInfo("Pick an end date: ");
        while(true) {
            String answer = scanner.getStringInput();
            try {
                LocalDate dateAnswer = LocalDate.parse(answer);
                Period period = Period.between(startDate, dateAnswer);
                int months = (int) period.toTotalMonths();
                int days = period.getDays();
                if((months == 0 && days >= 0 || months == 1 && days <= 0) && (days >= 3)) {
                    return dateAnswer;
                } else {
                    logger.logError("Pick an interval within 1 month and over 3 days");
                }
            } catch (Exception e) {
                logger.logError("Pick a valid date");
            }
        }
    }

    @Override
    public LocalDate getChosenDate(LocalDate startDate, LocalDate endDate) {
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