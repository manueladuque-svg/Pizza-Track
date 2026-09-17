// Controlador de pedidos: gestiona las pilas de Undo y Redo
public class GestionPedidos {

    private PilaPizza pilaPrincipal;   // Pedidos activos (Undo)
    private PilaPizza pilaSecundaria;  // Pedidos deshechos (Redo)

    public GestionPedidos() {
        this.pilaPrincipal = new PilaPizza();
        this.pilaSecundaria = new PilaPizza();
    }

    // Registra nuevo pedido en pila principal y limpia historial redo
    public void registrarPedido(Pizza pizza) {
        if (pizza == null) {
            System.out.println(" [ERROR] No se puede registrar un pedido nulo.");
            return;
        }

        pilaPrincipal.push(pizza);

        // Nuevo pedido invalida el rehacer previo
        if (!pilaSecundaria.isEmpty()) {
            pilaSecundaria.limpiar();
        }

        System.out.println(" [OK] Pedido registrado con exito en la Pila Principal.");
        System.out.println("      " + pizza);
    }

    // Undo: saca de principal y guarda en secundaria
    public boolean deshacer() {
        if (pilaPrincipal.isEmpty()) {
            System.out.println(" [AVISO] No hay pedidos en la Pila Principal para deshacer (Pila vacia).");
            return false;
        }

        Pizza pizzaDeshecha = pilaPrincipal.pop();
        pilaSecundaria.push(pizzaDeshecha);

        System.out.println(" [UNDO / DESHACER REALIZADO]");
        System.out.println("   -> Se retiro de pedidos activos: " + pizzaDeshecha.getNombre());
        System.out.println("   -> Movido a Pila Secundaria para posible recuperacion.");
        return true;
    }

    // Redo: saca de secundaria y restaura en principal
    public boolean rehacer() {
        if (pilaSecundaria.isEmpty()) {
            System.out.println(" [AVISO] No hay pedidos deshechos en la Pila Secundaria para rehacer.");
            return false;
        }

        Pizza pizzaRehecha = pilaSecundaria.pop();
        pilaPrincipal.push(pizzaRehecha);

        System.out.println(" [REDO / REHACER REALIZADO]");
        System.out.println("   -> Se restauro a pedidos activos: " + pizzaRehecha.getNombre());
        return true;
    }

    // Retorna el pedido actual en produccion (peek)
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

    // Imprime el estado visual de ambas pilas
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

    public PilaPizza getPilaPrincipal() {
        return pilaPrincipal;
    }

    public PilaPizza getPilaSecundaria() {
        return pilaSecundaria;
    }
}
