package br.com.itau.api_transacao.controllers.dtos;

import java.time.OffsetDateTime;

public record TransacaoRequestDTO(
        Double valor,
        OffsetDateTime dataHora
) {
}
