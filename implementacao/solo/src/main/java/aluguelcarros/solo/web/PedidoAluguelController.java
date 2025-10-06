package aluguelcarros.solo.web;

import aluguelcarros.solo.domain.Cliente;
import aluguelcarros.solo.domain.PedidoAluguel;
import aluguelcarros.solo.domain.Veiculo;
import aluguelcarros.solo.repository.ClienteRepository;
import aluguelcarros.solo.repository.PedidoAluguelRepository;
import aluguelcarros.solo.repository.VeiculoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoAluguelController {

    private final PedidoAluguelRepository repo;
    private final ClienteRepository clienteRepo;
    private final VeiculoRepository veiculoRepo;

    public PedidoAluguelController(PedidoAluguelRepository repo, ClienteRepository clienteRepo, VeiculoRepository veiculoRepo) {
        this.repo = repo;
        this.clienteRepo = clienteRepo;
        this.veiculoRepo = veiculoRepo;
    }

    @GetMapping
    public List<PedidoAluguel> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoAluguel> get(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Create a pedido linking existing cliente and veiculo by ids
    @PostMapping
    public ResponseEntity<PedidoAluguel> create(@RequestParam Long clienteId, @RequestParam Long veiculoId) {
        Cliente c = clienteRepo.findById(clienteId).orElse(null);
        Veiculo v = veiculoRepo.findById(veiculoId).orElse(null);
        if (c == null || v == null) {
            return ResponseEntity.badRequest().build();
        }
        PedidoAluguel p = new PedidoAluguel();
        p.setCliente(c);
        p.setVeiculo(v);
        PedidoAluguel saved = repo.save(p);
        return ResponseEntity.created(URI.create("/api/pedidos/" + saved.getId())).body(saved);
    }

}
