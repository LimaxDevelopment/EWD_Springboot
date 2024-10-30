package validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateTimeRangeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateTimeRange {
    String message() default "{validation.dateTimeRange.message}";
    String startDate();
    String endDate();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
