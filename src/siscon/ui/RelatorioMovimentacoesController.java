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
import siscon.controladorasinterface.CtrGerenciarPessoas;
import siscon.controladorasinterface.CtrGerenciarVeiculos;
import siscon.util.Funcoes;
import siscon.util.MaskCampos;

/**
 * FXML Controller class
 *
 * @author Marcos Vinícius
 */
public class RelatorioMovimentacoesController implements Initializable {

    @FXML
    private ComboBox<String> cbTipoMovimentacoes;
    @FXML
    private CheckBox ckbFiltrarPeriodo;
    @FXML
    private AnchorPane apPeriodo;
    @FXML
    private RadioButton rbHoje;
    @FXML
    private RadioButton rbDefinir;
    @FXML
    private DatePicker dpDataInicial;
    @FXML
    private DatePicker dpDataFinal;
    @FXML
    private TextField tfPessoa;
    @FXML
    private Button btSelecionar;
    @FXML
    private RadioButton rbFormatoCondutor;
    @FXML
    private RadioButton rbFormatoCondutorPassageiro;
    @FXML
    private RadioButton rbFormatoPadrao;
    private ToggleGroup grupoPeriodo;
    private ToggleGroup grupoFormato;
    @FXML
    private RadioButton rbFormatoVeiculoCondutor;
    @FXML
    private RadioButton rbFormatoVeiculoCondutorPassageiro;
    @FXML
    private RadioButton rbFormatoMovimentacao;
    public static Stage TelaSelecionarVeiculo = null;
    @FXML
    private TextField tfVeiculo;
    @FXML
    private Button btSelecionarVeiculo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rbFormatoMovimentacao.setTooltip(new Tooltip("Exibe as movimentações, indicando condutor e passageiros."));
        rbFormatoCondutor.setTooltip(new Tooltip("Exibe as movimentações de cada pessoa, sendo condutor."));
        rbFormatoCondutorPassageiro.setTooltip(new Tooltip("Exibe as movimentações de cada pessoa, sendo condutor ou passageiro."));
        rbFormatoVeiculoCondutor.setTooltip(new Tooltip("Exibe as movimentações de cada veículo, indicando os condutores."));
        rbFormatoVeiculoCondutorPassageiro.setTooltip(new Tooltip("Exibe as movimentações de cada veículo, indicando os condutores e passageiros."));
        rbFormatoPadrao.setTooltip(new Tooltip("Exibe uma lista de movimentações."));
        MaskCampos.dateField(dpDataInicial.getEditor());
        MaskCampos.dateField(dpDataFinal.getEditor());
        CarregarTipos();
        SelecionarPessoaController.pessoa = 0;
        SelecionarVeiculoController.veiculo = 0;
        grupoPeriodo = new ToggleGroup();
        rbDefinir.setToggleGroup(grupoPeriodo);
        rbHoje.setToggleGroup(grupoPeriodo);
        
