package comunicacion;

/**
 * Interfaz que enlaza la maquina, el interprete y el servidor de entrada.
 */
import componentes.Boton;

public interface Enlace {
    //RECIBE EL MENSAJE Y ACTUALIZA EL HISTORIAL DE CONVERSACION EN LA VISTA
    public void actualizar(String mensaje);
    //ENVIA UN MENSAJE DEL CHAT
    public void enviar();
    //ENVIA VARIOS TIPOS DE MENSAJES
    public void enviar(String obj);
    //RECIBE UN MENSAJE Y LO ENVIA AL INTERPRETE PARA SU PROCESAMIENTO
    public void interpretar(Object obj);
    //RECIBE UNA MATRIZ DE OBJETOS Y REACOMODA LA ACTUAL
    public void reAcomodar(Boton[][] matriz);
    //RECIBE COORDENADAS Y NOMBRE Y ACTUALIZA EL TABLERO
    public void reAcomodar(int x, int y, String nombre2);
    //EN CASO DE QUE EL JUEGO TERMINE
    public void ganar(String maybe);
    //CONTINUAR O NO
    public void continuar(String cont);
    //VERIFICA LA CONEXION
    public void conectado();
}
