
package siscon.controladorasinterface;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import siscon.controladoras.CtrCaixa;
import siscon.controladoras.CtrCidade;
import siscon.controladoras.CtrMovimentacao;
import siscon.controladoras.CtrPessoa;
import siscon.controladoras.CtrVeiculo;
import siscon.entidades.Dependente;
import siscon.entidades.Estado;
import siscon.entidades.Lote;
import siscon.entidades.MovCaixa;
import siscon.entidades.Movimentacao;
import siscon.entidades.Pessoa;
import siscon.entidades.Veiculo;
import siscon.ui.PrincipalController;
import sun.misc.BASE64Encoder;

public class CtrFluxoCaixa
{
    private CtrCaixa ctrcaixa;
    
    public CtrFluxoCaixa() {
        ctrcaixa = new CtrCaixa();
    }

    public CtrCaixa getCtrcaixa() {
        return ctrcaixa;
    }
    
    public void Novo()
    {
        ctrcaixa.novo();
    }
    
    public boolean CarregarDados() throws SQLException
    {
        return ctrcaixa.CarregarDados();
        
    }
    
    public void CarregarTabela(TableView tabela)
    {   
        ArrayList<MovCaixa> res = null;
        if(tabela.getId().contains("Saida"))
            res = ctrcaixa.getCaixa().getMovimentacoesSaida();
        else
            res = ctrcaixa.getCaixa().getMovimentacoesEntrada();
        
        //Agora converte para observable list pois a tabela precisa ser observable list
        ObservableList<MovCaixa> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }
    
   
    
   
}
