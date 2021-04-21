/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.ui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import siscon.controladorasinterface.CtrGerenciarPessoas;
import siscon.controladorasinterface.CtrGerenciarVeiculos;
import siscon.util.Funcoes;
import siscon.util.MaskCampos;

/**
 * FXML Controller class
 *
 * @author Marcos Vinícius
 */
public class RelatorioPessoasController implements Initializable {

    @FXML
    private CheckBox ckbFiltrarPeriodo;
    @FXML
    private AnchorPane apPeriodo;
    
    @FXML
    private DatePicker dpDataInicial;
    @FXML
    private DatePicker dpDataFinal;
    @FXML
    private TextField tfPessoa;
    @FXML
    private Button btSelecionar;    
    private ToggleGroup grupoFormato;    
    private ToggleGroup grupoFormatoPM;    
    @FXML
    private RadioButton rbFormatoCategoria;
    @FXML
    private RadioButton rbFormatoPessoa;
    @FXML
    private ComboBox<String> cbTipoPessoas;
    @FXML
    private RadioButton rbFormatoListaPessoas;
    @FXML
    private AnchorPane apRelatorio;
    @FXML
    private AnchorPane apRelatorioPessoas;
    @FXML
    private AnchorPane apRelatorioFuncionarios;
    @FXML
    private AnchorPane apRelatorioUsuarios;
    @FXML
    private AnchorPane apMenu;
    @FXML
    private Button btCancelar;
    @FXML
    private Button btGerar;
    @FXML
    private ComboBox<String> cbTipoUsuarios;
    @FXML
    private ComboBox<String> cbTipoFuncionarios;
    @FXML
    private AnchorPane apRelatorioPessoasXMovimentacoes;
    @FXML
    private RadioButton rbFormatoDentro;
    @FXML
    private RadioButton rbFormatoFora;
    @FXML
    private CheckBox ckbAssociados;
    @FXML
    private CheckBox ckbFuncionarios;
    @FXML
    private CheckBox ckbVisitantes;
    @FXML
    private Button btMarcar;
    @FXML
    private Label lbTiposPessoas;
    @FXML
    private Label lbErro;
    @FXML
    private Button btPessoasxMovimentacoes;
    @FXML
    private Button btMenuRelFuncionarios;
    @FXML
    private Button btMenuRelUsuarios;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CtrGerenciarPessoas ctr = new CtrGerenciarPessoas();
        ctr.getCtrpessoa().setUsuario(PrincipalController.usuario);
        if(ctr.getCtrpessoa().getUsuario().getNivel() == 2) //básico
        {
            btMenuRelFuncionarios.setVisible(false);
            btMenuRelUsuarios.setVisible(false);
            btPessoasxMovimentacoes.setLayoutY(92);
            apMenu.setLayoutY(137);
        }
        
        rbFormatoListaPessoas.setTooltip(new Tooltip("Exibe uma lista de pessoas."));      
        rbFormatoCategoria.setTooltip(new Tooltip("Exibe as pessoas agrupadas por categoria."));    
        rbFormatoPessoa.setTooltip(new Tooltip("Exibe os responsáveis e seus dependentes."));    
        btPessoasxMovimentacoes.setTooltip(new Tooltip("Relatório de pessoas que estão dentro ou fora do condomínio."));    
        MaskCampos.dateField(dpDataInicial.getEditor());
        MaskCampos.dateField(dpDataFinal.getEditor());
        CarregarTipos();
        SelecionarPessoaController.pessoa = 0;
        SelecionarVeiculoController.veiculo = 0;
        
        grupoFormato = new ToggleGroup();
        rbFormatoCategoria.setToggleGroup(grupoFormato);        
        rbFormatoPessoa.setToggleGroup(grupoFormato);
        rbFormatoListaPessoas.setToggleGroup(grupoFormato);
        
