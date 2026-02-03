package ru.spotic_api.aop;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidFilesTypeValidator.class)
public @interface ValidFilesType {
    String message() default ("Неразрешенный тип файлов");
    String[] allowedTypes() default {"image/jpeg", "image/png"};
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
