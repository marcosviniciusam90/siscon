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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import siscon.controladorasinterface.CtrGerenciarEmpresas;
import siscon.controladorasinterface.CtrGerenciarPessoas;
import siscon.controladorasinterface.CtrGerenciarVeiculos;
import static siscon.ui.PrincipalController.TelaEscolherOpcao;
import static siscon.ui.PrincipalController.TelaSelecionarEmpresa;
import static siscon.ui.PrincipalController.TelaSelecionarPessoa;
import siscon.util.Funcoes;
import siscon.util.MaskCampos;

/**
 * FXML Controller class
 *
 * @author Marcos Vinícius
 */
public class RelatorioContasController implements Initializable {

    
    private ToggleGroup PAGARgrupoFormato;    
    private ToggleGroup grupoTipos;   
    @FXML
    private RadioButton PAGARrbFormatoConta;
    @FXML
    private RadioButton PAGARrbFormatoEmpresaPessoa;
    @FXML
    private RadioButton PAGARrbFormatoLista;
    @FXML
    private ComboBox<String> PAGARcbTipos;
    @FXML
    private CheckBox PAGARckbFiltrarPeriodo;
    @FXML
    private AnchorPane PAGARapPeriodo;
    @FXML
    private DatePicker PAGARdpDataInicial;
    @FXML
    private DatePicker PAGARdpDataFinal;
    @FXML
    private TextField PAGARtfPessoa;
    @FXML
    private Button PAGARbtSelecionar;
    @FXML
    private AnchorPane apContasPagar;
    @FXML
    private AnchorPane apContasReceber;
    @FXML
    private RadioButton RECEBERrbFormatoConta;
    @FXML
    private RadioButton RECEBERrbFormatoPessoa;
    @FXML
    private RadioButton RECEBERrbFormatoLista;
    @FXML
    private ComboBox<String> RECEBERcbTipos;
    @FXML
    private CheckBox RECEBERckbFiltrarPeriodo;
    @FXML
    private AnchorPane RECEBERapPeriodo;
    @FXML
    private DatePicker RECEBERdpDataInicial;
    @FXML
    private DatePicker RECEBERdpDataFinal;
    @FXML
    private TextField RECEBERtfPessoa;
    @FXML
    private Button RECEBERbtSelecionar;
    @FXML
    private AnchorPane apMenu;
    @FXML
    private AnchorPane apFluxodeCaixa;
    @FXML
    private RadioButton CAIXArbFormatoDetalhado;
    @FXML
    private RadioButton CAIXArbFormatoSimplificado;
    @FXML
    private CheckBox CAIXAckbFiltrarPeriodo;
    @FXML
    private DatePicker CAIXAdpDataInicial;
    @FXML
    private DatePicker CAIXAdpDataFinal;
    @FXML
    private RadioButton CAIXArbTipoEntrada;
    @FXML
    private RadioButton CAIXArbTipoRetirada;
    @FXML
    private RadioButton CAIXArbTipoTodas;
    @FXML
    private AnchorPane CAIXAapPeriodo;
    @FXML
    private AnchorPane CAIXAapTiposMovimentacoes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PAGARrbFormatoConta.setTooltip(new Tooltip("Exibe as contas e suas parcelas."));
        RECEBERrbFormatoConta.setTooltip(new Tooltip("Exibe as contas e suas parcelas."));
        PAGARrbFormatoEmpresaPessoa.setTooltip(new Tooltip("Exibe as contas agrupadas por empresa/pessoa."));
        RECEBERrbFormatoPessoa.setTooltip(new Tooltip("Exibe as contas agrupadas por pessoa."));
        PAGARrbFormatoLista.setTooltip(new Tooltip("Exibe uma lista de contas."));
        RECEBERrbFormatoLista.setTooltip(new Tooltip("Exibe uma lista de contas."));
        CAIXArbFormatoDetalhado.setTooltip(new Tooltip("Exibe as movimentações em caixa de cada dia."));
        CAIXArbFormatoSimplificado.setTooltip(new Tooltip("Exibe um resumo financeiro de cada dia."));
        MaskCampos.dateField(PAGARdpDataInicial.getEditor());
        MaskCampos.dateField(PAGARdpDataFinal.getEditor());
        MaskCampos.dateField(RECEBERdpDataInicial.getEditor());
        MaskCampos.dateField(RECEBERdpDataFinal.getEditor());
        MaskCampos.dateField(CAIXAdpDataInicial.getEditor());
        MaskCampos.dateField(CAIXAdpDataFinal.getEditor());
        SelecionarEmpresaController.empresa = 0;
        SelecionarPessoaController.pessoa = 0;
        CarregarTipos();
        PAGARgrupoFormato = new ToggleGroup();        
        grupoTipos = new ToggleGroup();     
        PAGARrbFormatoConta.setToggleGroup(PAGARgrupoFormato);
        PAGARrbFormatoEmpresaPessoa.setToggleGroup(PAGARgrupoFormato);
        PAGARrbFormatoLista.setToggleGroup(PAGARgrupoFormato);
        RECEBERrbFormatoConta.setToggleGroup(PAGARgrupoFormato);
        RECEBERrbFormatoPessoa.setToggleGroup(PAGARgrupoFormato);
        RECEBERrbFormatoLista.setToggleGroup(PAGARgrupoFormato);
        
