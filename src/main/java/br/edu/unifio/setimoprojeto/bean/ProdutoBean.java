package br.edu.unifio.setimoprojeto.bean;

import br.edu.unifio.setimoprojeto.domain.Categoria;
import br.edu.unifio.setimoprojeto.domain.Produto;
import br.edu.unifio.setimoprojeto.repository.CategoriaRepository;
import br.edu.unifio.setimoprojeto.repository.ProdutoRepository;
import lombok.Data;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.faces.view.ViewScoped;
import java.util.Arrays;
import java.util.List;

@Component
@ViewScoped
@Data
public class ProdutoBean {
    private Produto produto;
    private List<Categoria> categorias;
    private List<Produto> produtos;

    // @Autowired
    // private ProdutoRepository produtoRepository;

    // @Autowired
    // private CategoriaRepository categoriaRepository;

    public void novo() {
        produto = new Produto();

        RestTemplate restTemplate = new RestTemplate();
        Categoria[] vetor = restTemplate.getForObject("http://localhost:8080/categorias", Categoria[].class);
        categorias = Arrays.asList(vetor);
    }

    public void listar() {
        // produtos = produtoRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
        RestTemplate restTemplate = new RestTemplate();
        Produto[] vetor = restTemplate.getForObject("http://localhost:8080/produtos", Produto[].class);
        produtos = Arrays.asList(vetor);
    }

    public void selecionarEdicao(Produto produto) {
        Faces.setFlashAttribute("produto", produto);
        Faces.navigate("produto-edicao.xhtml?faces-redirect=true");
    }

    public void selecionarExclusao(Produto produto) {
        Faces.setFlashAttribute("produto", produto);
        Faces.navigate("produto-exclusao.xhtml?faces-redirect=true");
    }

    public void carregar(){
        produto = Faces.getFlashAttribute("produto");

        RestTemplate restTemplate = new RestTemplate();
        Categoria[] vetor = restTemplate.getForObject("http://localhost:8080/categorias", Categoria[].class);
        categorias = Arrays.asList(vetor);

        if (produto == null) {
            Faces.navigate("produto-listagem.xhtml?faces-redirect=true");
        }
    }

    public void salvar(){
        try {
            // produtoRepository.save(produto);

            RestTemplate restTemplate = new RestTemplate();

            if(produto.getCodigo() == null) {
                restTemplate.postForObject("http://localhost:8080/produtos", produto, Produto.class);
            } else {
                HttpEntity<Produto> httpEntity = new HttpEntity<>(produto);
                restTemplate.exchange(
                        "http://localhost:8080/produtos/" + produto.getCodigo(),
                        HttpMethod.PUT,
                        httpEntity,
                        Produto.class
                );
            }

            Messages.addFlashGlobalInfo("Produto salvo com sucesso");

            Faces.navigate("produto-listagem.xhtml?faces-redirect=true");
        } catch (DataIntegrityViolationException excecao) {
            Messages.addGlobalError("Já existe um produto cadastrado para o nome informado");
        }
    }

    public void excluir(){
        try {
            // produtoRepository.deleteById(produto.getCodigo());

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.delete("http://localhost:8080/produtos/" + produto.getCodigo());

            Messages.addFlashGlobalInfo("Produto removido com sucesso");

            Faces.navigate("produto-listagem.xhtml?faces-redirect=true");
        } catch (DataIntegrityViolationException excecao) {
            Messages.addGlobalError("O produto selecionado está vinculado com outros registros");
        }
    }
}
