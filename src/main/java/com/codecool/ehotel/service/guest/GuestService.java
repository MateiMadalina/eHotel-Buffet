package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GuestService {
    Set<Guest> getGuestsForDay(List<Guest> guests, LocalDate date);
    List<Guest> generateGuestsForSeason(LocalDate startDate, LocalDate endDate, int hotelGuestNum);
    Map<Integer, List<Guest>> groupGuestsIntoCycles(Set<Guest> guests, int numberOfCycles);
}