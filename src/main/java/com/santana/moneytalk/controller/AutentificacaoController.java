package com.santana.moneytalk.controller;

import com.santana.moneytalk.domain.dto.security.CadastroUsuario;
import com.santana.moneytalk.domain.dto.security.LoginReponse;
import com.santana.moneytalk.domain.dto.security.UsuarioLogin;
import com.santana.moneytalk.domain.model.Usuario;
import com.santana.moneytalk.infra.security.TokenService;
import com.santana.moneytalk.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autorizacao")
@RequiredArgsConstructor
public class AutentificacaoController {

    private final AuthenticationManager manager;
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;

    @PostMapping("/login")
    public ResponseEntity<LoginReponse> login(@RequestBody UsuarioLogin usuario) {
        try {
            System.out.println("Login: " + usuario.login());
            var authenticationToken = new UsernamePasswordAuthenticationToken(usuario.login(), usuario.senha());

            var authenticate = manager.authenticate(authenticationToken);
            System.out.println("Autenticou com sucesso!");

            String tokenJwt = tokenService.criarToken((Usuario) authenticate.getPrincipal());
            return ResponseEntity.ok(new LoginReponse(tokenJwt));

        } catch (Exception e) {
            System.out.println("ERRO NO MANAGER: " + e.getMessage());
            return ResponseEntity.status(401).body(null); // Retorne 401 para teste
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastro(@RequestBody CadastroUsuario cadastro) {

        usuarioRepository.save(new Usuario(cadastro.nome(), cadastro.login(), encoder.encode(cadastro.senha())));
        return ResponseEntity.accepted().body(cadastro.login());
    }

}
