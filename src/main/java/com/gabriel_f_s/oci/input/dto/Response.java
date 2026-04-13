package com.gabriel_f_s.oci.input.dto;

import java.time.LocalDateTime;

public record Response(
        Boolean success,
        String message,
        LocalDateTime timestamp
) {
}
