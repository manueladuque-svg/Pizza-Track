import java.util.Arrays;

// Modelo Pizza con catalogo predefinido y 3 ingredientes
public class Pizza {

    // Catalogo oficial de tipos de pizza permitidos
    public static final String[] TIPOS_VALIDOS = {
        "Margarita",
        "Pepperoni",
        "Hawaiana",
        "Cuatro Quesos",
        "Vegetariana",
        "Mexicana",
        "Pollo y Champinones"
    };

    // Catalogo oficial de ingredientes autorizados
    public static final String[] INGREDIENTES_VALIDOS = {
        "Queso Mozzarella",
        "Salsa de Tomate",
        "Jamon",
        "Pina",
        "Pepperoni",
        "Champinones",
        "Tocineta",
        "Pollo Desmechado",
        "Carne Molida",
        "Cebolla",
        "Pimenton",
        "Aceitunas Negras",
        "Albahaca Fresca",
        "Maiz Tierno"
    };

    private String nombre;
    private String[] ingredientes; // Tamano fijo de 3

    public Pizza(String nombre, String[] ingredientes) throws TipoPizzaInvalidoException, IngredientesInvalidosException {
        setNombre(nombre);
        setIngredientes(ingredientes);
    }

    // Valida si el tipo pertenece al catalogo
    public static boolean esTipoValido(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            return false;
        }
        String tipoLimpio = tipo.trim();
        for (String permitido : TIPOS_VALIDOS) {
            if (permitido.equalsIgnoreCase(tipoLimpio)) {
                return true;
            }
        }
        return false;
    }

    // Devuelve el nombre exacto como esta en el catalogo
    public static String obtenerTipoCanonico(String tipo) {
        if (tipo == null) return "";
        String tipoLimpio = tipo.trim();
        for (String permitido : TIPOS_VALIDOS) {
            if (permitido.equalsIgnoreCase(tipoLimpio)) {
                return permitido;
            }
        }
        return tipoLimpio;
    }

    public static String[] getTiposDisponibles() {
        return Arrays.copyOf(TIPOS_VALIDOS, TIPOS_VALIDOS.length);
    }

    // Valida si el ingrediente pertenece al catalogo
    public static boolean esIngredienteValido(String ingrediente) {
        if (ingrediente == null || ingrediente.trim().isEmpty()) {
            return false;
        }
        String limpio = ingrediente.trim();
        for (String permitido : INGREDIENTES_VALIDOS) {
            if (permitido.equalsIgnoreCase(limpio)) {
                return true;
            }
        }
        return false;
    }

    // Devuelve el ingrediente exacto como esta en el catalogo
    public static String obtenerIngredienteCanonico(String ingrediente) {
        if (ingrediente == null) return "";
        String limpio = ingrediente.trim();
        for (String permitido : INGREDIENTES_VALIDOS) {
            if (permitido.equalsIgnoreCase(limpio)) {
                return permitido;
            }
        }
        return limpio;
    }

    public static String[] getIngredientesDisponibles() {
        return Arrays.copyOf(INGREDIENTES_VALIDOS, INGREDIENTES_VALIDOS.length);
    }

    public String getNombre() {
        return nombre;
    }

    // Asigna el tipo tras validar que pertenezca al catalogo
    public void setNombre(String nombre) throws TipoPizzaInvalidoException {
        if (!esTipoValido(nombre)) {
            throw new TipoPizzaInvalidoException(
                "El tipo de pizza '" + (nombre == null ? "nulo" : nombre) + "' no es valido. "
                + "Especialidades permitidas: " + String.join(", ", TIPOS_VALIDOS)
            );
        }
        this.nombre = obtenerTipoCanonico(nombre);
    }

    public String[] getIngredientes() {
        return ingredientes;
    }

    // Valida exactamente 3 ingredientes, catalogo y sin repetidos
    public void setIngredientes(String[] ingredientes) throws IngredientesInvalidosException {
        if (ingredientes == null || ingredientes.length != 3) {
            throw new IngredientesInvalidosException(
                "La pizza requiere obligatoriamente un arreglo de tamano fijo con exactamente 3 ingredientes."
            );
        }

        String[] normalizados = new String[3];

        for (int i = 0; i < 3; i++) {
            if (ingredientes[i] == null || ingredientes[i].trim().isEmpty()) {
                throw new IngredientesInvalidosException(
                    "El ingrediente " + (i + 1) + " no puede ser nulo o estar vacio."
                );
            }

            if (!esIngredienteValido(ingredientes[i])) {
                throw new IngredientesInvalidosException(
                    "El ingrediente '" + ingredientes[i] + "' no pertenece al catalogo autorizado de ingredientes."
                );
            }

            normalizados[i] = obtenerIngredienteCanonico(ingredientes[i]);
        }

        // Evitar ingredientes duplicados en la misma pizza
        if (normalizados[0].equalsIgnoreCase(normalizados[1])
                || normalizados[0].equalsIgnoreCase(normalizados[2])
                || normalizados[1].equalsIgnoreCase(normalizados[2])) {
            throw new IngredientesInvalidosException(
                "No se permite repetir ingredientes en la misma pizza. Deben seleccionarse 3 ingredientes diferentes."
            );
        }

        this.ingredientes = normalizados;
    }

    @Override
    public String toString() {
        return String.format("Pizza: '%s' | Ingredientes: [1: %s, 2: %s, 3: %s]",
                nombre, ingredientes[0], ingredientes[1], ingredientes[2]);
    }
}
