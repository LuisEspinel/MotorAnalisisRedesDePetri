/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author luis
 */
public class VentanaAbout extends JFrame{
    private final JLabel titulo,informacion;
    private final JPanel panel;
    private final String info="<html><body>Software desarrollador por"
            + "<br><br><center> - Luis Espinel Fuentes</center>"                  
            + "<br> Estudiante de Ingenieria de Sistemas"
            + "<br> Universidad de Pamplona - Colombia"
            + "</body></html>" ;
    
    public VentanaAbout(){
        super("SOBRE ESTE PROGRAMA");
        super.setSize(300,200);
        super.setLocation(400,200);
        super.setResizable(false);
        super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        panel=new JPanel(null);
        titulo=new JLabel("SOBRE ESTE SOFTWARE");
        titulo.setBounds(70,20,150,20);
        informacion=new JLabel(info);
        informacion.setBounds(30,10,250,200);
        
        panel.add(titulo);
        panel.add(informacion);
        
        super.getContentPane().add(panel);
    }
    
    
}
