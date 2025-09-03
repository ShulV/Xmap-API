package com.xmap_api.aop;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ValidFilesCountValidator implements ConstraintValidator<ValidFilesCount, List<MultipartFile>> {

    private int min;
    private int max;

    @Override
    public void initialize(ValidFilesCount constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    /**
     * Когда в форме не выбираются файлы, то в массиве MultipartFile все равно приходит файл-пустышка, к-ый нам не нужен
     */
    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext context) {
        long nonEmptyFilesCount = files.stream()
                .filter(file -> !file.isEmpty())
                .count();
        return nonEmptyFilesCount >= min && nonEmptyFilesCount <= max;
    }
}