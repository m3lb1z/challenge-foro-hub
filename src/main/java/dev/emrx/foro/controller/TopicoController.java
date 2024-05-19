package dev.emrx.foro.controller;

import dev.emrx.foro.domain.topico.DatosActualizarTopico;
import dev.emrx.foro.domain.topico.DatosCrearTopico;
import dev.emrx.foro.domain.topico.TopicoResponse;
import dev.emrx.foro.model.service.TopicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponse> obtenerTopico(@PathVariable Long id) {
        return ResponseEntity.ok(topicoService.obtenerTopico(id));
    }

    @GetMapping
    public ResponseEntity<List<TopicoResponse>> obtenerTodosLosTopicos() {
        return ResponseEntity.ok(topicoService.obtenerTodosLosTopicos());
    }

    @PostMapping
    public ResponseEntity<TopicoResponse> guardarTopico(@RequestBody DatosCrearTopico datos, UriComponentsBuilder uriBuilder) {
        var response = topicoService.guardarTopico(datos);
        URI url = uriBuilder.path("/topicos/{id}").buildAndExpand(response.id()).toUri();

        return ResponseEntity.created(url).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoResponse> actualizarTopico(@PathVariable Long id, @RequestBody DatosActualizarTopico datos) {
        return ResponseEntity.ok(topicoService.actualizarTopico(id, datos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        topicoService.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }

}
