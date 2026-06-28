package br.com.itau.api_transacao.controllers.dtos;

public record EstatisticasResponseDTO(
        Long count,
        Double sum,
        Double avg,
        Double min,
        Double max
) {
}
