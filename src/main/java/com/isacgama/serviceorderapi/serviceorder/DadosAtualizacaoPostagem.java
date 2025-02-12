package com.isacgama.serviceorderapi.serviceorder;

public record DadosAtualizacaoPostagem(
        DestinacaoOrdemDeServico destinacaoOrdemDeServico,
        String detalhamento,
        String localDoEvento,
        String horarioDoEvento
) {
}
