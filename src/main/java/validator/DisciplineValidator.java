package validator;

import domain.Match;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DisciplineValidator implements ConstraintValidator<DisciplineValidation, Match> {

    @Override
    public void initialize(DisciplineValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(Match match, ConstraintValidatorContext context) {
        String discipline1 = match.getDiscipline1();
        String discipline2 = match.getDiscipline2();

        if (discipline1 == null || discipline1.isEmpty()) {
            return discipline2 == null || discipline2.isEmpty();
        }

        if (discipline1.equals(discipline2)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Disciplines cannot be the same")
                    .addPropertyNode("discipline1")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
