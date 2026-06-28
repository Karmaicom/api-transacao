package br.com.itau.api_transacao.business.services;

import br.com.itau.api_transacao.controllers.dtos.EstatisticasResponseDTO;
import br.com.itau.api_transacao.controllers.dtos.TransacaoRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstatisticasService {

    private final TransacaoService transacaoService;

    public EstatisticasResponseDTO getEstatisticas(Integer intervaloDeBusca) {

        log.info("Iniciada a busca da estatistica da transações pelo período de tempo " +  intervaloDeBusca + " segundos");

        List<TransacaoRequestDTO> transacoes = transacaoService.buscarTransacoes(intervaloDeBusca);

        DoubleSummaryStatistics summaryStatistics = transacoes.stream()
                                                        .mapToDouble(TransacaoRequestDTO::valor)
                                                        .summaryStatistics();

        if (transacoes.isEmpty() || transacoes.equals(null)) {
            log.info("Não existem transações para processar.");
            return new EstatisticasResponseDTO(
                    0L,
                    0.,
                    0.,
                    0.,
                    0.
            );
        }

        log.info("Estatísticas retornadas com sucesso.");
        return new EstatisticasResponseDTO(
            summaryStatistics.getCount(),
            summaryStatistics.getSum(),
            summaryStatistics.getAverage(),
            summaryStatistics.getMin(),
            summaryStatistics.getMax()
        );



    }
}
