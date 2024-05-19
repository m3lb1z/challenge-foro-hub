package dev.emrx.foro.domain.topico.validacion;

import dev.emrx.foro.domain.topico.DatosCrearTopico;

public interface ValidadorDeTopico {

    void validar(DatosCrearTopico datos);
}
