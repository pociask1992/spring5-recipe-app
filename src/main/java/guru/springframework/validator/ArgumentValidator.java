package guru.springframework.validator;

public class ArgumentValidator {

    public static Long convertStringToLong(String valueToConvert, String message) {
        Long valueToReturn;
        try {
            valueToReturn = Long.parseLong(valueToConvert);
        } catch (NumberFormatException ex) {
         throw new NumberFormatException(message);
        }
        return valueToReturn;
    }
}
