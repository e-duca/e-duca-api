package educa.api.controller;

import educa.api.repository.HabilidadeRepository;
import educa.api.request.domain.Habilidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/habilidades")
public class HabilidadeController {

    @Autowired
    private HabilidadeRepository repository;

    @GetMapping
    public ResponseEntity<List<Habilidade>> read() {
        List<Habilidade> habilidades = repository.findAll();
        return habilidades.isEmpty()
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(habilidades);
    }

}
