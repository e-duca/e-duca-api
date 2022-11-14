package educa.api.controller;

import educa.api.request.RespostaRequest;
import educa.api.request.domain.Resposta;
import educa.api.request.domain.Usuario;
import educa.api.repository.RespostaRepository;
import educa.api.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/topicos/respostas")
public class RespostaController {

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    public ResponseEntity<Resposta> create(@RequestBody @Valid RespostaRequest newResposta, @AuthenticationPrincipal Usuario usuario) {
        Resposta resposta = new Resposta();
        resposta.setTopico(topicoRepository.findById(newResposta.getIdTopico()).get());
        resposta.setUsuario(usuario);
        resposta.setResposta(newResposta.getResposta());
        respostaRepository.save(resposta);
        return ResponseEntity.status(201).body(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resposta> update(@PathVariable int id, @RequestBody @Valid RespostaRequest newResposta, @AuthenticationPrincipal Usuario usuario) {
        if (respostaRepository.existsById(id)) {
            Resposta resposta = respostaRepository.findById(id).get();
            if (resposta.getUsuario().getIdUsuario() == usuario.getIdUsuario()) {
                resposta.setIdResposta(id);
                resposta.setResposta(newResposta.getResposta());
                resposta.setUsuario(usuario);
                respostaRepository.save(resposta);
                return ResponseEntity.status(200).body(resposta);
            }
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.status(400).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id, @AuthenticationPrincipal Usuario usuario) {
        if (respostaRepository.existsById(id)) {
            Resposta resposta = respostaRepository.findById(id).get();
            if (resposta.getUsuario().getIdUsuario() == usuario.getIdUsuario()) {
                respostaRepository.deleteById(id);
                return ResponseEntity.status(200).build();
            }
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.status(404).build();
    }
}
