/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Dao.LivroDao;
import Dao.LivroDaoImp;
import Model.Livro;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import static javax.faces.context.FacesContext.getCurrentInstance;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author José Alexandre
 */
@ManagedBean
@SessionScoped
public class LivroController {

    private Livro livro;
    private DataModel listaLivros;
    private Integer idSelecionado;

    public Integer  getIdSelecionado() {
        return idSelecionado;
    }

    public void setIdSelecionado(Integer idSelecionado) {
        this.idSelecionado = idSelecionado;
    }

    public DataModel getListarLivros() {
        List<Livro> lista = new LivroDaoImp().list();
        listaLivros = new ListDataModel(lista);
        return listaLivros;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public void prepararAdicionarLivro(ActionEvent actionEvent){
        livro = new Livro();
    }

    public void prepararAlterarLivro(){
        livro = (Livro)(listaLivros.getRowData());
    }

    public void editar() {
		if (idSelecionado == null) {
			return;
		}
                LivroDao dao = new LivroDaoImp();
                Integer idsel = 0;
                for (int i = 0; i < listaLivros.getRowCount()-1; i++) {
                      listaLivros.setRowIndex(i);
                      Livro liv =  (Livro) listaLivros.getRowData();
                      if (idSelecionado == liv.getId()) {
                          idsel = i;
                          i = listaLivros.getRowCount()+1;
                      }  
                    }
                listaLivros.setRowIndex(idsel);
                livro = (Livro)(listaLivros.getRowData());
//                livro = dao.getLivro(idSelecionado);
		//log.debug("Pronto pra editar");
	}

	public String remover() {
		try {
                   LivroDao dao = new LivroDaoImp();
                    dao.remove(livro);
		} catch(Exception ex) {
			//log.error("Erro ao remover mercadoria.", ex);
			addMessage("Erro ao excluir", ex.getMessage());
			return "";
		}
		//log.debug("Removeu mercadoria "+mercadoria.getId());
		return "listaMercadorias";
	}
  	public void incluir(){
		livro = new Livro();
		//log.debug("Pronto pra incluir");
	}
  
    
    public String excluirLivro(){

        
        Livro livroTemp = (Livro)(listaLivros.getRowData());
        LivroDao dao = new LivroDaoImp();
        dao.remove(livroTemp);
        return "index";

    }
	public String salvar() {
		try {
                    LivroDao dao = new LivroDaoImp();
                    if (livro.getId() == 0 ){
                       dao.save(livro);}
                      else         
                    { 
//                       livro.setId(idSelecionado);
                       dao.update(livro);}
                    
		} catch(Exception ex) {
			//log.error("Erro ao salvar mercadoria.", ex);
			addMessage("Erro ao salvar mercadoria" + ex.getMessage(), ex.getMessage());
			return "";
		}
		//log.debug("Salvour mercadoria "+mercadoria.getId());
		return "listaMercadorias";
	}

    public void adicionarLivro(ActionEvent actionEvent){

        LivroDao dao = new LivroDaoImp();
        dao.save(livro);
        
    }

    public void alterarLivro(ActionEvent actionEvent){

        LivroDao dao = new LivroDaoImp();
        dao.update(livro);

    }
	private void addMessage(String summary, String detail) {
		getCurrentInstance().addMessage(null, new FacesMessage(summary, summary.concat("<br/>").concat(detail)));
	}

}
