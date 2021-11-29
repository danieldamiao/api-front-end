package br.edu.unifio.setimoprojeto.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nome"})
})
@Data
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @NotBlank(message = "O campo nome é obrigatório")
    @Size(min = 3, max = 50, message = "O tamanho do campo nome deve ser entre 3 e 50")
    private String nome;
}
