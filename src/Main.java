import java.util.Scanner;

/**
 * Clase principal que ejecuta el sistema de consola interactivo Pizza-Track.
 * Cumple con el menu estricto de 5 opciones del enunciado:
 * 1. Registrar Pizza (Escribir / Push)
 * 2. Deshacer (Undo: Pop Principal -> Push Secundaria)
 * 3. Rehacer (Redo: Pop Secundaria -> Push Principal)
 * 4. Mostrar Pedido Actual (Peek)
 * 0. Salir
 * 
 * Incorpora control con excepciones personalizadas:
 * - TipoPizzaInvalidoException: Controla que solo se admitan especialidades del catalogo.
 * - IngredientesInvalidosException: Controla el arreglo estricto de 3 ingredientes no vacios,
 *   pertenecientes al catalogo oficial y sin duplicados.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestionPedidos sistema = new GestionPedidos();

        boolean salir = false;
        String proximaOpcion = null;

        System.out.println("============================================================");
        System.out.println("              SISTEMA DE PEDIDOS PIZZA-TRACK                ");
        System.out.println("    Gestion con Estructuras de Datos: Pilas y Listas Ligadas ");
        System.out.println("============================================================");

        while (!salir) {
            String opcionStr;
            if (proximaOpcion != null && !proximaOpcion.isEmpty()) {
                mostrarMenu();
                System.out.println(">> Ingrese una opcion: " + proximaOpcion);
                opcionStr = proximaOpcion;
                proximaOpcion = null;
            } else {
                mostrarMenu();
                System.out.print(">> Ingrese una opcion: ");
                opcionStr = scanner.nextLine().trim();
            }

            switch (opcionStr) {
                case "1":
                    // 1. Registrar Pizza (Escribir): Solicita nombre y 3 ingredientes (arreglo)
                    // Ejecuta push() en la Pila Principal bajo control de excepciones
                    registrarPizzaInteractiva(scanner, sistema);
                    break;

                case "2":
                    // 2. Deshacer (Undo): Pop de pila principal y Push a la secundaria
                    System.out.println("\n------------------------------------------------------------");
                    System.out.println("  OPERACION: DESHACER (UNDO)");
                    System.out.println("------------------------------------------------------------");
                    sistema.deshacer();
                    break;

                case "3":
                    // 3. Rehacer (Redo): Pop de pila secundaria y Push a la principal
                    System.out.println("\n------------------------------------------------------------");
                    System.out.println("  OPERACION: REHACER (REDO)");
                    System.out.println("------------------------------------------------------------");
                    sistema.rehacer();
                    break;

                case "4":
                    // 4. Mostrar Pedido Actual: Utiliza peek() de la pila principal
                    System.out.println("\n------------------------------------------------------------");
                    System.out.println("  OPERACION: MOSTRAR PEDIDO ACTUAL (PEEK)");
                    System.out.println("------------------------------------------------------------");
                    sistema.mostrarPedidoActual();
                    break;

                case "0":
                    // 0. Salir
                    System.out.println("\n============================================================");
                    System.out.println("  Gracias por usar Pizza-Track. Saliendo del sistema...");
                    System.out.println("============================================================");
                    salir = true;
                    break;

                default:
                    System.out.println("\n [AVISO] Opcion invalida. Por favor, ingrese un numero valido (1, 2, 3, 4 o 0).");
                    break;
            }

            if (!salir) {
                System.out.print("\nPresione ENTER para continuar al menu...");
                String entradaPausa = scanner.nextLine().trim();
                if (!entradaPausa.isEmpty()) {
                    proximaOpcion = entradaPausa;
                }
            }
        }

        scanner.close();
    }

    /**
     * Muestra el menu principal ajustado estrictamente al diseno del enunciado.
     */
    private static void mostrarMenu() {
        System.out.println();
        System.out.println("+----------------------------------------------------------+");
        System.out.println("|                     MENU PRINCIPAL                       |");
        System.out.println("+----------------------------------------------------------+");
        System.out.println("| 1. Registrar Pizza (Escribir / Push Pila Principal)      |");
        System.out.println("| 2. Deshacer (Undo: Pop Principal -> Push Secundaria)     |");
        System.out.println("| 3. Rehacer (Redo: Pop Secundaria -> Push Principal)      |");
        System.out.println("| 4. Mostrar Pedido Actual (Peek Pila Principal)           |");
        System.out.println("| 0. Salir                                                 |");
        System.out.println("+----------------------------------------------------------+");
    }

    /**
     * Solicita interactivamente los datos de la pizza asegurando que el tipo de pizza
     * y cada uno de los 3 ingredientes pertenezcan a los catalogos oficiales autorizados.
     * Implementa control estricto con excepciones personalizadas mediante try-catch.
     *
     * @param scanner Objeto Scanner para capturar datos por teclado.
     * @param sistema Instancia de GestionPedidos.
     */
    private static void registrarPizzaInteractiva(Scanner scanner, GestionPedidos sistema) {
        System.out.println("\n------------------------------------------------------------");
        System.out.println("  REGISTRO DE NUEVO PEDIDO (PUSH)");
        System.out.println("------------------------------------------------------------");

        // 1. SELECCION Y VALIDACION DEL TIPO DE PIZZA
        String[] catalogoPizzas = Pizza.getTiposDisponibles();
        System.out.println("Catalogo de Especialidades Autorizadas:");
        for (int i = 0; i < catalogoPizzas.length; i++) {
            System.out.println("  [" + (i + 1) + "] " + catalogoPizzas[i]);
        }
        System.out.println("------------------------------------------------------------");

        String nombreElegido = null;

        while (nombreElegido == null) {
            System.out.print(">> Seleccione el numero [1-" + catalogoPizzas.length + "] o escriba el nombre de la pizza: ");
            String entrada = scanner.nextLine().trim();

            try {
                if (entrada.matches("\\d+")) {
                    int indice = Integer.parseInt(entrada);
                    if (indice >= 1 && indice <= catalogoPizzas.length) {
                        nombreElegido = catalogoPizzas[indice - 1];
                    } else {
                        throw new TipoPizzaInvalidoException(
                            "El indice '" + entrada + "' no corresponde a ninguna especialidad autorizada."
                        );
                    }
                } else {
                    if (Pizza.esTipoValido(entrada)) {
                        nombreElegido = Pizza.obtenerTipoCanonico(entrada);
                    } else {
                        throw new TipoPizzaInvalidoException(
                            "El tipo '" + entrada + "' no es valido. No se admiten pizzas no autorizadas."
                        );
                    }
                }
            } catch (TipoPizzaInvalidoException ex) {
                System.out.println(" [ERROR: TipoPizzaInvalidoException] " + ex.getMessage());
                System.out.println("   Intente nuevamente con una especialidad del catalogo.");
            }
        }

        System.out.println(">> Especialidad confirmada: " + nombreElegido);

        // 2. SELECCION Y VALIDACION DE LOS 3 INGREDIENTES
        String[] catalogoIngredientes = Pizza.getIngredientesDisponibles();
        System.out.println("\n------------------------------------------------------------");
        System.out.println("Catalogo de Ingredientes Autorizados (Seleccione 3 distintos):");
        for (int i = 0; i < catalogoIngredientes.length; i++) {
            System.out.printf("  [%2d] %-22s", (i + 1), catalogoIngredientes[i]);
            if ((i + 1) % 2 == 0 || i == catalogoIngredientes.length - 1) {
                System.out.println();
            }
        }
        System.out.println("------------------------------------------------------------");

        // Arreglo obligatorio de tamano fijo (3)
        String[] ingredientes = new String[3];

        for (int i = 0; i < 3; i++) {
            boolean ingredienteValido = false;

            while (!ingredienteValido) {
                System.out.print(">> Ingrese el Ingrediente " + (i + 1) + " de 3 (numero [1-" + catalogoIngredientes.length + "] o nombre): ");
                String entradaIng = scanner.nextLine().trim();

                try {
                    String seleccionado = null;

                    if (entradaIng.matches("\\d+")) {
                        int idx = Integer.parseInt(entradaIng);
                        if (idx >= 1 && idx <= catalogoIngredientes.length) {
                            seleccionado = catalogoIngredientes[idx - 1];
                        } else {
                            throw new IngredientesInvalidosException(
                                "El numero '" + entradaIng + "' esta fuera de rango [1-" + catalogoIngredientes.length + "]."
                            );
                        }
                    } else {
                        if (Pizza.esIngredienteValido(entradaIng)) {
                            seleccionado = Pizza.obtenerIngredienteCanonico(entradaIng);
                        } else {
                            throw new IngredientesInvalidosException(
                                "'" + entradaIng + "' no es un ingrediente permitido del catalogo oficial."
                            );
                        }
                    }

                    // Validar si ya fue seleccionado en esta misma pizza
                    for (int j = 0; j < i; j++) {
                        if (ingredientes[j].equalsIgnoreCase(seleccionado)) {
                            throw new IngredientesInvalidosException(
                                "El ingrediente '" + seleccionado + "' ya fue agregado previamente. No se permiten ingredientes repetidos."
                            );
                        }
                    }

                    ingredientes[i] = seleccionado;
                    ingredienteValido = true;
                    System.out.println("   [OK] Ingrediente " + (i + 1) + " asignado: " + seleccionado);

                } catch (IngredientesInvalidosException ex) {
                    System.out.println(" [ERROR: IngredientesInvalidosException] " + ex.getMessage());
                    System.out.println("   Por favor elija un ingrediente valido del catalogo.");
                }
            }
        }

        // 3. CREACION DEL OBJETO PIZZA Y PUSH EN PILA PRINCIPAL
        try {
            Pizza nuevaPizza = new Pizza(nombreElegido, ingredientes);
            sistema.registrarPedido(nuevaPizza);
        } catch (TipoPizzaInvalidoException | IngredientesInvalidosException ex) {
            System.out.println(" [ERROR AL CREAR PEDIDO] " + ex.getMessage());
        }
    }
}
