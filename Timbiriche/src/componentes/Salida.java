package componentes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Cliente de salida. Recibe mensajes para enviar al otro jugador.
 */
public class Salida {
    
    private int port2;
    private Socket cliente;
    private DataOutputStream flujo_de_salida;
    private DataInputStream flujo_de_entrada;
    private String ip;
    private Object mensaje;
    
    public Salida(String ip, int port2, String mensaje){
        this.port2=port2;
        this.ip=ip;
        this.mensaje=mensaje;
        mensaje_Saliente();
    }
    
    public void mensaje_Saliente(){
        try{
            cliente = new Socket(ip, port2);
            flujo_de_entrada = new DataInputStream(cliente.getInputStream());
            flujo_de_salida = new DataOutputStream(cliente.getOutputStream());
            flujo_de_salida.writeUTF((String) mensaje);
            flujo_de_entrada.readUTF();
            cliente.close();
        }
        catch(Exception ex){}
    }
    
}
