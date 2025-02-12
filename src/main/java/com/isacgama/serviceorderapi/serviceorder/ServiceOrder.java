package com.isacgama.serviceorderapi.serviceorder;

import com.isacgama.serviceorderapi.cliente.Cliente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "service_order")
@Entity(name = "Service_Order")
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ServiceOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private DestinacaoOrdemDeServico destinacaoOrdemDeServico;
    private String detalhamento;
    private LocalDateTime dataCriacao;
    private LocalDateTime ultimaAtualizacao;
    private String localDoEvento;
    private String horarioDoEvento;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    private Boolean ativo;

    public ServiceOrder(DadosCadastroPostagem dados, Cliente autor) {
        this.id = dados.id(); // ou gere um ID automaticamente, se necessário
        this.destinacaoOrdemDeServico = dados.destinacaoOrdemDeServico();
        this.detalhamento = dados.detalhamento();
        this.dataCriacao = dados.dataCriacao() != null ? dados.dataCriacao() : LocalDateTime.now();
        this.ultimaAtualizacao = dados.ultimaAtualizacao() != null ? dados.ultimaAtualizacao() : LocalDateTime.now();
        this.localDoEvento = dados.localDoEvento();
        this.horarioDoEvento = dados.horarioDoEvento();
        this.status = dados.status() != null ? dados.status() : Status.PENDENTE;
        this.prioridade = dados.prioridade() != null ? dados.prioridade() : Prioridade.BAIXA;
        this.cliente = autor;
        this.ativo = dados.ativo() != null ? dados.ativo() : true;
    }

    public void atualizarInformacoes(DadosAtualizacaoPostagem dados) {
        if (dados.detalhamento() != null) {
            this.detalhamento = dados.detalhamento();
        }
        if (dados.horarioDoEvento() != null) {
            this.horarioDoEvento = dados.horarioDoEvento();
        }
        if (dados.localDoEvento() != null) {
            this.localDoEvento = dados.localDoEvento();
        }
        if (dados.destinacaoOrdemDeServico() != null) {
            this.destinacaoOrdemDeServico = dados.destinacaoOrdemDeServico();
        }
    }

    public Long getId() {
        return id;
    }

    public DestinacaoOrdemDeServico getDestinacaoOrdemDeServico() {
        return destinacaoOrdemDeServico;
    }

    public String getDetalhamento() {
        return detalhamento;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public String getLocalDoEvento() {
        return localDoEvento;
    }

    public String getHorarioDoEvento() {
        return horarioDoEvento;
    }

    public Status getStatus() {
        return status;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public ServiceOrder() {
    }
}
