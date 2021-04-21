/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.ui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import siscon.controladorasinterface.CtrGerenciarPessoas;
import siscon.controladorasinterface.CtrRealizarMovimentacao;
import siscon.entidades.Associado;
import siscon.entidades.Dependente;
import siscon.entidades.Pessoa;
import siscon.entidades.Veiculo;
import static siscon.ui.RealizarMovimentacaoController.TelaRealizarMovimentacao;
import siscon.util.Banco;

/**
 * FXML Controller class
 *
 * @author Marcos
 */
public class RealizarMovimentacaoController implements Initializable {

    @FXML
    private AnchorPane apCondutor;
    private CtrRealizarMovimentacao ctrRealizarMovimentacao = new CtrRealizarMovimentacao();
    private int pes_cod;
    public static Stage TelaRealizarMovimentacao = null;
    //private CtrGerenciarPessoas ctrGerenciarPessoas = new CtrGerenciarPessoas();
    @FXML
    private ScrollPane spCondutor;
    @FXML
    private AnchorPane apVeiculo;
    @FXML
    private Label lbVeiculo;
    @FXML
    private Label lbCondutor;
    @FXML
    private Label lbPassageiros;
    @FXML
    private ScrollPane spPassageiros;
    @FXML
    private AnchorPane apPassageiros;
    @FXML
    private AnchorPane apTela;
    @FXML
    private Button btMovimentar;
    @FXML
    private ImageView imvAjuda;
    @FXML
    private Label lbAutorizado;
    @FXML
    private Label lbHorario;
    @FXML
    private Label lbCamposObrigatorios;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {   
        Tooltip.install(imvAjuda, new Tooltip("Obter ajuda")); 
        
        if(PrincipalController.TelaRealizarMovimentacao.getTitle().contains("Realizar"))
           AdicionarVeiculo(0);
        else
        {
            lbCamposObrigatorios.setVisible(false);
            if(PrincipalController.TelaRealizarMovimentacao.getTitle().contains("Desfazer"))
            {
               ctrRealizarMovimentacao.CarregarDados(ctrRealizarMovimentacao.getCtrmovimentacao().getMovimentacao().getUltimaMovimentacao().getCod());
               btMovimentar.setText("DESFAZER");
            }
            else
            {
               ctrRealizarMovimentacao.CarregarDados(PrincipalController.movimentacao);            
               btMovimentar.setText("FECHAR");
            }
            
            if(ctrRealizarMovimentacao.getCtrmovimentacao().getMovimentacao().getVeiculo() != null)
               AdicionarVeiculo(ctrRealizarMovimentacao.getCtrmovimentacao().getMovimentacao().getVeiculo().getCod());
            else
                AdicionarVeiculo(0);
            
            lbHorario.setText(ctrRealizarMovimentacao.getCtrmovimentacao().getMovimentacao().getData());
            lbAutorizado.setText("Autorizado por: "+ctrRealizarMovimentacao.getCtrmovimentacao().getMovimentacao().getUsuario().getNome().split(" ")[0]);
            ctrRealizarMovimentacao.AdicionarPessoa(apCondutor, ctrRealizarMovimentacao.getCtrmovimentacao().getMovimentacao().getCondutor());
            VBox vbox = (VBox) apCondutor.getChildren().get(0);
            String dados = ctrRealizarMovimentacao.getCtrmovimentacao().getMovimentacao().getCondutor().ObterDados(Integer.parseInt(vbox.getId()));
            
            Label lbDados = new Label(dados);
            lbDados.setLayoutX(135);
            lbDados.setLayoutY(20);
            lbDados.setPrefHeight(117);
            lbDados.setPrefWidth(458);
            lbDados.setWrapText(true);
            lbDados.setAlignment(Pos.CENTER_LEFT);
            apCondutor.getChildren().add(lbDados);
            
            ctrRealizarMovimentacao.CarregarPassageiros(apPassageiros);
            lbPassageiros.setVisible(true);
            spPassageiros.setVisible(true);
            apVeiculo.getChildren().get(3).setVisible(false); //btRemover
            String tipo = "";
            if(ctrRealizarMovimentacao.getCtrmovimentacao().getMovimentacao().getTipo().equals("S"))
                tipo = "E";
            else
                tipo = "S";
            DefinirTipoMovimentacao(tipo);            
            btMovimentar.setVisible(true);
            apVeiculo.getChildren().get(1).setLayoutY(apVeiculo.getChildren().get(1).getLayoutY()+20);
            lbVeiculo.setText("VEÍCULO");
            if(apVeiculo.getChildren().get(2).isVisible())
            {
                lbCondutor.setText("PESSOA");
                lbPassageiros.setText("ACOMPANHANTE(S)");
            }
            else
            {
                lbCondutor.setText("CONDUTOR");
                lbPassageiros.setText("PASSAGEIRO(S)");
            }
            if(ctrRealizarMovimentacao.getCtrmovimentacao().getMovimentacao().getVeiculo().getCod() == 0)
            {
                NenhumVeiculo();
                lbCondutor.setVisible(true);
                spCondutor.setVisible(true);
                //apVeiculo.getChildren().get(0).setDisable(true);
                apVeiculo.getChildren().get(0).setStyle("-fx-opacity: 1; -fx-cursor: default;");
            }
            
            if(apPassageiros.getChildren().size() == 0)
            {
                Label label = new Label("SEM "+lbPassageiros.getText());
                label.setText(label.getText().replace("(S)", "S"));
                label.setPrefWidth(613);
                label.setPrefHeight(153);
                label.setLayoutX(0);
                label.setLayoutY(0);
                label.setStyle("-fx-font-size: 20px;");
                label.setAlignment(Pos.CENTER);
                apPassageiros.getChildren().add(label);
            }
            
        }
    }

