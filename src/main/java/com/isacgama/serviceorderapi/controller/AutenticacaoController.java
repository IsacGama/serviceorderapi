package com.isacgama.serviceorderapi.controller;

import com.isacgama.serviceorderapi.cliente.*;
import com.isacgama.serviceorderapi.infra.security.DadosTokenJWT;
import com.isacgama.serviceorderapi.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cliente")
public class AutenticacaoController {
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AutenticacaoService autenticacaoService;

    @PostMapping("/login")
    public ResponseEntity<DadosTokenJWT> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(authenticationToken);
        var cliente = (Cliente) authentication.getPrincipal();
        var tokenJWT = tokenService.gerarToken(cliente);
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

    @PostMapping("/cadastro")
    public ResponseEntity<DadosListagemCliente> cadastrarCliente(@RequestBody @Valid DadosCadastroCliente dados) {
        Cliente cliente = new Cliente(dados.nome(), dados.login(), dados.senha(), dados.role());
        Cliente novocliente = autenticacaoService.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new DadosListagemCliente(novocliente.getNome(), novocliente.getUsername()));
    }
}
