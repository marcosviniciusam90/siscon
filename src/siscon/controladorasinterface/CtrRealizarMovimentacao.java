
package siscon.controladorasinterface;

import com.sun.javafx.scene.control.skin.ScrollPaneSkin;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import siscon.controladoras.CtrCidade;
import siscon.controladoras.CtrMovimentacao;
import siscon.controladoras.CtrPessoa;
import siscon.controladoras.CtrVeiculo;
import siscon.entidades.Associado;
import siscon.entidades.Dependente;
import siscon.entidades.Estado;
import siscon.entidades.Lote;
import siscon.entidades.Movimentacao;
import siscon.entidades.Pessoa;
import siscon.entidades.Veiculo;
import siscon.ui.RealizarMovimentacaoController;
import siscon.ui.PrincipalController;
import siscon.ui.RelatorioMovimentacoesController;
import siscon.ui.SelecionarVeiculoController;
import sun.misc.BASE64Encoder;

public class CtrRealizarMovimentacao
{
    private CtrMovimentacao ctrmovimentacao;   
    
    public CtrRealizarMovimentacao() {
        ctrmovimentacao = new CtrMovimentacao();
    }

    public CtrMovimentacao getCtrmovimentacao() {
        return ctrmovimentacao;
    }
    
    public void Novo()
    {
        ctrmovimentacao.novo();
    }
    
    public void CarregarDados(int cod)
    {
        Novo();
        ctrmovimentacao.getMovimentacao().setCod(cod); //mudando apenas o código, com propósito de carregar dados das tabelas Dependentes, Veiculos, Lotes
        ctrmovimentacao.setMovimentacao(ctrmovimentacao.getMovimentacao().getMovimentacao(cod));
        ctrmovimentacao.getMovimentacao().CarregarPassageiros(); //carregar os veiculos, dependentes e lotes do BANCO
        
    }
    
    public void CarregarTabela(TableView tabela, String filtro, String categoria, boolean ultimas)
    {   
        ArrayList<Movimentacao> res = null;
        if(tabela.getId().contains("Entrada"))
           res = ctrmovimentacao.ListarMovimentacoes(filtro, categoria, "E", ultimas);  
        else
           res = ctrmovimentacao.ListarMovimentacoes(filtro, categoria, "S", ultimas);  
        
        //Agora converte para observable list pois a tabela precisa ser observable list
        ObservableList<Movimentacao> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }
    
    public void CarregarVeiculos(ComboBox combobox)
    {
        ArrayList<Veiculo> res = null;
        if(getCtrmovimentacao().getMovimentacao().getCondutor().getCondutor().equals("Sim"))
           res = ctrmovimentacao.ListarVeiculos();
        else
           res = new ArrayList();
        
        //Agora converte para observable list pois a tabela precisa ser observable list
        ObservableList<Veiculo> modelo;
        modelo = FXCollections.observableArrayList(res);
        combobox.setItems(modelo);
    }
    
    public void CarregarPessoas(TableView tabelaD, TableView tabelaP)
    {        
        tabelaD.getItems().clear();
        tabelaP.getItems().clear();
        ObservableList<Pessoa> ol;
        ol = FXCollections.observableArrayList(ctrmovimentacao.getMovimentacao().getDisponiveis());
        tabelaD.setItems(ol);
        ol = FXCollections.observableArrayList(ctrmovimentacao.getMovimentacao().getPassageiros());
        tabelaP.setItems(ol);
        
    }
    
