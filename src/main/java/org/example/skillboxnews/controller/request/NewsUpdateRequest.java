package org.example.skillboxnews.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewsUpdateRequest(@NotBlank String text, @NotBlank @Size(min = 1, max = 255) String title,
                                @NotNull Long categoryId) {
}