    private void MarcarPassageiro(VBox vbox)
    {
        if(vbox.getStyleClass().size() == 2) 
        {
            vbox.getStyleClass().remove(1);
            vbox.getChildren().get(1).getStyleClass().remove(1);
        }
        else
        {            
            vbox.getStyleClass().add("selecionar");
            vbox.getChildren().get(1).getStyleClass().add("fundo-azul");
        }
    }
    
    private void MarcarCondutor(VBox vbox)
    {
        ObservableList <Node> componentes =  ((AnchorPane)vbox.getParent()).getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof VBox && n.getStyleClass().size() == 2) //TextField, TextArea e HTMLeditor
            {
                n.getStyleClass().remove(1);
                ((VBox)n).getChildren().get(1).getStyleClass().remove(1);
            }
        }
        vbox.getStyleClass().add("selecionar");
        vbox.getChildren().get(1).getStyleClass().add("fundo-azul");
    }
    
    private void SelecionarCondutor(VBox vbox) throws IOException
    {
        
        if(!vbox.getId().equals("0")) //pessoa
        {
            MarcarCondutor(vbox);
            ctrRealizarMovimentacao.Novo();
            ctrRealizarMovimentacao.getCtrmovimentacao().setCondutor(Integer.parseInt(vbox.getId()));
            ctrRealizarMovimentacao.getCtrmovimentacao().setTipo();            
            spPassageiros.setVisible(true);
            lbPassageiros.setVisible(true);
            ctrRealizarMovimentacao.ListarPessoas(apPassageiros, Integer.parseInt(vbox.getId()));
            HabilitaCliqueMousePassageiro();
            spPassageiros.setVvalue(0);
            CtrGerenciarPessoas ctr = new CtrGerenciarPessoas();
            String tipo = ctr.getCtrpessoa().getPessoa().getPessoa(Integer.parseInt(vbox.getId())).getUltimamov();
            DefinirTipoMovimentacao(tipo);
            btMovimentar.setVisible(true);
            
        }
        else      //adicionar condutor
        {
            Stage stage = new Stage();
            CheckBox ckbNenhumVeiculo = (CheckBox)apVeiculo.getChildren().get(2);
            if(ckbNenhumVeiculo.isVisible()) //nenhum veículo utilizado
                stage.setTitle("Adicionar Condutor");
            else
            {
                String tipo = "Entrada";
                Label lbTipoMov = (Label)apVeiculo.getChildren().get(4);
                if(lbTipoMov.getText().equals("SAÍDA"))
                    tipo = "Saída";
                stage.setTitle("Adicionar Condutor - Movimentação: "+tipo);
            }
            java.net.URL url = getClass().getResource("icones/siscon.png");
            stage.getIcons().add(new Image(url.toString()));
            TelaRealizarMovimentacao = stage;
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("SelecionarPessoa.fxml")));
            stage.setScene(scene); 
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);         
            stage.showAndWait();

            if(SelecionarPessoaController.pessoa != 0) //retorn o código da pessoa, 0 se não retornou!
            {
                if(!CondutorRepetido(SelecionarPessoaController.pessoa))
                {
                    if((apCondutor.getChildren().size() - 1) % 5 == 0)
                    {
                        double altura = apCondutor.getPrefHeight()-135;
                        if(altura < 153)
                            altura = 153;
                        apCondutor.setPrefHeight(altura);
                    }
                    apCondutor.getChildren().remove(vbox);
                    CtrGerenciarPessoas ctr = new CtrGerenciarPessoas();
                    ctrRealizarMovimentacao.AdicionarPessoa(apCondutor, ctr.getCtrpessoa().getPessoa().getPessoa(SelecionarPessoaController.pessoa));
                    ctrRealizarMovimentacao.AdicionarPessoa(apCondutor, null);
                    setCliqueCondutor(((VBox) apCondutor.getChildren().get(apCondutor.getChildren().size()-2)));
                    setCliqueCondutor(((VBox) apCondutor.getChildren().get(apCondutor.getChildren().size()-1)));
                    SelecionarCondutor(((VBox) apCondutor.getChildren().get(apCondutor.getChildren().size()-2)));
                    
                }
            }
        
        }
        
    }
    
    private boolean CondutorRepetido(int novo_condutor)
    {
        for(int i=0; i<apCondutor.getChildren().size();i++)
            if(apCondutor.getChildren().get(i).getId().equals(novo_condutor+""))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Erro");
                alert.setContentText("A pessoa já está na lista.");
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm()); 
                alert.showAndWait();
                return true;
            }
        return false;
    }
    
    private boolean PassageiroRepetido(int novo_passageiro)
    {
        VerificarCondutor();
        if(pes_cod == novo_passageiro)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro");
            alert.setContentText("A pessoa já foi selecionada na lista acima.");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm()); 
            alert.showAndWait();
            return true;
        }
        
        for(int i=0; i<apPassageiros.getChildren().size();i++)
            if(apPassageiros.getChildren().get(i).getId().equals(novo_passageiro+""))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Erro");
                alert.setContentText("A pessoa já está na lista.");
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm()); 
                alert.showAndWait();
                return true;
            }
        
        return false;
    }
    private void SelecionarPassageiro(VBox vbox) throws IOException
    {
        if(!vbox.getId().equals("0")) //pessoa
        {
            MarcarPassageiro(vbox);
        }
        else      //adicionar passageiro
        {
            Stage stage = new Stage();

            String tipo = "Entrada";
            Label lbTipoMov = (Label)apVeiculo.getChildren().get(4);
            if(lbTipoMov.getText().equals("SAÍDA"))
                tipo = "Saída";
            stage.setTitle("Adicionar Passageiro - Movimentação: "+tipo);
            
            java.net.URL url = getClass().getResource("icones/siscon.png");
            stage.getIcons().add(new Image(url.toString()));
            TelaRealizarMovimentacao = stage;
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("SelecionarPessoa.fxml")));
            stage.setScene(scene); 
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);         
            stage.showAndWait();

            if(SelecionarPessoaController.pessoa != 0) //retorn o código da pessoa, 0 se não retornou!
            {
                if(!PassageiroRepetido(SelecionarPessoaController.pessoa))
                {
                    if((apPassageiros.getChildren().size() - 1) % 5 == 0)
                    {
                        double altura = apPassageiros.getPrefHeight()-135;
                        if(altura < 153)
                            altura = 153;
                        apPassageiros.setPrefHeight(altura);
                    }
                    
                    apPassageiros.getChildren().remove(vbox);
                    CtrGerenciarPessoas ctr = new CtrGerenciarPessoas();
                    ctrRealizarMovimentacao.AdicionarPessoa(apPassageiros, ctr.getCtrpessoa().getPessoa().getPessoa(SelecionarPessoaController.pessoa));
                    ctrRealizarMovimentacao.AdicionarPessoa(apPassageiros, null);
                    setCliquePassageiro(((VBox) apPassageiros.getChildren().get(apPassageiros.getChildren().size()-2)));
                    setCliquePassageiro(((VBox) apPassageiros.getChildren().get(apPassageiros.getChildren().size()-1)));
                    SelecionarPassageiro(((VBox) apPassageiros.getChildren().get(apPassageiros.getChildren().size()-2)));
                    
                }
            }
        
        }
        
    }
    
    private void NenhumVeiculo()
    {
        java.net.URL urlimg = getClass().getResource("imagens/nenhum.png"); 
        VBox vbox = (VBox)apVeiculo.getChildren().get(0);
        HBox hbox = (HBox)vbox.getChildren().get(1);
        ((Label)hbox.getChildren().get(0)).setText("Nenhum");
        if(PrincipalController.TelaRealizarMovimentacao.getTitle().contains("Realizar"))
        {
             ctrRealizarMovimentacao.ListarPessoas(apCondutor, 0);
             HabilitaCliqueMouseCondutor();
             lbCondutor.setText("2. SELECIONE A PESSOA (*)");
             lbPassageiros.setText("3. SELECIONE O(S) ACOMPANHANTE(S)");
             spCondutor.setVisible(true);
             lbCondutor.setVisible(true);
        }
        ((ImageView)(vbox.getChildren().get(0))).setImage(new Image(urlimg.toString()));
    }
    
    private void AdicionarVeiculo(int veiculo)
    {
        apVeiculo.getChildren().clear();
        apCondutor.getChildren().clear();
        apPassageiros.getChildren().clear();
        spCondutor.setVisible(false);
        spPassageiros.setVisible(false);
        lbCondutor.setVisible(false);
        lbPassageiros.setVisible(false);
        btMovimentar.setVisible(false);
        lbCondutor.setTextFill(Color.BLACK);
        lbVeiculo.setTextFill(Color.BLACK);
        VBox vbox = new VBox();
        CheckBox ckbNenhumVeiculo = new CheckBox("Não está conduzindo nenhum veículo");
        ckbNenhumVeiculo.setFont(new Font(19));
        if(ctrRealizarMovimentacao.getCtrmovimentacao().getMovimentacao().getVeiculo() == null)
        {
           ctrRealizarMovimentacao.getCtrmovimentacao().getMovimentacao().setVeiculo(new Veiculo());
           veiculo = 0;
           ckbNenhumVeiculo.setSelected(true);
           ckbNenhumVeiculo.setFocusTraversable(false);
           ckbNenhumVeiculo.setMouseTransparent(true);
        }
        Veiculo v = ctrRealizarMovimentacao.getCtrmovimentacao().getMovimentacao().getVeiculo().getVeiculo(veiculo);
        vbox.setId(veiculo+""); //depois recupero o veiculo pegando o código dele no ID do VBox
        if(PrincipalController.TelaRealizarMovimentacao.getTitle().contains("Realizar"))
        {
            vbox.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                VBox vbox_atual = (VBox)event.getSource();
                try 
                {  
                    SelecionarVeiculo(vbox_atual);
                } 
                catch (IOException ex) 
                {
                    Logger.getLogger(CtrRealizarMovimentacao.class.getName()).log(Level.SEVERE, null, ex);
                }
                event.consume();
            }
            });
        }        

        
        ImageView img = new ImageView();
        img.setFitWidth(92);
        img.setFitHeight(94);
        java.net.URL urlimg;
        if(v == null)
        {
           urlimg = getClass().getResource("imagens/adicionar.png"); 
           img.setImage(new Image(urlimg.toString()));
        }
        else
        {
            spCondutor.setVisible(true);
            lbCondutor.setVisible(true);
            CtrGerenciarPessoas ctrGerenciarPessoas = new CtrGerenciarPessoas(); //substituir por ctrgerenciarveiculos
            BufferedImage bimg = ctrGerenciarPessoas.getCtrpessoa().LerImagem(0); //substituir por código veiculo
            if(bimg != null)                
               img.setImage(SwingFXUtils.toFXImage(bimg, null));
            else 
            {
               urlimg = getClass().getResource("imagens/veiculo.png"); 
               img.setImage(new Image(urlimg.toString()));
            }
        }
        

        vbox.getChildren().add(img);
        vbox.setPrefHeight(109);
        vbox.setPrefWidth(92);

        HBox hbox = new HBox();
        Label lbdados = new Label();
        Label lbNome = new Label();
        lbNome.setPrefWidth(92);
        lbNome.setAlignment(Pos.CENTER);
        lbNome.setTextFill(Color.WHITE);
        hbox.setPrefWidth(92);
        if(v != null)
        {      
           lbNome.setText(v.getCategoria().toString());
           vbox.getStyleClass().add("selecionar");
           hbox.getStyleClass().add("fundo-azul");
           String dados = "Veículo: "+v.getMarca().getNome()+" "+v.getModelo()+" "+v.getCor().getNome()+"\nPlaca: "+v.getPlaca()+"\nProprietário: "+v.getPessoa().toString();
           lbdados.setText(dados);
        }
        else
        {
           lbNome.setText("Selecionar");
           vbox.getStyleClass().add("selecao");
           lbdados.setText("");
           hbox.getStyleClass().add("fundo-preto");
        }
                     
        lbdados.setLayoutX(115);
        lbdados.setLayoutY(15);
        lbdados.setPrefWidth(350);
        lbdados.setWrapText(true);
        hbox.getChildren().add(lbNome);
        vbox.getChildren().add(hbox);
        
        
        ckbNenhumVeiculo.setLayoutX(115);
        ckbNenhumVeiculo.setLayoutY(45);
        if(v==null)
        {            
            ckbNenhumVeiculo.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                CheckBox ckb = (CheckBox)event.getSource();
                VBox vbox = (VBox)((AnchorPane)ckb.getParent()).getChildren().get(0);
                HBox hbox = (HBox) (vbox.getChildren().get(1));
                vbox.setDisable(ckb.isSelected());
                java.net.URL urlimg;
                if(ckb.isSelected())
                {
                   NenhumVeiculo();
                }
                else
                {
                    lbCondutor.setText("2. SELECIONE O CONDUTOR (*)");
                    lbPassageiros.setText("3. SELECIONE O(S) PASSAGEIRO(S)");
                    spCondutor.setVisible(false);
                    lbCondutor.setVisible(false);
                    spPassageiros.setVisible(false);
                    lbPassageiros.setVisible(false);
                    btMovimentar.setVisible(false);
                    apCondutor.getChildren().clear();
                    apPassageiros.getChildren().clear();
                    apVeiculo.getChildren().get(4).setVisible(false);
                    apVeiculo.getChildren().get(5).setVisible(false);
                    urlimg = getClass().getResource("imagens/adicionar.png");
                   ((Label)hbox.getChildren().get(0)).setText("Selecionar");
                   ((ImageView)(vbox.getChildren().get(0))).setImage(new Image(urlimg.toString()));
                }
                
                
                event.consume();
            }
            });
            
        }
        else
            ckbNenhumVeiculo.setVisible(false);
        Button btRemover = new Button("REMOVER");
        btRemover.setPrefWidth(100);
        //btRemover.setPrefHeight(40);
        btRemover.setLayoutX(lbdados.getLayoutX());
        btRemover.setLayoutY(75);
        if(!lbdados.getText().equals(""))
        {            
            btRemover.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                AdicionarVeiculo(0);                                
                event.consume();
            }
            });
            
        }
        else
            btRemover.setVisible(false);
        
        apVeiculo.getChildren().add(vbox);      
        apVeiculo.getChildren().add(lbdados);
        apVeiculo.getChildren().add(ckbNenhumVeiculo);
        apVeiculo.getChildren().add(btRemover);
        
        String tipo = "";
        if(v!=null)
           tipo = v.getUltimamov();
           
        DefinirTipoMovimentacao(tipo); //label, imageview (nesta ordem)
    }

    private void DefinirTipoMovimentacao(String tipo)
    {    
        ImageView tipo_mov = new ImageView();
        Label lbTipoMov = new Label();
        if(tipo.equals(""))
        {
            tipo_mov.setVisible(false);
            lbTipoMov.setVisible(false);            
        }
        else
        {
            lbTipoMov.setStyle("-fx-font-size: 20px;");
            java.net.URL url;           
            if(tipo.equals("E"))
            {
                url = getClass().getResource("imagens/sair.png");
                lbTipoMov.setText("SAÍDA");
                lbTipoMov.setTextFill(Color.RED);
            }
            else
            {
                url = getClass().getResource("imagens/entrar.png");
                lbTipoMov.setText("ENTRADA");
                lbTipoMov.setTextFill(Color.BLUE);
            }
            tipo_mov.setImage(new Image(url.toString()));
            lbTipoMov.setLayoutX(485);
            lbTipoMov.setLayoutY(-5);   
            lbTipoMov.setPrefWidth(96);
            lbTipoMov.setAlignment(Pos.CENTER);
            tipo_mov.setLayoutX(485);
            tipo_mov.setLayoutY(25);
        }
        if(apVeiculo.getChildren().size() == 4)
        {
            apVeiculo.getChildren().add(lbTipoMov);
            apVeiculo.getChildren().add(tipo_mov);
        }
        else
        {
            apVeiculo.getChildren().set(4, lbTipoMov);
            apVeiculo.getChildren().set(5, tipo_mov);
        }
        
    }
    
    public void SelecionarVeiculo(VBox vbox) throws IOException {
        if(vbox.getId().equals("0")) //selecionar
        {
            Stage stage = new Stage();
            stage.setTitle("Selecionar Veículo");
            java.net.URL url = getClass().getResource("icones/siscon.png");
            stage.getIcons().add(new Image(url.toString()));
            RelatorioMovimentacoesController.TelaSelecionarVeiculo = stage;
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("SelecionarVeiculo.fxml")));
            stage.setScene(scene); 
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);         
            stage.showAndWait();

            if(SelecionarVeiculoController.veiculo != 0) //retorn o código da pessoa, 0 se não retornou!
            {
                spCondutor.setVisible(true);
                lbCondutor.setVisible(true);
                AdicionarVeiculo(SelecionarVeiculoController.veiculo);
                //lbNomeVeiculo.setText(ctrRealizarMovimentacao.getCtrmovimentacao().getMovimentacao().getVeiculo().getVeiculo(SelecionarVeiculoController.veiculo).getModelo());
                ctrRealizarMovimentacao.ListarPessoas(apCondutor, SelecionarVeiculoController.veiculo);
                HabilitaCliqueMouseCondutor();
                spCondutor.setVvalue(0);
            }
        }
    }
    
    private void HabilitaCliqueMouseCondutor()
    {
        if(PrincipalController.TelaRealizarMovimentacao.getTitle().contains("Realizar"))
        {
        ObservableList <Node> componentes = apCondutor.getChildren();
            for(Node n : componentes) //Node é superclasse de todos os componentes
            {
                if(n instanceof VBox) 
                {
                    setCliqueCondutor((VBox)n);
                }
            }
        }
    }
    
    private void HabilitaCliqueMousePassageiro()
    {
        if(PrincipalController.TelaRealizarMovimentacao.getTitle().contains("Realizar"))
        {
            ObservableList <Node> componentes = apPassageiros.getChildren();
            for(Node n : componentes) //Node é superclasse de todos os componentes
            {
                if(n instanceof VBox) //TextField, TextArea e HTMLeditor
                {
                    setCliquePassageiro(((VBox)n));
                    
                }
            }
        }
    }
    
    private void setCliqueCondutor(VBox vbox)
    {
        vbox.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    VBox vbox_atual = (VBox)event.getSource();
                    try {
                        SelecionarCondutor(vbox_atual);
                    } catch (IOException ex) {
                        Logger.getLogger(RealizarMovimentacaoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    event.consume();
                }
                });
    }
    
    private void setCliquePassageiro(VBox vbox)
    {
        vbox.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    VBox vbox_atual = (VBox)event.getSource();
                    try {
                        SelecionarPassageiro(vbox_atual);
                    } catch (IOException ex) {
                        Logger.getLogger(RealizarMovimentacaoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    event.consume();
                }
                });
    }
    
    private boolean VerificarCondutor()
    {
        ObservableList <Node> componentes =  apCondutor.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof VBox && n.getStyleClass().size() == 2 && !n.getId().equals("0")) //selecionado
            {
                pes_cod = Integer.parseInt(n.getId());
                return true;
            }
        }
        return false;
        
    }

    @FXML
    private void clkMovimentar(ActionEvent event) throws SQLException 
    {
        if(btMovimentar.getText().equals("FECHAR"))
        {
            PrincipalController.TelaRealizarMovimentacao.close();
            return;
        }
        
        boolean erro = false;
        if(!btMovimentar.getText().equals("DESFAZER") && !VerificarCondutor()) //erro
        {
            //btCondutor.requestFocus();
            lbCondutor.setTextFill(Color.RED);
            erro = true;           
        }
        else   
            lbCondutor.setTextFill(Color.BLACK);
        
        CheckBox ckbNenhumVeiculo = (CheckBox) apVeiculo.getChildren().get(2);
        if(apVeiculo.getChildren().get(0).getId().equals("0") && !ckbNenhumVeiculo.isSelected()) //erro
        {
            //cbVeiculo.requestFocus();
            lbVeiculo.setTextFill(Color.RED);
            erro = true;           
        }
        else   
            lbVeiculo.setTextFill(Color.BLACK);
        
        
        
        if(erro)
            return;
        
        if(PrincipalController.TelaRealizarMovimentacao.getTitle().contains("Realizar"))
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Movimentação");
            alert.setContentText("Deseja realmente realizar a movimentação ?");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
            ButtonType buttonTypeSim = new ButtonType("SIM", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNao = new ButtonType("NÃO", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao); 

            if(alert.showAndWait().get() == buttonTypeSim)
            {
                AdicionarPassageiros();
                Banco.con.IniciarTransacao();
                int vei_cod = 0;
                if(!ckbNenhumVeiculo.isSelected())
                    vei_cod = Integer.parseInt(apVeiculo.getChildren().get(0).getId());
                if(!ctrRealizarMovimentacao.getCtrmovimentacao().Movimentar(pes_cod, vei_cod, PrincipalController.usuario))
                {
                    Banco.con.Rollback("Não foi possível realizar a movimentação.");
                    return;                    
                }
                else
                    Banco.con.Commit("Realizar movimentação E/S");
                PrincipalController.TelaRealizarMovimentacao.close();
            }
        }
        else //DESFAZER
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Desfazer");
            alert.setContentText("Deseja realmente desfazer a última movimentação ?");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
            ButtonType buttonTypeSim = new ButtonType("SIM", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNao = new ButtonType("NÃO", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao); 

            if(alert.showAndWait().get() == buttonTypeSim)
            {
                Banco.con.IniciarTransacao();
                int vei_cod = 0;
                if(!ckbNenhumVeiculo.isSelected())
                    vei_cod = Integer.parseInt(apVeiculo.getChildren().get(0).getId());
                if(!ctrRealizarMovimentacao.getCtrmovimentacao().Desfazer(ctrRealizarMovimentacao.getCtrmovimentacao().getMovimentacao().getCondutor().getCod(), vei_cod))
                {
                    Banco.con.Rollback("Não foi possível desfazer a última movimentação.");
                    return;
                }
                else
                    Banco.con.Commit("Desfazer movimentação E/S");
                PrincipalController.TelaRealizarMovimentacao.close();
            }
        }
    }
    
    private void AdicionarPassageiros()
    {
        ObservableList <Node> componentes =  apPassageiros.getChildren();
        for(Node n : componentes) //Node é superclasse de todos os componentes
        {
            if(n instanceof VBox && n.getStyleClass().size() == 2 && !n.getId().equals("0")) //selecionado
            {
                int cod = Integer.parseInt(n.getId());
                ctrRealizarMovimentacao.getCtrmovimentacao().getMovimentacao().adicionar_passageiro(cod);
            }
        }
        
    }

    @FXML
    private void onMouseExitAjuda(MouseEvent event) {
        java.net.URL url = getClass().getResource("icones/help_p.png");
        imvAjuda.setImage(new Image(url.toString()));
    }

    @FXML
    private void clickAjuda(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Ajuda - Movimentações de E/S");
        java.net.URL url = getClass().getResource("icones/siscon.png");
        stage.getIcons().add(new Image(url.toString()));
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("AjudaMovimentacoes.fxml")));
        stage.setScene(scene); 
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);         
        stage.showAndWait();
    }

    @FXML
    private void OnMouseOverAjuda(MouseEvent event) {
        java.net.URL url = getClass().getResource("icones/help_p2.png");
        imvAjuda.setImage(new Image(url.toString()));
    }
    
    
}
