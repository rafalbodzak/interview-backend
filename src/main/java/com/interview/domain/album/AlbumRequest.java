package com.interview.domain.album;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Release year is required")
    private Integer releaseYear;
}
