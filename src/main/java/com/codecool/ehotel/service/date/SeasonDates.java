package com.codecool.ehotel.service.date;

import java.time.LocalDate;

public interface SeasonDates {
    LocalDate getStartDate();
    LocalDate getEndDate(LocalDate startDate);
    LocalDate getChosenDate(LocalDate startDate, LocalDate endDate);
}