package com.interview.domain.album;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrackRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be positive")
    private Integer durationSeconds;

    private Integer trackNumber;
}