    public void CarregarPassageiros(AnchorPane anchorpane)
    {
        for(int i=0; i<ctrmovimentacao.getMovimentacao().getPassageiros().size();i++)
            AdicionarPessoa(anchorpane, ctrmovimentacao.getMovimentacao().getPassageiros().get(i));
            
    }
    
    
    public void AdicionarPessoa(AnchorPane anchorpane, Pessoa pessoa)
    {
        VBox vbox = new VBox();
        if(PrincipalController.TelaRealizarMovimentacao.getTitle().contains("Realizar"))
           vbox.getStyleClass().add("selecao");
        else
           vbox.getStyleClass().add("selecionar");

        ImageView img = new ImageView();
        HBox hbox = new HBox();
        hbox.setPrefWidth(92);
        Label nome = new Label();
        if(pessoa != null)
        {
            nome.setText(pessoa.getNome().split(" ")[0]);
            vbox.setId(pessoa.getCod()+""); //depois recupero as pessoas pegando o código delas no ID dos VBox
            CtrGerenciarPessoas ctrGerenciarPessoas = new CtrGerenciarPessoas();
            BufferedImage bimg = ctrGerenciarPessoas.getCtrpessoa().LerImagem(pessoa.getCod());            

            if(bimg != null)                
               img.setImage(SwingFXUtils.toFXImage(bimg, null));
            else 
            {
               java.net.URL urlimg = getClass().getResource("/siscon/ui/imagens/usuario.png"); 
               img.setImage(new Image(urlimg.toString()));
            }
            
            if(PrincipalController.TelaRealizarMovimentacao.getTitle().contains("Realizar") || anchorpane.getId().equals("apPassageiros"))
            {
                String dados = getCtrmovimentacao().getMovimentacao().getCondutor().ObterDados(pessoa.getCod());
                Tooltip.install(vbox, new Tooltip(dados));  
            }

            
        }
        else
        {
            nome.setText("Adicionar");
            vbox.setId(0+"");
            java.net.URL urlimg = getClass().getResource("/siscon/ui/imagens/adicionar.png"); 
            img.setImage(new Image(urlimg.toString()));
            Tooltip.install(vbox, new Tooltip("Não está na lista?\nClique aqui para adicionar."));  
        }            
        
        img.setFitWidth(92);
        img.setFitHeight(94);

        vbox.getChildren().add(img);
        vbox.setPrefHeight(109);
        vbox.setPrefWidth(92);

        if(PrincipalController.TelaRealizarMovimentacao.getTitle().contains("Realizar"))
           hbox.getStyleClass().add("fundo-preto");
        else
           hbox.getStyleClass().add("fundo-azul");
        
        nome.setPrefWidth(92);
        nome.setAlignment(Pos.CENTER);
        nome.setTextFill(Color.WHITE);
        hbox.getChildren().add(nome);
        vbox.getChildren().add(hbox);

        vbox.setLayoutY(getPosicaoY(anchorpane));
        vbox.setLayoutX(getPosicaoX(anchorpane));
        anchorpane.getChildren().add(vbox);

    }
    
    public void ListarPessoas(AnchorPane anchorpane, int codigo)
    {
        ArrayList <Pessoa> pessoas = null;
        if(anchorpane.getId().equals("apCondutor"))
        {
            if(codigo != 0)
            {
                String tipomov = getCtrmovimentacao().getMovimentacao().getVeiculo().getVeiculo(codigo).getUltimamov();
                pessoas = getCtrmovimentacao().ListarCondutores(getCtrmovimentacao().getMovimentacao().getVeiculo().getVeiculo(codigo).getPessoa().getCod(), tipomov);
            }
            else
                pessoas = new ArrayList();
        }
        if(anchorpane.getId().equals("apPassageiros"))
        {
            getCtrmovimentacao().setCondutor(codigo);
            getCtrmovimentacao().CarregarDados();
            pessoas = getCtrmovimentacao().getMovimentacao().getDisponiveis();
        }
        anchorpane.getChildren().clear();
        anchorpane.setPrefHeight(153);
        for(int i=0; i<pessoas.size();i++)                
        {
            AdicionarPessoa(anchorpane, pessoas.get(i));
        }
        AdicionarPessoa(anchorpane, null);
    }
    
    private double getPosicaoX(AnchorPane anchorpane)
    {
        double x = 20;
        if(anchorpane.getChildren().size() > 0)
            x = anchorpane.getChildren().get(anchorpane.getChildren().size()-1).getLayoutX() + 115;
        if(x == 595)
        {
            x = 20;
            anchorpane.setPrefHeight(anchorpane.getPrefHeight()+135);
        }
        return x;
    }
    
    private double getPosicaoY(AnchorPane anchorpane)
    {
        double y = 20;  
        if(anchorpane.getChildren().size() > 0)
            y = anchorpane.getChildren().get(anchorpane.getChildren().size()-1).getLayoutY();
        
        if(anchorpane.getChildren().size() > 0 && anchorpane.getChildren().size() % 5 == 0)
            y = anchorpane.getChildren().get(anchorpane.getChildren().size()-1).getLayoutY() + 135;
        
        return y;
    }
    
    
   
}