        grupoFormato = new ToggleGroup();
        rbFormatoCondutor.setToggleGroup(grupoFormato);
        rbFormatoCondutorPassageiro.setToggleGroup(grupoFormato);
        rbFormatoPadrao.setToggleGroup(grupoFormato);
        rbFormatoVeiculoCondutor.setToggleGroup(grupoFormato);
        rbFormatoVeiculoCondutorPassageiro.setToggleGroup(grupoFormato);
        rbFormatoMovimentacao.setToggleGroup(grupoFormato);
        
    }    

    private void CarregarTipos()
    {
        ArrayList <String> lista = new ArrayList();
        lista.add("Exibir todas as movimentações");
        lista.add("Exibir somente movimentações de entrada");
        lista.add("Exibir somente movimentações de saída");
        ObservableList<String> modelo;
        modelo = FXCollections.observableArrayList(lista);
        cbTipoMovimentacoes.setItems(modelo);
        cbTipoMovimentacoes.getSelectionModel().select(0); //padrão exibir todas
    }
    
    @FXML
    private void clkSelecionar(ActionEvent event) throws IOException {
        if(btSelecionar.getText().equals("SELECIONAR"))
        {
            SelecionarPessoaController.pessoa = 0;
            Stage stage = new Stage();
            stage.setTitle("Selecionar Pessoa");
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
            }
        }
        else //remover
        {
            
            SelecionarPessoaController.pessoa = 0;
            tfPessoa.setText("");
            btSelecionar.setText("SELECIONAR");
            ModelosRelatorios();
        }
    }

    @FXML
    private void clkFiltrarPeriodo(ActionEvent event) {
        apPeriodo.setDisable(!ckbFiltrarPeriodo.isSelected());
        
        if(!rbHoje.isSelected() && !rbDefinir.isSelected())
            rbHoje.setSelected(true);
    }
    
    @FXML
    private void clkDefinirPeriodo(ActionEvent event) {
         dpDataInicial.setDisable(false);
         dpDataFinal.setDisable(false);         
    }
    
    @FXML
    private void clkHoje(ActionEvent event) {
        dpDataInicial.setDisable(true);
        dpDataFinal.setDisable(true);   
    }
    
    @FXML
    private void clkCancelar(ActionEvent event) {
        PrincipalController.TelaRelatorioMovimentacoes.close();
    }
 
    @FXML
    private void clkGerar(ActionEvent event) {
        
        //faço as validações
        if(ckbFiltrarPeriodo.isSelected() && rbDefinir.isSelected() && (dpDataInicial.getValue() == null || dpDataFinal.getValue() == null || dpDataInicial.getValue().isAfter(LocalDate.now()) || dpDataInicial.getValue().isAfter(dpDataFinal.getValue())))
        { //erro
            ckbFiltrarPeriodo.setTextFill(Color.RED);
            return;
        }
        ckbFiltrarPeriodo.setTextFill(Color.BLACK);        
        //validei as informações
        
        String condicao = "movimentacao.mov_cod is not null";
        String subtitulo = "";
        if(cbTipoMovimentacoes.getValue().contains("entrada"))
        {
           condicao += " and mov_tipo = 'E'";
           subtitulo += "Movimentações de entrada";
        }
        if(cbTipoMovimentacoes.getValue().contains("saída"))
        {
           condicao += " and mov_tipo = 'S'";
           subtitulo += "Movimentações de saída";
        }
        
        if(ckbFiltrarPeriodo.isSelected())
        {
            if(!subtitulo.equals(""))
                subtitulo += " - ";
            
            if(rbHoje.isSelected())
            {
                subtitulo += "Dia: "+Funcoes.DateToString(LocalDate.now());
                if(!condicao.equals(""))
                   condicao += " and ";
                condicao += "mov_data >= '"+LocalDate.now()+"' and mov_data < '"+LocalDate.now().plusDays(1)+"'";
            }
            else //por periodo
            {
                subtitulo += "Período: "+Funcoes.DateToString(dpDataInicial.getValue())+" a "+Funcoes.DateToString(dpDataFinal.getValue());
                if(!condicao.equals(""))
                   condicao += " and ";
                condicao += "mov_data >= '"+dpDataInicial.getValue()+"' and mov_data < '"+dpDataFinal.getValue().plusDays(1)+"'";
                
            }
        }
        
        if(SelecionarPessoaController.pessoa != 0)
        {
            if(!subtitulo.equals(""))
                subtitulo += " - ";
            subtitulo += "Pessoa: "+tfPessoa.getText().split(" ")[0];
            if(!condicao.equals(""))
               condicao += " and ";
            condicao += "pessoa.pes_cod = "+SelecionarPessoaController.pessoa;
        }
        
        if(SelecionarVeiculoController.veiculo != 0)
        {
            if(!subtitulo.equals(""))
                subtitulo += " - ";
            subtitulo += "Veículo: "+tfVeiculo.getText();
            if(!condicao.equals(""))
               condicao += " and ";
            condicao += "veiculo.vei_cod = "+SelecionarVeiculoController.veiculo;
        }
        
        //TIPOS de Relatórios
        String query = "";        
        if(rbFormatoCondutor.isSelected())
        {
            query = "select pessoa.pes_cod, pes_cpf, pes_nome, mov_cod, mov_data, vei_modelo, vei_placa, veiculo.vei_cod, veiculo.mar_cod, veiculo.cor_cod, mov_tipo, 'C' as forma, mar_nome, cor_nome from pessoa INNER JOIN movimentacao ON pessoa.pes_cod = movimentacao.pes_cod" +
                     " FULL JOIN veiculo ON veiculo.vei_cod = movimentacao.vei_cod" +
                     " LEFT JOIN marca ON veiculo.mar_cod = marca.mar_cod LEFT JOIN cor ON veiculo.cor_cod = cor.cor_cod";
             if(!condicao.equals(""))
                 query += " WHERE "+condicao;
             query += " ORDER BY pes_nome, mov_data, forma";
             Funcoes.gerarRelatorio(query,"relatorios/movimentacoes/Rel_MovimentacoesPessoa.jasper", "Relatório de Movimentações por Pessoa (Condutor)", subtitulo);
        }
        else
          if(rbFormatoCondutorPassageiro.isSelected())
          {
             query = "select * from (select pessoa.pes_cod, mov_cod, 'C' as forma from pessoa INNER JOIN movimentacao ON pessoa.pes_cod = movimentacao.pes_cod" +
                     " UNION " +
                     "select pessoa.pes_cod, mov_cod, 'P' as forma from pessoa INNER JOIN movimentacaopassageiros ON pessoa.pes_cod = movimentacaopassageiros.pes_cod)" +
                     " T INNER JOIN movimentacao ON T.mov_cod = movimentacao.mov_cod INNER JOIN pessoa ON T.pes_cod = pessoa.pes_cod FULL JOIN veiculo ON veiculo.vei_cod = movimentacao.vei_cod" +
                     " LEFT JOIN marca ON veiculo.mar_cod = marca.mar_cod LEFT JOIN cor ON veiculo.cor_cod = cor.cor_cod";
             if(!condicao.equals(""))
                 query += " WHERE "+condicao;
             query += " ORDER BY pes_nome, mov_data";
             Funcoes.gerarRelatorio(query,"relatorios/movimentacoes/Rel_MovimentacoesPessoa.jasper", "Relatório de Movimentações por Pessoa (Condutor X Passageiro)",subtitulo);
          }
          else
            if(rbFormatoVeiculoCondutor.isSelected())
            {
               query = "select pessoa.pes_cod, pes_cpf, pes_nome, mov_cod, mov_data, vei_modelo, vei_placa, veiculo.vei_cod, veiculo.mar_cod, veiculo.cor_cod, mov_tipo, 'C' as forma, mar_nome, cor_nome from pessoa INNER JOIN movimentacao ON pessoa.pes_cod = movimentacao.pes_cod" +
                       " FULL JOIN veiculo ON veiculo.vei_cod = movimentacao.vei_cod" +
                       " LEFT JOIN marca ON veiculo.mar_cod = marca.mar_cod LEFT JOIN cor ON veiculo.cor_cod = cor.cor_cod";
               if(!condicao.equals(""))
                   query += " WHERE "+condicao;
               query += " ORDER BY mov_data, forma, pes_nome";
               Funcoes.gerarRelatorio(query,"relatorios/movimentacoes/Rel_MovimentacoesVeiculo.jasper", "Relatório de Movimentações por Veículo (Condutor)",subtitulo);
            }
            else
                if(rbFormatoVeiculoCondutorPassageiro.isSelected())
                {
                   query = "select * from (select pessoa.pes_cod, mov_cod, 'C' as forma from pessoa INNER JOIN movimentacao ON pessoa.pes_cod = movimentacao.pes_cod" +
                           " UNION " +
                           "select pessoa.pes_cod, mov_cod, 'P' as forma from pessoa INNER JOIN movimentacaopassageiros ON pessoa.pes_cod = movimentacaopassageiros.pes_cod)" +
                           " T INNER JOIN movimentacao ON T.mov_cod = movimentacao.mov_cod INNER JOIN pessoa ON T.pes_cod = pessoa.pes_cod FULL JOIN veiculo ON veiculo.vei_cod = movimentacao.vei_cod" +
                           " LEFT JOIN marca ON veiculo.mar_cod = marca.mar_cod LEFT JOIN cor ON veiculo.cor_cod = cor.cor_cod";
                   if(!condicao.equals(""))
                       query += " WHERE "+condicao;
                   query += " ORDER BY mov_data, forma, pes_nome";
                   Funcoes.gerarRelatorio(query,"relatorios/movimentacoes/Rel_MovimentacoesVeiculo.jasper", "Relatório de Movimentações por Veículo (Condutor X Passageiro)",subtitulo);
                }
                else
                    if(rbFormatoMovimentacao.isSelected())
                    {
                       query = "select * from (select pessoa.pes_cod, mov_cod,'C' as condutor from pessoa INNER JOIN movimentacao ON pessoa.pes_cod = movimentacao.pes_cod" +
                               " UNION " +
                               "select pessoa.pes_cod, mov_cod,'P' as condutor from pessoa INNER JOIN movimentacaopassageiros ON pessoa.pes_cod = movimentacaopassageiros.pes_cod)" +
                               " T INNER JOIN movimentacao ON T.mov_cod = movimentacao.mov_cod INNER JOIN pessoa ON T.pes_cod = pessoa.pes_cod FULL JOIN veiculo ON veiculo.vei_cod = movimentacao.vei_cod" +
                               " LEFT JOIN marca ON veiculo.mar_cod = marca.mar_cod LEFT JOIN cor ON veiculo.cor_cod = cor.cor_cod";
                       if(!condicao.equals(""))
                           query += " WHERE "+condicao;
                       query += " ORDER BY mov_data, condutor, pes_nome";
                       Funcoes.gerarRelatorio(query,"relatorios/movimentacoes/Rel_MovimentacoesMovimentacao.jasper", "Relatório de Movimentações (Condutor X Passageiro)",subtitulo);
                    }
                    else //formato padrão
                    {
                        query = "select * from pessoa INNER JOIN movimentacao ON pessoa.pes_cod = movimentacao.pes_cod" +
                               " FULL JOIN veiculo ON veiculo.vei_cod = movimentacao.vei_cod" +
                               " LEFT JOIN marca ON veiculo.mar_cod = marca.mar_cod LEFT JOIN cor ON veiculo.cor_cod = cor.cor_cod";
                       if(!condicao.equals(""))
                           query += " WHERE "+condicao;
                       query += " ORDER BY mov_data";
                       Funcoes.gerarRelatorio(query,"relatorios/movimentacoes/Rel_MovimentacoesPadrao.jasper", "Relatório de Movimentações",subtitulo);
                    }
        
        
        //gero o código sql com base nos filtros estabelecidos pelo usuário
    }
    
    private void ModelosRelatorios()
    {
        rbFormatoCondutor.setDisable(!tfVeiculo.getText().equals("") || !tfPessoa.getText().equals(""));
        rbFormatoCondutorPassageiro.setDisable(!tfVeiculo.getText().equals("") || !tfPessoa.getText().equals(""));
        rbFormatoVeiculoCondutor.setDisable(!tfVeiculo.getText().equals("") || !tfPessoa.getText().equals(""));
        rbFormatoVeiculoCondutorPassageiro.setDisable(!tfVeiculo.getText().equals("") || !tfPessoa.getText().equals(""));
        rbFormatoPadrao.setDisable(!tfVeiculo.getText().equals("") || !tfPessoa.getText().equals(""));
        rbFormatoMovimentacao.setDisable(!tfVeiculo.getText().equals("") || !tfPessoa.getText().equals(""));
        
        if(!tfVeiculo.getText().equals(""))
        {
            rbFormatoVeiculoCondutor.setDisable(false);
            rbFormatoVeiculoCondutorPassageiro.setDisable(false); 
            if(!rbFormatoVeiculoCondutorPassageiro.isSelected() && tfPessoa.getText().equals(""))
               rbFormatoVeiculoCondutor.setSelected(true);
        }

        if(!tfPessoa.getText().equals(""))
        {
            rbFormatoCondutor.setDisable(false);
            rbFormatoCondutorPassageiro.setDisable(false); 
            if(!rbFormatoCondutorPassageiro.isSelected())
               rbFormatoCondutor.setSelected(true);
        }
        
    }

    @FXML
    private void clkSelecionarVeiculo(ActionEvent event) throws IOException {
        if(btSelecionarVeiculo.getText().equals("SELECIONAR"))
        {
            SelecionarVeiculoController.veiculo = 0;
            Stage stage = new Stage();
            stage.setTitle("Selecionar Veículo");
            java.net.URL url = getClass().getResource("icones/siscon.png");
            stage.getIcons().add(new Image(url.toString()));
            TelaSelecionarVeiculo = stage;
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("SelecionarVeiculo.fxml")));
            stage.setScene(scene); 
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);         
            stage.showAndWait();
            
            if(SelecionarVeiculoController.veiculo != 0) //retorn o código da pessoa, 0 se não retornou!
            { 
                CtrGerenciarVeiculos ctr = new CtrGerenciarVeiculos();
                tfVeiculo.setText(ctr.getCtrveiculo().getVeiculo().getVeiculo(SelecionarVeiculoController.veiculo).getMarca()+" "+ctr.getCtrveiculo().getVeiculo().getVeiculo(SelecionarVeiculoController.veiculo).getModelo()+" "+ctr.getCtrveiculo().getVeiculo().getVeiculo(SelecionarVeiculoController.veiculo).getCor()+" ("+ctr.getCtrveiculo().getVeiculo().getVeiculo(SelecionarVeiculoController.veiculo).getPlaca()+")");
                btSelecionarVeiculo.setText("REMOVER");
                ModelosRelatorios();
            }
        }
        else //remover
        {
            
            SelecionarVeiculoController.veiculo = 0;
            tfVeiculo.setText("");
            btSelecionarVeiculo.setText("SELECIONAR");
            ModelosRelatorios();
        }
    }

    

    
    
}
