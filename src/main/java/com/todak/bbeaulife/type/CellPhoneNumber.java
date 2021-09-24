package com.todak.bbeaulife.type;

import lombok.Getter;

@Getter
public class CellPhoneNumber {

    private final String head;
    private final String middle;
    private final String tail;

    private CellPhoneNumber(String head, String middle, String tail) {

        this.head = head;
        this.middle = middle;
        this.tail = tail;
    }

    public static CellPhoneNumber fromFullNumber(String number) {
        try {
            String[] split = number.split("-");
            if (split.length != 3) {
                throw new RuntimeException();
            }
            return new CellPhoneNumber(split[0], split[1], split[2]);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public static CellPhoneNumber from(String head, String middle, String tail) {
        return new CellPhoneNumber(head, middle, tail);
    }

    public String fullNumber() {
        return String.join("-", head, middle, tail);
    }

}
