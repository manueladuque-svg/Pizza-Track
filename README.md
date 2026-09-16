# 🍕 Pizza-Track: Sistema de Gestión de Pedidos con Pilas Manuales (Listas Ligadas)

## 📌 1. Objetivo del Proyecto

Desarrollar una aplicación en Java en modo consola que simule el sistema de gestión de pedidos de una pizzería (**Pizza-Track**). El sistema implementa las operaciones de:
- **Registro de Pedidos (Escribir):** Almacenamiento del pedido con nombre y un arreglo estricto de 3 ingredientes en la **Pila Principal**.
- **Deshacer (Undo):** Retiro del último pedido activo mediante `pop()` en la Pila Principal y `push()` hacia la Pila Secundaria.
- **Rehacer (Redo):** Recuperación del pedido deshecho mediante `pop()` en la Pila Secundaria y `push()` de retorno hacia la Pila Principal.
- **Mostrar Pedido Actual:** Inspección no destructiva mediante `peek()` de la pizza lista para producción.

> **Restricción Académica Estricta:** No se utiliza la clase predeterminada `java.util.Stack` ni ninguna colección estándar de Java. La pila se implementa manualmente desde cero mediante **Listas Simplemente Ligadas (Nodos y Punteros)**.

---

## 🏛️ 2. Arquitectura de Datos y Lógica de Punteros

El proyecto se compone de dos pilas dinámicas coordinadas:
1. **Pila Principal (Undo):** Gestiona los pedidos activos bajo la política **LIFO** (*Last-In, First-Out*).
2. **Pila Secundaria (Redo):** Almacena temporalmente los pedidos deshechos para permitir su restauración inmediata.

### Diagrama Conceptual de la Lista Ligada

```text
               +------------------------------------------------------+
               |                     PILA MANUAL                      |
               +------------------------------------------------------+
                                         |
                                         v [tope]
                           +----------------------------+
                           |         NodoPizza          |
                           |----------------------------|
                           | Pizza: "Pepperoni"         |
                           | Ingredientes: [3]          |
                           | siguiente: ----------------+---> +----------------------------+
                           +----------------------------+     |         NodoPizza          |
                                                              |----------------------------|
                                                              | Pizza: "Margarita"         |
                                                              | Ingredientes: [3]          |
                                                              | siguiente: null            |
                                                              +----------------------------+
```

### Explicación de Métodos Obligatorios

| Método | Complejidad | Descripción y Manejo de Punteros |
| :--- | :---: | :--- |
| `push(Pizza pizza)` | $\mathcal{O}(1)$ | Crea un nuevo `NodoPizza`. Su puntero `siguiente` apunta al nodo referenciado por `tope`. Luego, el puntero `tope` se actualiza para referenciar al nuevo nodo. |
| `pop()` | $\mathcal{O}(1)$ | Si `isEmpty()` es falso, recupera la `Pizza` del `tope`. El puntero `tope` avanza a `tope.getSiguiente()`. El nodo desvinculado es liberado por el recolector de basura. |
| `peek()` | $\mathcal{O}(1)$ | Consulta no destructiva que retorna la `Pizza` contenida en el `tope` sin modificar ninguna referencia de memoria. |
| `isEmpty()` | $\mathcal{O}(1)$ | Evalúa si `tope == null`, determinando si la pila carece de nodos enlazados. |

---

## 📂 3. Estructura del Repositorio

```text
Pizza-Track/
├── src/
│   ├── Pizza.java                         # Modelo de datos con validación de catálogo y String[3]
│   ├── NodoPizza.java                     # Nodo de lista ligada (dato + puntero siguiente)
│   ├── PilaPizza.java                     # Pila manual con push(), pop(), peek(), isEmpty()
│   ├── GestionPedidos.java                # Controlador Undo/Redo coordinando dos pilas
│   ├── TipoPizzaInvalidoException.java    # Excepción para tipos de pizza fuera de catálogo
│   ├── IngredientesInvalidosException.java# Excepción para control estricto de 3 ingredientes
│   └── Main.java                          # Menú interactivo en consola con control de excepciones
├── .gitignore                             # Exclusión de binarios (.class y bin/)
└── README.md                              # Documentación completa y evidencias
```

---

## ⚙️ 4. Requisitos y Ejecución

### Requisitos
- **Java Development Kit (JDK):** Versión 8 o superior (verificado con JDK 25 LTS).
- **Consola:** PowerShell, Bash o CMD.

### Instrucciones de Compilación y Ejecución

1. **Abrir la terminal** en la carpeta raíz del proyecto (`Pizza-Track`).
2. **Compilar las clases:**
   ```bash
   javac -d bin src/*.java
   ```
3. **Ejecutar el programa:**
   ```bash
   java -cp bin Main
   ```

---

## 🎮 5. Menú Interactivo en Consola

Al ejecutar el programa, se despliega el menú oficial de 5 opciones conforme al enunciado:

