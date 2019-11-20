package componentes;

import javax.swing.JButton;
import vista.Tablero;

/**
 * Extensión de la clase JButton que permite el funcionamiento de los metodos
 * de la maquina.
 */
public class Boton extends JButton{
    
    Tablero v = new Tablero();
    
    public Boton(){}
    
    public Boton(boolean Cuadro){
        this.Cuadro=Cuadro;
    }
    //ME DICE SI ESTE BOTON YA FUÉ USADO
    private boolean usado=false;
    //ME DIRÁ SI EL BOTON ES UN CUADRO(TRUE) O NO(FALSE)
    private boolean Cuadro=false;
    //GUARDA EL NOMBRE DEL JUGADOR QUE CERRÓ EL CUADRO, EN CASO DE SERLO
    private String propietario;
    
    public boolean getUsado(){
        return usado;
    }
    public void setUsado(boolean Usado){
        this.usado=Usado;
    }
    
    public String getPropietario(){
        return propietario;
    }
    public void setPropietario(String propietario){
        this.propietario=propietario;
        try{
            //EL CUADRO MOSTRARÁ EL PRIMER CARACTER DEL NOMBRE DEL PROPIETARIO
            this.setText(Character.toString(propietario.charAt(0)));
        }
        catch(Exception e){
            this.setText(null);
        }
    }
    
    public boolean getCuadro(){
        return Cuadro;
    }
    public void setCuadro(boolean Cuadro){
        this.Cuadro=Cuadro;
    }
}
