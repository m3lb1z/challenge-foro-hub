package dev.emrx.foro.domain.topico.validacion;

import dev.emrx.foro.domain.topico.DatosCrearTopico;
import dev.emrx.foro.model.repository.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TituloUnico implements ValidadorDeTopico {

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validar(DatosCrearTopico datos) {
        if(datos.titulo() != null) {
            boolean esUnico = topicoRepository.existsByTitulo(datos.titulo());
            if(esUnico) {
                throw new ValidationException("El título del tópico ya existe");
            }
        }
    }
}
