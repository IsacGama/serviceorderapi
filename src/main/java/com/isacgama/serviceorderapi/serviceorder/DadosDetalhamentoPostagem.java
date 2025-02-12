package com.isacgama.serviceorderapi.serviceorder;

import com.isacgama.serviceorderapi.cliente.Cliente;
import com.isacgama.serviceorderapi.cliente.DadosListagemCliente;

import java.time.LocalDateTime;

public record DadosDetalhamentoPostagem(
        Long id,
        DestinacaoOrdemDeServico destinacaoOrdemDeServico,
        String detalhamento,
        LocalDateTime dataCriacao,
        LocalDateTime ultimaAtualizacao,
        String localDoEvento,
        String horarioDoEvento,
        Status status,
        Prioridade prioridade,
        DadosListagemCliente cliente
) {
    public DadosDetalhamentoPostagem(ServiceOrder postagem) {
        this(
                postagem.getId(),
                postagem.getDestinacaoOrdemDeServico(),
                postagem.getDetalhamento(),
                postagem.getDataCriacao(),
                postagem.getUltimaAtualizacao(),
                postagem.getLocalDoEvento(),
                postagem.getHorarioDoEvento(),
                postagem.getStatus(),
                postagem.getPrioridade(),
                new DadosListagemCliente(postagem.getCliente().getNome(), postagem.getCliente().getUsername())
        );
    }
}
