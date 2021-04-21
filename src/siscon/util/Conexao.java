package siscon.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class Conexao
{
    private Connection connect;
    private Parametros parametros;
    private String erro = "";    
    private int cod;
    private String ip = "", usuario = "", horario = "", tipo = "";

    public Connection getConnect() {
        return connect;
    }

    public void setConnect(Connection connect) {
        this.connect = connect;
    }

    public Parametros getParametros() {
        return parametros;
    }

    public void setParametros(Parametros parametros) {
        this.parametros = parametros;
    }
    
    public Conexao(Parametros parametros)
    {
        this.parametros = parametros;     
        conectar();
    }
    
    private void conectar()
    {
        String local = "jdbc:postgresql://"+parametros.getServidor()+":"+parametros.getPorta()+"/";
        try
        {
            Class.forName(parametros.getDriver()); //"org.postgresql.Driver");
            String url = local+parametros.getBase_dados(); //"jdbc:postgresql://localhost/"+banco;
            connect = DriverManager.getConnection(url, parametros.getUsuario(), parametros.getSenha());  
        }
        catch (ClassNotFoundException cnfex)
        {
            erro = "Falha ao ler o driver JDBC: " + cnfex.toString();
            System.out.println(erro);
        }
        catch (SQLException sqlex)
        {
            erro = "Impossivel conectar com a base de dados: " + sqlex.toString();
            System.out.println(erro);
        }
        catch (Exception ex)
        {
            erro = "Outro erro: " + ex.toString();
            System.out.println(erro);
        }
    }

    public int getCod() {
        return cod;
    }

    public String getIp() {
        return ip;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getHorario() {
        return horario;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public void IniciarTransacao() throws SQLException
    {
        connect.setAutoCommit(false); //permite agrupar execuções de sql numa só transação, desde o último commit ou rollback. Precisa utilizar manualmente os comandos de commit e rollback
    }
    
    public void FecharTransacao() throws SQLException
    {
        connect.setAutoCommit(true); //cada execução de insert, update, delete é tratada de forma independente, o commit é automático pra cada execução
    }
    
    public void Commit(String msg) throws SQLException
    {
        connect.commit();
        FecharTransacao();
        if(msg.equals(""))
          System.out.println("Commit efetuado!");
        else
          System.out.println("Commit efetuado! ("+msg+")");
    }
    
    public void Rollback(String msg_erro) throws SQLException
    {        
        connect.rollback();
        FecharTransacao();
        if(msg_erro.equals(""))
           System.out.println("Rollback efetuado!");
        else
          System.out.println("Rollback efetuado! ("+msg_erro+")");
   
        /*
        if(msg_erro.equals(""))
           msg_erro = "Não foi possível realizar esta operação.";
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Erro");
        alert.setContentText(msg_erro); 
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm()); 
        alert.showAndWait();  */  
    }
    
    public String getMensagemErro()
    {
        return erro;
    }
    
    public boolean getEstadoConexao()
    {
        if(connect == null)
            return false;
        else
            return true;
    }
    
    public boolean testarTransacao() //vai dar erro nessa instrução, claro!
    {
        return cmdDelete("teste", "tes_cod", 0);
    }
    
    public boolean cmdInsert(String tabela, List <String> campos, List <String> valores)
    {
        String sql = "insert into "+tabela+ " ("+campos.get(0);
        for(int i=1; i<campos.size();i++)
            sql += ", "+campos.get(i);
        
        sql += ") values ('"+valores.get(0)+"'";
        
        for(int i=1; i<valores.size();i++)
            sql += ", '"+valores.get(i)+"'";
        
        sql += ")";
        sql = sql.replace("'null'", "null");
        return manipular(sql);
    }
    
    public boolean cmdUpdate(String tabela, List <String> campos, List <String> valores)
    {
        String sql = "update "+tabela+ " set "+campos.get(1)+ " = '"+valores.get(1)+"'";
        for(int i=2; i<campos.size();i++)
            sql += ", "+campos.get(i)+ " = '"+valores.get(i)+"'";
        
        sql += " where "+campos.get(0)+" = "+valores.get(0);
        sql = sql.replace("'null'", "null");
        return manipular(sql);
    }
    
    public boolean cmdDelete(String tabela, String campo_chave, int chave)
    {
        String sql = "delete from "+tabela+ " where "+campo_chave+ " = '"+chave+"'";        
        return manipular(sql);
    }
    
    public boolean manipular(String sql) // inserir, alterar, excluir
    {
        try
        {
            Statement statement = connect.createStatement();
            int result = statement.executeUpdate( sql );
            statement.close();
            if(result>=1)
                return true;
        }
        catch (SQLException sqlex)
        {  
            String mensagem = TratarErro(sql, sqlex.toString());
            if(mensagem.equals("erro")) //quer dizer que não conseguiu tratar o erro, o erro persiste
            {
               System.out.println("Erro: " + sqlex.toString());
               return false;
            }
            else //tratou o erro!
               return true;
        }
        return false;
    }
    
    public ResultSet consultar(String sql)
    {   
        ResultSet rs = null;
        try
        {
           Statement statement = connect.createStatement();
             //ResultSet.TYPE_SCROLL_INSENSITIVE,
             //ResultSet.CONCUR_READ_ONLY);
           rs = statement.executeQuery( sql );
           //statement.close();
        }
        catch (SQLException sqlex)
        {
            if(!sqlex.toString().contains("Nenhum resultado")) //esse erro acontece quando altera a pessoa q não possui foto
               System.out.println("Erro: " + sqlex.toString());
            return null;
        }
        return rs;
    }
    
    public int getMaxPK(String tabela, String chave, String filtro) 
    {
        String sql = "select max("+chave+") from " + tabela+" ";
        if(!filtro.equals(""))
           sql += "where "+filtro;
        int max = 0;
        ResultSet rs = consultar(sql);
        try 
        {
            if(rs.next())
                max = rs.getInt(1);
        }
        catch (SQLException sqlex)
        { 
             System.out.println("Erro: " + sqlex.toString());
             return -1;
        }
        return max;
    }
    
    public String TratarErro(String sql, String sqlex) //sql = código sql , sqlex = mensagem do erro (SQLException)
    {
        String mensagem = "erro";
        
        //vou tratar os erros nas operações em Banco
        if(sqlex.contains("restrição de chave estrangeira") || sqlex.contains("violates foreign key"))
        {
            if(sql.contains("delete")) //tentando deletar uma informação q está sendo utilizada em outra tabela
            {
                if(sql.contains("pessoa")) //tentando deletar uma linha da tabela pessoa
                {
                    mensagem = "Violação na exclusão do registro de uma Pessoa.\nIsto ocorre quando o registro está relacionado com outra operação (ex: movimentação).";
                    int pos = sql.indexOf("pes_cod");
                    sql = sql.substring(pos, sql.length()); //vou pegar o finalzinho do comando sql, para reutilizar no novo sql de UPDATE (abaixo)
                    sql = "update pessoa set pes_status = 'D' where "+sql;
                }                
                if(sql.contains("empresa")) //tentando deletar uma linha da tabela empresa
                {
                    mensagem = "Violação na exclusão do registro de uma Empresa.\nIsto ocorre quando o registro está relacionado com outra operação (ex: conta a pagar).";
                    int pos = sql.indexOf("emp_cod");
                    sql = sql.substring(pos, sql.length()); //vou pegar o finalzinho do comando sql, para reutilizar no novo sql de UPDATE (abaixo)
                    sql = "update empresa set emp_status = 'D' where "+sql;
                }
                if(sql.contains("veiculo")) 
                {
                    mensagem = "Violação na exclusão do registro de um Veículo.\nIsto ocorre quando o registro está relacionado com outra operação (ex: movimentação).";
                    int pos = sql.indexOf("vei_cod");
                    sql = sql.substring(pos, sql.length()); //vou pegar o finalzinho do comando sql, para reutilizar no novo sql de UPDATE (abaixo)
                    sql = "update veiculo set vei_status = 'D' where "+sql;
                }
                if(sql.contains("lote")) 
                {
                    mensagem = "Violação na exclusão do registro de um Lote.\nIsto ocorre quando o registro está relacionado com outra operação (ex: evento).";
                    int pos = sql.indexOf("lot_cod");
                    sql = sql.substring(pos, sql.length()); //vou pegar o finalzinho do comando sql, para reutilizar no novo sql de UPDATE (abaixo)
                    sql = "update lote set lot_status = 'D' where "+sql;
                }
                
            }
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Erro");
        alert.setHeaderText(mensagem);
        alert.setContentText("Deseja desativar o registro ?");
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
        ButtonType buttonTypeSim = new ButtonType("SIM", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNao = new ButtonType("NÃO", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao); 
        if(!mensagem.equals("erro"))
           if(alert.showAndWait().get() == buttonTypeSim)
              manipular(sql);
           else
              return "erro";
        
        return mensagem;
    }
    
    public boolean registrar() 
    {     
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        horario =  Funcoes.DateToString(timestamp);
        List <String> campos = new ArrayList<>(Arrays.asList("con_maquinaip", "con_maquinausuario", "con_horario", "con_tipo"));
        List <String> valores = new ArrayList<>(Arrays.asList(ip, usuario, timestamp+"", tipo));
        
        if(Banco.con.cmdInsert("conexao", campos, valores))         
        {
            cod = Banco.con.getMaxPK("conexao", "con_cod", "");
            return true;
        }
        
        return false;    
    }
    
}
