package validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DisciplineValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DisciplineValidation {
    String message() default "{validation.disciplines.NotSameDisciplines.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

