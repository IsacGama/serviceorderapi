package com.isacgama.serviceorderapi.controller;

import com.isacgama.serviceorderapi.cliente.Cliente;
import com.isacgama.serviceorderapi.serviceorder.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("order")
public class ServiceOrderController {

    @Autowired
    private OSRepository postagemRepository;

    @PostMapping
    @Transactional
    public ResponseEntity postar(@RequestBody @Valid DadosCadastroPostagem dados, UriComponentsBuilder uriBuilder) {
        var usuarioAutenticado = (Cliente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var postagem = new ServiceOrder(dados, usuarioAutenticado);
        postagemRepository.save(postagem);
        var uri = uriBuilder.path("/order/{id}").buildAndExpand(postagem.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPostagem(postagem));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPostagem>> listar(@PageableDefault(size = 10) Pageable paginacao) {
        var usuarioLogado = (Cliente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean isAdmin = authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ROLE_ADMIN"));

        Page<ServiceOrder> page;

        if (isAdmin) {
            page = postagemRepository.findAllByAtivoTrue(paginacao);
        } else {
            page = postagemRepository.findByClienteAndAtivoTrue(usuarioLogado, paginacao);
        }

        return ResponseEntity.ok(page.map(DadosListagemPostagem::new));
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity detalhar(@PathVariable Long id) {
        var usuarioLogado = (Cliente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean isAdmin = authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ROLE_ADMIN"));

        if (postagemRepository.existsById(id)) {
            var postagem = postagemRepository.getReferenceById(id);

            // Verifica se o usuário tem permissão para visualizar
            if (!isAdmin && !postagem.getCliente().equals(usuarioLogado)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Você não tem permissão para visualizar esta ordem de serviço.");
            }

            return ResponseEntity.ok(new DadosDetalhamentoPostagem(postagem));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizacaoPostagem dados) {
        var usuarioLogado = (Cliente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean isAdmin = authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ROLE_ADMIN"));

        var postagem = postagemRepository.findById(id)
                .orElse(null);

        if (postagem == null) {
            return ResponseEntity.notFound().build();
        }

        if (!isAdmin && !postagem.getCliente().equals(usuarioLogado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Você não tem permissão para editar esta postagem.");
        }

        postagem.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoPostagem(postagem));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var usuarioLogado = (Cliente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean isAdmin = authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ROLE_ADMIN"));

        var postagem = postagemRepository.findById(id)
                .orElse(null);

        if (postagem == null) {
            return ResponseEntity.notFound().build();
        }

        if (!isAdmin && !postagem.getCliente().equals(usuarioLogado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Você não tem permissão para excluir esta postagem.");
        }

        postagemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}