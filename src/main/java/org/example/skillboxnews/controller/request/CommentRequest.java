package org.example.skillboxnews.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentRequest(@Size(min = 1, max = 511) @NotBlank String text) {
}