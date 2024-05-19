package dev.emrx.foro.model.service;

import dev.emrx.foro.domain.topico.DatosActualizarTopico;
import dev.emrx.foro.domain.topico.DatosCrearTopico;
import dev.emrx.foro.domain.topico.TopicoResponse;
import dev.emrx.foro.domain.topico.validacion.ValidadorDeTopico;
import dev.emrx.foro.infra.error.ValidacionDeIntegridad;
import dev.emrx.foro.model.entity.Curso;
import dev.emrx.foro.model.entity.Topico;
import dev.emrx.foro.model.entity.Usuario;
import dev.emrx.foro.model.repository.CursoRepository;
import dev.emrx.foro.model.repository.TopicoRepository;
import dev.emrx.foro.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository temaRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private List<ValidadorDeTopico> validadores;

    @Transactional(readOnly = true)
    public TopicoResponse obtenerTopico(Long id) {
        return temaRepository.findById(id)
                .map(TopicoResponse::new)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<TopicoResponse> obtenerTodosLosTopicos() {
        return temaRepository.findAll().stream()
                .map(TopicoResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public TopicoResponse guardarTopico(DatosCrearTopico datos) {
        validadores.forEach(validador -> validador.validar(datos));
        Curso curso = existeCurso(datos.nombreCurso());
        Usuario usuario = existeUsuario(datos.usuarioId());

        Topico tema = Topico.builder()
                .titulo(datos.titulo())
                .mensaje(datos.mensaje())
                .curso(curso)
                .autor(usuario)
                .fecha(LocalDateTime.now())
                .status(true)
                .build();

        return new TopicoResponse(temaRepository.save(tema));
    }

    private Curso existeCurso(String nombreCurso) {
        Curso curso = cursoRepository.findByNombre(nombreCurso)
                .orElseThrow(() -> new ValidacionDeIntegridad("El curso no existe"));
        return curso;
    }

    private Usuario existeUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.getReferenceById(usuarioId);
        if(usuario == null)
            throw new ValidacionDeIntegridad("El usuario no existe");

        return usuario;
    }

    @Transactional
    public TopicoResponse actualizarTopico(Long id, DatosActualizarTopico datos) {
        validadores.forEach(
            validador -> validador.validar(new DatosCrearTopico(null, datos.titulo(), datos.mensaje(), datos.nombreCurso()))
        );

        Optional<Topico> topicoBuscado = temaRepository.findById(id);
        if(topicoBuscado.isPresent()) {
            Topico tema = topicoBuscado.get();
            if(datos.titulo() != null)
                tema.setTitulo(datos.titulo());
            if(datos.mensaje() != null)
                tema.setMensaje(datos.mensaje());
            if(datos.nombreCurso() != null) {
                Curso curso = existeCurso(datos.nombreCurso());
                tema.setCurso(curso);
            }

            return new TopicoResponse(tema);
        }

        return null;
    }

    @Transactional
    public void eliminarTopico(Long id) {
        existeUsuario(id);
        temaRepository.deleteById(id);
    }

}
