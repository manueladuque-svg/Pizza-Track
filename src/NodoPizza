/**
 * Clase que representa un Nodo dentro de la lista ligada para la pila manual de Pizza-Track.
 * 
 * Concepto de punteros en memoria:
 * En Java, las variables de tipo objeto almacenan referencias (punteros a direcciones de memoria).
 * Cada nodo actúa como un eslabón compuesto por:
 * 1. La carga útil (objeto Pizza).
 * 2. El puntero de enlace ('siguiente'), que apunta hacia el nodo que se encuentra debajo
 *    en la estructura de la pila (el nodo previo insertado).
 */
public class NodoPizza {

    // Información u objeto almacenado en el nodo
    private Pizza pizza;

    // Puntero/referencia que apunta al siguiente nodo en la lista ligada (hacia abajo en la pila)
    private NodoPizza siguiente;

    /**
     * Constructor que inicializa el nodo con su información (Pizza) y deja el puntero siguiente en null.
     *
     * @param pizza Objeto de tipo Pizza a guardar en este nodo.
     */
    public NodoPizza(Pizza pizza) {
        this.pizza = pizza;
        this.siguiente = null; // Inicialmente no apunta a ningún otro nodo
    }

    /**
     * Constructor sobrecargado para vincular directamente con el nodo sucesor.
     *
     * @param pizza     Objeto Pizza.
     * @param siguiente Referencia al nodo al que debe apuntar este nuevo nodo.
     */
    public NodoPizza(Pizza pizza, NodoPizza siguiente) {
        this.pizza = pizza;
        this.siguiente = siguiente;
    }

    // Métodos Getters y Setters
    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public NodoPizza getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoPizza siguiente) {
        this.siguiente = siguiente;
    }
}
