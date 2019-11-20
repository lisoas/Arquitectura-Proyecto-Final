package comunicacion;



/**
 * Interfaz que enlaza la ventana de inicio con la vista del juego y permite la
 * creación de la maquina de estados.
 */
public interface StartVista {
    //RECIBE LOS DATOS QUE VAN A CREAR LA VISTA Y LA MAQUINA.
public void crear(String nombre, String ip, int port1, int port2, String p);
}
