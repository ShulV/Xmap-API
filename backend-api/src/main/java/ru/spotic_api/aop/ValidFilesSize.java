package ru.spotic_api.aop;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidFilesSizeValidator.class)
public @interface ValidFilesSize {
    String message() default ("Суммарный размер файлов слишком большой");
    long maxFileSize() default 2048;//2MB
    long maxTotalFileListSize() default 12288;//12MB
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
