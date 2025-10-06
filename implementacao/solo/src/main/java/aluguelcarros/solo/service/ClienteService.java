package aluguelcarros.solo.service;

import aluguelcarros.solo.domain.Cliente;
import aluguelcarros.solo.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repo;

    public ClienteService(ClienteRepository repo) {
        this.repo = repo;
    }

    public Cliente save(Cliente c) {
        return repo.save(c);
    }

    public List<Cliente> findAll() {
        return repo.findAll();
    }

    public Cliente findById(Long id) {
        return repo.findById(id).orElse(null);
    }

}
