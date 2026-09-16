import java.util.Arrays;

/**
 * Clase que representa el modelo de datos de una Pizza en el sistema Pizza-Track.
 * Este objeto encapsula la informacion de cada pedido y es el elemento que se
 * almacena y manipula dentro de las pilas basadas en listas ligadas.
 * 
 * Restricciones del modelo:
 * 1. Solo acepta tipos de pizza del catalogo oficial predefinido.
 * 2. Requiere obligatoriamente un arreglo de tamano fijo exactamente de 3 ingredientes
 *    pertenecientes al catalogo oficial de ingredientes autorizados y sin repeticiones.
 * 3. Valida datos mediante excepciones personalizadas:
 *    - TipoPizzaInvalidoException
 *    - IngredientesInvalidosException
 */
public class Pizza {

    /**
     * Catalogo oficial de tipos de pizza permitidos por la pizzeria.
     */
    public static final String[] TIPOS_VALIDOS = {
        "Margarita",
        "Pepperoni",
        "Hawaiana",
        "Cuatro Quesos",
        "Vegetariana",
        "Mexicana",
        "Pollo y Champinones"
    };

    /**
     * Catalogo oficial de ingredientes autorizados para la preparacion.
     */
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

    // Nombre/tipo validado de la pizza
    private String nombre;

    // Arreglo de tamano fijo (3) obligatorio para almacenar los ingredientes
    private String[] ingredientes;

    /**
     * Constructor para inicializar una Pizza validando su tipo y sus 3 ingredientes.
     *
     * @param nombre       Nombre o tipo de especialidad de la pizza.
     * @param ingredientes Arreglo de cadenas que contiene exactamente 3 ingredientes.
     * @throws TipoPizzaInvalidoException     Si el nombre no pertenece al catalogo oficial.
     * @throws IngredientesInvalidosException Si el arreglo no tiene longitud 3, contiene ingredientes
     *                                        no autorizados, vacios o repetidos.
     */
    public Pizza(String nombre, String[] ingredientes) throws TipoPizzaInvalidoException, IngredientesInvalidosException {
        setNombre(nombre);
        setIngredientes(ingredientes);
    }

    /**
     * Valida si un nombre o tipo dado corresponde a una de las especialidades autorizadas.
     * Realiza comparacion insensible a mayusculas/minusculas y espacios.
     *
     * @param tipo Nombre a evaluar.
     * @return true si es valido, false en caso contrario.
     */
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

    /**
     * Obtiene el nombre canonico correspondiente al tipo ingresado.
     *
     * @param tipo Nombre ingresado.
     * @return Nombre oficial del catalogo si existe, o el mismo string limpio.
     */
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

    /**
     * Retorna una copia del catalogo de tipos de pizza disponibles.
     * @return Arreglo con los nombres permitidos.
     */
    public static String[] getTiposDisponibles() {
        return Arrays.copyOf(TIPOS_VALIDOS, TIPOS_VALIDOS.length);
    }

    /**
     * Valida si un ingrediente dado pertenece al catalogo oficial autorizado.
     * Comparacion insensible a mayusculas/minusculas y tildes comunes.
     *
     * @param ingrediente Nombre del ingrediente a evaluar.
     * @return true si es valido, false en caso contrario.
     */
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

    /**
     * Obtiene el nombre canonico oficial del ingrediente.
     *
     * @param ingrediente Texto del ingrediente.
     * @return Nombre oficial estandarizado del catalogo.
     */
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

    /**
     * Retorna una copia del catalogo de ingredientes disponibles.
     * @return Arreglo con los ingredientes autorizados.
     */
    public static String[] getIngredientesDisponibles() {
        return Arrays.copyOf(INGREDIENTES_VALIDOS, INGREDIENTES_VALIDOS.length);
    }

    /**
     * Obtiene el nombre/tipo de la pizza.
     * @return Nombre de la pizza.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el tipo de pizza con validacion estricta.
     * 
     * @param nombre Nuevo tipo de pizza.
     * @throws TipoPizzaInvalidoException Si el tipo no esta en el catalogo permitido.
     */
    public void setNombre(String nombre) throws TipoPizzaInvalidoException {
        if (!esTipoValido(nombre)) {
            throw new TipoPizzaInvalidoException(
                "El tipo de pizza '" + (nombre == null ? "nulo" : nombre) + "' no es valido. "
                + "Especialidades permitidas: " + String.join(", ", TIPOS_VALIDOS)
            );
        }
        this.nombre = obtenerTipoCanonico(nombre);
    }

    /**
     * Obtiene el arreglo fijo de 3 ingredientes.
     * @return Arreglo de tamano 3 con los ingredientes.
     */
    public String[] getIngredientes() {
        return ingredientes;
    }

    /**
     * Establece los ingredientes verificando el tamano fijo estricto de 3 elementos,
     * que pertenezcan al catalogo oficial y que no esten duplicados.
     * 
     * @param ingredientes Arreglo con exactamente 3 ingredientes no vacios.
     * @throws IngredientesInvalidosException Si el arreglo no tiene longitud 3, contiene ingredientes
     *                                        no autorizados o estan duplicados.
     */
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

        // Validacion de no repeticion de ingredientes
        if (normalizados[0].equalsIgnoreCase(normalizados[1])
                || normalizados[0].equalsIgnoreCase(normalizados[2])
                || normalizados[1].equalsIgnoreCase(normalizados[2])) {
            throw new IngredientesInvalidosException(
                "No se permite repetir ingredientes en la misma pizza. Deben seleccionarse 3 ingredientes diferentes."
            );
        }

        this.ingredientes = normalizados;
    }

    /**
     * Representacion textual detallada de la Pizza.
     * @return Cadena formateada con el nombre y los 3 ingredientes.
     */
    @Override
    public String toString() {
        return String.format("Pizza: '%s' | Ingredientes: [1: %s, 2: %s, 3: %s]",
                nombre, ingredientes[0], ingredientes[1], ingredientes[2]);
    }
}
