/**
 * Clase controladora del sistema Pizza-Track.
 * Coordina el flujo de pedidos y el mecanismo de Deshacer (Undo) y Rehacer (Redo)
 * utilizando dos pilas manuales basadas en listas ligadas.
 * 
 * Arquitectura de Pilas:
 * 1. Pila Principal (Undo): Almacena el historial de pedidos activos en orden cronologico inverso (LIFO).
 *    Permite eliminar el ultimo pedido registrado mediante la accion 'Deshacer'.
 * 2. Pila Secundaria (Redo): Almacena temporalmente los pedidos que fueron deshechos,
 *    permitiendo restaurarlos inmediatamente a la lista activa mediante la accion 'Rehacer'.
 */
public class GestionPedidos {

    // Pila que mantiene los pedidos activos y listos para produccion
    private PilaPizza pilaPrincipal;

    // Pila que almacena temporalmente los pedidos deshechos para su posible recuperacion
    private PilaPizza pilaSecundaria;

    /**
     * Constructor que inicializa las dos pilas manuales en estado vacio.
     */
    public GestionPedidos() {
        this.pilaPrincipal = new PilaPizza();
        this.pilaSecundaria = new PilaPizza();
    }

    /**
     * Registra un nuevo pedido en el sistema.
     * Inserta la pizza en el tope de la Pila Principal (push).
     * 
     * Regla de gestion de historial:
     * Al registrar una nueva accion/pedido, se limpia la Pila Secundaria (Redo),
     * ya que se inicia una nueva linea de tiempo de pedidos y el historial
     * de rehacer previo pierde validez.
     *
     * @param pizza Objeto Pizza a registrar.
     */
    public void registrarPedido(Pizza pizza) {
        if (pizza == null) {
            System.out.println(" [ERROR] No se puede registrar un pedido nulo.");
            return;
        }

        // Operacion push en la Pila Principal
        pilaPrincipal.push(pizza);

        // Al crear un nuevo pedido, se invalida el historial de rehacer
        if (!pilaSecundaria.isEmpty()) {
            pilaSecundaria.limpiar();
        }

        System.out.println(" [OK] Pedido registrado con exito en la Pila Principal.");
        System.out.println("      " + pizza);
    }

    /**
     * Deshace el ultimo pedido realizado.
     * Operacion Undo:
     * 1. Extrae (pop) la pizza del tope de la Pila Principal.
     * 2. Inserta (push) dicha pizza en el tope de la Pila Secundaria.
     *
     * @return true si la operacion fue exitosa; false si la pila principal estaba vacia.
     */
    public boolean deshacer() {
        if (pilaPrincipal.isEmpty()) {
            System.out.println(" [AVISO] No hay pedidos en la Pila Principal para deshacer (Pila vacia).");
            return false;
        }

        // Pop de la pila principal
        Pizza pizzaDeshecha = pilaPrincipal.pop();

        // Push a la pila secundaria
        pilaSecundaria.push(pizzaDeshecha);

        System.out.println(" [UNDO / DESHACER REALIZADO]");
        System.out.println("   -> Se retiro de pedidos activos: " + pizzaDeshecha.getNombre());
        System.out.println("   -> Movido a Pila Secundaria para posible recuperacion.");
        return true;
    }

    /**
     * Rehace el pedido que fue deshecho mas recientemente.
     * Operacion Redo:
     * 1. Extrae (pop) la pizza del tope de la Pila Secundaria.
     * 2. Inserta (push) dicha pizza de vuelta en el tope de la Pila Principal.
     *
     * @return true si la operacion fue exitosa; false si la pila secundaria estaba vacia.
     */
    public boolean rehacer() {
        if (pilaSecundaria.isEmpty()) {
            System.out.println(" [AVISO] No hay pedidos deshechos en la Pila Secundaria para rehacer.");
            return false;
        }

        // Pop de la pila secundaria
        Pizza pizzaRehecha = pilaSecundaria.pop();

        // Push de vuelta a la pila principal
        pilaPrincipal.push(pizzaRehecha);

        System.out.println(" [REDO / REHACER REALIZADO]");
        System.out.println("   -> Se restauro a pedidos activos: " + pizzaRehecha.getNombre());
        return true;
    }

    /**
     * Consulta y muestra la pizza que esta actualmente en el tope de la Pila Principal,
     * la cual corresponde al pedido mas reciente listo para produccion.
     * Utiliza la operacion no destructiva peek().
     *
     * @return La Pizza en el tope de la Pila Principal, o null si esta vacia.
     */
    public Pizza mostrarPedidoActual() {
        if (pilaPrincipal.isEmpty()) {
            System.out.println(" [INFO] No hay pedidos activos pendientes de produccion en este momento.");
            return null;
        }

        Pizza pizzaActual = pilaPrincipal.peek();
        System.out.println(" [PEEK / PEDIDO ACTUAL LISTO PARA PRODUCCION]");
        System.out.println("   -> " + pizzaActual);
        return pizzaActual;
    }

    /**
     * Muestra el estado visual de ambas pilas para monitoreo y fines didacticos.
     */
    public void mostrarEstadoPilas() {
        System.out.println("\n------------------------------------------------------------");
        System.out.println(" ESTADO ACTUAL DEL SISTEMA PIZZA-TRACK");
        System.out.println("------------------------------------------------------------");
        System.out.println(" PILA PRINCIPAL (Pedidos Activos / Undo) [" + pilaPrincipal.size() + " pedido(s)]:");
        pilaPrincipal.imprimirPila();

        System.out.println("\n PILA SECUNDARIA (Pedidos Deshechos / Redo) [" + pilaSecundaria.size() + " pedido(s)]:");
        pilaSecundaria.imprimirPila();
        System.out.println("------------------------------------------------------------\n");
    }

    // Getters auxiliares para pruebas o inspeccion
    public PilaPizza getPilaPrincipal() {
        return pilaPrincipal;
    }

    public PilaPizza getPilaSecundaria() {
        return pilaSecundaria;
    }
}
