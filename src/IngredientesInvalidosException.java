// Excepcion para validacion de los 3 ingredientes requeridos
public class IngredientesInvalidosException extends Exception {

    public IngredientesInvalidosException(String mensaje) {
        super(mensaje);
    }

    public IngredientesInvalidosException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
