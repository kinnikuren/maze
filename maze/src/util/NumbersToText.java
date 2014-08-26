package util;

public class NumbersToText {

    public static String convert(int num) {
        String result = "";
        switch (num) {
            case 10:
                return "ten";
            case 11:
                return "eleven";
            case 12:
                return "twelve";
            case 13:
                return "thirteen";
            case 14:
                return "fourteen";
            case 15:
                return "fifteen";
            case 16:
                return "sixteen";
            case 17:
                return "seventeen";
            case 18:
                return "eighteen";
            case 19:
                return "nineteen";
        }
        if (num < 10) {
            return singleDigits(num);
        }
        else if (num >=20 && num < 30) {
            result = "twenty";
            if (num > 20) {
                result = (result + "-" + singleDigits(num%20));
            }
        } else {
            result = ((Integer)num).toString();
        }
        return result;
    }

    public static String singleDigits(int num) {
        switch (num) {
        case 1:
            return "one";
        case 2:
            return "two";
        case 3:
            return "three";
        case 4:
            return "four";
        case 5:
            return "five";
        case 6:
            return "six";
        case 7:
            return "seven";
        case 8:
            return "eight";
        case 9:
            return "nine";
            }
        return null;
    }
}
