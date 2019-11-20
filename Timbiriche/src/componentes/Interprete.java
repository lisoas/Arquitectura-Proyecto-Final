package componentes;

//import javax.swing.JTextPane;
import comunicacion.Enlace;
//import javax.swing.text.StyledDocument;

/**
 * Clase encargada de diferenciar un mensaje de otro y notificar a la maquina.
 */
public class Interprete {
    
//    JTextPane panel;
    private Enlace enlace;
    private final String victoria="victoria", rendicion="rendicion", 
            continuar="continuar", nocontinuar="nocontinuar", ping="ping", 
            pong="pong", pingpong="pingpong";
    
    public Interprete(Enlace enlace){
        this.enlace=enlace;
    }
    
    public void interpretar(Object obj){
        //AQUI SE CREA UNA VARIABLE STRING PARA SU COMPARACION
        String verificar="";
        //SI EL OBJETO RECIBIDO ES UN STRING LO SEPARA, YA QUE SI ES DEL CHAT,
        //DEBIÓ LLEGAR CON EL FORMATO DE "##-MENSAJE"
        if(obj.getClass()==verificar.getClass()){
            String mensaje=obj.toString();
            String[] mensajes=mensaje.split("-");
            if(mensajes[0].equalsIgnoreCase("##"))
                enlace.actualizar(mensajes[1]);
            else{
                //EN CASO CONTRARIO PREGUNTA SI ES UN MENSAJE DE RENDICION DEL
                //OPONENTE
                if(mensaje.equalsIgnoreCase(victoria)){
                    enlace.ganar(mensaje);
                }
                else if(mensaje.equalsIgnoreCase(continuar) || 
                        mensaje.equalsIgnoreCase(nocontinuar)){
                    enlace.continuar(mensaje);
                }
                else if(mensaje.equalsIgnoreCase(ping)){
                    enlace.enviar(pong);
                }
                else if(mensaje.equalsIgnoreCase(pong)){
                    enlace.conectado();
                    enlace.enviar(pingpong);
                }
                else if(mensaje.equalsIgnoreCase(pingpong))
                    enlace.conectado();
                else{
                    //O SIMPLEMENTE RECIBIO LAS COORDENADAS DE UN MOVIMIENTO
                    String[] coord=mensaje.split(" ");
                    int x=Integer.parseInt(coord[0]);
                    int y=Integer.parseInt(coord[1]);
                    String nombre2=coord[2];
                    enlace.reAcomodar(x, y, nombre2);
                }
            }
        }
    }
    
    //METODO INSERVIBLE. SERVIRIA PARA CONVERTIR A ICONOS
//    public void Emoticonos(String verificar, Enlace enlace){
//        panel=new JTextPane();
//        String nuevoMensaje="";
//        String[] mensajes=verificar.split(" ");
//        StyledDocument doc;
//        for(int x=0;x<mensajes.length;x++){
//            try{
//                if(mensajes[x].charAt(0)==':'){
//                    
//                }
//                else{
//                    nuevoMensaje+=mensajes[x]+" ";
//                }
//            }catch(NullPointerException e){
//                
//            }
//        }
//        enlace.actualizar(nuevoMensaje);
//        enlace.actualizar(verificar);
//    }
}
