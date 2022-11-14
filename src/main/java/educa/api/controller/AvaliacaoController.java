package educa.api.controller;

import educa.api.controller.dto.AvaliacaoDto;
import educa.api.controller.dto.RespostaDto;
import educa.api.domain.Avaliacao;
import educa.api.domain.Resposta;
import educa.api.domain.Usuario;
import educa.api.repository.AvaliacaoRepository;
import educa.api.repository.ConteudoRepository;
import educa.api.repository.RespostaRepository;
import educa.api.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/conteudos/avaliacoes")
public class AvaliacaoController {


    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private ConteudoRepository conteudoRepository;

    @PostMapping
    public ResponseEntity<Avaliacao> create(@RequestBody @Valid AvaliacaoDto newAvaliacao, @AuthenticationPrincipal Usuario usuario) {
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
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.status(400).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id, @AuthenticationPrincipal Usuario usuario) {
        if (avaliacaoRepository.existsById(id)) {
            Avaliacao avaliacao = avaliacaoRepository.findById(id).get();
            if (avaliacao.getUsuario().getIdUsuario() == usuario.getIdUsuario()) {
                avaliacaoRepository.deleteById(id);
                return ResponseEntity.status(200).build();
            }
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.status(404).build();
    }
}
