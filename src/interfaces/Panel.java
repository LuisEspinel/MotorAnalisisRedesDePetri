/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import funcional.Nodo;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author luis
 */
public class Panel extends JPanel{
    public ArrayList<Nodo> listaNodos;
    
    @Override 
    public void paint(Graphics g){
        g.setColor(Color.BLUE);
        pintarNodo(g, listaNodos.get(0));
    }    
    
    public void pintarNodo(Graphics g,Nodo nodo){
        g.setColor(Color.WHITE);
        g.fillOval(nodo.posX, nodo.posY, 140, 20);
        g.setColor(Color.BLACK);
        g.drawString(nodo.obtenerUltimoDisparo()+" - "+nodo.printVectorNodo(), nodo.posX+20, nodo.posY+15);
        for(Nodo hijo : nodo.hijos){
            g.setColor(Color.RED);
            g.drawLine(nodo.posX+60, nodo.posY+20, hijo.posX+60, hijo.posY);            
            pintarNodo(g, hijo);
        }            
    }
        
}
