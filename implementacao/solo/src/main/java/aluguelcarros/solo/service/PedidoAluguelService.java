package aluguelcarros.solo.service;

import aluguelcarros.solo.domain.PedidoAluguel;
import aluguelcarros.solo.repository.PedidoAluguelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoAluguelService {

    private final PedidoAluguelRepository repo;

    public PedidoAluguelService(PedidoAluguelRepository repo) {
        this.repo = repo;
    }

    public PedidoAluguel save(PedidoAluguel p) {
        return repo.save(p);
    }

    public List<PedidoAluguel> findAll() {
        return repo.findAll();
    }

    public PedidoAluguel findById(Long id) {
        return repo.findById(id).orElse(null);
    }

}
