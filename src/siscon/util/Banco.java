package siscon.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Banco
{

    public static Conexao con;

    private Banco() 
    {
    }

    public static boolean conectar() throws SQLException, UnknownHostException {
        con = new Conexao(con.getParametros());
        registrarConexao();
        return con.getEstadoConexao();
    }
    
    public static boolean conectar(Parametros parametros) throws SQLException, UnknownHostException
    {
        con = new Conexao(parametros);
        registrarConexao();
        return con.getEstadoConexao();
    }
    
    private static void registrarConexao() throws SQLException, UnknownHostException
    {
        if(con.getEstadoConexao()) //conectou
        {                
            con.setIp(Inet4Address.getLocalHost().getHostAddress());
            con.setUsuario(System.getProperty("user.name"));
            if(con.getParametros().getServidor().equals("localhost"))
               con.setTipo("L");
            else
               con.setTipo("R"); 
            con.IniciarTransacao();
            if(con.registrar())
                con.Commit("Conexão registrada.");
            else
                con.Rollback("");
        }
    }

    //Para criar a base de dados
    public static boolean criarBD() {
        try {
            Class.forName(con.getParametros().getDriver());
            String url = "jdbc:postgresql://"+con.getParametros().getServidor()+":"+con.getParametros().getPorta()+"/";
            con.setConnect(DriverManager.getConnection(url, con.getParametros().getUsuario(), con.getParametros().getSenha()));

            Statement statement = con.getConnect().createStatement();
            statement.execute("CREATE DATABASE " + con.getParametros().getBase_dados() + " WITH OWNER = postgres ENCODING = 'UTF8'  "
                    + "TABLESPACE = pg_default LC_COLLATE = 'Portuguese_Brazil.1252'  "
                    + "LC_CTYPE = 'Portuguese_Brazil.1252'  CONNECTION LIMIT = -1;");
            statement.close();
            con.getConnect().close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    /*
    public static void BackupAndRestore(String arquivo) {
        String estado = "";
        String mensagem = "";
        Runtime r = Runtime.getRuntime();
        try {
            Process p = r.exec(arquivo);
            if (p != null) {
                InputStreamReader str = new InputStreamReader(p.getErrorStream());
                BufferedReader reader = new BufferedReader(str);
                String linha;
                while ((linha = reader.readLine()) != null) {
                    System.out.println(linha);
                }
            }
            File f = new File("util/siscon.backup");
            DateFormat formatData = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String data = formatData.format(new Date(f.lastModified()));
            if (arquivo.contains("backup")) {
                estado = "Backup";
                mensagem = "Backup realizado com sucesso!\nArquivo: " + f.getCanonicalPath() + "\nData de modificação: " + data;
                //Runtime.getRuntime().exec("explorer "+f.getCanonicalPath());

            } else {
                estado = "Restore";
                mensagem = "Restauração realizada com sucesso!\nArquivo: " + f.getCanonicalPath() + "\nData de modificação: " + data;
                
                //Platform.exit();
            }
            JOptionPane.showMessageDialog(null, mensagem);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro no " + estado + " dos dados! " + ex.getMessage());
        }
    }
    */
    
    public static boolean Backup(String arquivo) throws IOException, InterruptedException
    {      
        final ArrayList<String> comandos = new ArrayList();      
        comandos.add("util/pg_dump.exe");     
        comandos.add("--host");      
        comandos.add(con.getParametros().getServidor());     //ou  comandos.add("192.168.0.1"); 
        comandos.add("--port");      
        comandos.add(con.getParametros().getPorta());      
        comandos.add("--username");      
        comandos.add(con.getParametros().getUsuario());      
        comandos.add("--format");      
        comandos.add("custom");      
        comandos.add("--blobs");      
        comandos.add("--verbose");      
        comandos.add("--file"); 
        if(arquivo.equals(""))
        {
            String horario = Funcoes.DateToString(new Timestamp(System.currentTimeMillis()));
            horario = horario.replace("/", "-");
            horario = horario.replace(":", ".");
            //horario = horario.replace(" ", "_");     
            arquivo = "backups/"+horario+".backup";
        }
        comandos.add(arquivo);   
        comandos.add(con.getParametros().getBase_dados());      
        ProcessBuilder pb = new ProcessBuilder(comandos);      
        pb.environment().put("PGPASSWORD", con.getParametros().getSenha());               
        try {      
            final Process process = pb.start();      
            final BufferedReader r = new BufferedReader(      
                new InputStreamReader(process.getErrorStream()));      
            String line = r.readLine();      
            while (line != null) {      
            System.err.println(line);      
            line = r.readLine();      
            }      
            r.close();      
            process.waitFor();    
            process.destroy();             
            File f = new File(arquivo);
            DateFormat formatData = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String data = formatData.format(new Date(f.lastModified()));
            JOptionPane.showMessageDialog(null,"Backup realizado com sucesso!\nArquivo: " + f.getCanonicalPath() + "\nData de modificação: " + data);  
            return true;
        } catch (IOException e) {      
            JOptionPane.showMessageDialog(null,"Ocorreu um erro durante a realização do backup.");    
            return false;
        } catch (InterruptedException ie) {      
            JOptionPane.showMessageDialog(null,"Ocorreu um erro durante a realização do backup.");  
            return false;
        }         
    }     
    
    public static boolean Restaurar(String arquivo) throws IOException, InterruptedException
    {      
        final ArrayList<String> comandos = new ArrayList();      
        comandos.add("util/pg_restore.exe"); 
        comandos.add("-c");      
        comandos.add("--host");      
        comandos.add(con.getParametros().getServidor());    //ou   comandos.add("192.168.0.1"); 
        comandos.add("--port");      
        comandos.add(con.getParametros().getPorta());      
        comandos.add("--username");      
        comandos.add(con.getParametros().getUsuario());      
        comandos.add("--dbname");      
        comandos.add(con.getParametros().getBase_dados());     
        comandos.add("--verbose");   
        if(arquivo.equals(""))
           arquivo = "util/inicial.backup";
        comandos.add(arquivo);   
        ProcessBuilder pb = new ProcessBuilder(comandos);      
        pb.environment().put("PGPASSWORD", con.getParametros().getSenha());            
        try {      
            final Process process = pb.start();      
            final BufferedReader r = new BufferedReader(      
                new InputStreamReader(process.getErrorStream()));      
            String line = r.readLine();      
            while (line != null) {      
            System.err.println(line);      
            line = r.readLine();      
            }      
            r.close();      
            process.waitFor();    
            process.destroy(); 
            if(!arquivo.equals("util/inicial.backup"))
               JOptionPane.showMessageDialog(null,"Restauração realizada com sucesso!");  
            return true;
        } catch (IOException e) {      
            JOptionPane.showMessageDialog(null,"Ocorreu um erro durante a restauração.");    
            return false;
        } catch (InterruptedException ie) {      
            JOptionPane.showMessageDialog(null,"Ocorreu um erro durante a restauração.");     
            return false;
        }    
    }  
    
    public static void salvar_servidor()
    {
        FileOutputStream fout = null;
        ObjectOutputStream out;
        try
        {
            fout = new FileOutputStream("util/acessodb.dat");
            out = new ObjectOutputStream(fout);
            out.writeObject(con.getParametros());
            out.close();
            //Para ocultar, colocar somente leitura, e colocar como arquivo de sistema:
            Runtime.getRuntime().exec("cmd /c attrib +h +s +r util/acessodb.dat"); 
            //Para desocultar, e tirar os atributos somente leitura e de sistema:
            //Runtime.getRuntime().exec("cmd /c attrib -h -s -r util/acesso.dat");
        }
        catch(Exception e)
        {
            System.out.println("Erro: "+e.getMessage());
        }
    }
    
    public static Parametros carregar_servidor()
    {
        Parametros parametros = null;
        FileInputStream fin = null;
        ObjectInputStream in;
        try
        {
            fin = new FileInputStream("util/acessodb.dat");
            in = new ObjectInputStream(fin);
            parametros = (Parametros) in.readObject();            
            in.close();
        }
        catch(Exception e)
        {
            System.out.println("Servidor não configurado.");
        }
        return parametros;
    }
        
}
