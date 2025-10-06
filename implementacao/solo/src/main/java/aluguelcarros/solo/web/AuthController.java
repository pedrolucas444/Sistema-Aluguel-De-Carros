package aluguelcarros.solo.web;

import aluguelcarros.solo.domain.Cliente;
import aluguelcarros.solo.repository.ClienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

record AuthRequest(String cpf) {}

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final ClienteRepository clienteRepo;

    public AuthController(ClienteRepository clienteRepo) {
        this.clienteRepo = clienteRepo;
    }

    @PostMapping("/register")
    public ResponseEntity<Cliente> register(@RequestBody Cliente c) {
        Cliente saved = clienteRepo.save(c);
        return ResponseEntity.created(URI.create("/api/clientes/" + saved.getId())).body(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<Cliente> login(@RequestBody AuthRequest req) {
        return clienteRepo.findByCpf(req.cpf()).map(ResponseEntity::ok).orElse(ResponseEntity.status(401).build());
    }

}