        grupoFormatoPM = new ToggleGroup();
        rbFormatoDentro.setToggleGroup(grupoFormatoPM);        
        rbFormatoFora.setToggleGroup(grupoFormatoPM);   
        
        
    }    

    private void CarregarTipos()
    {
        ArrayList <String> lista = new ArrayList();
        lista.add("Exibir todas as pessoas");
        lista.add("Exibir somente associados");        
        lista.add("Exibir somente visitantes");
        ObservableList<String> modelo;
        modelo = FXCollections.observableArrayList(lista);
        cbTipoPessoas.setItems(modelo);
        cbTipoPessoas.getSelectionModel().select(0); //padrão exibir todas
        
        lista.clear();
        lista.add("Exibir todos os usuários");
        lista.add("Exibir somente administradores");        
        lista.add("Exibir somente básicos");
        modelo = FXCollections.observableArrayList(lista);
        cbTipoUsuarios.setItems(modelo);
        cbTipoUsuarios.getSelectionModel().select(0); //padrão exibir todas
        
        lista.clear();
        lista.add("Exibir todos os funcionários");
        lista.add("Exibir somente admitidos");        
        lista.add("Exibir somente demitidos");
        modelo = FXCollections.observableArrayList(lista);
        cbTipoFuncionarios.setItems(modelo);
        cbTipoFuncionarios.getSelectionModel().select(0); //padrão exibir todas
        
    }
    
    @FXML
    private void clkSelecionar(ActionEvent event) throws IOException {
        if(btSelecionar.getText().equals("SELECIONAR"))
        {
            SelecionarPessoaController.pessoa = 0;
            Stage stage = new Stage();
            stage.setTitle("Selecionar Responsável");
            java.net.URL url = getClass().getResource("icones/siscon.png");
            stage.getIcons().add(new Image(url.toString()));
            RealizarMovimentacaoController.TelaRealizarMovimentacao = null;
            PrincipalController.TelaSelecionarPessoa = stage;
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("SelecionarPessoa.fxml")));
            stage.setScene(scene); 
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);         
            stage.showAndWait();
            
            if(SelecionarPessoaController.pessoa != 0) //retorn o código da pessoa, 0 se não retornou!
            {      
                
                CtrGerenciarPessoas ctr = new CtrGerenciarPessoas();
                tfPessoa.setText(ctr.getCtrpessoa().getPessoa().getPessoa(SelecionarPessoaController.pessoa).getNome());
                btSelecionar.setText("REMOVER");
                ModelosRelatorios();
                cbTipoPessoas.getSelectionModel().select(0);
                cbTipoPessoas.setFocusTraversable(false);
                cbTipoPessoas.setMouseTransparent(true);
            }
        }
        else //remover
        {
            cbTipoPessoas.setFocusTraversable(true);
            cbTipoPessoas.setMouseTransparent(false);
            SelecionarPessoaController.pessoa = 0;
            tfPessoa.setText("");
            btSelecionar.setText("SELECIONAR");
            ModelosRelatorios();
        }
    }

    @FXML
    private void clkFiltrarPeriodo(ActionEvent event) {
        apPeriodo.setDisable(!ckbFiltrarPeriodo.isSelected());
        
    }    
    
    
    @FXML
    private void clkCancelar(ActionEvent event) {
        TelaRelatorio(false); //volta pro menu
    }
 
    @FXML
    private void clkGerar(ActionEvent event) {
        
        //faço as validações
        if(apRelatorioFuncionarios.isVisible() && ckbFiltrarPeriodo.isSelected() && (dpDataInicial.getValue() == null || dpDataFinal.getValue() == null || dpDataInicial.getValue().isAfter(LocalDate.now()) || dpDataInicial.getValue().isAfter(dpDataFinal.getValue())))
        { //erro
            ckbFiltrarPeriodo.setTextFill(Color.RED);
            return;
        }
        ckbFiltrarPeriodo.setTextFill(Color.BLACK);        
        //validei as informações
        
        String query = "";   
        String condicao = "pes_status = 'A'";
        String subtitulo = "";
        
        
        if(apRelatorioFuncionarios.isVisible()) //Relatório de Funcionários
        {
            condicao += " and pes_categoria LIKE 'Funcionário'";
            if(cbTipoFuncionarios.getValue().contains("admitidos"))
            {
                subtitulo += "Admitidos";
                if(!condicao.equals(""))
                   condicao += " and ";
                condicao += "pes_datademissao is null";
            }
            if(cbTipoFuncionarios.getValue().contains("demitidos"))
            {
                subtitulo += "Demitidos";
                if(!condicao.equals(""))
                   condicao += " and ";
                condicao += "pes_datademissao is not null";
            }  
            
            if(ckbFiltrarPeriodo.isSelected())
            {
                if(!subtitulo.equals(""))
                   subtitulo += " - Período: "+Funcoes.DateToString(dpDataInicial.getValue())+" a "+Funcoes.DateToString(dpDataFinal.getValue());
                if(cbTipoFuncionarios.getValue().contains("admitidos"))
                {
                    if(!condicao.equals(""))
                       condicao += " and ";
                    condicao += "pes_dataadmissao >= '"+dpDataInicial.getValue()+"' and pes_dataadmissao <= '"+dpDataFinal.getValue()+"'";
                }
                if(cbTipoFuncionarios.getValue().contains("demitidos"))
                {
                    if(!condicao.equals(""))
                       condicao += " and ";
                    condicao += "pes_datademissao >= '"+dpDataInicial.getValue()+"' and pes_datademissao <= '"+dpDataFinal.getValue()+"'";
                }  
            }
            query = "select * from pessoa";
             if(!condicao.equals(""))
                 query += " WHERE "+condicao;
            Funcoes.gerarRelatorio(query,"relatorios/pessoas/Rel_Funcionarios.jasper", "Relatório de Funcionários",subtitulo);            
        }
        
        if(apRelatorioPessoas.isVisible())
        {
            condicao += " and pes_categoria NOT LIKE 'Funcionário'";
            if(cbTipoPessoas.getValue().contains("associados"))
            {
                subtitulo += "Associados";
                condicao += " and pes_categoria LIKE 'Associado'";
            }
            if(cbTipoPessoas.getValue().contains("visitantes"))
            {
                subtitulo += "Visitantes";
                condicao += " and pes_categoria LIKE 'Visitante'";
            }
            
            if(SelecionarPessoaController.pessoa != 0)
            {
               if(!subtitulo.equals(""))
                  subtitulo += " - ";
               subtitulo += "Responsável: "+tfPessoa.getText().split(" ")[0];
               if(rbFormatoPessoa.isSelected())
                  condicao += " and sup_cod = "+SelecionarPessoaController.pessoa;
               else
                  condicao += " and pes_cod = "+SelecionarPessoaController.pessoa;
            }
            
            if(rbFormatoCategoria.isSelected())
            {
                query = "select * from pessoa";
                 if(!condicao.equals(""))
                     query += " WHERE "+condicao;
                 Funcoes.gerarRelatorio(query,"relatorios/pessoas/Rel_PessoasCategoria.jasper", "Relatório de Pessoas por Categoria",subtitulo);
            }
            else
                if(rbFormatoListaPessoas.isSelected())
                {
                    query = "select * from pessoa";
                     if(!condicao.equals(""))
                         query += " WHERE "+condicao;
                     Funcoes.gerarRelatorio(query,"relatorios/pessoas/Rel_Pessoas.jasper", "Relatório de Pessoas",subtitulo);
                }
                else
                    if(rbFormatoPessoa.isSelected())
                    {
                        query = "select * from " +
                                "(select pes_cod as sup_cod, pes_status as sup_status, pes_nome as sup_nome, pes_tipo_log as sup_tipo_log, pes_logradouro as sup_logradouro, pes_numero as sup_numero, pes_bairro as sup_bairro, pes_cpf as sup_cpf, pes_categoria as sup_categoria, pes_fone as sup_fone, pes_interfone as sup_interfone, pes_ultimamov as sup_ultimamov from pessoa WHERE pes_supervisor is null and pes_categoria not like 'Funcionário' and pes_status = 'A') Pes " +
                                "LEFT JOIN " +
                                "(select pes_cod, pes_nome, pes_cpf, pes_fone, pes_ultimamov, pes_supervisor, pes_status, 1 as c FROM pessoa WHERE pes_status = 'A') Dep ON Dep.pes_supervisor = Pes.sup_cod";
                         if(!condicao.equals(""))
                            query += " WHERE "+condicao.replace("pes", "sup");
                         Funcoes.gerarRelatorio(query,"relatorios/pessoas/Rel_PessoasPessoa.jasper", "Relatório de Responsáveis X Dependentes",subtitulo);
                    }
            
            
        }
        
        if(apRelatorioUsuarios.isVisible())
        {
            condicao += " and pes_nivel is not null";
            if(cbTipoUsuarios.getValue().contains("administradores"))
            {
                subtitulo += "Administradores";
                condicao += " and pes_nivel = 1";
            }
            if(cbTipoUsuarios.getValue().contains("básicos"))
            {
                subtitulo += "Básicos";
                condicao += " and pes_nivel = 2"; 
            }
            query = "select * from pessoa";
            query += " WHERE "+condicao;
            Funcoes.gerarRelatorio(query,"relatorios/pessoas/Rel_Usuarios.jasper", "Relatório de Usuários",subtitulo);  
            
        }
        
        if(apRelatorioPessoasXMovimentacoes.isVisible())
        {
            if(!ckbAssociados.isSelected() && !ckbFuncionarios.isSelected() && !ckbVisitantes.isSelected())
            {
                lbErro.setVisible(true);
                return;
            }
            lbErro.setVisible(false);
            
            condicao = "pes_status = 'A' and (";
            
            if(ckbAssociados.isSelected())
            {
                subtitulo += "Associados";
                condicao += "pes_categoria like 'Associado'";
            }

            if(ckbFuncionarios.isSelected())
            {
                if(!subtitulo.equals(""))
                    subtitulo += "/";
                subtitulo += "Funcionários";
                
                if(condicao.contains("like"))
                    condicao += " OR ";
                condicao += "pes_categoria like 'Funcionário'";
            }
            
            if(ckbVisitantes.isSelected())
            {
                if(!subtitulo.equals(""))
                    subtitulo += "/";
                subtitulo += "Visitantes";
                
                if(condicao.contains("like"))
                    condicao += " OR ";
                condicao += "pes_categoria like 'Visitante'";
            }
            condicao += ")";
            
            if(rbFormatoDentro.isSelected())
            {
                subtitulo += " - Dentro do condomínio";
                condicao += " and pes_ultimamov = 'E'";
            }
            else
            {
                subtitulo += " - Fora do condomínio";
                condicao += " and pes_ultimamov = 'S'";
            }
            
            
            
            query = "select * from pessoa";
            query += " WHERE "+condicao;
            Funcoes.gerarRelatorio(query,"relatorios/pessoas/Rel_PessoasMovimentacoes.jasper", "Relatório de Pessoas X Movimentações",subtitulo);   
        }
            
        
    }
    
    private void ModelosRelatorios()
    {
        rbFormatoCategoria.setDisable(!tfPessoa.getText().equals(""));
        rbFormatoListaPessoas.setDisable(!tfPessoa.getText().equals(""));
        rbFormatoPessoa.setDisable(!tfPessoa.getText().equals(""));
        
        
        if(!tfPessoa.getText().equals(""))
        {
            rbFormatoPessoa.setDisable(false);
            rbFormatoPessoa.setSelected(true);
        }
        
    }
    
    private void TelaRelatorio(boolean status)
    {
        apRelatorio.setVisible(status);
        btGerar.setVisible(status);
        btCancelar.setVisible(status);
        apRelatorioFuncionarios.setVisible(false);
        apRelatorioUsuarios.setVisible(false);
        apRelatorioPessoas.setVisible(false);
        apRelatorioPessoasXMovimentacoes.setVisible(false);
        apMenu.setVisible(!status);
        if(!status)
        {   //estado original
            dpDataFinal.setValue(null);
            dpDataInicial.setValue(null);
            cbTipoPessoas.setFocusTraversable(true);
            cbTipoPessoas.setMouseTransparent(false);
            PrincipalController.TelaRelatorioPessoas.setTitle("Emitir Relatório de Pessoas");
            ckbFiltrarPeriodo.setSelected(false);
            ckbFiltrarPeriodo.setDisable(true);
            apPeriodo.setDisable(true);
            rbFormatoListaPessoas.setSelected(true);
            rbFormatoDentro.setSelected(true);
            rbFormatoFora.setSelected(false);
            cbTipoPessoas.getSelectionModel().select(0);
            SelecionarPessoaController.pessoa = 0;
            tfPessoa.setText("");
            btSelecionar.setText("SELECIONAR");
            ModelosRelatorios();
            cbTipoUsuarios.getSelectionModel().select(0);
            cbTipoFuncionarios.getSelectionModel().select(0);
            
            ckbAssociados.setSelected(true);
            ckbFuncionarios.setSelected(true);
            ckbVisitantes.setSelected(true);
            btMarcar.setText("DESMARCAR TODOS");
            lbErro.setVisible(false);
        }
    }

    @FXML
    private void clkMenuRelFuncionarios(ActionEvent event) {
        TelaRelatorio(true);
        apRelatorioFuncionarios.setVisible(true);
        btGerar.setLayoutY(176);
        btCancelar.setLayoutY(176);
        PrincipalController.TelaRelatorioPessoas.setTitle("Emitir Relatório de Funcionários");
    }

    @FXML
    private void clkMenuRelPessoas(ActionEvent event) {
        TelaRelatorio(true);
        apRelatorioPessoas.setVisible(true);
        btGerar.setLayoutY(261);
        btCancelar.setLayoutY(261);
        PrincipalController.TelaRelatorioPessoas.setTitle("Emitir Relatório de Associados X Visitantes");
    }

    @FXML
    private void clkMenuRelUsuarios(ActionEvent event) {
        TelaRelatorio(true);
        apRelatorioUsuarios.setVisible(true);
        btGerar.setLayoutY(62);
        btCancelar.setLayoutY(62);
        PrincipalController.TelaRelatorioPessoas.setTitle("Emitir Relatório de Usuários");
    }

    @FXML
    private void clkTipoFuncionario(ActionEvent event) {
        ckbFiltrarPeriodo.setDisable(cbTipoFuncionarios.getValue().contains("todos"));
        if(cbTipoFuncionarios.getValue().contains("todos"))
        {
            ckbFiltrarPeriodo.setSelected(false);
            apPeriodo.setDisable(true);
        }
        
    }

    @FXML
    private void clkMenuRelPessoasxMovimentacoes(ActionEvent event) {
        TelaRelatorio(true);
        apRelatorioPessoasXMovimentacoes.setVisible(true);
        btGerar.setLayoutY(252);
        btCancelar.setLayoutY(252);
        PrincipalController.TelaRelatorioPessoas.setTitle("Emitir Relatório de Pessoas X Movimentações");
    }

    @FXML
    private void clkMarcar(ActionEvent event) {
        ckbAssociados.setSelected(btMarcar.getText().equals("MARCAR TODOS"));
        ckbFuncionarios.setSelected(btMarcar.getText().equals("MARCAR TODOS"));
        ckbVisitantes.setSelected(btMarcar.getText().equals("MARCAR TODOS"));
        
        if(btMarcar.getText().equals("MARCAR TODOS"))
        {
            lbErro.setVisible(false);
            btMarcar.setText("DESMARCAR TODOS");
        }
        else
        {
            lbErro.setVisible(true);
            btMarcar.setText("MARCAR TODOS");
        }
    }

    @FXML
    private void clkAssociados(ActionEvent event) {
        if(ckbAssociados.isSelected() && ckbFuncionarios.isSelected() && ckbVisitantes.isSelected())
        {
            btMarcar.setText("DESMARCAR TODOS");            
        }
        if(!ckbAssociados.isSelected() && !ckbFuncionarios.isSelected() && !ckbVisitantes.isSelected())
        {
            btMarcar.setText("MARCAR TODOS");    
            lbErro.setVisible(true);
        }
        
        if(ckbAssociados.isSelected() || ckbFuncionarios.isSelected() || ckbVisitantes.isSelected())
        {
            lbErro.setVisible(false);          
        }
    }

    @FXML
    private void clkFuncionarios(ActionEvent event) {
        if(ckbAssociados.isSelected() && ckbFuncionarios.isSelected() && ckbVisitantes.isSelected())
        {
            btMarcar.setText("DESMARCAR TODOS");            
        }
        if(!ckbAssociados.isSelected() && !ckbFuncionarios.isSelected() && !ckbVisitantes.isSelected())
        {
            btMarcar.setText("MARCAR TODOS");    
            lbErro.setVisible(true);
        }
        
        if(ckbAssociados.isSelected() || ckbFuncionarios.isSelected() || ckbVisitantes.isSelected())
        {
            lbErro.setVisible(false);          
        }
    }

    @FXML
    private void clkVisitantes(ActionEvent event) {
        if(ckbAssociados.isSelected() && ckbFuncionarios.isSelected() && ckbVisitantes.isSelected())
        {
            btMarcar.setText("DESMARCAR TODOS");            
        }
        if(!ckbAssociados.isSelected() && !ckbFuncionarios.isSelected() && !ckbVisitantes.isSelected())
        {
            btMarcar.setText("MARCAR TODOS");    
            lbErro.setVisible(true);
        }
        
        if(ckbAssociados.isSelected() || ckbFuncionarios.isSelected() || ckbVisitantes.isSelected())
        {
            lbErro.setVisible(false);          
        }
    }

    
    

    

    
    
}
