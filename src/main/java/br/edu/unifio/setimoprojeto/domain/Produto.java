package br.edu.unifio.setimoprojeto.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nome"})
})
@Data
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @NotBlank(message = "O campo nome é obrigatório")
    @Size(min = 2, max = 50, message = "O tamanho do campo nome deve ser entre 2 e 50")
    private String nome;

    @NotNull(message = "O campo preço é obrigatório")
    @DecimalMin(value = "0.01", message = "O valor mínimo para o campo preço é 0,01")
    @DecimalMax(value = "1000.00", message = "O valor máximo para o campo preço é 1000,00")
    private Double preco;

    @NotNull(message = "O campo quantidade é obrigatório")
    @Min(value = 0, message = "O valor mínimo para o campo quantidade é 0")
    @Max(value = 10, message = "O valor máximo para o campo quantidade é 10")
    private Integer quantidade;

    @NotNull(message = "O campo validade é obrigatório")
    @FutureOrPresent(message = "A data informada no campo validade deve ser uma data presente ou futura")
    private LocalDate validade;

    @ManyToOne
    @NotNull(message = "O campo categoria é obrigatório")
    private Categoria categoria;
}
