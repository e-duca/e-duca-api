package educa.api.controller;

import educa.api.domain.Conteudo;
import educa.api.repository.ConteudoRepository;
import educa.api.utils.ListObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/conteudos")
public class ConteudoController {

    @Autowired
    private ConteudoRepository repository;

    @PostMapping
    public ResponseEntity<Conteudo> create(@RequestBody @Valid Conteudo postagem) {
        return ResponseEntity.status(201).body(repository.save(postagem));
    }

    @GetMapping
    public ResponseEntity<Page<Conteudo>> read(
            @RequestParam(required = false) String titulo,
            @PageableDefault(sort = "idConteudo", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable paginacao
    ) {
        if (titulo == null) {
            Page<Conteudo> conteudos = repository.findAll(paginacao);
            return conteudos.isEmpty()
                    ? ResponseEntity.status(204).build()
                    : ResponseEntity.status(200).body(conteudos);
        } else {
            Page<Conteudo> conteudos = repository.findByTitulo(titulo, paginacao);
            return conteudos.isEmpty()
                    ? ResponseEntity.status(204).build()
                    : ResponseEntity.status(200).body(conteudos);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Conteudo> update(
            @PathVariable int id,
            @RequestBody @Valid Conteudo postagem) {
        if (repository.existsById(id)) {
            postagem.setIdConteudo(id);
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

    @GetMapping("/order")
    public ResponseEntity<List<Conteudo>> getOrderConteudos() {
        List<Conteudo> list = repository.findAll();
        ListObj<Conteudo> listObj = new ListObj<>(list.size());

        for (Conteudo conteudo : list) {
            listObj.add(conteudo);
        }

        for (int i = 0; i < listObj.getTamanho(); i++) {
            for (int j = i+1; j < listObj.getTamanho(); j++) {
                if (listObj.getElemento(j).getTempoEstimado() < listObj.getElemento(i).getTempoEstimado()) {
                    Conteudo aux = listObj.getElemento(i);
                    listObj.adicionaIndice(i, listObj.getElemento(j));
                    listObj.adicionaIndice(j, aux);
                }
            }
        }

        return listObj.getTamanho() == 0
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(listObj.all());

    }

}
