package educa.api.controller;

import educa.api.request.AvaliacaoRequest;
import educa.api.request.domain.Avaliacao;
import educa.api.request.domain.Usuario;
import educa.api.repository.AvaliacaoRepository;
import educa.api.repository.ConteudoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/conteudos/avaliacoes")
public class AvaliacaoController {


    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private ConteudoRepository conteudoRepository;

    @PostMapping
    public ResponseEntity<Avaliacao> create(@RequestBody @Valid AvaliacaoRequest newAvaliacao, @AuthenticationPrincipal Usuario usuario) {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setConteudo(conteudoRepository.findById(newAvaliacao.getIdConteudo()).get());
        avaliacao.setUsuario(usuario);
        avaliacao.setAvaliacao(newAvaliacao.getAvaliacao());
        avaliacaoRepository.save(avaliacao);
        return ResponseEntity.status(201).body(avaliacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Avaliacao> update(@PathVariable int id, @RequestBody @Valid Avaliacao newAvaliacao, @AuthenticationPrincipal Usuario usuario) {
        if (avaliacaoRepository.existsById(id)) {
            Avaliacao avaliacao = avaliacaoRepository.findById(id).get();
            if (avaliacao.getUsuario().getIdUsuario() == usuario.getIdUsuario()) {
                avaliacao.setIdAvaliacao(id);
                avaliacao.setAvaliacao(newAvaliacao.getAvaliacao());
                avaliacao.setUsuario(usuario);
                avaliacaoRepository.save(avaliacao);
                return ResponseEntity.status(200).body(avaliacao);
            }
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Usuário não autorizado para atualização, identificador não compatível"
            );
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Identificador não encontrado"
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id, @AuthenticationPrincipal Usuario usuario) {
        if (avaliacaoRepository.existsById(id)) {
            Avaliacao avaliacao = avaliacaoRepository.findById(id).get();
            if (avaliacao.getUsuario().getIdUsuario() == usuario.getIdUsuario()) {
                avaliacaoRepository.deleteById(id);
                return ResponseEntity.status(200).build();
            }
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Usuário não autorizado para atualização, identificador não compatível"
            );
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Identificador não encontrado"
        );
    }
}
