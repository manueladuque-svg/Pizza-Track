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
 * - IngredientesInvalidosException: Controla el arreglo estricto de 3 ingredientes no vacios.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestionPedidos sistema = new GestionPedidos();

        boolean salir = false;

        System.out.println("============================================================");
        System.out.println("              SISTEMA DE PEDIDOS PIZZA-TRACK                ");
        System.out.println("    Gestion con Estructuras de Datos: Pilas y Listas Ligadas ");
        System.out.println("============================================================");

        while (!salir) {
            mostrarMenu();
            System.out.print(">> Ingrese una opcion: ");
            String opcionStr = scanner.nextLine().trim();

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
                scanner.nextLine();
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
     * pertenezca al catalogo oficial y que contenga 3 ingredientes obligatorios.
     * Implementa control estricto con excepciones personalizadas mediante try-catch.
     *
     * @param scanner Objeto Scanner para capturar datos por teclado.
     * @param sistema Instancia de GestionPedidos.
     */
    private static void registrarPizzaInteractiva(Scanner scanner, GestionPedidos sistema) {
        System.out.println("\n------------------------------------------------------------");
        System.out.println("  REGISTRO DE NUEVO PEDIDO (PUSH)");
        System.out.println("------------------------------------------------------------");

        String[] catalogo = Pizza.getTiposDisponibles();
        System.out.println("Catalogo de Especialidades Autorizadas:");
        for (int i = 0; i < catalogo.length; i++) {
            System.out.println("  [" + (i + 1) + "] " + catalogo[i]);
        }
        System.out.println("------------------------------------------------------------");

        String nombreElegido = null;

        // Bucle de captura del tipo de pizza con control de TipoPizzaInvalidoException
        while (nombreElegido == null) {
            System.out.print(">> Seleccione el numero [1-" + catalogo.length + "] o escriba el nombre de la pizza: ");
            String entrada = scanner.nextLine().trim();

            try {
                // Verificar si ingreso un numero de indice
                if (entrada.matches("\\d+")) {
                    int indice = Integer.parseInt(entrada);
                    if (indice >= 1 && indice <= catalogo.length) {
                        nombreElegido = catalogo[indice - 1];
                    } else {
                        throw new TipoPizzaInvalidoException(
                            "El indice '" + entrada + "' no corresponde a ninguna especialidad autorizada."
                        );
                    }
                } else {
                    // Validar si el texto ingresado coincide con alguna especialidad
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

        System.out.println(">> Especialidad seleccionada: " + nombreElegido);

        // Arreglo obligatorio de tamano fijo (3) para almacenar los ingredientes
        String[] ingredientes = new String[3];
        boolean ingredientesCorrectos = false;

        while (!ingredientesCorrectos) {
            try {
                System.out.println("\nIngrese a continuacion los 3 ingredientes requeridos:");
                for (int i = 0; i < 3; i++) {
                    System.out.print("   * Ingrediente " + (i + 1) + " de 3: ");
                    String ing = scanner.nextLine().trim();

                    if (ing.isEmpty()) {
                        throw new IngredientesInvalidosException(
                            "El ingrediente " + (i + 1) + " no puede estar vacio."
                        );
                    }
                    ingredientes[i] = ing;
                }

                // Instanciacion del modelo Pizza controlando posibles excepciones
                Pizza nuevaPizza = new Pizza(nombreElegido, ingredientes);
                
                // Registro (Push) en la Pila Principal a traves del controlador
                sistema.registrarPedido(nuevaPizza);
                ingredientesCorrectos = true;

            } catch (IngredientesInvalidosException ex) {
                System.out.println(" [ERROR: IngredientesInvalidosException] " + ex.getMessage());
                System.out.println("   Por favor reingrese los 3 ingredientes desde el inicio.");
            } catch (TipoPizzaInvalidoException ex) {
                System.out.println(" [ERROR: TipoPizzaInvalidoException] " + ex.getMessage());
                break;
            }
        }
    }
}
