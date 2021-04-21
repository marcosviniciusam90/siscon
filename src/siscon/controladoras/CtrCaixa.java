/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.controladoras;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javax.imageio.ImageIO;
import siscon.entidades.AcertoCaixa;
import siscon.entidades.Cidade;
import siscon.entidades.Associado;
import siscon.entidades.Caixa;
import siscon.entidades.Dependente;
import siscon.entidades.Funcionario;
import siscon.entidades.Lote;
import siscon.entidades.MovCaixa;
import siscon.entidades.Movimentacao;
import siscon.entidades.Pessoa;
import siscon.entidades.Veiculo;
import siscon.entidades.Visitante;
import siscon.util.Banco;
import siscon.util.Funcoes;

/**
 *
 * @author Marcos Vinícius
 */
public class CtrCaixa {
    
    private Caixa caixa;

    public CtrCaixa() {
        caixa = new Caixa();
    }

    public Caixa getCaixa() {
        return caixa;
    }
    
    public void setCaixa(Caixa caixa)
    {
        this.caixa = caixa;
    }
    public void novo()
    {
        caixa = new Caixa();
    }
    
    public boolean CarregarDados() throws SQLException
    {
        setCaixa(caixa.getUltimoCaixa());
        if(caixa == null) //primeiro caixa
        {
           return false;
        }
        if(caixa.estaAberto() && !caixa.estaAtualizado()) //Caixa do dia anterior aberto, vou fechar
        {
            Banco.con.IniciarTransacao();
            if(!caixa.fechar())
            {
                Banco.con.Rollback("");
                return false;
            }
            Banco.con.Commit("Fechar caixa.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Aviso");
            alert.setContentText("O Caixa que está aberto não é referente ao dia de hoje. \nEle será fechado!");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());   
            alert.showAndWait();
            return true;
        }
        if(caixa.estaAberto()) //caixa aberto e atualizado
           caixa.carregar_dados("caixa.cai_cod = "+caixa.getCod());
        return true;
    }
    
    public boolean movimentar(float valor, String tipo, String descricao)
    {
        //atualizar caixa
        if(tipo.equals("A")) //Aporte (soma)
        {
           descricao = "Aporte: "+descricao; 
           if(!caixa.aporte(valor))
              return false;
        }
        else
        {
           descricao = "Sangria: "+descricao;
           if(!caixa.sangria(valor))
               return false;
        }
        AcertoCaixa ac = new AcertoCaixa(valor, tipo, descricao);
        if(!ac.inserir())
            return false;
        MovCaixa mc = new MovCaixa(caixa, new Timestamp(System.currentTimeMillis()), ac, null, null);
        if(!mc.inserir())
            return false;
                
        return caixa.atualizar();
           
    }
    
    
}
