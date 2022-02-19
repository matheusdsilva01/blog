package com.teste.testeblg.controller;

import com.teste.testeblg.model.Usuario;
import com.teste.testeblg.model.UsuarioLogin;
import com.teste.testeblg.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrarUsuario(usuario));
    }

    @PostMapping("/logar")
    public ResponseEntity<UsuarioLogin> logar(@RequestBody Optional<UsuarioLogin> usuario) throws Exception {
        return usuarioService.logar(usuario).map(usuarioLogin -> ResponseEntity.ok(usuarioLogin))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

}
