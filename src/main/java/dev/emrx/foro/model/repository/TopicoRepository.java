package dev.emrx.foro.model.repository;

import dev.emrx.foro.model.entity.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    boolean existsByTitulo(String titulo);
    boolean existsByMensaje(String mensaje);

}
