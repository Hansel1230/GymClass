# GymClass App

GymClass es una aplicación Android moderna y funcional para la reserva de clases de gimnasio. Ha sido desarrollada siguiendo las mejores prácticas de la industria, con una arquitectura limpia, una interfaz de usuario completamente reactiva construida con Jetpack Compose y una integración robusta con Firebase para la gestión de datos y autenticación en tiempo real.

## Tabla de Contenidos
1. [Requerimientos y Configuración Inicial](#requerimientos-y-configuración-inicial)
    - [Requerimientos de Software](#requerimientos-de-software)
    - [Requerimientos de Hardware](#requerimientos-de-hardware)
    - [Pasos de Configuración](#pasos-de-configuración)
2. [Especificaciones de la Aplicación](#especificaciones-de-la-aplicación)
    - [Tecnologías y Stack Utilizado](#tecnologías-y-stack-utilizado)
    - [Arquitectura del Proyecto](#arquitectura-del-proyecto)
    - [Buenas Prácticas Implementadas](#buenas-prácticas-implementadas)
3. [Funcionamiento de la Aplicación](#funcionamiento-de-la-aplicación)
    - [Módulo de Autenticación](#módulo-de-autenticación)
    - [Pantalla Principal (Home)](#pantalla-principal-home)
    - [Pantalla de Detalles de Clase](#pantalla-de-detalles-de-clase)
    - [Sistema de Reservas](#sistema-de-reservas)
    - [Recordatorios y Notificaciones](#recordatorios-y-notificaciones)
    - [Módulo de Configuración](#módulo-de-configuración)

---

## Requerimientos y Configuración Inicial

Para compilar y ejecutar este proyecto, necesitarás la siguiente configuración en tu entorno de desarrollo.

### Requerimientos de Software
- **IDE:** Android Studio Hedgehog (2023.1.1) o superior.
- **Java:** JDK 17.
- **Gradle:**
    - Gradle Version: 8.x
    - Android Gradle Plugin (AGP) Version: 8.x

### Requerimientos de Hardware
- Un ordenador capaz de ejecutar Android Studio de forma fluida.
- Un dispositivo Android físico o un emulador con **Android 7.0 (Nougat) API 24** o superior.

### Pasos de Configuración
Para que la aplicación funcione, es **imprescindible** conectarla a tu propio proyecto de Firebase.

1.  **Clonar el Repositorio:**
    ```bash
    git clone https://URL_DEL_REPOSITORIO.git
    ```

2.  **Abrir en Android Studio:**
    - Abre Android Studio.
    - Selecciona `Open` y navega hasta la carpeta del proyecto clonado.

3.  **Configurar Firebase:**
    - Ve a la [Consola de Firebase](https://console.firebase.google.com/).
    - Crea un nuevo proyecto.
    - Dentro de tu proyecto, haz clic en **"Añadir aplicación"** y selecciona el icono de Android.
    - Registra la aplicación usando el nombre de paquete: `com.universidad.gymclass`.
    - Descarga el archivo `google-services.json` que te proporcionará Firebase.
    - **Copia y pega** este archivo en la carpeta `app/` de tu proyecto en Android Studio.
    - Vuelve a la Consola de Firebase y en el menú de la izquierda, activa los siguientes servicios:
        - **Authentication:** Ve a la pestaña `Sign-in method` y habilita los proveedores **"Email/Contraseña"** y **"Google"**.
        - **Firestore Database:** Crea una nueva base de datos. Puedes empezar en modo de prueba.

4.  **Configurar Reglas de Seguridad de Firestore:**
    - En la consola de Firestore, ve a la pestaña **"Reglas"**.
    - Reemplaza el contenido por defecto con las siguientes reglas, que permiten el acceso solo a usuarios autenticados:
      ```
      rules_version = '2';
      service cloud.firestore {
        match /databases/{database}/documents {
          match /{document=**} {
            allow read, write: if request.auth != null;
          }
        }
      }
      ```
    - Haz clic en **"Publicar"**.

5.  **Crear Colecciones en Firestore:**
    - Ve a la pestaña **"Datos"** en Firestore.
    - Crea una colección llamada `classes`. Añade un documento con la siguiente estructura (los nombres de los campos deben ser exactos):
      ```json
      {
        "name": "Yoga Matutino",
        "instructor": "Ana García",
        "description": "Sesión de yoga para comenzar el día con energía.",
        "startTime": "07:00",
        "endTime": "08:00",
        "availableSlots": 14,
        "dayOfWeek": 1 
      }
      ```
      > **Nota:** `dayOfWeek` es un número, donde 1=Lunes y 7=Domingo.

6.  **Sincronizar y Ejecutar:**
    - Haz clic en `Sync Project with Gradle Files` en Android Studio.
    - Ejecuta la aplicación en tu emulador o dispositivo.

---

## Especificaciones de la Aplicación

### Tecnologías y Stack Utilizado
- **UI:** 100% construida con [Jetpack Compose](https://developer.android.com/jetpack/compose).
- **Arquitectura:** MVVM (Model-View-ViewModel) sobre una **Clean Architecture** (Capas de `data`, `domain`, `presentation`).
- **Inyección de Dependencias:** [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) para gestionar las dependencias y el ciclo de vida.
- **Asincronía:** [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) y [Flow](https://kotlinlang.org/docs/flow.html) para un manejo de datos asíncrono y reactivo.
- **Navegación:** [Navigation-Compose](https://developer.android.com/jetpack/compose/navigation) para gestionar el flujo entre pantallas.
- **Backend y Base de Datos:** [Firebase](https://firebase.google.com/) (Authentication para usuarios y Firestore para la base de datos en tiempo real).
- **Tareas en Segundo Plano:** [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) para programar notificaciones de recordatorio de forma fiable.
- **Persistencia Local:** [DataStore Preferences](https://developer.android.com/topic/libraries/architecture/datastore) para guardar las preferencias del usuario (ej. tiempo de recordatorio).

### Arquitectura del Proyecto
La aplicación se divide en tres capas bien definidas:
- **`data`:** Se encarga de la lógica de acceso a datos. Es la única capa que conoce Firebase. Contiene los `DataSource`, `Repositories` y `Mappers`.
- **`domain`:** Contiene la lógica de negocio pura (Casos de Uso) y los modelos de datos de la aplicación. No tiene dependencias de Android ni de la capa de datos.
- **`presentation`:** Es la capa de la UI. Contiene los `ViewModels` y los `Composables` que conforman las pantallas.

### Buenas Prácticas Implementadas
- **Flujo de Datos Unidireccional (UDF):** El estado fluye hacia abajo (del ViewModel a la UI) y los eventos fluyen hacia arriba (de la UI al ViewModel). Esto hace que el comportamiento de la app sea predecible.
- **Reactividad de Extremo a Extremo:** Gracias a `Flow` y `addSnapshotListener` de Firestore, cualquier cambio en la base de datos se propaga automáticamente hasta la UI, que se actualiza en tiempo real.
- **Inyección de Dependencias:** Hilt gestiona la creación de objetos, lo que desacopla las clases y facilita las pruebas.
- **Manejo de Operaciones Atómicas:** Las operaciones críticas, como cancelar una reserva, se realizan dentro de **Transacciones de Firestore**, garantizando que todos los pasos (archivar, borrar y actualizar el cupo) se completen con éxito o ninguno lo haga.

---

## Funcionamiento de la Aplicación

### Módulo de Autenticación
- **Funcionalidad:** Permite a los usuarios registrarse y iniciar sesión con correo electrónico y contraseña, o a través de su cuenta de Google.
- **Tecnología:** Utiliza **Firebase Authentication**. La lógica está encapsulada en un `AuthRepository`.

### Pantalla Principal (Home)
- **Funcionalidad:** Muestra la lista de todas las clases disponibles, agrupadas por día de la semana. Incluye una barra de filtros horizontal y desplazable para seleccionar un día específico.
- **Características Clave:**
    - La lista de clases y los cupos disponibles (`availableSlots`) se actualizan en **tiempo real**.
    - La navegación a la pantalla de configuración y a la lista de reservas del usuario está disponible en la barra superior.

### Pantalla de Detalles de Clase
- **Funcionalidad:** Muestra toda la información de una clase seleccionada (instructor, horario, descripción, etc.).
- **Características Clave:**
    - El botón principal cambia dinámicamente entre **"Reservar Lugar"** y **"Cancelar Reserva"** dependiendo de si el usuario ya tiene una reserva activa para esa clase.
    - La información de los cupos disponibles también se actualiza en tiempo real en esta pantalla.

### Sistema de Reservas
- **Creación:** Al reservar, se valida que el usuario no tenga una reserva duplicada. Luego, se decrementa atómicamente el contador de `availableSlots` de la clase y se crea un nuevo documento en la colección `reservations`.
- **Cancelación (Archivado Atómico):** Para no perder el historial, al cancelar una reserva no se borra, sino que se mueve. Una transacción de Firestore garantiza que el documento se copie a la colección `canceled_reservations`, se elimine de la colección `reservations` y se incremente el contador `availableSlots` de la clase, todo como una única operación segura.

### Recordatorios y Notificaciones
- **Funcionalidad:** La aplicación programa automáticamente un recordatorio cuando el usuario reserva una clase.
- **Tecnología:** Utiliza **WorkManager** para garantizar que la notificación se entregue de forma fiable, incluso si la app está cerrada o el dispositivo se reinicia. El recordatorio se cancela automáticamente si el usuario cancela su reserva.

### Módulo de Configuración
- **Funcionalidad:** Permite al usuario personalizar con cuántos minutos de antelación desea recibir la notificación del recordatorio (1, 5, 10, 15 o 30 minutos).
- **Tecnología:** La preferencia del usuario se guarda localmente en el dispositivo utilizando **DataStore Preferences**, asegurando que su elección se recuerde entre sesiones.
