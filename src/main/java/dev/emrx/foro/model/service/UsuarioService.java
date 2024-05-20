package dev.emrx.foro.model.service;

import dev.emrx.foro.domain.usuario.DatosUsuario;
import dev.emrx.foro.model.entity.Usuario;
import dev.emrx.foro.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<DatosUsuario> listarUsuarios() {
        List<DatosUsuario> datosUsuarios = usuarioRepository.findAll().stream()
                .map(DatosUsuario::new)
                .collect(Collectors.toList());

        return datosUsuarios;
    }

    public DatosUsuario obtenerUsuario(Long id) {
        return usuarioRepository.findById(id).map(DatosUsuario::new).orElse(null);
    }

}
