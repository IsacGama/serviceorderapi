package com.isacgama.serviceorderapi.serviceorder;

import com.isacgama.serviceorderapi.cliente.Cliente;
import java.time.LocalDateTime;

public record DadosCadastroPostagem(
    Long id,
    DestinacaoOrdemDeServico destinacaoOrdemDeServico,
    String detalhamento,
    LocalDateTime dataCriacao,
    LocalDateTime ultimaAtualizacao,
    String localDoEvento,
    String horarioDoEvento,
    Status status,
    Prioridade prioridade,
    Cliente cliente,
    Boolean ativo
) {
}
