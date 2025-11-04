package com.interview.domain.playlist;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaylistRequest {

    @NotBlank(message = "Name is required")
    private String name;

    private Boolean isPublic = false;
}
