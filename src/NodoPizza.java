// Nodo para la lista ligada de la pila
public class NodoPizza {

    private Pizza pizza;
    private NodoPizza siguiente; // Referencia al nodo inferior

    public NodoPizza(Pizza pizza) {
        this.pizza = pizza;
        this.siguiente = null;
    }

    public NodoPizza(Pizza pizza, NodoPizza siguiente) {
        this.pizza = pizza;
        this.siguiente = siguiente;
    }

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
