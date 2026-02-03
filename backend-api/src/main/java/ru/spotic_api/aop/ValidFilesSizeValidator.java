package ru.spotic_api.aop;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ValidFilesSizeValidator implements ConstraintValidator<ValidFilesSize, List<MultipartFile>> {
    private long maxFileSize;
    private long maxTotalFileListSize;

    @Override
    public void initialize(ValidFilesSize constraintAnnotation) {
        this.maxFileSize = constraintAnnotation.maxFileSize();
        this.maxTotalFileListSize = constraintAnnotation.maxTotalFileListSize();
    }

    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext context) {
        long totalSize = 0;
        for (MultipartFile file : files) {
            if (file.getSize() > maxFileSize) {
                return false;
            }
            totalSize += file.getSize();
        }
        return totalSize <= maxTotalFileListSize;
    }
}
