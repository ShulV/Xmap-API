package ru.spotic_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewUserDTO(
        @NotBlank(message = "Имя пользователя не может быть пустым")
        @Size(min = 3, max = 20, message = "Должно быть от 3 до 20 символов")
        String username,
        @NotBlank(message = "Пароль обязателен.")
        @Size(min = 3, message = "Минимальная длина пароля: 3 символа")
        String rawPassword
) {
}
