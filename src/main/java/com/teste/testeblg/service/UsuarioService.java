package com.teste.testeblg.service;

import com.teste.testeblg.model.Usuario;
import com.teste.testeblg.model.UsuarioLogin;
import com.teste.testeblg.repository.UsuarioRepository;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario cadastrarUsuario(Usuario usuario) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String senhaEncoder = encoder.encode(usuario.getSenha());

        usuario.setSenha(senhaEncoder);

        return usuarioRepository.save(usuario);
    }

    public Optional<UsuarioLogin> logar(Optional<UsuarioLogin> user) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Optional<Usuario> usuario = usuarioRepository.findByUsuario(user.get().getUsuario());

        if (usuario.isPresent()) {

            if (encoder.matches(user.get().getSenha(), usuario.get().getSenha())) {
                String auth = user.get().getUsuario() + ":" + user.get().getSenha();

                byte[] encodeAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));

                String authHeader = "Basic " + new String(encodeAuth);
                user.get().setNome(usuario.get().getNome());
                user.get().setToken(authHeader);

                return user;
            }
        }
        throw new Exception("Usuário ou senha inválidos");
    }
}
