package componentes;

import comunicacion.Enlace;
import comunicacion.VistaMaquina;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.image.ImageObserver.WIDTH;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Maquina de estados del juego. Se enlaza por medio de la interface con el
 * cliente, el interprete y el servidor. Maneja prácticamente todo el
 * funcionamiento interno del juego.
 */
public class Maquina implements ActionListener, Enlace{
    
    private VistaMaquina vistamaquina;
    private Entrada entrada;
    private Interprete interprete;
    private int port1, port2;
    private String nombre, ip;
    private final String victoria="victoria", rendicion="rendicion", 
            continuar="continuar", nocontinuar="nocontinuar", ping="ping", 
            pong="pong", pingpong="pingpong";
    private int player1=0, player2=0;
    private boolean turno, cont1=false, cont2=false, connect=false;
    
    //RECIBE UN OBJETO DE LA INTERFAZ VISTAMAQUINA, EL NOMBRE DEL JUGADOR, LA
    //DIRECCION IP, LOS PUERTOS 1 Y 2, Y EL IDENTIFICADOR DE JUGADOR
    public Maquina(VistaMaquina vistamaquina, String nombre, String ip, 
                   int port1, int port2, String p){
        this.vistamaquina=vistamaquina;
        this.nombre=nombre;
        this.ip=ip;
        this.port1=port1;
        this.port2=port2;
        interprete=new Interprete(this);
        entrada=new Entrada(port1,this);
        entrada.start();
        if(p.equalsIgnoreCase("p1"))
            turno=true;
        else
            turno=false;
    }
    
    //RECIBE LA MATRIZ DE BOTONES Y LOS CREARÁ Y ACOMODARÁ DE ACUERDO A LAS
    //NECESIDADES DEL TABLERO
    public void acomodar(Boton[][] matriz, boolean add){
        for(int x=0;x<19;x++){
            for(int y=0;y<19;y++){
                if(add){
                    matriz[x][y]=new Boton();
                    matriz[x][y].addActionListener(this);
                }
                else{
                    matriz[x][y].setPropietario(null);
                    matriz[x][y].setUsado(false);
                }
//                localHost, white
                if(x%2==0){
                    if(y%2==0){
                        matriz[x][y].setBounds(0+(55*(y/2)),0+(55*(x/2)),5,5);
                        matriz[x][y].setBorderPainted(false);
                        matriz[x][y].setBackground(Color.black);
                        matriz[x][y].setEnabled(false);
                    }
                    else{
                        matriz[x][y].setBounds(5+(55*(y/2)),0+(55*(x/2)),50,5);
                        matriz[x][y].setBorderPainted(false);
                        matriz[x][y].setBackground(Color.white);
                    }
                }
                else{
                    if(y%2==0){
                        matriz[x][y].setBounds(0+(55*(y/2)),5+(55*(x/2)),5,50);
                        matriz[x][y].setBorderPainted(false);
                        matriz[x][y].setBackground(Color.white);
                    }
                    else{
                        matriz[x][y].setBounds(5+(55*(y/2)),5+(55*(x/2)),50,50);
                        matriz[x][y].setCuadro(true);
                        matriz[x][y].setBorderPainted(false);
                        matriz[x][y].setBackground(Color.white);
                        matriz[x][y].setEnabled(false);
                    }
                }
                if(add)
                    vistamaquina.agregar(matriz[x][y]);
            }
        }
    }
    
    //REVISA SI HAY NUEVAS CASILLAS CERRADAS, EN CUYO CASO LAS MARCA CON EL
    //NOMBRE QUE RECIBE Y OTORGA PUNTOS
    public void check(String player, Boton[][] matriz, int who){
        for(int x=0;x<19;x++){
            for(int y=0;y<19;y++){
                if(matriz[x][y].getCuadro() && !matriz[x][y].getUsado()){
                    if(matriz[x-1][y].getUsado() && matriz[x+1][y].getUsado() 
                            && matriz[x][y-1].getUsado() 
                            && matriz[x][y+1].getUsado()){
                        matriz[x][y].setPropietario(player);
                        matriz[x][y].setUsado(true);
                        if(who==1)
                            player1++;
                        if(who==2)
                            player2++;
                        vistamaquina.puntajes(player1, player2);
                        turno=true;
                    }
                }
            }
        }
    }
    
    //REVISA SI TODOS LOS BOTONES HAN SIDO USADOS, EN CUYO CASO REGRESA UN TRUE
    public boolean forTheWin(){
        for(int x=0;x<19;x++){
            for(int y=0;y<19;y++){
                if(vistamaquina.getBoton(x,y).isEnabled())
                    return false;
            }
        }
        return true;
    }
    
