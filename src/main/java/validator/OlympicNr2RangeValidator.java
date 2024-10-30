package validator;

import domain.Match;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OlympicNr2RangeValidator implements ConstraintValidator<OlympicNr2Range, Match> {
    @Override
    public boolean isValid(Match match, ConstraintValidatorContext context) {
        if (match == null || match.getOlympicNr1() == null || match.getOlympicNr2() == null) {
            return false;
        }

        try {
            int olympicNr1 = Integer.parseInt(match.getOlympicNr1());
            int olympicNr2 = Integer.parseInt(match.getOlympicNr2());
            return Math.abs(olympicNr2 - olympicNr1) <= 1000;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

