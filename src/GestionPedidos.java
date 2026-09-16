/**
 * Clase controladora del sistema Pizza-Track.
 * Coordina el flujo de pedidos y el mecanismo de Deshacer (Undo) y Rehacer (Redo)
 * utilizando dos pilas manuales basadas en listas ligadas.
 * 
 * Arquitectura de Pilas:
 * 1. Pila Principal (Undo): Almacena el historial de pedidos activos en orden cronológico inverso (LIFO).
 *    Permite eliminar el último pedido registrado mediante la acción 'Deshacer'.
 * 2. Pila Secundaria (Redo): Almacena temporalmente los pedidos que fueron deshechos,
 *    permitiendo restaurarlos inmediatamente a la lista activa mediante la acción 'Rehacer'.
 */
public class GestionPedidos {

    // Pila que mantiene los pedidos activos y listos para producción
    private PilaPizza pilaPrincipal;

    // Pila que almacena temporalmente los pedidos deshechos para su posible recuperación
    private PilaPizza pilaSecundaria;

    /**
     * Constructor que inicializa las dos pilas manuales en estado vacío.
     */
    public GestionPedidos() {
        this.pilaPrincipal = new PilaPizza();
        this.pilaSecundaria = new PilaPizza();
    }

    /**
     * Registra un nuevo pedido en el sistema.
     * Inserta la pizza en el tope de la Pila Principal (push).
     * 
     * Regla de gestión de historial:
     * Al registrar una nueva acción/pedido, se limpia la Pila Secundaria (Redo),
     * ya que se inicia una nueva línea de tiempo de pedidos y el historial
     * de rehacer previo pierde validez.
     *
     * @param pizza Objeto Pizza a registrar.
     */
    public void registrarPedido(Pizza pizza) {
        if (pizza == null) {
            System.out.println("❌ Error: No se puede registrar un pedido nulo.");
            return;
        }

        // Operación push en la Pila Principal
        pilaPrincipal.push(pizza);

        // Al crear un nuevo pedido, se invalida el historial de rehacer
        if (!pilaSecundaria.isEmpty()) {
            pilaSecundaria.limpiar();
        }

        System.out.println("✅ Pedido registrado con éxito en la Pila Principal.");
        System.out.println("   🍕 " + pizza);
    }

    /**
     * Deshace el último pedido realizado.
     * Operación Undo:
     * 1. Extrae (pop) la pizza del tope de la Pila Principal.
     * 2. Inserta (push) dicha pizza en el tope de la Pila Secundaria.
     *
     * @return true si la operación fue exitosa; false si la pila principal estaba vacía.
     */
    public boolean deshacer() {
        if (pilaPrincipal.isEmpty()) {
            System.out.println("⚠️ No hay pedidos en la Pila Principal para deshacer.");
            return false;
        }

        // Pop de la pila principal
        Pizza pizzaDeshecha = pilaPrincipal.pop();

        // Push a la pila secundaria
        pilaSecundaria.push(pizzaDeshecha);

        System.out.println("↩️ [DESHACER / UNDO realizado]");
        System.out.println("   Se retiró de pedidos activos: " + pizzaDeshecha.getNombre());
        System.out.println("   Guardada en Pila Secundaria para posible recuperación.");
        return true;
    }

    /**
     * Rehace el pedido que fue deshecho más recientemente.
     * Operación Redo:
     * 1. Extrae (pop) la pizza del tope de la Pila Secundaria.
     * 2. Inserta (push) dicha pizza de vuelta en el tope de la Pila Principal.
     *
     * @return true si la operación fue exitosa; false si la pila secundaria estaba vacía.
     */
    public boolean rehacer() {
        if (pilaSecundaria.isEmpty()) {
            System.out.println("⚠️ No hay pedidos deshechos en la Pila Secundaria para rehacer.");
            return false;
        }

        // Pop de la pila secundaria
        Pizza pizzaRehecha = pilaSecundaria.pop();

        // Push de vuelta a la pila principal
        pilaPrincipal.push(pizzaRehecha);

        System.out.println("↪️ [REHACER / REDO realizado]");
        System.out.println("   Se restauró a pedidos activos: " + pizzaRehecha.getNombre());
        return true;
    }

    /**
     * Consulta y muestra la pizza que está actualmente en el tope de la Pila Principal,
     * la cual corresponde al pedido más reciente listo para producción.
     * Utiliza la operación no destructiva peek().
     *
     * @return La Pizza en el tope de la Pila Principal, o null si está vacía.
     */
    public Pizza mostrarPedidoActual() {
        if (pilaPrincipal.isEmpty()) {
            System.out.println("ℹ️ No hay pedidos activos pendientes de producción en este momento.");
            return null;
        }

        Pizza pizzaActual = pilaPrincipal.peek();
        System.out.println("🔥 [PEDIDO ACTUAL LISTO PARA PRODUCCIÓN (PEEK)]");
        System.out.println("   🍕 " + pizzaActual);
        return pizzaActual;
    }

    /**
     * Muestra el estado visual de ambas pilas para monitoreo y fines didácticos.
     */
    public void mostrarEstadoPilas() {
        System.out.println("\n------------------------------------------------------------");
        System.out.println("📦 ESTADO ACTUAL DEL SISTEMA PIZZA-TRACK");
        System.out.println("------------------------------------------------------------");
        System.out.println("▶ PILA PRINCIPAL (Pedidos Activos / Undo) [" + pilaPrincipal.size() + " pedido(s)]:");
        pilaPrincipal.imprimirPila();

        System.out.println("\n▶ PILA SECUNDARIA (Pedidos Deshechos / Redo) [" + pilaSecundaria.size() + " pedido(s)]:");
        pilaSecundaria.imprimirPila();
        System.out.println("------------------------------------------------------------\n");
    }

    // Getters auxiliares para pruebas o inspección
    public PilaPizza getPilaPrincipal() {
        return pilaPrincipal;
    }

    public PilaPizza getPilaSecundaria() {
        return pilaSecundaria;
    }
}
