# Arquitectura del Proyecto: GymClass

Este documento describe la arquitectura de la aplicación GymClass, que sigue los principios de **Clean Architecture**. Esta arquitectura separa las responsabilidades del software en distintas capas, logrando un código más limpio, mantenible, escalable y fácil de probar.

## Capas Principales

El proyecto está organizado en tres capas fundamentales:

### 1. Capa de Presentación (`presentation`, `ui`)

- **Responsabilidad:** Es la capa más externa y se encarga de todo lo relacionado con la interfaz de usuario (UI).
- **Componentes:**
    - **Vistas (Activities/Fragments/Composables):** Muestran los datos en la pantalla y capturan los eventos del usuario (como clics).
    - **ViewModels (`HomeViewModel`):** Contienen la lógica de la UI y preparan los datos para ser mostrados. Se comunican con la capa de dominio a través de los Casos de Uso. No tienen conocimiento de la UI de Android, lo que facilita las pruebas.
    - **Navegación (`GymClassNavigation`):** Gestiona el flujo de pantallas dentro de la aplicación.

### 2. Capa de Dominio (`domain`)

- **Responsabilidad:** Contiene la lógica de negocio central de la aplicación. Es el núcleo de la arquitectura y es completamente independiente de cualquier framework de Android o de detalles de implementación (como la fuente de datos).
- **Componentes:**
    - **Casos de Uso (Use Cases) (`GetClassesUseCase`):** Representan acciones o funcionalidades específicas del negocio. Orquestan la obtención de datos desde la capa de datos y aplican la lógica de negocio correspondiente.
    - **Modelos de Dominio (`GymClass`):** Representan las entidades principales del negocio. Son estructuras de datos simples (POJOs/data classes) que no contienen lógica compleja.
    - **Interfaces de Repositorio:** Definen los contratos que la capa de datos debe implementar para proveer los datos que los casos de uso necesitan.

### 3. Capa de Datos (`data`)

- **Responsabilidad:** Es la responsable de proveer los datos que la aplicación necesita. Abstrae el origen de los datos (API, base de datos, etc.) de la capa de dominio.
- **Componentes:**
    - **Repositorios (Implementaciones):** Implementan las interfaces de repositorio definidas en el dominio. Son la única fuente de verdad para los datos. Deciden si obtener los datos de una fuente remota (API) o de una fuente local (caché, base de datos).
    - **Fuentes de Datos (Data Sources):** Clases responsables de interactuar directamente con un solo tipo de fuente de datos (ej. un cliente Retrofit para una API o un DAO para una base de datos Room).
    - **Modelos de Datos (DTOs/Entities):** Representan los datos tal como provienen de la fuente (ej. un modelo JSON de una API). El repositorio se encarga de mapear estos modelos a los Modelos de Dominio.

## Inyección de Dependencias (`di`)

El proyecto utiliza un framework de Inyección de Dependencias (como Hilt) para gestionar y proveer las dependencias entre las distintas clases.

- **Responsabilidad:** Proporcionar las instancias necesarias a cada clase sin que esta tenga que crearlas. Esto facilita el desacoplamiento entre capas y simplifica las pruebas unitarias al permitir el reemplazo de dependencias por dobles de prueba (mocks).

## Flujo de Datos

Un flujo típico de datos sería:

1.  La **Vista** notifica una acción del usuario al **ViewModel**.
2.  El **ViewModel** ejecuta un **Caso de Uso** en la capa de dominio.
3.  El **Caso de Uso** solicita los datos al **Repositorio** (a través de su interfaz).
4.  El **Repositorio** obtiene los datos de una **Fuente de Datos** (remota o local).
5.  Los datos viajan de vuelta a través de las capas, posiblemente transformándose en el camino (de modelo de datos a modelo de dominio, y luego a un estado de UI).
6.  El **ViewModel** recibe los datos del caso de uso, los convierte en un estado de UI y actualiza la **Vista** para que el usuario los vea.
