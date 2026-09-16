/**
 * Excepción personalizada para controlar el registro de tipos de pizza no autorizados
 * dentro del sistema Pizza-Track.
 * 
 * Se lanza cuando un pedido intenta registrar un nombre/tipo que no se encuentra
 * en el catálogo oficial de especialidades permitidas.
 */
public class TipoPizzaInvalidoException extends Exception {

    public TipoPizzaInvalidoException(String mensaje) {
        super(mensaje);
    }

    public TipoPizzaInvalidoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
