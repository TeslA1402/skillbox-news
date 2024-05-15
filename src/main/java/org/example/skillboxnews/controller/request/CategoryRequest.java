package org.example.skillboxnews.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(@Size(min = 1, max = 255) @NotBlank String name) {
}