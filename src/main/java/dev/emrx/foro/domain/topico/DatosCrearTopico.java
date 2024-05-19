package dev.emrx.foro.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosCrearTopico(
        @NotNull(message = "El id del usuario es obligatorio")
        Long usuarioId,
        @NotBlank(message = "El título es obligatorio")
        String titulo,
        @NotBlank(message = "El mensaje es obligatorio")
        String mensaje,
        @NotBlank(message = "El nombre del curso es obligatorio")
        String nombreCurso
) {
}
