package aluguelcarros.solo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String rg;
    private String cpf;
    private String endereco;
    private String profissao;

    // ... simple string list for empregadores; in a full model this would be separate entity
    @ElementCollection
    private List<String> empregadores;

}
