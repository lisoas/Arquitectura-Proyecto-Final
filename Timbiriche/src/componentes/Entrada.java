package componentes;

import comunicacion.Enlace;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Servidor de entrada. Solo se encarga de la comunicación, pues todo lo que
 * entra, lo manda directamente al interprete para ser procesado.
 */
public class Entrada extends Thread{
    
    private Socket socket;
    private ServerSocket servidor;
    private DataInputStream flujo_entrada;
    private DataOutputStream flujo_salida;
    private Enlace enlace;
    
    public Entrada(int port, Enlace enlace){
        this.enlace=enlace;
        try {
            servidor=new ServerSocket(port);
        }
        catch(IOException ex ){
            System.out.println("puerto ocupado");
        }
        enlace.enviar("ping");
    }
    
    @Override
    public void run(){
        String ej="";
        while(true){
            Object input=null;
            try{
                socket=servidor.accept();
                flujo_entrada = new DataInputStream(socket.getInputStream());
                flujo_salida = new DataOutputStream(socket.getOutputStream());
                
                input = flujo_entrada.readUTF();
                flujo_salida.writeUTF(ej);
                socket.close();
            }
            catch(IOException e){
                
            }
            //RECIBE UN MENSAJE Y LO ENVIA AL INTERPRETE
            enlace.interpretar(input);
        }
    }

        
}
