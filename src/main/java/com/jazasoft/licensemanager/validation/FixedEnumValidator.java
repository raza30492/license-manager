package com.jazasoft.licensemanager.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FixedEnumValidator implements ConstraintValidator<FixedEnum, String> {

    private Set<String> AVAILABLE_ENUM_NAMES;

    public static Set<String> getNamesSet(Class<? extends Enum<?>> e) {
        Enum<?>[] enums = e.getEnumConstants();
        String[] names = new String[enums.length];
        for (int i = 0; i < enums.length; i++) {
            names[i] = enums[i].name();
        }
        Set<String> mySet = new HashSet<String>(Arrays.asList(names));
        return mySet;
    }

    @Override
    public void initialize(FixedEnum fixedEnum) {
        Class<? extends Enum<?>> enumSelected = fixedEnum.enumClass();
        AVAILABLE_ENUM_NAMES = getNamesSet(enumSelected);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) return true;
        if (!AVAILABLE_ENUM_NAMES.contains(value)) {
            StringBuilder builder = new StringBuilder();
            builder.append("[");
            AVAILABLE_ENUM_NAMES.forEach(v -> builder.append(v + ","));
            builder.setLength(builder.length()-1);
            builder.append("].");
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Accepted values " + builder.toString()).addConstraintViolation();
            return false;
        }
        return true;
    }

}
