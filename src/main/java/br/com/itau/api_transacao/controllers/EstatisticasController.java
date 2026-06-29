package br.com.itau.api_transacao.controllers;

import br.com.itau.api_transacao.business.services.EstatisticasService;
import br.com.itau.api_transacao.controllers.dtos.EstatisticasResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatistica")
@RequiredArgsConstructor
@Slf4j
public class EstatisticasController {

    private final EstatisticasService estatisticasService;

    @Operation(description = "Endpoint responsável por buscar estatísticas das transações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca de estatísticas efetuadas com sucesso."),
            @ApiResponse(responseCode = "400", description = "Falha na requisição das estatísticas."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    @GetMapping
    public ResponseEntity<EstatisticasResponseDTO> buscarEstatisticas(
            @RequestParam(value = "intervalorDeBusca", required = false, defaultValue = "60") Integer intervalorDeBusca) {

        log.info("Busca de estatisticas");
        return ResponseEntity.status(HttpStatus.OK).body(estatisticasService.calcularEstatisticasTransacoes(intervalorDeBusca));

    }

}
