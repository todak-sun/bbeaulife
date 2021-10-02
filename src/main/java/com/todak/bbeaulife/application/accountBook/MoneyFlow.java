package com.todak.bbeaulife.application.accountBook;

public enum MoneyFlow {
    INCOME(Values.INCOME),
    EXPENDITURE(Values.EXPENDITURE);

    private String value;

    MoneyFlow(String value) {
        if (!this.name().equals(value)) {
            throw new IllegalArgumentException("존재하지 않는 기록 타입");
        }
    }

    public static class Values {
        public static final String INCOME = "INCOME";
        public static final String EXPENDITURE = "EXPENDITURE";
    }

}
