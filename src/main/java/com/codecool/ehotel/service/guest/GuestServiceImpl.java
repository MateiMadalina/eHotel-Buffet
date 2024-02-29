package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.GuestType;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GuestServiceImpl implements GuestService {

    private final GuestCreator guestCreator;

    public GuestServiceImpl(GuestCreator guestCreator) {
        this.guestCreator = guestCreator;
    }

    @Override
    public Set<Guest> getGuestsForDay(List<Guest> guests, LocalDate date) {
        return guests.stream()
                .filter(guest -> (guest.checkIn().isBefore(date) || guest.checkIn().equals(date)) &&
                        (guest.checkOut().isAfter(date) || guest.checkOut().equals(date)))
                .collect(Collectors.toSet());
    }

    @Override
    public List<Guest> generateGuestsForSeason(LocalDate startDate, LocalDate endDate, int hotelGuestNum) {
        return IntStream.range(0, hotelGuestNum)
                .mapToObj(i -> generateRandomGuest(startDate, endDate))
                .collect(Collectors.toList());
    }

    @Override
    public Map<Integer, List<Guest>> groupGuestsIntoCycles(Set<Guest> guests, int numberOfCycles) {
        List<Guest> guestList = new ArrayList<>(guests);

        int totalGuests = guestList.size();
        int guestsPerCycle = totalGuests / numberOfCycles;
        int remainingGuests = totalGuests % numberOfCycles;

        Map<Integer, List<Guest>> cycles = new HashMap<>();

        int currentIndex = 0;

        for (int cycleNumber = 0; cycleNumber < numberOfCycles; cycleNumber++) {
            int guestsInCycle = cycleNumber < remainingGuests ? guestsPerCycle + 1 : guestsPerCycle;

            List<Guest> cycleGuests = new ArrayList<>();
            for (int i = 0; i < guestsInCycle; i++) {
                cycleGuests.add(guestList.get(currentIndex++));
            }
            cycles.put(cycleNumber, cycleGuests);
        }
        return cycles;
    }

    private Guest generateRandomGuest(LocalDate seasonStart, LocalDate seasonEnd) {
        String guestName = guestCreator.getGuestName();
        GuestType guestType = guestCreator.getGuestType();
        LocalDate checkIn = guestCreator.getCheckIn(seasonStart, seasonEnd);
        LocalDate checkOut = guestCreator.getCheckOut(seasonStart);

        return new Guest(guestName, guestType, checkIn, checkOut);
    }
}