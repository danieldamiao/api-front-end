package br.edu.unifio.setimoprojeto.resource;

import br.edu.unifio.setimoprojeto.domain.Produto;
import br.edu.unifio.setimoprojeto.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {
    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    public List<Produto> listar() {
        List<Produto> produtos = produtoRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
        return produtos;
    }

    @PostMapping
    public Produto inserir (@RequestBody Produto produto) {
        Produto produtoSalvo = produtoRepository.save(produto);
        return produtoSalvo;
    }

    @DeleteMapping("/{codigo}")
    public void excluir (@PathVariable Integer codigo) {
        produtoRepository.deleteById(codigo);
    }

    @PutMapping("/{codigo}")
    public Produto editar (@PathVariable Integer codigo, @RequestBody Produto produtoEntrada) {
        Optional<Produto> opcional = produtoRepository.findById(codigo);
        Produto produtoSaida= opcional.get();

        produtoSaida.setCategoria(produtoEntrada.getCategoria());
        produtoSaida.setNome(produtoEntrada.getNome());
        produtoSaida.setQuantidade(produtoEntrada.getQuantidade());
        produtoSaida.setPreco(produtoEntrada.getPreco());
        produtoSaida.setValidade(produtoEntrada.getValidade());

        Produto produtoSalvo = produtoRepository.save(produtoSaida);
        return produtoSalvo;
    }
}
