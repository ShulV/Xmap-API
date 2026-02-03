package ru.spotic_api.aop;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Проблема, из-за которой была создана аннотация, заключается в том, что из формы отправляется файл-пустышка
 * даже если файлы не добавлялись в файловый input
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidFilesCountValidator.class)
public @interface ValidFilesCount {
    String message() default ("Неверное количество файлов");
    int min() default 1;
    int max() default 5;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}