package junit.isbn;

public class Validator {
    private static final int SHORT_ISBN = 10;
    private static final int LONG_ISBN = 13;

    public boolean check(String isbn) {
        if (isbn.length() == SHORT_ISBN) {
            return checkShortIsbn(isbn);
        } else if (isbn.length() == LONG_ISBN) {
            return checkLongIsbn(isbn);
        }
        throw new NumberFormatException();
    }

    private boolean checkLongIsbn(String isbn) {
        int result = 0;
        for (int i = 0; i < LONG_ISBN; i++) {
            if (Character.isDigit(isbn.charAt(i))) {
                if (i % 2 == 0) {
                    result += Character.getNumericValue(isbn.charAt(i)) * 3;
                } else {
                    result += Character.getNumericValue(isbn.charAt(i));
                }
            } else {
                throw new NumberFormatException();
            }
        }
        return (result % 10 == 0);
    }

    private boolean checkShortIsbn(String isbn) {
        int result = 0;
        for (int i = 0; i < SHORT_ISBN; i++) {
            if (i == 9 && isbn.charAt(9) == 'X') {
                result += 10;
            } else if (Character.isDigit(isbn.charAt(i))) {
                result += Character.getNumericValue(isbn.charAt(i)) * (SHORT_ISBN - i);
            } else {
                throw new NumberFormatException();
            }
        }
        return (result % 11 == 0);
    }
}