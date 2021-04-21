/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javafx.util.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import javafx.scene.control.Tooltip;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author MarcosVinícius
 */
public class Funcoes 
{
    public static void gerarRelatorio(String sql, String relat, String titulo, String subtitulo)
    { 
        try
        {  //sql para obter os dados para o relatorio
           ResultSet rs = Banco.con.consultar(sql);
           //implementação da interface JRDataSource para DataSource ResultSet
           JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
           
           HashMap map = new HashMap();
           //parametros que serão utilizados no topo do relatório
           if(subtitulo.equals(""))
               subtitulo = "Todos os resultados";
           map.put("Titulo", titulo); 
           map.put("Subtitulo", subtitulo);
           String jasperPrint = JasperFillManager.fillReportToFile(relat, map, jrRS);
           //chamando o relatório
           JasperViewer viewer = new JasperViewer(jasperPrint, false, false);
           viewer.setIconImage(new javax.swing.ImageIcon(Funcoes.class.getResource("/siscon/ui/icones/siscon.png")).getImage());
           
           viewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);//maximizado
           viewer.setTitle(titulo);
           viewer.setVisible(true);  
           
        } 
        catch (JRException erro)  
        {            
           System.out.println(erro); 
        }
    }
    
    public static double truncate(double value) 
    {
        return Math.round(value * 100) / 100d;
    }
    
    public static boolean ValidaTelefone(String fone)
    {
        fone = fone.replace("(", "");
        fone = fone.replace(")", "");
        fone = fone.replace(" ", "");
        fone = fone.replace("-", "");
        return fone.length() == 10 || fone.length() == 11;        
    }
    
    public static boolean ValidaCEP(String CEP)
    {
        CEP = CEP.replace(".", "");
        CEP = CEP.replace("-", "");
        
        return CEP.length() == 8;
    }
        
    public static boolean ValidaCPF(String cpf)
    {   
        cpf = cpf.replace(".", "");
        cpf = cpf.replace("-", "");
        
        //VERIFICA SE TODOS OS NÚMEROS SÃO IGUAIS, se forem iguais é INVÁLIDO!
        
        boolean todos_iguais = true;
        for(int i=1; i<cpf.length(); i++)
            if(cpf.charAt(0) != cpf.charAt(i))
               todos_iguais = false;
        if(todos_iguais)
           return false;            
        
        
        if(cpf.length() != 11)
           return false;
        int soma = 0;
        int multi = 10;
        for(int i=0; i<9;i++)
            soma += multi-- * Integer.parseInt(cpf.charAt(i)+"");
        soma = (soma*10)%11;
        if(soma == 10)
           soma = 0;
        if(Integer.parseInt(cpf.charAt(9)+"") != soma)
           return false;
        
        soma = 0;
        multi = 11;
        for(int i=0; i<10;i++)
            soma += multi-- * Integer.parseInt(cpf.charAt(i)+"");
        soma = (soma*10)%11;
        if(soma == 10)
           soma = 0;
        if(Integer.parseInt(cpf.charAt(10)+"") != soma)
           return false;
        return true;            
    }
    
    public static boolean ValidaCNPJ(String cnpj)
    {
        cnpj = cnpj.replace(".", "");
        cnpj = cnpj.replace("-", "");
        cnpj = cnpj.replace("/", "");
        if(cnpj.length() != 14)
           return false;
        String sequencia = "543298765432";
        int soma = 0;
        for(int i=0; i<12;i++)
            soma +=  Integer.parseInt(cnpj.charAt(i)+"") * Integer.parseInt(sequencia.charAt(i)+"");
        soma = soma % 11;
        if(soma < 2)
           soma = 0;           
        else
           soma = 11 - soma;
        if(Integer.parseInt(cnpj.charAt(12)+"") != soma)
           return false;
        
        sequencia = "6" + sequencia;        
        soma = 0;
        for(int i=0; i<13;i++)
            soma +=  Integer.parseInt(cnpj.charAt(i)+"") * Integer.parseInt(sequencia.charAt(i)+"");
        soma = soma % 11;
        if(soma < 2)
           soma = 0;
        else
           soma = 11 - soma;
        if(Integer.parseInt(cnpj.charAt(13)+"") != soma)
           return false;
        
        return true;            
    }
    
    public static String DateToString(LocalDate data) //retorna data
    {
        return String.format("%02d/%02d/%04d", data.getDayOfMonth(), data.getMonthValue(), data.getYear());
    }
    
    public static String DateToString(Timestamp ts) //retorna data e hora
    {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(ts.getTime());  
    }
    
    public static LocalDate StringToDate(String data)
    {
        String dia, mes, ano;
        
        data = data.replace("/", "");
        dia = data.substring(0, 2);
        mes = data.substring(2, 4);
        ano = data.substring(4, 8);
        data = ano + "-" + mes + "-" + dia;
        return LocalDate.parse(data);
        
        
        
    }
    
    public static String ValorMonetario(float valor)
    {
        valor = (float)truncate(valor);
        if(valor == 0)
           return "0,00";
        return String.format("%.2f", valor);
    }

    public static boolean isFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static boolean isEmail(String str) {
        str = str.toLowerCase();
        if (!str.endsWith(".com") && !str.endsWith(".br") && !str.endsWith(".org") && !str.endsWith(".gov")) {
            return false;
        }

        int qtde = 0;

        for (int i = 0; i < str.length() && qtde < 2; i++) {
            if (i == 0 && str.charAt(i) == '@') {
                return false;
            }
            if (str.charAt(i) == '@') {
                qtde++;
            }
        }
        if (qtde == 1) {
            return true;
        }
        return false;
    }

    public static boolean isWebsite(String str) {
        str = str.toLowerCase();
        if (!str.startsWith("www.")) {
            return false;
        }

        if (!str.endsWith(".com") && !str.endsWith(".br") && !str.endsWith(".org") && !str.endsWith(".gov")) {
            return false;
        }

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != '.' && str.charAt(i) < 'a' || str.charAt(i) > 'z') {
                return false;
            }
        }
        return true;
    }

    public static void updateTooltipBehavior(double openDelay, double visibleDuration,
    double closeDelay, boolean hideOnExit) { //controla o tempo dos Tooltip
    try {
        // Get the non public field "BEHAVIOR"
        Field fieldBehavior = Tooltip.class.getDeclaredField("BEHAVIOR");
        // Make the field accessible to be able to get and set its value
        fieldBehavior.setAccessible(true);
        // Get the value of the static field
        Object objBehavior = fieldBehavior.get(null);
        // Get the constructor of the private static inner class TooltipBehavior
        Constructor<?> constructor = objBehavior.getClass().getDeclaredConstructor(
            Duration.class, Duration.class, Duration.class, boolean.class
        );
        // Make the constructor accessible to be able to invoke it
        constructor.setAccessible(true);
        // Create a new instance of the private static inner class TooltipBehavior
        Object tooltipBehavior = constructor.newInstance(
            new Duration(openDelay), new Duration(visibleDuration),
            new Duration(closeDelay), hideOnExit);
        // Set the new instance of TooltipBehavior
        fieldBehavior.set(null, tooltipBehavior);
    } catch (Exception e) {
        throw new IllegalStateException(e);
    }
    
    
}
    

}
