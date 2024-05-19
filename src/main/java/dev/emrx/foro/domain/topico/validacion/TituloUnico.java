package dev.emrx.foro.domain.topico.validacion;

import dev.emrx.foro.domain.topico.DatosCrearTopico;
import dev.emrx.foro.model.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TituloUnico implements ValidadorDeTopico {

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validar(DatosCrearTopico datos) {
        boolean esUnico = topicoRepository.existsByTitulo(datos.titulo());
        if(esUnico) {
            throw new IllegalArgumentException("El título del tópico ya existe");
        }
    }
}
