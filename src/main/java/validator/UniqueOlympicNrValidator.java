package validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import repository.MatchRepository;

//@Component
public class UniqueOlympicNrValidator implements ConstraintValidator<UniqueOlympicNr, String> {

    @Autowired
    private MatchRepository matchRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        System.out.println(!matchRepository.findByNameOlympicNr(value).isEmpty());
        return !matchRepository.findByNameOlympicNr(value).isEmpty();
        //return !matchRepository.existByOlympicNr1(value);
        //		matchRepository.findAll().stream().map(match -> match.getOlympicNr2()).filter(nr -> nr == value).collect(Collectors.toList()) == null;
    }
}

