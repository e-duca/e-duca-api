package educa.api.controller;

import educa.api.repository.UsuarioRepository;
import educa.api.response.UsuarioProfessorResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProfessorControllerTest {

    @Autowired
    ProfessorController controller;

    @MockBean
    UsuarioRepository repository;

    @Test
    @DisplayName("01 - Deve retornar 200 com o corpo válido e listar todos os Professores")
    void retorna200MostraEstudantes() {

        List<UsuarioProfessorResponse> list = List.of(
                mock(UsuarioProfessorResponse.class),
                mock(UsuarioProfessorResponse.class)
        );

        when(repository.getProfessores()).thenReturn(list);

        ResponseEntity<List<UsuarioProfessorResponse>> response = controller.read();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(list, response.getBody());
    }

    @Test
    @DisplayName("02 - Dev retornar 204 e sem corpo com a tabela vázia")
    void retorna204SemCorpo() {
        when(repository.getProfessores()).thenReturn(new ArrayList<>());

        ResponseEntity<List<UsuarioProfessorResponse>> response = controller.read();

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

}