package com.jazasoft.licensemanager.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by razamd on 3/30/2017.
 */
public class FixedValidator implements ConstraintValidator<Fixed, String> {

    private Set<String> fixedSet = new HashSet<>();

    @Override
    public void initialize(Fixed fixed) {
        Class<? extends FixedValue> c = fixed.fixClass();
        Set<String> values = null;
        try {
            Method method = c.getMethod("getFixedValues");
            Object obj=  method.invoke(c.getConstructor().newInstance());
            values = (Set<String>) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.fixedSet.addAll(values);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) return true;
        String[] vals = value.split(",");
        for (String val: vals) {
            if (!fixedSet.contains(val.trim())) {
                StringBuilder builder = new StringBuilder();
                builder.append("[");
                fixedSet.forEach(v -> builder.append(v + ","));
                builder.setLength(builder.length()-1);
                builder.append("].");
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Accepted values are comma separated " + builder.toString()).addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
