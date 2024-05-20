package dev.emrx.foro.domain.topico.validacion;

import dev.emrx.foro.domain.topico.DatosCrearTopico;
import dev.emrx.foro.model.repository.CursoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CursoExiste implements ValidadorDeTopico {

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public void validar(DatosCrearTopico datos) {
        if(datos.nombreCurso() != null) {
            if(!datos.nombreCurso().isEmpty()) {
                if(!cursoRepository.existsByNombre(datos.nombreCurso())) {
                    throw new ValidationException("El curso no existe");
                }
            } else {
                throw new ValidationException("El nombre del curso no puede estar vacío");
            }
        }
    }
}
