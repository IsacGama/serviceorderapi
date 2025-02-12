package com.isacgama.serviceorderapi.serviceorder;

import com.isacgama.serviceorderapi.cliente.Cliente;
import com.isacgama.serviceorderapi.cliente.DadosListagemCliente;

import java.time.LocalDateTime;

public record DadosListagemPostagem(
        Long id,
        DestinacaoOrdemDeServico destinacaoOrdemDeServico,
        String detalhamento,
        LocalDateTime dataCriacao,
        LocalDateTime ultimaAtualizacao,
        String localDoEvento,
        String horarioDoEvento,
        Status status,
        Prioridade prioridade,
        DadosListagemCliente cliente,
        Boolean ativo
) {
    public DadosListagemPostagem(ServiceOrder serviceOrder) {
        this(
                serviceOrder.getId(),
                serviceOrder.getDestinacaoOrdemDeServico(),
                serviceOrder.getDetalhamento(),
                serviceOrder.getDataCriacao(),
                serviceOrder.getUltimaAtualizacao(),
                serviceOrder.getLocalDoEvento(),
                serviceOrder.getHorarioDoEvento(),
                serviceOrder.getStatus(),
                serviceOrder.getPrioridade(),
                new DadosListagemCliente(serviceOrder.getCliente().getNome(), serviceOrder.getCliente().getUsername()),
                serviceOrder.getAtivo()
        );
    }
}
