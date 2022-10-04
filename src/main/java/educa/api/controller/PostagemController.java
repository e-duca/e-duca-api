package educa.api.controller;

import educa.api.model.Postagem;
import educa.api.repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/postagens")
public class PostagemController {

    @Autowired
    private PostagemRepository repository;

    @PostMapping
    public ResponseEntity<Postagem> create(@RequestBody @Valid Postagem postagem) {
        return ResponseEntity.status(201).body(repository.save(postagem));
    }

    @GetMapping
    public ResponseEntity<List<Postagem>> read() {
        List<Postagem> list = repository.findAll();
        return list.isEmpty()
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Postagem> update(
            @PathVariable int id,
            @RequestBody @Valid Postagem postagem) {
        if (repository.existsById(id)) {
            postagem.setIdPostagem(id);
            repository.save(postagem);
            return ResponseEntity.status(200).body(postagem);
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
