/**
 * Implementación manual de una Pila (Stack) dinámica basada en Listas Ligadas.
 * Cumple con la política LIFO (Last-In, First-Out: El último en entrar es el primero en salir).
 * 
 * RESTRICCIÓN DEL PROYECTO:
 * No utiliza java.util.Stack ni ninguna otra estructura de colecciones de Java.
 * Todas las operaciones se realizan manipulando manualmente referencias en memoria (punteros).
 * 
 * Lógica de Punteros:
 * - El puntero 'tope' apunta siempre al nodo situado en la cima de la pila.
 * - Al insertar (push), el nuevo nodo enlaza su puntero 'siguiente' hacia el 'tope' actual,
 *   y luego el puntero 'tope' se actualiza para apuntar al nuevo nodo.
 * - Al extraer (pop), se recupera el elemento apuntado por 'tope' y la referencia 'tope'
 *   se desplaza hacia el nodo al que apuntaba 'tope.getSiguiente()'.
 */
public class PilaPizza {

    // Puntero raíz/cabeza de la lista ligada que referencia al elemento en la cima
    private NodoPizza tope;

    // Contador del número de elementos presentes en la pila
    private int tamanio;

    /**
     * Constructor que inicializa una pila vacía.
     * El puntero 'tope' apunta a null indicando que no hay nodos conectados.
     */
    public PilaPizza() {
        this.tope = null;
        this.tamanio = 0;
    }

    /**
     * Inserta un objeto Pizza en el tope de la pila.
     * Complejidad temporal: O(1)
     * 
     * Lógica de punteros (Paso a paso):
     * 1. Se crea un nuevo objeto NodoPizza en el heap que contiene la Pizza.
     * 2. El puntero 'siguiente' del nuevo nodo se hace apuntar a la dirección
     *    del nodo actualmente en el 'tope'.
     * 3. El puntero 'tope' se reasigna para apuntar al nuevo nodo creado,
     *    convirtiéndolo en la nueva cima de la estructura.
     *
     * @param pizza Objeto Pizza a insertar en la pila.
     */
    public void push(Pizza pizza) {
        if (pizza == null) {
            throw new IllegalArgumentException("No se puede apilar un objeto nulo.");
        }

        // Paso 1 y 2: Creamos el nuevo nodo y enlazamos su puntero siguiente al nodo que actualmente está en el tope
        NodoPizza nuevoNodo = new NodoPizza(pizza, this.tope);

        // Paso 3: Actualizamos el puntero 'tope' para que apunte al nuevo nodo recién creado
        this.tope = nuevoNodo;

        this.tamanio++;
    }

    /**
     * Retira el objeto del tope de la pila y devuelve su contenido.
     * Complejidad temporal: O(1)
     * 
     * Lógica de punteros (Paso a paso):
     * 1. Se valida si la pila está vacía (tope == null). En tal caso, se retorna null o excepción.
     * 2. Se almacena la referencia de la Pizza contenida en el nodo del 'tope'.
     * 3. Se avanza el puntero 'tope' hacia el siguiente nodo de la lista: 'tope = tope.getSiguiente()'.
     *    El nodo extraído queda desvinculado de la cadena y será reclamado por el Garbage Collector.
     * 4. Se decrementa el contador de tamaño y se retorna la Pizza obtenida.
     *
     * @return El objeto Pizza retirado del tope, o null si la pila está vacía.
     */
    public Pizza pop() {
        if (isEmpty()) {
            return null;
        }

        // Recuperamos el dato alojado en el nodo de la cima
        Pizza pizzaExtraida = this.tope.getPizza();

        // Desplazamos el puntero 'tope' al siguiente nodo inferior en la pila
        this.tope = this.tope.getSiguiente();

        this.tamanio--;
        return pizzaExtraida;
    }

    /**
     * Visualiza la pizza en el tope sin retirarla de la lista (inspección no destructiva).
     * Complejidad temporal: O(1)
     * 
     * Lógica de punteros:
     * - No modifica ningún puntero de la estructura.
     * - Únicamente consulta la referencia contenida en 'tope.getPizza()'.
     *
     * @return El objeto Pizza en el tope, o null si la pila está vacía.
     */
    public Pizza peek() {
        if (isEmpty()) {
            return null;
        }
        return this.tope.getPizza();
    }

    /**
     * Valida si la pila de pedidos se encuentra vacía.
     * 
     * Lógica de punteros:
     * - Una pila está vacía si su puntero 'tope' no apunta a ninguna dirección de memoria (tope == null).
     *
     * @return true si la pila no tiene elementos; false en caso contrario.
     */
    public boolean isEmpty() {
        return this.tope == null;
    }

    /**
     * Retorna la cantidad actual de elementos en la pila.
     * @return Entero con el número de elementos.
     */
    public int size() {
        return this.tamanio;
    }

    /**
     * Vacía completamente la pila reasignando el puntero 'tope' a null.
     * Al romper la referencia de cabeza, los nodos subsiguientes quedan sin referencias
     * activas y son liberados por el Garbage Collector de Java.
     */
    public void limpiar() {
        this.tope = null;
        this.tamanio = 0;
    }

    /**
     * Recorre la lista ligada desde el tope hacia la base para imprimir
     * de manera ilustrativa los elementos de la pila sin modificarlos.
     */
    public void imprimirPila() {
        if (isEmpty()) {
            System.out.println("   [Pila vacía]");
            return;
        }

        NodoPizza actual = this.tope; // Puntero auxiliar de recorrido
        int nivel = 1;
        while (actual != null) {
            String etiqueta = (actual == this.tope) ? "TOPE -> " : "        ";
            System.out.println("   " + etiqueta + "[" + nivel + "] " + actual.getPizza());
            actual = actual.getSiguiente(); // Avanzamos el puntero auxiliar al siguiente nodo
            nivel++;
        }
    }
}
