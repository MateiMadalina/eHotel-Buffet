package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Guest;

import java.time.LocalDate;
import java.util.Set;

public interface BreakfastManager {
    void serve(Set<Guest> guestsForTheDay, LocalDate chosenDate);
}