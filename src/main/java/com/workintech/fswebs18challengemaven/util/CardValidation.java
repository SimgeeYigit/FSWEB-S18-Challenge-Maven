package com.workintech.fswebs18challengemaven.util;

import com.workintech.fswebs18challengemaven.entity.Card;
import com.workintech.fswebs18challengemaven.exceptions.CardException;
import org.springframework.http.HttpStatus;

public class CardValidation {

    public static void idValid(long id){
        if (id < 0)
            throw new CardException("Id cannot be less than 0!", HttpStatus.BAD_REQUEST);
    }

    public static void cardCheck(Card card) {
        if (card == null)
            throw new CardException("Card cannot be null!", HttpStatus.BAD_REQUEST);
    }

    public static void textCheck(String text, String fieldName) {
        if (text == null || text.isBlank()) {
            throw new CardException(fieldName + " cannot be null or blank!", HttpStatus.BAD_REQUEST);
        }
    }

    public static void colorCheck(String color) {
        if (!color.equals("SPADE") && !color.equals("HEARTH") && !color.equals("DIAMOND") && !color.equals("CLUB")) {
            throw new CardException("Invalid color", HttpStatus.BAD_REQUEST);
        }
    }

    public static void valueValid(int value) {
        if (value < 2|| value > 10) {
            throw new CardException("Value cannot be less than 2 or greater than 10!", HttpStatus.BAD_REQUEST);
        }
    }
}
