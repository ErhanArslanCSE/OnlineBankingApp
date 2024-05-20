package org.ea.finance.onlinebankingapp.util.datavalidator.util;

public abstract class Validator {

    public abstract ValidationResult validateObject(Object value,boolean existingObject);

    public static boolean isValidDouble(Object value) {
        if (value == null || ((String)value).isEmpty()) {
            return false;
        }

        try {
            Double.parseDouble((String)value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean isValidEMail(Object value) {
        return ((String) value).matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }
    public static boolean isValidInt(Object value) {
        if (value == null || ((String)value).isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt((String)value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidLength(Object value,int minLength, int maxLength) {
        int length = ((String) value).length();
        return length >= minLength && length <= maxLength;
    }
}