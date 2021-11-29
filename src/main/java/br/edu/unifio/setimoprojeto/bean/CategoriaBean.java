package br.edu.unifio.setimoprojeto.bean;

import br.edu.unifio.setimoprojeto.domain.Categoria;
import br.edu.unifio.setimoprojeto.repository.CategoriaRepository;
import lombok.Data;
import org.omnifaces.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.faces.view.ViewScoped;

@Component
@ViewScoped
@Data
public class CategoriaBean {
    private Categoria categoria;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public void novo(){
        categoria = new Categoria();
    }

    public void salvar(){
        try {
            categoriaRepository.save(categoria);
            Messages.addGlobalInfo("Categoria salva com sucesso");

            novo();
        } catch (DataIntegrityViolationException excecao) {
            Messages.addGlobalError("Já existe uma categoria cadastrada para o nome informado");
        }
    }
}