    //TERMINA LA PARTIDA. REVISA LOS PUNTAJES PARA DECIDIR UN GANADOR, A MENOS
    //QUE HAYA RECIBIDO UN MENSAJE DE RENDICION, EN CUYO CASO SOLO LO AVISA
    public void finDePartida(String maybe){
        if(maybe.equalsIgnoreCase("")){
            if(player1>player2)
                JOptionPane.showMessageDialog(null, null, "Victoria", WIDTH, 
                        new ImageIcon("src/recursos/victoria.png"));
            else if(player1<player2)
                JOptionPane.showMessageDialog(null, null, "Derrota", WIDTH, 
                        new ImageIcon("src/recursos/derrota.png"));
            else
                JOptionPane.showMessageDialog(null, null, "Empate", WIDTH, 
                        new ImageIcon("src/recursos/empate.png"));
        }
        else{
            if(maybe.equalsIgnoreCase("rendicion"))
                JOptionPane.showMessageDialog(null, null, "Derrota", WIDTH, 
                        new ImageIcon("src/recursos/derrota.png"));
            else
                JOptionPane.showMessageDialog(null, null, "Victoria", WIDTH, 
                        new ImageIcon("src/recursos/victoria.png"));
        }
        String[] opcs={"Si", "No"};
        if(JOptionPane.showOptionDialog(null, "¿Desea reiniciar la partida?", 
                "Continuar", JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE, null, opcs, opcs[0])==0){
            cont1=true;
            enviar(continuar);
            continuar();
        }
        else{
            enviar(nocontinuar);
            System.exit(0);
        }
    }
    
    public void reiniciar(){
        player1=0;
        player2=0;
        vistamaquina.puntajes(player1, player2);
        acomodar(vistamaquina.getMatriz(),false);
    }
    
    public void continuar(){
        if(cont1 && cont2)
            reiniciar();
    }
    @Override
    public void continuar(String cont){
        if(cont.equalsIgnoreCase(continuar))
            cont2=true;
        else if(cont.equalsIgnoreCase(nocontinuar)){
            JOptionPane.showMessageDialog(null, "El oponente no desea continuar");
            System.exit(0);
        }
        if(cont1 && cont2)
            reiniciar();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        //SI EL BOTON PRESIONADO FUE EL ENVIO DEL CHAT, ENVIA EL MENSAJE DEL 
        //CHAT
        if(ae.getSource()==vistamaquina.getEnviar()){
            enviar();
            vistamaquina.setMessage("");
            vistamaquina.getEnviar().setEnabled(false);
        }
        //SI EL BOTON PRESIONADO FUE EL DE RENDICION, ENVIA EL MENSAJE DE
        //RENDICION Y TERMINA EL JUEGO
        else if(ae.getSource()==vistamaquina.getRendir()){
            enviar(victoria);
            finDePartida(rendicion);
        }
        //SI NO ES NINGUNO DE LOS DOS CASOS ANTERIORES, PROCEDE A AVERIGUAR QUE
        //BOTON DE LA MATRIZ FUE PRESIONADO, NO SIN ANTES CONSULTAR SI
        //EFECTIVAMENTE EL TURNO LE PERTENECE AL JUGADOR
        else if(turno && connect){
            turno=false;
            Integer x=0;
            Integer y=0;
            boolean ban=false;
            for(x=0;x<19;x++){
                for(y=0;y<19;y++){
                    if(ae.getSource()==vistamaquina.getBoton(x, y)){
                        vistamaquina.getBoton(x, y).setUsado(true);
                        vistamaquina.getBoton(x, y).setBackground(Color.black);
                        vistamaquina.getBoton(x, y).setEnabled(false);
                        ban=true;
                        break;
                    }
                }
                if(ban)
                    break;
            }
            check(nombre, vistamaquina.getMatriz(), 1);
            //ENVIA LAS COORDENADAS Y EL NOMBRE DEL JUGADOR, SEPARADOS CON
            //ESPACIOS PARA QUE EL INTERPRETE LOS RECONOZCA
            enviar(x.toString()+" "+y.toString()+" "+nombre);
            if(forTheWin()){
                finDePartida("");
            }
        }
    }

    @Override
    public void interpretar(Object obj) {
        interprete.interpretar(obj);
    }

    @Override
    public void actualizar(String mensaje) {
        if(vistamaquina.getHistory().equalsIgnoreCase(""))
            vistamaquina.setHistory(mensaje);
        else
            vistamaquina.setHistory(vistamaquina.getHistory()+"\n"+mensaje);
    }

    @Override
    public void enviar() {
        String mensaje=nombre+" dice: "+vistamaquina.getMessage();
        new Salida(ip,port2,"##-"+mensaje);
        actualizar(mensaje);
    }

    @Override
    public void reAcomodar(Boton[][] matriz) {
        for(int x=0;x<19;x++)
            for(int y=0;y<19;y++){
                if(matriz[x][y].getUsado()!=vistamaquina.getBoton(x, y).getUsado()){
                    vistamaquina.getBoton(x, y).setUsado(matriz[x][y].getUsado());
                    vistamaquina.getBoton(x, y).setPropietario(matriz[x][y].getPropietario());
                    vistamaquina.getBoton(x, y).setBackground(matriz[x][y].getBackground());
                    if(matriz[x][y].getUsado())
                        vistamaquina.getBoton(x, y).setEnabled(false);
                }
            }
        turno=true;
        if(forTheWin()){
            finDePartida("");
        }
    }

    @Override
    public void enviar(String obj) {
        new Salida(ip,port2,obj);
    }

    @Override
    public void reAcomodar(int x, int y, String nombre2) {
        vistamaquina.nombres(nombre, nombre2);
        try{
            vistamaquina.getBoton(x, y).setUsado(true);
            vistamaquina.getBoton(x, y).setBackground(Color.black);
            check(nombre2,vistamaquina.getMatriz(), 2);
            turno=true;
        }
        catch(Exception e){
            turno=true;
        }
        if(forTheWin()){
            finDePartida("");
        }
    }

    @Override
    public void ganar(String maybe) {
        finDePartida(maybe);
    }

    @Override
    public void conectado() {
        connect=true;
    }
}
