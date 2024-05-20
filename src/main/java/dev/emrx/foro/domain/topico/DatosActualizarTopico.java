package dev.emrx.foro.domain.topico;

import jakarta.validation.constraints.NotBlank;

public record DatosActualizarTopico(
        String titulo,
        String mensaje,
        String nombreCurso
) {
}
