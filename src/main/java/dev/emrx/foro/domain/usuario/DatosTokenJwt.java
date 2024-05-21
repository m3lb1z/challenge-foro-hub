package dev.emrx.foro.domain.usuario;

public record DatosTokenJwt(
        String jwtToken,
        String expiracion) {
}
