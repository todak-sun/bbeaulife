package com.todak.bbeaulife.application.memory;

import java.time.LocalDateTime;

public class Coupon {

    private String image;

    private String description;

    private LocalDateTime expirationDateTime;

    public boolean isExpired() {
        return this.expirationDateTime.isAfter(expirationDateTime);
    }


}
