// Excepcion para tipos de pizza no permitidos en el catalogo
public class TipoPizzaInvalidoException extends Exception {

    public TipoPizzaInvalidoException(String mensaje) {
        super(mensaje);
    }

    public TipoPizzaInvalidoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
