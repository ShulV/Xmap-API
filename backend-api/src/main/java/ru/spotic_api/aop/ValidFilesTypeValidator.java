package ru.spotic_api.aop;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class ValidFilesTypeValidator implements ConstraintValidator<ValidFilesType, List<MultipartFile>> {

    private String[] allowedTypes;

    @Override
    public void initialize(ValidFilesType constraintAnnotation) {
        allowedTypes = constraintAnnotation.allowedTypes();
    }

    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext context) {
        if (files == null || files.isEmpty()) return true;

        return files.stream().allMatch(file -> {
            String contentType = file.getContentType();
            return contentType != null && Arrays.asList(allowedTypes).contains(contentType);
        });
    }

}
