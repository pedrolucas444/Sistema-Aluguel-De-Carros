package aluguelcarros.solo.web;

import aluguelcarros.solo.domain.Veiculo;
import aluguelcarros.solo.repository.VeiculoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController {

    private final VeiculoRepository repo;

    public VeiculoController(VeiculoRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Veiculo> list() {
        return repo.findAll();
    }

    @PostMapping
    public ResponseEntity<Veiculo> create(@RequestBody Veiculo v) {
        Veiculo saved = repo.save(v);
        return ResponseEntity.created(URI.create("/api/veiculos/" + saved.getId())).body(saved);
    }

}