```text
+----------------------------------------------------------+
|                     MENU PRINCIPAL                       |
+----------------------------------------------------------+
| 1. Registrar Pizza (Escribir / Push Pila Principal)      |
| 2. Deshacer (Undo: Pop Principal -> Push Secundaria)     |
| 3. Rehacer (Redo: Pop Secundaria -> Push Principal)      |
| 4. Mostrar Pedido Actual (Peek Pila Principal)           |
| 0. Salir                                                 |
+----------------------------------------------------------+
```

### Catálogo de Especialidades y Control por Excepciones:
Al seleccionar la opción 1 (`Registrar Pizza`), el sistema valida que únicamente se acepten pizzas del catálogo oficial:
- `Margarita`
- `Pepperoni`
- `Hawaiana`
- `Cuatro Quesos`
- `Vegetariana`
- `Mexicana`
- `Pollo y Champinones`

Cualquier valor fuera de este catálogo o índice inválido lanza y captura una `TipoPizzaInvalidoException`. Asimismo, si alguno de los 3 ingredientes obligatorios del arreglo `String[3]` está vacío, se lanza y controla una `IngredientesInvalidosException`.

---

## 📸 6. Evidencias de Ejecución (Capturas de Consola)

### 1. Registro de Pedidos con Arreglo Fijo de 3 Ingredientes
Muestra la captura de pantalla al registrar una pizza con sus 3 ingredientes y cómo ingresa a la Pila Principal:
*(Agrega aquí tu captura: `docs/1_registro_pizza.png`)*

### 2. Mostrar Pedido Actual (`peek`)
Muestra el pedido en la cima de la Pila Principal sin retirarlo:
*(Agrega aquí tu captura: `docs/2_peek_pedido.png`)*

### 3. Operación Deshacer (`Undo`)
Muestra cómo el pedido sale de la Pila Principal (`pop`) y pasa a la Pila Secundaria (`push`):
*(Agrega aquí tu captura: `docs/3_undo_deshacer.png`)*

### 4. Operación Rehacer (`Redo`)
Muestra cómo el pedido es recuperado de la Pila Secundaria y devuelto a la Pila Principal:
*(Agrega aquí tu captura: `docs/4_redo_rehacer.png`)*

---

## 🎬 7. Sustentación Individual (Video Explicativo - Máx. 3 Minutos)

- **Enlace del Video:** `[PEGA AQUÍ EL ENLACE DE TU VIDEO DE YOUTUBE / DRIVE / LOOM]`
- **Estudiante:** `[Nombre del Estudiante]`

### 📋 Guion Recomendado para la Grabación (Estructura de 3 Minutos):

1. **Minuto 0:00 - 0:30 (Presentación Formal):**
   - Enciende tu cámara (mostrando tu rostro).
   - Preséntate con tu nombre completo y menciona la asignatura / actividad: *Sistema de Gestión de Pedidos Pizza-Track con Pilas basadas en Listas Ligadas*.
2. **Minuto 0:30 - 1:30 (Explicación de `push()` y `pop()` en `PilaPizza.java`):**
   - Abre el archivo `PilaPizza.java` en tu editor.
   - Explica el método `push()`: Muestra cómo se crea un nuevo `NodoPizza`, cómo su enlace `siguiente` apunta al antiguo `tope` y cómo se actualiza la referencia `tope = nuevoNodo`.
   - Explica el método `pop()`: Muestra la validación `isEmpty()`, cómo se extrae el dato del nodo en `tope` y cómo se avanza el puntero con `tope = tope.getSiguiente()`.
3. **Minuto 1:30 - 2:45 (Demostración Práctica en Consola):**
   - Ejecuta `java -cp bin Main`.
   - **Paso A (Registro):** Registra una pizza (ej. "Margarita" con Tomate, Queso y Albahaca) y luego otra (ej. "Pepperoni").
   - **Paso B (Peek):** Opción 4 (`peek()`) para evidenciar que "Pepperoni" está en la cima.
   - **Paso C (Deshacer / Undo):** Opción 2 para deshacer Pepperoni. Muestra con la opción 4 que ahora el pedido activo es "Margarita".
   - **Paso D (Rehacer / Redo):** Opción 3 para rehacer Pepperoni y opción 4 para verificar su recuperación.
4. **Minuto 2:45 - 3:00 (Conclusión y Despedida):**
   - Resume la importancia de las listas ligadas para gestionar memoria dinámica en pilas $\mathcal{O}(1)$ y despídete formalmente.

---

## 📈 8. Historial de Commits en Git

Se garantiza un historial de commits claro que evidencia el proceso de construcción modular:

1. `feat: implementar modelo Pizza y estructura manual de Pila con lista ligada`
2. `feat: implementar controlador GestionPedidos (Undo/Redo) y menú interactivo en consola`
3. `docs: agregar README detallado con arquitectura, instrucciones de ejecución y guía de sustentación`
