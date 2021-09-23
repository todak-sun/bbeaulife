package com.todak.bbeaulife.type;

public enum CoupleRole {
    HUSBAND("WIFE"),
    WIFE("HUSBAND"),
    EMPTY("EMPTY");

    private final String opposite;

    CoupleRole(String opposite) {
        this.opposite = opposite;
    }

    public CoupleRole getOpposite() {
        return valueOf(this.opposite);
    }
}
