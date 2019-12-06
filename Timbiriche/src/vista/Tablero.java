package vista;

import comunicacion.VistaMaquina;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import componentes.Boton;
import componentes.Maquina;

/**
 * Tablero principal del tablero de juego y el cliente de chat.
 */
public class Tablero extends JFrame implements VistaMaquina, KeyListener, ActionListener{
    
    private Boton[][] matriz=new Boton[20][20];
    private JPanel p1, p2;
    private JButton Enviar, Ayuda, Rendirce;
    private JTextPane Historial, Mensaje;
    private JLabel Jugador1, Jugador2, Puntaje1, Puntaje2;
    private JScrollPane sp1, sp2;
    private Maquina Maquina;
    
    public Tablero(String nombre, String ip, int port1, int port2, String p){
        crearMaquina(nombre, ip, port1, port2, p);
        inicializar();
    }
    
    public void inicializar(){
        setSize(805,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setBackground(Color.WHITE);
        
        p1=new JPanel();
        p1.setLayout(null);
        Maquina.acomodar(matriz, true);
        
        Historial=new JTextPane();
        sp1=new JScrollPane(Historial);
        sp1.setBounds(510,10,280,450);
        add(sp1);
        
        Mensaje=new JTextPane();
        Mensaje.addKeyListener(this);
        sp2=new JScrollPane(Mensaje);
        sp2.setBounds(510,470,230,30);
        add(sp2);
        
        Enviar=new JButton(">>");
        Enviar.setBounds(740,470,49,29);
        Enviar.setContentAreaFilled(false);
        Enviar.addActionListener(Maquina);
        Enviar.setEnabled(false);
        add(Enviar);
        
        Jugador1=new JLabel("Player 1");
        Jugador1.setBounds(10,550,100,20);
        add(Jugador1);
        
        Puntaje1=new JLabel("0");
        Puntaje1.setBounds(110,550,100,20);
        add(Puntaje1);
        
        Jugador2=new JLabel("Player 2");
        Jugador2.setBounds(200, 550, 100, 20);
        add(Jugador2);
        
        Puntaje2=new JLabel("0");
        Puntaje2.setBounds(300,550,100,20);
        add(Puntaje2);
        
        Ayuda=new JButton("Ayuda");
        Ayuda.setBounds(510, 550, 100, 20);
        Ayuda.addActionListener(this);
        add(Ayuda);
        
        Rendirce=new JButton("Rendirse");
        Rendirce.setBounds(620,550,100,20);
        Rendirce.addActionListener(Maquina);
        add(Rendirce);
        
        add(p1);
        
        setVisible(true);
    }
    
    //REVISA SI LA CAJA DE TEXTO ESTÁ VACIA PARA PERMITIR O NO EL ENVIO DEL
    //MENSAJES
    public void check(){
        if(Mensaje.getText().equalsIgnoreCase(""))
            Enviar.setEnabled(false);
        else
            Enviar.setEnabled(true);
    }

    @Override
    public Tablero obtenerVista() {
        return this;
    }

    @Override
    public void crearMaquina(String nombre, String ip, int port1, int port2, String p) {
        Maquina=new Maquina(this, nombre, ip, port1, port2, p);
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        check();
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        check();
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        check();
    }

    @Override
    public void puntajes(Integer uno, Integer dos) {
        Puntaje1.setText(uno.toString());
        Puntaje2.setText(dos.toString());
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==Ayuda){
            JOptionPane.showMessageDialog(null, null, null, WIDTH, new ImageIcon("src/recursos/reglas.png"));
        }
    }

    @Override
    public void agregar(Boton boton) {
        p1.add(boton);
    }

    @Override
    public Boton getBoton(int x, int y) {
        return matriz[x][y];
    }

    @Override
    public Boton[][] getMatriz() {
        return matriz;
    }

    @Override
    public void setHistory(String history) {
        this.Historial.setText(history);
    }

    @Override
    public String getHistory() {
        return this.Historial.getText();
    }

    @Override
    public void setMessage(String message){
        this.Mensaje.setText(message);
    }
    
    @Override
    public String getMessage() {
        return this.Mensaje.getText();
    }
    
    @Override
    public JButton getEnviar(){
        return this.Enviar;
    }
    
    @Override
    public JButton getRendir(){
        return this.Rendirce;
    }

    @Override
    public void nombres(String player1, String player2) {
        this.Jugador1.setText(player1+":");
        this.Jugador2.setText(player2+":");
    }

    
}
