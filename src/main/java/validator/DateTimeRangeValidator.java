package validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeRangeValidator implements ConstraintValidator<DateTimeRange, LocalDateTime> {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    public void initialize(DateTimeRange constraintAnnotation) {
        startDate = LocalDateTime.parse(constraintAnnotation.startDate(), formatter);
        endDate = LocalDateTime.parse(constraintAnnotation.endDate(), formatter);
    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.isAfter(startDate) && value.isBefore(endDate);
    }
}
