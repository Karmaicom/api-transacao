package br.com.itau.api_transacao.business.services;

import br.com.itau.api_transacao.controllers.dtos.EstatisticasResponseDTO;
import br.com.itau.api_transacao.controllers.dtos.TransacaoRequestDTO;
import br.com.itau.api_transacao.infraestructure.exceptions.UnprocessableEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

    @InjectMocks
    TransacaoService transacaoService;

    TransacaoRequestDTO transacao;
    EstatisticasResponseDTO estatisticas;

    @BeforeEach
    public void setUp() {
        transacao = new TransacaoRequestDTO(20.0, OffsetDateTime.now());
        estatisticas = new EstatisticasResponseDTO(1L, 20., 20., 20., 20.);
    }

    @Test
    void deveAdicionarTransacaoComSucesso() {
        transacaoService.adicionarTransacoes(transacao);
        List<TransacaoRequestDTO> transacoes = transacaoService.buscarTransacoes(5000);
        assertTrue(transacoes.contains(transacao));
    }

    @Test
    void deveLancarExcessaoQuandoValorNegativo() {
        UnprocessableEntity exception = assertThrows(UnprocessableEntity.class, () -> {
            transacaoService.adicionarTransacoes(new TransacaoRequestDTO(-20., OffsetDateTime.now()));
        });

        assertEquals("Valor não pode ser menor que zero", exception.getMessage());
    }

    @Test
    void deveLancarExcessaoQuandoDataOUHoraMaiorQueAtual() {
        UnprocessableEntity exception = assertThrows(UnprocessableEntity.class, () -> {
            transacaoService.adicionarTransacoes(new TransacaoRequestDTO(200., OffsetDateTime.now().plusDays(1)));
        });

        assertEquals("Data e hora maiores que a data e hora atuais.", exception.getMessage());
    }

    @Test
    void deveLimparTransacoesComSucesso() {
        transacaoService.limparTransacoes();
        List<TransacaoRequestDTO> transacoes = transacaoService.buscarTransacoes(5000);
        assertTrue(transacoes.isEmpty());
    }

    @Test
    void deveBuscarTransacoesDentroDoIntervalo() {
        TransacaoRequestDTO dto = new TransacaoRequestDTO(150.0, OffsetDateTime.now().minusHours(1));
        transacaoService.adicionarTransacoes(transacao);

        List<TransacaoRequestDTO> transacoes = transacaoService.buscarTransacoes(60);
        assertTrue(transacoes.contains(transacao));
        assertFalse(transacoes.contains(dto));
    }
}