        CAIXArbFormatoDetalhado.setToggleGroup(PAGARgrupoFormato);
        CAIXArbFormatoSimplificado.setToggleGroup(PAGARgrupoFormato);
        
        CAIXArbTipoEntrada.setToggleGroup(grupoTipos);
        CAIXArbTipoRetirada.setToggleGroup(grupoTipos);
        CAIXArbTipoTodas.setToggleGroup(grupoTipos);
        
    }    

    private void CarregarTipos()
    {
        ArrayList <String> lista = new ArrayList();
        lista.add("Exibir todas as parcelas");
        lista.add("Exibir somente parcelas pagas");
        lista.add("Exibir somente parcelas vencidas");
        lista.add("Exibir parcelas que vencem hoje");
        ObservableList<String> modelo;
        modelo = FXCollections.observableArrayList(lista);
        PAGARcbTipos.setItems(modelo);
        PAGARcbTipos.getSelectionModel().select(3); //padrão exibir que vencem hoje
        
        lista.clear();
        lista.add("Exibir todas as parcelas");
        lista.add("Exibir somente parcelas recebidas");
        lista.add("Exibir somente parcelas vencidas");
        lista.add("Exibir parcelas que vencem hoje");
        modelo = FXCollections.observableArrayList(lista);
        RECEBERcbTipos.setItems(modelo);
        RECEBERcbTipos.getSelectionModel().select(3); //padrão exibir que vencem hoje
    }
    
    @FXML
    private void clkCancelar(ActionEvent event) {
        //PrincipalController.TelaRelatorioContas.close();
        
        //VOLTAR
        apMenu.setVisible(true);
        apContasPagar.setVisible(false);
        apContasReceber.setVisible(false);
        apFluxodeCaixa.setVisible(false);
        PrincipalController.TelaRelatorioContas.setTitle("Emitir Relatório Financeiro");
        
        //ESTADO ORIGINAL DAS TELAS
        SelecionarEmpresaController.empresa = 0;
        SelecionarPessoaController.pessoa = 0;
        PAGARrbFormatoConta.setDisable(false);
        PAGARrbFormatoEmpresaPessoa.setDisable(false);
        PAGARrbFormatoLista.setDisable(false);
        RECEBERrbFormatoConta.setDisable(false);
        RECEBERrbFormatoPessoa.setDisable(false);
        RECEBERrbFormatoLista.setDisable(false);
        PAGARcbTipos.getSelectionModel().select(3);
        RECEBERcbTipos.getSelectionModel().select(3);
        
        PAGARckbFiltrarPeriodo.setDisable(true);
        RECEBERckbFiltrarPeriodo.setDisable(true);
        PAGARckbFiltrarPeriodo.setSelected(false);
        RECEBERckbFiltrarPeriodo.setSelected(false);
        PAGARapPeriodo.setDisable(true);
        RECEBERapPeriodo.setDisable(true);
        PAGARdpDataInicial.setValue(null);
        RECEBERdpDataInicial.setValue(null);
        PAGARdpDataFinal.setValue(null);
        RECEBERdpDataFinal.setValue(null);
        PAGARtfPessoa.setText("");
        RECEBERtfPessoa.setText("");
        PAGARbtSelecionar.setText("SELECIONAR");
        RECEBERbtSelecionar.setText("SELECIONAR");
        
        CAIXAckbFiltrarPeriodo.setSelected(false);
        CAIXAapPeriodo.setDisable(true);
        CAIXAdpDataFinal.setValue(null);
        CAIXAdpDataInicial.setValue(null);
        CAIXAapTiposMovimentacoes.setDisable(true);
        CAIXArbTipoEntrada.setSelected(false);
        CAIXArbTipoRetirada.setSelected(false);
        CAIXArbTipoTodas.setSelected(false);
    } 
    
    private void ModelosRelatorios()
    {
        PAGARrbFormatoConta.setDisable(!PAGARtfPessoa.getText().equals(""));
        PAGARrbFormatoEmpresaPessoa.setDisable(!PAGARtfPessoa.getText().equals(""));
        PAGARrbFormatoLista.setDisable(!PAGARtfPessoa.getText().equals(""));
        RECEBERrbFormatoConta.setDisable(!RECEBERtfPessoa.getText().equals(""));
        RECEBERrbFormatoPessoa.setDisable(!RECEBERtfPessoa.getText().equals(""));
        RECEBERrbFormatoLista.setDisable(!RECEBERtfPessoa.getText().equals(""));
        
        
        if(!PAGARtfPessoa.getText().equals(""))
        {
            PAGARrbFormatoEmpresaPessoa.setDisable(false);
            PAGARrbFormatoEmpresaPessoa.setSelected(true);
        }
        
        if(!RECEBERtfPessoa.getText().equals(""))
        {
            RECEBERrbFormatoPessoa.setDisable(false);
            RECEBERrbFormatoPessoa.setSelected(true);
        }
        
    }

    
    @FXML
    private void PAGARclkFiltrarPeriodo(ActionEvent event) {
        PAGARapPeriodo.setDisable(!PAGARckbFiltrarPeriodo.isSelected());
    }
   

    @FXML
    private void PAGARclkSelecionar(ActionEvent event) throws IOException {
        if(PAGARbtSelecionar.getText().contains("SELECIONAR"))
        {
            //Escolher opção
            EscolherOpcaoController.opcao = 0; //não escolheu nenhuma opção por padrão
            Stage stage = new Stage();
            stage.setTitle("Selecionar");
            java.net.URL url = getClass().getResource("icones/siscon.png");
            stage.getIcons().add(new Image(url.toString()));
            PrincipalController.TelaEscolherOpcao = stage;
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("EscolherOpcao.fxml")));
            stage.setScene(scene); 
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);         
            stage.showAndWait();
            if(EscolherOpcaoController.opcao == 0) //não escolheu nenhuma
                return;

            if(EscolherOpcaoController.opcao == 1) //selecionar empresa
            {
                stage = new Stage();
                stage.setTitle("Selecionar Empresa");
                stage.getIcons().add(new Image(url.toString()));
                TelaSelecionarEmpresa = stage;
                scene = new Scene(FXMLLoader.load(getClass().getResource("SelecionarEmpresa.fxml")));
                stage.setScene(scene); 
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false);         
                stage.showAndWait();

                if(SelecionarEmpresaController.empresa != 0) //retorn o código da empresa, 0 se não retornou!
                { //retornou alguma empresa
                    CtrGerenciarEmpresas ctrE = new CtrGerenciarEmpresas();
                    PAGARtfPessoa.setText(ctrE.getCtrempresa().getEmpresa().getEmpresa(SelecionarEmpresaController.empresa).getNome());
                    PAGARbtSelecionar.setText("REMOVER");
                    ModelosRelatorios();

                }
            }

            if(EscolherOpcaoController.opcao == 2) //selecionar pessoa
            {
                //int pes_aux = SelecionarPessoaController.pessoa; 
                stage = new Stage();
                stage.setTitle("Selecionar Pessoa");
                stage.getIcons().add(new Image(url.toString()));
                RealizarMovimentacaoController.TelaRealizarMovimentacao = null;
                TelaSelecionarPessoa = stage;
                scene = new Scene(FXMLLoader.load(getClass().getResource("SelecionarPessoa.fxml")));
                stage.setScene(scene); 
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false);         
                stage.showAndWait();

                if(SelecionarPessoaController.pessoa != 0) //retorn o código da pessoa, 0 se não retornou!
                { //retornou alguma pessoa
                    CtrGerenciarPessoas ctr = new CtrGerenciarPessoas();
                    PAGARtfPessoa.setText(ctr.getCtrpessoa().getPessoa().getPessoa(SelecionarPessoaController.pessoa).getNome());
                    PAGARbtSelecionar.setText("REMOVER");
                    ModelosRelatorios();

                }
            }
        
        }
        else //REMOVER
        {
            SelecionarPessoaController.pessoa = 0;
            SelecionarEmpresaController.empresa = 0;
            PAGARtfPessoa.setText("");
            PAGARbtSelecionar.setText("SELECIONAR");
            ModelosRelatorios();
            
        }
    }

    @FXML
    private void PAGARclkGerar(ActionEvent event) {
        //faço as validações
        if(PAGARckbFiltrarPeriodo.isSelected() && (PAGARdpDataInicial.getValue() == null || PAGARdpDataFinal.getValue() == null || PAGARdpDataInicial.getValue().isAfter(LocalDate.now()) || PAGARdpDataInicial.getValue().isAfter(PAGARdpDataFinal.getValue())))
        { //erro
            PAGARckbFiltrarPeriodo.setTextFill(Color.RED);
            return;
        }
        PAGARckbFiltrarPeriodo.setTextFill(Color.BLACK);        
        //validei as informações
        
        String condicao = "par_status = 'A' and par_dtestorno is null";
        String subtitulo = "";
        if(PAGARcbTipos.getValue().contains("pagas"))
        {
           condicao += " and par_dtpagamento is not null";
           subtitulo += "Parcelas pagas";
        }
        if(PAGARcbTipos.getValue().contains("vencidas"))
        {
           condicao += " and par_dtpagamento is null and par_dtvencimento < '"+LocalDate.now()+"'";
           subtitulo += "Parcelas vencidas";
        }
        
        if(PAGARcbTipos.getValue().contains("hoje"))
        {
           condicao += " and par_dtpagamento is null and par_dtvencimento = '"+LocalDate.now()+"'";
           subtitulo += "Parcelas que vencem em "+Funcoes.DateToString(LocalDate.now());
        }
        
        if(PAGARckbFiltrarPeriodo.isSelected())
        {
            if(!subtitulo.equals(""))
                subtitulo += " - ";
            
            if(PAGARcbTipos.getValue().contains("todas")) //exibir todas as parcelas com vencimento no periodo definido, pagas ou não
            {
                subtitulo += "Com vencimento entre "+Funcoes.DateToString(PAGARdpDataInicial.getValue())+" a "+Funcoes.DateToString(PAGARdpDataFinal.getValue());
                condicao += " and par_dtvencimento >= '"+PAGARdpDataInicial.getValue()+"' and par_dtvencimento <= '"+PAGARdpDataFinal.getValue()+"'";
            }
            if(PAGARcbTipos.getValue().contains("pagas")) //exibir somente parcelas pagas no periodo definido para o pagamento
            {
               subtitulo += "Pagas entre "+Funcoes.DateToString(PAGARdpDataInicial.getValue())+" a "+Funcoes.DateToString(PAGARdpDataFinal.getValue());
               condicao += " and par_dtpagamento >= '"+PAGARdpDataInicial.getValue()+"' and par_dtpagamento <= '"+PAGARdpDataFinal.getValue()+"'";
            }
            if(PAGARcbTipos.getValue().contains("vencidas")) //exibir somente parcelas vencidas no periodo definido para o vencimento
            {
                subtitulo += "Vencidas entre "+Funcoes.DateToString(PAGARdpDataInicial.getValue())+" a "+Funcoes.DateToString(PAGARdpDataFinal.getValue());
                condicao += " and par_dtvencimento >= '"+PAGARdpDataInicial.getValue()+"' and par_dtvencimento <= '"+PAGARdpDataFinal.getValue()+"'";
            }
                      
        }
        
        if(SelecionarPessoaController.pessoa != 0)
        {
           if(!subtitulo.equals(""))
               subtitulo += " - ";
           
           subtitulo += "Pessoa: "+PAGARtfPessoa.getText().split(" ")[0];
           condicao += " and pes_cod = "+SelecionarPessoaController.pessoa;
        }
         
        if(SelecionarEmpresaController.empresa != 0)
        {
           if(!subtitulo.equals(""))
               subtitulo += " - ";
           subtitulo += "Empresa: "+PAGARtfPessoa.getText().split(" ")[0];
           condicao += " and emp_cod = "+SelecionarEmpresaController.empresa;
        }
        
        
        
        //TIPOS de Relatórios
        String query = "";        
        if(PAGARrbFormatoConta.isSelected())
        {
            query = "select * from parcelapag INNER JOIN contapagar ON contapagar.cp_cod = parcelapag.cp_cod " +
                    "LEFT JOIN pessoa ON pessoa.pes_cod = contapagar.pes_cod LEFT JOIN empresa ON empresa.emp_cod = contapagar.emp_cod";
            query += " WHERE "+condicao;
            Funcoes.gerarRelatorio(query,"relatorios/contas/Rel_ContasaPagarConta.jasper", "Relatório de Contas a Pagar (Conta X Parcelas)", subtitulo);
        }
        else
            if(PAGARrbFormatoEmpresaPessoa.isSelected())
            {
                query = "select * from " +
                        "(" +
                        "select cp_cod, cp_numparcelas, cp_descricao, cod_empresa, cod_pessoa, nome, cpf from contapagar INNER JOIN (select 0 as cod_empresa, pes_cod as cod_pessoa, pes_nome as nome, pes_cpf as cpf from pessoa) P ON contapagar.pes_cod = P.cod_pessoa" +
                        " UNION " +
                        "select contapagar.cp_cod, cp_numparcelas, cp_descricao, E.emp_cod, cod_pessoa, emp_nome, emp_cnpj from contapagar INNER JOIN (select emp_cod, 0 as cod_pessoa, emp_nome, emp_cnpj from empresa) E ON contapagar.emp_cod = E.emp_cod" +
                        ") T" +
                        " INNER JOIN parcelapag ON T.cp_cod = parcelapag.cp_cod";
                if(condicao.contains("emp_cod"))
                   condicao = condicao.replace("emp_cod", "cod_empresa");
                if(condicao.contains("pes_cod"))
                   condicao = condicao.replace("pes_cod", "cod_pessoa");
                query += " WHERE "+condicao;
                Funcoes.gerarRelatorio(query,"relatorios/contas/Rel_ContasaPagarEmpresaPessoa.jasper", "Relatório de Contas a Pagar (Empresa/Pessoa X Parcelas)", subtitulo);
            }
            else
            {
                query = "select * from parcelapag INNER JOIN contapagar ON contapagar.cp_cod = parcelapag.cp_cod " +
                        "LEFT JOIN pessoa ON pessoa.pes_cod = contapagar.pes_cod LEFT JOIN empresa ON empresa.emp_cod = contapagar.emp_cod";
                query += " WHERE "+condicao;
                Funcoes.gerarRelatorio(query,"relatorios/contas/Rel_ContasaPagar.jasper", "Relatório de Contas a Pagar", subtitulo);
            }
        
    }

    @FXML
    private void PAGARclkcbTipos(ActionEvent event) {
        if(PAGARcbTipos.getValue().contains("hoje"))
        {
            PAGARckbFiltrarPeriodo.setSelected(false);
            PAGARckbFiltrarPeriodo.setDisable(true);  
            PAGARapPeriodo.setDisable(true);
        }
        else
        {
            PAGARckbFiltrarPeriodo.setDisable(false);            
        }        
    }

    @FXML
    private void RECEBERclkcbTipos(ActionEvent event) {
        if(RECEBERcbTipos.getValue().contains("hoje"))
        {
            RECEBERckbFiltrarPeriodo.setSelected(false);
            RECEBERckbFiltrarPeriodo.setDisable(true);  
            RECEBERapPeriodo.setDisable(true);
        }
        else
        {
            RECEBERckbFiltrarPeriodo.setDisable(false);            
        }   
    }

    @FXML
    private void RECEBERclkFiltrarPeriodo(ActionEvent event) {
        RECEBERapPeriodo.setDisable(!RECEBERckbFiltrarPeriodo.isSelected());
    }

    @FXML
    private void RECEBERclkSelecionar(ActionEvent event) throws IOException {
        if(RECEBERbtSelecionar.getText().contains("SELECIONAR"))
        {           
            Stage stage = new Stage();            
            stage.setTitle("Selecionar Pessoa");
            java.net.URL url = getClass().getResource("icones/siscon.png");
            stage.getIcons().add(new Image(url.toString()));
            RealizarMovimentacaoController.TelaRealizarMovimentacao = null;
            TelaSelecionarPessoa = stage;
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("SelecionarPessoa.fxml")));
            stage.setScene(scene); 
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);         
            stage.showAndWait();

            if(SelecionarPessoaController.pessoa != 0) //retorn o código da pessoa, 0 se não retornou!
            { //retornou alguma pessoa
                CtrGerenciarPessoas ctr = new CtrGerenciarPessoas();
                RECEBERtfPessoa.setText(ctr.getCtrpessoa().getPessoa().getPessoa(SelecionarPessoaController.pessoa).getNome());
                RECEBERbtSelecionar.setText("REMOVER");
                ModelosRelatorios();

            }
        
        }
        else //REMOVER
        {
            SelecionarPessoaController.pessoa = 0;
            RECEBERtfPessoa.setText("");
            RECEBERbtSelecionar.setText("SELECIONAR");
            ModelosRelatorios();
            
        }
    }

    @FXML
    private void RECEBERclkGerar(ActionEvent event) {
        //faço as validações
        if(RECEBERckbFiltrarPeriodo.isSelected() && (RECEBERdpDataInicial.getValue() == null || RECEBERdpDataFinal.getValue() == null || RECEBERdpDataInicial.getValue().isAfter(LocalDate.now()) || RECEBERdpDataInicial.getValue().isAfter(RECEBERdpDataFinal.getValue())))
        { //erro
            RECEBERckbFiltrarPeriodo.setTextFill(Color.RED);
            return;
        }
        RECEBERckbFiltrarPeriodo.setTextFill(Color.BLACK);        
        //validei as informações
        
        String condicao = "par_status = 'A' and par_dtestorno is null";
        String subtitulo = "";
        if(RECEBERcbTipos.getValue().contains("recebidas"))
        {
           condicao += " and par_dtpagamento is not null";
           subtitulo += "Parcelas recebidas";
        }
        if(RECEBERcbTipos.getValue().contains("vencidas"))
        {
           condicao += " and par_dtpagamento is null and par_dtvencimento < '"+LocalDate.now()+"'";
           subtitulo += "Parcelas vencidas";
        }
        
        if(RECEBERcbTipos.getValue().contains("hoje"))
        {
           condicao += " and par_dtpagamento is null and par_dtvencimento = '"+LocalDate.now()+"'";
           subtitulo += "Parcelas que vencem em "+Funcoes.DateToString(LocalDate.now());
        }
        
        if(RECEBERckbFiltrarPeriodo.isSelected())
        {
            if(!subtitulo.equals(""))
                subtitulo += " - ";
            
            if(RECEBERcbTipos.getValue().contains("todas")) //exibir todas as parcelas com vencimento no periodo definido, pagas ou não
            {
                subtitulo += "Com vencimento entre "+Funcoes.DateToString(RECEBERdpDataInicial.getValue())+" a "+Funcoes.DateToString(RECEBERdpDataFinal.getValue());
                condicao += " and par_dtvencimento >= '"+RECEBERdpDataInicial.getValue()+"' and par_dtvencimento <= '"+RECEBERdpDataFinal.getValue()+"'";
            }
            if(RECEBERcbTipos.getValue().contains("recebidas")) //exibir somente parcelas pagas no periodo definido para o pagamento
            {
               subtitulo += "Recebidas entre "+Funcoes.DateToString(RECEBERdpDataInicial.getValue())+" a "+Funcoes.DateToString(RECEBERdpDataFinal.getValue());
               condicao += " and par_dtpagamento >= '"+RECEBERdpDataInicial.getValue()+"' and par_dtpagamento <= '"+RECEBERdpDataFinal.getValue()+"'";
            }
            if(RECEBERcbTipos.getValue().contains("vencidas")) //exibir somente parcelas vencidas no periodo definido para o vencimento
            {
                subtitulo += "Vencidas entre "+Funcoes.DateToString(RECEBERdpDataInicial.getValue())+" a "+Funcoes.DateToString(RECEBERdpDataFinal.getValue());
                condicao += " and par_dtvencimento >= '"+RECEBERdpDataInicial.getValue()+"' and par_dtvencimento <= '"+RECEBERdpDataFinal.getValue()+"'";
            }
                      
        }
        
        if(SelecionarPessoaController.pessoa != 0)
        {
           if(!subtitulo.equals(""))
               subtitulo += " - ";
           
           subtitulo += "Pessoa: "+RECEBERtfPessoa.getText().split(" ")[0];
           condicao += " and contareceber.pes_cod = "+SelecionarPessoaController.pessoa;
        }  
        
        //TIPOS de Relatórios
        String query = "";        
        if(RECEBERrbFormatoConta.isSelected())
        {
            query = "select * from parcelarec INNER JOIN contareceber ON contareceber.cr_cod = parcelarec.cr_cod " +
                    "LEFT JOIN pessoa ON pessoa.pes_cod = contareceber.pes_cod ";
            query += " WHERE "+condicao;
            Funcoes.gerarRelatorio(query,"relatorios/contas/Rel_ContasaReceberConta.jasper", "Relatório de Contas a Receber (Conta X Parcelas)", subtitulo);
        }
        else
            if(RECEBERrbFormatoPessoa.isSelected())
            {
                query = "select * from parcelarec INNER JOIN contareceber ON contareceber.cr_cod = parcelarec.cr_cod " +
                        "INNER JOIN Pessoa ON pessoa.pes_cod = contareceber.pes_cod";
                query += " WHERE "+condicao;
                Funcoes.gerarRelatorio(query,"relatorios/contas/Rel_ContasaReceberPessoa.jasper", "Relatório de Contas a Receber (Pessoa X Parcelas)", subtitulo);
            }
            else
            {
                query = "select * from parcelarec INNER JOIN contareceber ON contareceber.cr_cod = parcelarec.cr_cod " +
                        "LEFT JOIN pessoa ON pessoa.pes_cod = contareceber.pes_cod ";
                query += " WHERE "+condicao;
                Funcoes.gerarRelatorio(query,"relatorios/contas/Rel_ContasaReceber.jasper", "Relatório de Contas a Receber", subtitulo);
            }
        
    }

    @FXML
    private void clkContasPagar(ActionEvent event) {
        apMenu.setVisible(false);
        apContasPagar.setVisible(true);
        PAGARrbFormatoLista.setSelected(true);
        PrincipalController.TelaRelatorioContas.setTitle("Emitir Relatório de Contas a Pagar");
    }

    @FXML
    private void clkContasReceber(ActionEvent event) {
        apMenu.setVisible(false);
        apContasReceber.setVisible(true);
        RECEBERrbFormatoLista.setSelected(true);
        PrincipalController.TelaRelatorioContas.setTitle("Emitir Relatório de Contas a Receber");
    }

    @FXML
    private void CAIXAclkFiltrarPeriodo(ActionEvent event) {
        CAIXAapPeriodo.setDisable(!CAIXAckbFiltrarPeriodo.isSelected());
        
    }

    @FXML
    private void clkFluxodeCaixa(ActionEvent event) {
        apMenu.setVisible(false);
        apFluxodeCaixa.setVisible(true);
        CAIXArbFormatoSimplificado.setSelected(true);
        PrincipalController.TelaRelatorioContas.setTitle("Emitir Relatório de Fluxo de Caixa");
    }

    @FXML
    private void CAIXAclkDetalhado(ActionEvent event) {
        CAIXAapTiposMovimentacoes.setDisable(!CAIXArbFormatoDetalhado.isSelected());
        if(!CAIXArbTipoEntrada.isSelected() && !CAIXArbTipoRetirada.isSelected() && !CAIXArbTipoTodas.isSelected())
            CAIXArbTipoTodas.setSelected(true);
    }

    @FXML
    private void CAIXAclkSimplificado(ActionEvent event) {
        CAIXAapTiposMovimentacoes.setDisable(!CAIXArbFormatoDetalhado.isSelected());
    }

    @FXML
    private void CAIXAclkGerar(ActionEvent event) {
        if(CAIXAckbFiltrarPeriodo.isSelected() && (CAIXAdpDataInicial.getValue() == null || CAIXAdpDataFinal.getValue() == null || CAIXAdpDataInicial.getValue().isAfter(LocalDate.now()) || CAIXAdpDataInicial.getValue().isAfter(CAIXAdpDataFinal.getValue())))
        { //erro
            CAIXAckbFiltrarPeriodo.setTextFill(Color.RED);
            return;
        }
        CAIXAckbFiltrarPeriodo.setTextFill(Color.BLACK);
        //validei
        
        String condicao = "";
        String subtitulo = "";
        String query = "";
        String relatorio = "";
        
        if(CAIXArbFormatoDetalhado.isSelected())
        {
            
            if(CAIXArbTipoEntrada.isSelected())
            {
                query = "select parcelarec.pr_seq as seq, mov_horario, cast(mov_horario as date) as horario, cr_descricao as descricao, par_numero || '/' || cr_numparcelas as parcela, pes_nome, par_valorpago*1 as valor, par_valorpago as entrada,  0.0 as retirada from movcaixa " +
                        "INNER JOIN parcelarec ON movcaixa.pr_seq = parcelarec.pr_seq INNER JOIN contareceber ON contareceber.cr_cod = parcelarec.cr_cod INNER JOIN caixa ON caixa.cai_cod = movcaixa.cai_cod INNER JOIN pessoa ON pessoa.pes_cod = caixa.pes_cod" +
                        " $$ UNION " +
                        "select acertocaixa.ac_cod, mov_horario, cast(mov_horario as date), ac_descricao, '- - - - - - -', pes_nome, ac_valor*1, ac_valor, 0.0 from movcaixa " +
                        "INNER JOIN acertocaixa ON movcaixa.ac_cod = acertocaixa.ac_cod INNER JOIN caixa ON caixa.cai_cod = movcaixa.cai_cod INNER JOIN pessoa ON pessoa.pes_cod = caixa.pes_cod " +
                        "WHERE ac_tipo = 'A'";
                relatorio = "relatorios/caixa/Rel_FinanceiroDiaTipo.jasper";
                subtitulo += "Movimentações de entrada";
                
            }
            
            if(CAIXArbTipoRetirada.isSelected())
            {
                query = "select parcelapag.pp_seq as seq, mov_horario, cast(mov_horario as date) as horario, cp_descricao as descricao, par_numero || '/' || cp_numparcelas as parcela, pes_nome, par_valorpago*-1 as valor, 0.0 as entrada, par_valorpago as retirada from movcaixa " +
                        "INNER JOIN parcelapag ON movcaixa.pp_seq = parcelapag.pp_seq INNER JOIN contapagar ON contapagar.cp_cod = parcelapag.cp_cod INNER JOIN caixa ON caixa.cai_cod = movcaixa.cai_cod INNER JOIN pessoa ON pessoa.pes_cod = caixa.pes_cod" +
                        " WHERE car_cod is null $$ " +
                        "UNION " +
                        "select acertocaixa.ac_cod, mov_horario, cast(mov_horario as date), ac_descricao, '- - - - - - -', pes_nome, ac_valor*-1, 0.0, ac_valor from movcaixa " +
                        "INNER JOIN acertocaixa ON movcaixa.ac_cod = acertocaixa.ac_cod INNER JOIN caixa ON caixa.cai_cod = movcaixa.cai_cod INNER JOIN pessoa ON pessoa.pes_cod = caixa.pes_cod " +
                        "WHERE ac_tipo = 'S'";
                relatorio = "relatorios/caixa/Rel_FinanceiroDiaTipo.jasper";
                subtitulo += "Movimentações de retirada";
                
            }
            
            if(CAIXArbTipoTodas.isSelected())
            {
                query = "select * from " +
                        "(select parcelapag.pp_seq as seq, mov_horario, cast(mov_horario as date) as horario, cp_descricao as descricao, par_numero || '/' || cp_numparcelas as parcela, pes_nome, par_valorpago*-1 as valor, 0.0 as entrada, par_valorpago as retirada from movcaixa " +
                        "INNER JOIN parcelapag ON movcaixa.pp_seq = parcelapag.pp_seq INNER JOIN contapagar ON contapagar.cp_cod = parcelapag.cp_cod INNER JOIN caixa ON caixa.cai_cod = movcaixa.cai_cod INNER JOIN pessoa ON pessoa.pes_cod = caixa.pes_cod " +
                        "WHERE car_cod is null " +
                        "UNION " +
                        "select acertocaixa.ac_cod, mov_horario, cast(mov_horario as date), ac_descricao, '- - - - - - -', pes_nome, ac_valor*-1, 0.0, ac_valor from movcaixa " +
                        "INNER JOIN acertocaixa ON movcaixa.ac_cod = acertocaixa.ac_cod INNER JOIN caixa ON caixa.cai_cod = movcaixa.cai_cod INNER JOIN pessoa ON pessoa.pes_cod = caixa.pes_cod " +
                        "WHERE ac_tipo = 'S' " +
                        "UNION " +
                        "select parcelarec.pr_seq as seq, mov_horario, cast(mov_horario as date), cr_descricao, par_numero || '/' || cr_numparcelas, pes_nome, par_valorpago*1, par_valorpago as entrada, 0.0 as retirada from movcaixa " +
                        "INNER JOIN parcelarec ON movcaixa.pr_seq = parcelarec.pr_seq INNER JOIN contareceber ON contareceber.cr_cod = parcelarec.cr_cod INNER JOIN caixa ON caixa.cai_cod = movcaixa.cai_cod INNER JOIN pessoa ON pessoa.pes_cod = caixa.pes_cod " +
                        "UNION " +
                        "select acertocaixa.ac_cod, mov_horario, cast(mov_horario as date), ac_descricao, '- - - - - - -', pes_nome, ac_valor*1, ac_valor, 0.0 from movcaixa " +
                        "INNER JOIN acertocaixa ON movcaixa.ac_cod = acertocaixa.ac_cod INNER JOIN caixa ON caixa.cai_cod = movcaixa.cai_cod INNER JOIN pessoa ON pessoa.pes_cod = caixa.pes_cod " +
                        "WHERE ac_tipo = 'A') T1" +
                        " INNER JOIN " +
                        "(select * from(SELECT max(cai_cod) As UltimoRegistroDia " +
                        "FROM caixa GROUP BY cai_data) Reg " +
                        "INNER JOIN caixa ON caixa.cai_cod = Reg.UltimoRegistroDia) T2 ON T1.horario = T2.cai_data";
                relatorio = "relatorios/caixa/Rel_FinanceiroDia.jasper";                
                
            }
            
            if(CAIXAckbFiltrarPeriodo.isSelected())
            {
                if(!subtitulo.equals(""))
                    subtitulo += " - ";
                subtitulo += "Período: "+Funcoes.DateToString(CAIXAdpDataInicial.getValue())+" a "+Funcoes.DateToString(CAIXAdpDataFinal.getValue());
                
                condicao += "mov_horario >= '"+CAIXAdpDataInicial.getValue()+"' and mov_horario < '"+CAIXAdpDataFinal.getValue().plusDays(1)+"'";
            }
            
            if(!condicao.equals(""))
            {
                if(CAIXArbTipoEntrada.isSelected() || CAIXArbTipoRetirada.isSelected())
                {
                   query += " and "+condicao;
                   if(CAIXArbTipoEntrada.isSelected())
                       query = query.replace("$$","WHERE "+condicao);
                   if(CAIXArbTipoRetirada.isSelected())
                       query = query.replace("$$","and "+condicao);
                }
                else
                   query += " WHERE "+condicao;
            }
            else
            {
                query = query.replace("$$","");
            }
            Funcoes.gerarRelatorio(query, relatorio, "Relatório de Fluxo de Caixa (Detalhado)", subtitulo);
            
        }
        else //FORMATO SIMPLIFICADO
        {
            query = "select * from(SELECT max(cai_cod) As UltimoRegistroDia " +
                    "FROM caixa GROUP BY cai_data) Reg " +
                    "INNER JOIN caixa ON caixa.cai_cod = Reg.UltimoRegistroDia " +
                    "WHERE (cai_entrada > 0 or cai_saida > 0)";
            
            
            if(CAIXAckbFiltrarPeriodo.isSelected())
            {
                subtitulo += "Período: "+Funcoes.DateToString(CAIXAdpDataInicial.getValue())+" a "+Funcoes.DateToString(CAIXAdpDataFinal.getValue());
                condicao += "cai_data >= '"+CAIXAdpDataInicial.getValue()+"' and cai_data <= '"+CAIXAdpDataFinal.getValue()+"'";
            }
            
            if(!condicao.equals(""))
                query += " and "+condicao;
            
            Funcoes.gerarRelatorio(query, "relatorios/caixa/Rel_FinanceiroCaixa.jasper", "Relatório de Fluxo de Caixa (Simplificado)", subtitulo);
            
        }
        
        
        
        
    }
    
}
