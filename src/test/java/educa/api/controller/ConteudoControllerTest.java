package educa.api.controller;

import educa.api.repository.ConteudoRepository;
import educa.api.request.domain.Conteudo;
import educa.api.response.UsuarioEstudanteResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class ConteudoControllerTest {

    @Autowired
    ConteudoController controller;

    @MockBean
    ConteudoRepository repository;

    @Test
    @DisplayName("01 - Deve retornar 200 com o corpo válido e listar todos os Conteudos")
    void retorna200MostraConteudos() {

        List<Conteudo> list = List.of(
                mock(Conteudo.class),
                mock(Conteudo.class)
        );

        when(repository.findAll()).thenReturn(list);

        ResponseEntity<List<Conteudo>> response = controller.getOrderConteudos();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(list, response.getBody());
    }

    @Test
    @DisplayName("02 - Dev retornar 204 e sem corpo com a tabela vázia")
    void retorna204SemCorpo() {
        when(repository.findAll()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Conteudo>> response = controller.getOrderConteudos();

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
}