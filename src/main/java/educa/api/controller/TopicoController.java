package educa.api.controller;

import educa.api.domain.Topico;
import educa.api.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @PostMapping
    public ResponseEntity<Topico> create(@RequestBody @Valid Topico topico) {
        return ResponseEntity.status(201).body(repository.save(topico));
    }

    @GetMapping
    public ResponseEntity<List<Topico>> read() {
        List<Topico> list = repository.findAll();
        return list.isEmpty()
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topico> update(
            @PathVariable int id,
            @RequestBody @Valid Topico topico) {
        if (repository.existsById(id)) {
            topico.setIdTopico(id);
            repository.save(topico);
            return ResponseEntity.status(200).body(topico);
        }
        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }
}
