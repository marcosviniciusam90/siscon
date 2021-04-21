/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscon;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;
import siscon.controladorasinterface.CtrFluxoCaixa;
import siscon.controladorasinterface.CtrGerenciarPessoas;
import siscon.controladorasinterface.CtrRegistrarEvento;
import siscon.ui.PrincipalController;
import siscon.util.Banco;
import siscon.util.Funcoes;

/**
 *
 * @author Marcos Vinícius
 */
public class SisCon extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        java.net.URL url = getClass().getResource("ui/icones/siscon.png");
        stage.getIcons().add(new Image(url.toString())); 
        Parent root = FXMLLoader.load(getClass().getResource("ui/Principal.fxml"));
        
        Scene scene = new Scene(root);        
        stage.setScene(scene);
        //stage.initModality(Modality.APPLICATION_MODAL);
        //stage.setMaximized(true);
        //stage.setFullScreen(true);        
        //stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("SisCon - Gerenciador de Condomínios");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
        //EVENTO PARA QUANDO O USUÁRIO FECHAR O SISTEMA
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() 
        {
            public void handle(WindowEvent we) 
            {
                if(Banco.con != null)
                {
                    CtrRegistrarEvento ctrRegistrarEvento = new CtrRegistrarEvento();
                    try
                    {
                        Banco.con.IniciarTransacao();
                        if(ctrRegistrarEvento.getCtrevento().getEvento().finalizar_eventos_passados())
                            Banco.con.Commit("Finalizar eventos passados.");
                        else
                            Banco.con.Rollback("");
                    }
                    catch(Exception ex)
                    {
                        System.out.println("");
                    }

                    //Fechando Caixa se estiver aberto, pois pode ter trocado de usuário, nova sessão
                    CtrFluxoCaixa ctrFluxoCaixa = new CtrFluxoCaixa();
                    if(ctrFluxoCaixa.getCtrcaixa().getCaixa().getUltimoCaixa() != null) //já existia um caixa
                    {
                        ctrFluxoCaixa.getCtrcaixa().setCaixa(ctrFluxoCaixa.getCtrcaixa().getCaixa().getUltimoCaixa());
                        if(ctrFluxoCaixa.getCtrcaixa().getCaixa().estaAberto())
                        {
                           try
                           {
                                Banco.con.IniciarTransacao();
                                if(ctrFluxoCaixa.getCtrcaixa().getCaixa().fechar())
                                   Banco.con.Commit("Fechar caixa.");
                                else
                                {
                                    Banco.con.Rollback("");
                                    return;
                                }
                           }
                           catch(Exception ex)
                           {
                                   System.out.println("");
                           }

                        }

                    }

                    CtrGerenciarPessoas ctr = new CtrGerenciarPessoas();
                    ctr.getCtrpessoa().setUsuario(PrincipalController.usuario);
                    if(ctr.getCtrpessoa().getUsuario() != null && ctr.getCtrpessoa().getUsuario().getNivel() == 1) //admin (faz backup)
                    {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setHeaderText("Backup");
                        alert.setContentText("O sistema será fechado.\nDeseja fazer backup dos dados ?");
                        alert.getDialogPane().getStylesheets().add(getClass().getResource("/siscon/util/estilo.css").toExternalForm());            
                        ButtonType buttonTypeSim = new ButtonType("SIM", ButtonBar.ButtonData.YES);
                        ButtonType buttonTypeNao = new ButtonType("NÃO", ButtonBar.ButtonData.NO);
                        alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao); 

                        if(alert.showAndWait().get() == buttonTypeSim)
                        {
                            try 
                            {
                                String arquivo = "";
                                Banco.Backup(arquivo); 
                            } catch (IOException ex) {
                                Logger.getLogger(SisCon.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(SisCon.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }  
                    }
                }
                System.out.println("Fechando o sistema.");
            }
        });        
    }

   
    public static void main(String[] args) throws IOException, InterruptedException {
        //Desenvolvido por: Marcos Vinícius (0261641425)              
        launch(args);
    }
    
}
