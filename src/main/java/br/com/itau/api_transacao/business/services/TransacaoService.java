package br.com.itau.api_transacao.business.services;

import br.com.itau.api_transacao.controllers.dtos.TransacaoRequestDTO;
import br.com.itau.api_transacao.infraestructure.exceptions.UnprocessableEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransacaoService {

    private final List<TransacaoRequestDTO> listaDeTransacoes = new ArrayList<>();

    public void adicionarTransacoes(TransacaoRequestDTO request) {

        log.info("Iniciado o processamento de gravar transações " + request.toString());

        if (request.dataHora().isAfter(OffsetDateTime.now())) {
            log.error("Data e hora maiores que a data e hora atuais.");
            throw new UnprocessableEntity("Data e hora maiores que a data e hora atuais.");
        }

        if (request.valor() < 0) {
            log.error("Valor não pode ser menor que zero");
            throw new UnprocessableEntity("Valor não pode ser menor que zero");
        }

        listaDeTransacoes.add(request);
        log.info("Transação adicionadas com sucesso.");
    }

    public void limparTransacoes() {
        log.info("Iniciado o processamento para deletar transações.");
        listaDeTransacoes.clear();
        log.info("Transações deletadas com sucesso.");
    }

    public List<TransacaoRequestDTO> buscarTransacoes(Integer intervaloDeBusca) {
        log.info("Iniciada as buscas de transações por tempo "  + intervaloDeBusca + " segundos");
        OffsetDateTime dataHoraIntervalo = OffsetDateTime.now().minusSeconds(intervaloDeBusca);

        log.info("Retorno de transações com sucesso.");
        return listaDeTransacoes
                .stream()
                .filter(transacoes -> transacoes.dataHora().isAfter(dataHoraIntervalo)).toList();

    }


}
