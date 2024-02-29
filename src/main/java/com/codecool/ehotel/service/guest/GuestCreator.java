package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.GuestType;

import java.time.LocalDate;

public interface GuestCreator {
    String getGuestName();

    GuestType getGuestType();

    LocalDate getCheckIn(LocalDate seasonStart, LocalDate seasonEnd);

    LocalDate getCheckOut(LocalDate seasonStart);
}