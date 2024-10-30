package validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OlympicNr2RangeValidator.class)
@Documented
public @interface OlympicNr2Range {
    String message() default "{validation.olympicNr2.OlympicNr2Range.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

