# 🏗️ GymClass - Kotlin Development Guidelines

## 📋 Guía de Buenas Prácticas y Arquitectura Limpia para Kotlin

### 🎯 Visión General del Proyecto
Este documento establece las directrices para desarrollar **GymClass** en Kotlin, basándose en el proyecto React existente pero adaptado a las mejores prácticas de desarrollo móvil con Android.

### 🏫 Contexto del Problema Universitario
**El Problema**: Las clases populares en el gimnasio universitario se llenan rápidamente. Los estudiantes necesitan una forma fácil de ver el horario y reservar un lugar.

**Objetivo**: Una app Android para que los estudiantes vean el horario de clases de fitness del gimnasio universitario y reserven su asistencia de manera eficiente.

### 🎯 Características Principales de la App
- **📅 Ver horario de clases** por día o por semana
- **📋 Pantalla de detalle** para cada clase con descripción, instructor y lugares disponibles
- **🔘 Botón para 'Reservar' o 'Cancelar'** una reserva
- **📱 Sección 'Mis Reservas'** para ver las próximas clases
- **🔔 Notificaciones de recordatorio** antes de que comience una clase reservada

### 🛠️ Habilidades Android Clave a Desarrollar
- **📋 RecyclerView/LazyColumn** para mostrar horarios y listas
- **🏗️ ViewModel** para gestión de estados y lógica de presentación
- **🗄️ Room Database** para almacenamiento local offline
- **☁️ Firebase Firestore** para sincronización en tiempo real
- **⏰ AlarmManager/WorkManager** para recordatorios programados
- **🔔 Notifications** para alertas contextuales

### 📋 Requerimientos Técnicos del Proyecto
- **🤖 Kotlin**: Lenguaje de programación principal para desarrollo Android
- **🎨 Jetpack Compose**: Toolkit moderno para construcción de UI nativa Android
- **☁️ Firebase Storage**: Almacenamiento de datos de usuario y assets de la aplicación
- **🔐 Authentication**: Gestión de cuentas de usuario y acceso seguro a la app
- **🔔 Notifications**: Alertas a usuarios sobre tareas importantes y fechas límite

---

## 🏛️ Arquitectura del Proyecto

### � Diagrama de Arquitectura Clean + MVVM
La arquitectura sigue el patrón **Clean Architecture** con **MVVM** organizada en capas concéntricas:

```
🔵 Presentation Layer (UI/Views)
  ├── Activity/Fragment/View
  ├── ViewModel
  └── Dependency Injection
  
🟢 Data Layer 
  ├── Repository (Implementation)
  ├── Model (Remote/Local)
  ├── API Service
  └── DataSource (Local/Remote/Cache)
  
🟡 Domain Layer (Business Logic)
  ├── Repository (Interface)
  ├── Model (Entities)
  └── Use Cases
```

### �📁 Estructura de Directorios Basada en Clean Architecture
```
GymClass/
├── app/                                    # Módulo principal de aplicación
│   ├── src/
│   │   ├── main/
│   │   │   ├── kotlin/
│   │   │   │   └── com/gymclass/
│   │   │   │       │
│   │   │   │       ├── 🔵 presentation/           # PRESENTATION LAYER
│   │   │   │       │   ├── views/                # Views (Activity/Fragment/Compose)
│   │   │   │       │   │   ├── auth/            # Pantallas de autenticación
│   │   │   │       │   │   ├── classes/         # Pantallas de clases
│   │   │   │       │   │   ├── reservations/    # Pantallas de reservas
│   │   │   │       │   │   └── profile/         # Pantallas de perfil
│   │   │   │       │   ├── viewmodel/           # ViewModels por módulo
│   │   │   │       │   │   ├── AuthViewModel.kt
│   │   │   │       │   │   ├── ClassViewModel.kt
│   │   │   │       │   │   └── ReservationViewModel.kt
│   │   │   │       │   ├── navigation/          # Navegación
│   │   │   │       │   └── components/          # Componentes UI reutilizables
│   │   │   │       │
│   │   │   │       ├── 🟢 data/                  # DATA LAYER
│   │   │   │       │   ├── repository/          # Implementaciones de repositorios
│   │   │   │       │   │   ├── ClassRepositoryImpl.kt
│   │   │   │       │   │   ├── UserRepositoryImpl.kt
│   │   │   │       │   │   └── ReservationRepositoryImpl.kt
│   │   │   │       │   ├── model/              # Modelos de datos
│   │   │   │       │   │   ├── remote/         # DTOs de API
│   │   │   │       │   │   └── local/          # Entidades de BD local
│   │   │   │       │   ├── api/                # Servicios de API
│   │   │   │       │   │   ├── GymClassApi.kt
│   │   │   │       │   │   └── AuthApi.kt
│   │   │   │       │   └── datasource/         # Fuentes de datos
│   │   │   │       │       ├── local/          # Room Database
│   │   │   │       │       ├── remote/         # Network calls
│   │   │   │       │       └── cache/          # Cache management
│   │   │   │       │
│   │   │   │       ├── 🟡 domain/               # DOMAIN LAYER
│   │   │   │       │   ├── repository/         # Interfaces de repositorios
│   │   │   │       │   │   ├── ClassRepository.kt
│   │   │   │       │   │   ├── UserRepository.kt
│   │   │   │       │   │   └── ReservationRepository.kt
│   │   │   │       │   ├── model/              # Entidades de dominio
│   │   │   │       │   │   ├── GymClass.kt
│   │   │   │       │   │   ├── User.kt
│   │   │   │       │   │   └── Reservation.kt
│   │   │   │       │   └── usecase/            # Casos de uso
│   │   │   │       │       ├── auth/           # Casos de uso de autenticación
│   │   │   │       │       ├── classes/        # Casos de uso de clases
│   │   │   │       │       └── reservations/   # Casos de uso de reservas
│   │   │   │       │
│   │   │   │       └── 🔧 di/                   # DEPENDENCY INJECTION
│   │   │   │           ├── DatabaseModule.kt
│   │   │   │           ├── NetworkModule.kt
│   │   │   │           ├── RepositoryModule.kt
│   │   │   │           └── UseCaseModule.kt
│   │   │   │
│   │   │   └── res/                            # Recursos Android
│   │   └── test/                               # Tests unitarios
│   └── build.gradle.kts
├── core/                                       # Módulo core común
└── gradle/
```

---

## 🎨 Principios de Arquitectura Limpia + MVVM

### 1. **🔵 Presentation Layer (Capa de Presentación)**
**Responsabilidades:**
- **Views**: Activities, Fragments, Composables - Interfaz de usuario
- **ViewModels**: Gestión de estado de UI y comunicación con Domain Layer  
- **Dependency Injection**: Configuración de dependencias para la capa de presentación

**Reglas:**
- Los Views solo observan ViewModels
- ViewModels ejecutan Use Cases del Domain Layer
- No conoce detalles de implementación de Data Layer

### 2. **🟢 Data Layer (Capa de Datos)**
**Responsabilidades:**
- **Repository Implementation**: Implementación concreta de interfaces del dominio
- **Models**: DTOs de API y entidades de base de datos local
- **API Services**: Servicios de red (Retrofit)
- **DataSources**: Local (Room), Remote (API), Cache (SharedPrefs/DataStore)

**Reglas:**
- Implementa las interfaces definidas en Domain Layer
- Maneja la persistencia y sincronización de datos
- Transforma datos entre diferentes formatos (DTO ↔ Entity)

### 3. **🟡 Domain Layer (Capa de Dominio)**
**Responsabilidades:**
- **Repository Interfaces**: Contratos para acceso a datos
- **Models/Entities**: Entidades puras del negocio
- **Use Cases**: Lógica de negocio específica

**Reglas:**
- **NO** depende de otras capas
- Contiene la lógica de negocio pura
- Define contratos que otras capas deben implementar

### 4. **Flujo de Dependencias (Dependency Rule)**
```
🔵 Presentation → 🟡 Domain ← 🟢 Data
     (Views)        (UseCases)    (Repository Impl)
       ↓               ↑              ↑
   ViewModel    Repository Interface   API/DB
```

### 5. **Patrón MVVM en Presentation Layer**
```
View ↔ ViewModel ↔ UseCase ↔ Repository
 ↑        ↑         ↑          ↑
UI      State    Business   Data Access
```

---

## 🏫 Funcionalidades Específicas para Gimnasio Universitario

### 📱 Pantallas Principales de la App

#### 1. **📅 Schedule Screen - Horario de Clases**
**Funcionalidad**: Ver el horario de clases por día o por semana
```kotlin
// presentation/views/schedule/ScheduleScreen.kt
@Composable
fun ScheduleScreen(
    viewModel: ScheduleViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Column {
        // Toggle entre vista diaria y semanal
        ScheduleViewToggle(
            currentView = uiState.viewMode,
            onViewChange = viewModel::setViewMode
        )
        
        when (uiState.viewMode) {
            ViewMode.DAILY -> DailyScheduleView(
                classes = uiState.todayClasses,
                onClassClick = viewModel::navigateToClassDetail
            )
            ViewMode.WEEKLY -> WeeklyScheduleView(
                weeklyClasses = uiState.weeklyClasses,
                onClassClick = viewModel::navigateToClassDetail
            )
        }
    }
}

// RecyclerView Implementation (habilidad clave Android)
@Composable
fun DailyScheduleView(
    classes: List<GymClass>,
    onClassClick: (String) -> Unit
) {
    LazyColumn { // Equivalente moderno a RecyclerView
        items(classes) { gymClass ->
            ClassScheduleCard(
                gymClass = gymClass,
                onClick = { onClassClick(gymClass.id) }
            )
        }
    }
}
```

#### 2. **📋 Class Detail Screen - Detalle de Clase**
**Funcionalidad**: Pantalla de detalle con descripción, instructor y lugares disponibles
```kotlin
// presentation/views/classdetail/ClassDetailScreen.kt
@Composable
fun ClassDetailScreen(
    classId: String,
    viewModel: ClassDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(classId) {
        viewModel.loadClassDetail(classId)
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Información de la clase
        ClassInfoSection(
            gymClass = uiState.gymClass,
            instructor = uiState.instructor
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Descripción detallada
        ClassDescriptionCard(
            description = uiState.gymClass?.description
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Disponibilidad y capacidad
        AvailabilitySection(
            currentCapacity = uiState.currentReservations,
            maxCapacity = uiState.gymClass?.maxCapacity ?: 0
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Botón principal: Reservar o Cancelar
        ReservationButton(
            isReserved = uiState.isUserReserved,
            isClassFull = uiState.isClassFull,
            onReserve = { viewModel.reserveClass() },
            onCancel = { viewModel.cancelReservation() }
        )
    }
}

// ViewModel con habilidades Android clave
@HiltViewModel
class ClassDetailViewModel @Inject constructor(
    private val getClassDetailUseCase: GetClassDetailUseCase,
    private val reserveClassUseCase: ReserveClassUseCase,
    private val cancelReservationUseCase: CancelReservationUseCase,
    private val scheduleNotificationUseCase: ScheduleClassReminderUseCase
) : ViewModel() {
    
    fun reserveClass() {
        viewModelScope.launch {
            reserveClassUseCase(classId = currentClassId).fold(
                onSuccess = { reservation ->
                    // Programar recordatorio con AlarmManager
                    scheduleNotificationUseCase(reservation)
                    _uiState.update { it.copy(isUserReserved = true) }
                },
                onFailure = { error ->
                    _uiState.update { it.copy(error = error.message) }
                }
            )
        }
    }
}
```

#### 3. **📱 My Reservations Screen - Mis Reservas**
**Funcionalidad**: Ver las próximas clases reservadas del estudiante
```kotlin
// presentation/views/reservations/MyReservationsScreen.kt
@Composable
fun MyReservationsScreen(
    viewModel: MyReservationsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Column {
        Text(
            text = "Mis Próximas Clases",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )
        
        when {
            uiState.reservations.isEmpty() -> EmptyReservationsMessage()
            else -> {
                LazyColumn {
                    items(uiState.reservations) { reservation ->
                        ReservationCard(
                            reservation = reservation,
                            onCancel = { viewModel.cancelReservation(reservation.id) },
                            onViewDetails = { viewModel.navigateToClassDetail(reservation.classId) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ReservationCard(
    reservation: Reservation,
    onCancel: () -> Unit,
    onViewDetails: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = reservation.className,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Instructor: ${reservation.instructorName}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "${reservation.classDate} - ${reservation.startTime}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                // Estado de la reserva
                ReservationStatusChip(status = reservation.status)
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = onViewDetails) {
                    Text("Ver Detalles")
                }
                
                if (reservation.canBeCancelled) {
                    OutlinedButton(
                        onClick = onCancel,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Cancelar")
                    }
                }
            }
        }
    }
}
```

### 🛠️ Habilidades Android Clave - Implementaciones

#### 1. **📋 RecyclerView/LazyColumn Implementation**
```kotlin
// Adaptador tradicional RecyclerView (si se usa Views)
class ClassScheduleAdapter(
    private val onClassClick: (GymClass) -> Unit
) : RecyclerView.Adapter<ClassScheduleAdapter.ViewHolder>() {
    
    private var classes = emptyList<GymClass>()
    
    fun updateClasses(newClasses: List<GymClass>) {
        val diffCallback = ClassDiffCallback(classes, newClasses)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        classes = newClasses
        diffResult.dispatchUpdatesTo(this)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemClassScheduleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(classes[position])
    }
    
    override fun getItemCount() = classes.size
    
    inner class ViewHolder(
        private val binding: ItemClassScheduleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(gymClass: GymClass) {
            binding.apply {
                textClassName.text = gymClass.name
                textInstructor.text = "Instructor: ${gymClass.instructor.name}"
                textSchedule.text = "${gymClass.schedule.startTime} - ${gymClass.schedule.endTime}"
                textAvailability.text = "${gymClass.capacity.available} lugares disponibles"
                
                // Click listener
                root.setOnClickListener {
                    onClassClick(gymClass)
                }
                
                // Estado visual según disponibilidad
                when {
                    gymClass.capacity.isFull -> {
                        chipAvailability.apply {
                            text = "Lleno"
                            setChipBackgroundColorResource(R.color.error)
                        }
                    }
                    gymClass.capacity.isAlmostFull -> {
                        chipAvailability.apply {
                            text = "Últimos lugares"
                            setChipBackgroundColorResource(R.color.warning)
                        }
                    }
                    else -> {
                        chipAvailability.apply {
                            text = "Disponible"
                            setChipBackgroundColorResource(R.color.success)
                        }
                    }
                }
            }
        }
    }
}

class ClassDiffCallback(
    private val oldList: List<GymClass>,
    private val newList: List<GymClass>
) : DiffUtil.Callback() {
    
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }
    
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
```

#### 2. **🗄️ Room Database Implementation**
```kotlin
// data/database/AppDatabase.kt
@Database(
    entities = [
        ClassEntity::class,
        ReservationEntity::class,
        StudentEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun classDao(): ClassDao
    abstract fun reservationDao(): ReservationDao
    abstract fun studentDao(): StudentDao
}

// data/database/ClassDao.kt (habilidad Room clave)
@Dao
interface ClassDao {
    
    @Query("SELECT * FROM classes ORDER BY dayOfWeek, startTime")
    fun getAllClasses(): Flow<List<ClassEntity>>
    
    @Query("SELECT * FROM classes WHERE dayOfWeek = :day ORDER BY startTime")
    fun getClassesByDay(day: Int): Flow<List<ClassEntity>>
    
    @Query("SELECT * FROM classes WHERE id = :classId")
    suspend fun getClassById(classId: String): ClassEntity?
    
    @Query("""
        SELECT c.*, COUNT(r.id) as currentReservations 
        FROM classes c 
        LEFT JOIN reservations r ON c.id = r.classId 
            AND r.classDate = :date 
            AND r.status = 'ACTIVE'
        GROUP BY c.id
        ORDER BY c.dayOfWeek, c.startTime
    """)
    fun getClassesWithReservationCount(date: String): Flow<List<ClassWithReservationCount>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClasses(classes: List<ClassEntity>)
    
    @Update
    suspend fun updateClass(classEntity: ClassEntity)
    
    @Delete
    suspend fun deleteClass(classEntity: ClassEntity)
    
    @Query("DELETE FROM classes")
    suspend fun clearAllClasses()
}

// data/database/ReservationDao.kt
@Dao
interface ReservationDao {
    
    @Query("""
        SELECT r.*, c.name as className, c.instructor, c.startTime, c.endTime
        FROM reservations r
        INNER JOIN classes c ON r.classId = c.id
        WHERE r.userId = :userId 
            AND r.status = 'ACTIVE'
            AND r.classDate >= :currentDate
        ORDER BY r.classDate, c.startTime
    """)
    fun getUserActiveReservations(userId: String, currentDate: String): Flow<List<ReservationWithClassInfo>>
    
    @Query("""
        SELECT COUNT(*) 
        FROM reservations 
        WHERE classId = :classId 
            AND classDate = :date 
            AND status = 'ACTIVE'
    """)
    suspend fun getReservationCount(classId: String, date: String): Int
    
    @Query("""
        SELECT * 
        FROM reservations 
        WHERE userId = :userId 
            AND classId = :classId 
            AND classDate = :date 
            AND status = 'ACTIVE'
    """)
    suspend fun getUserReservation(userId: String, classId: String, date: String): ReservationEntity?
    
    @Insert
    suspend fun insertReservation(reservation: ReservationEntity): Long
    
    @Update
    suspend fun updateReservation(reservation: ReservationEntity)
    
    @Query("UPDATE reservations SET status = 'CANCELLED' WHERE id = :reservationId")
    suspend fun cancelReservation(reservationId: String)
}
```

#### 3. **⏰ AlarmManager para Recordatorios**
```kotlin
// data/notifications/AlarmManagerService.kt (habilidad clave Android)
class AlarmManagerService @Inject constructor(
    private val context: Context
) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    
    fun scheduleClassReminder(reservation: Reservation, gymClass: GymClass) {
        val reminderTime = calculateReminderTime(reservation.classDate, gymClass.schedule.startTime)
        
        val intent = Intent(context, ClassReminderReceiver::class.java).apply {
            putExtra("reservation_id", reservation.id)
            putExtra("class_name", gymClass.name)
            putExtra("start_time", gymClass.schedule.startTime.toString())
            putExtra("instructor", gymClass.instructor.name)
        }
        
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reservation.id.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        // Programar alarma exacta para el recordatorio
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                reminderTime,
                pendingIntent
            )
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                reminderTime,
                pendingIntent
            )
        }
    }
    
    fun cancelClassReminder(reservationId: String) {
        val intent = Intent(context, ClassReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reservationId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        alarmManager.cancel(pendingIntent)
    }
    
    private fun calculateReminderTime(classDate: LocalDate, startTime: LocalTime): Long {
        val classDateTime = LocalDateTime.of(classDate, startTime)
        val reminderDateTime = classDateTime.minusMinutes(30) // 30 min antes
        
        return reminderDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }
}

// BroadcastReceiver para manejar las alarmas
class ClassReminderReceiver : BroadcastReceiver() {
    
    override fun onReceive(context: Context, intent: Intent) {
        val reservationId = intent.getStringExtra("reservation_id") ?: return
        val className = intent.getStringExtra("class_name") ?: return
        val startTime = intent.getStringExtra("start_time") ?: return
        val instructor = intent.getStringExtra("instructor") ?: return
        
        showClassReminderNotification(context, className, startTime, instructor, reservationId)
    }
    
    private fun showClassReminderNotification(
        context: Context,
        className: String,
        startTime: String,
        instructor: String,
        reservationId: String
    ) {
        val notificationManager = NotificationManagerCompat.from(context)
        
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("navigate_to", "my_reservations")
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val notification = NotificationCompat.Builder(context, "class_reminders")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("⏰ Tu clase comienza en 30 minutos")
            .setContentText("$className con $instructor a las $startTime")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("¡No olvides tu clase de $className con el instructor $instructor que comienza a las $startTime!"))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
        
        notificationManager.notify(reservationId.hashCode(), notification)
    }
}
```

---

## 🔧 Configuración de Inyección de Dependencias (Hilt)

### 📦 Dependencias del Proyecto (build.gradle.kts)

```kotlin
// build.gradle.kts (Module: app)
dependencies {
    // Core Android & Kotlin
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    
    // 🎨 Jetpack Compose UI (Requerimiento)
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.navigation:navigation-compose:2.7.6")
    
    // 🔐 Firebase Authentication & Storage (Requerimientos)
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    
    // 🔔 Firebase Cloud Messaging - Notifications (Requerimiento)
    implementation("com.google.firebase:firebase-messaging-ktx")
    
    // Dependency Injection
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-compiler:2.48")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    
    // Networking (Backup/Complemento a Firebase)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    
    // Local Database
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")
    
    // Image Loading (Para Firebase Storage)
    implementation("io.coil-kt:coil-compose:2.5.0")
    
    // Date & Time
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
    
    // Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:5.1.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
}
```

### 🏗️ Módulos de Dependencias Organizados por Capa

#### Database Module (Data Layer)
```kotlin
// di/DatabaseModule.kt
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "gymclass_database"
        )
        .fallbackToDestructiveMigration()
        .build()
    }
    
    @Provides
    fun provideClassDao(database: AppDatabase): ClassDao = database.classDao()
    
    @Provides 
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()
    
    @Provides
    fun provideReservationDao(database: AppDatabase): ReservationDao = database.reservationDao()
}
```

#### Network Module (Data Layer)
```kotlin
// di/NetworkModule.kt
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            })
            .addInterceptor(AuthInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            }.asConverterFactory("application/json".toMediaType()))
            .build()
    }
    
    @Provides
    @Singleton
    fun provideGymClassApi(retrofit: Retrofit): GymClassApi = retrofit.create()
    
    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create()
}
```

#### 🔥 Firebase Module (Requerimientos)
```kotlin
// di/FirebaseModule.kt
@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
    
    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()
    
    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()
    
    @Provides
    @Singleton
    fun provideFirebaseMessaging(): FirebaseMessaging = FirebaseMessaging.getInstance()
    
    @Provides
    @Singleton
    fun provideStorageReference(
        storage: FirebaseStorage
    ): StorageReference = storage.reference
}
```

#### Repository Module (Data → Domain Binding)
```kotlin
// di/RepositoryModule.kt
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    abstract fun bindClassRepository(
        classRepositoryImpl: ClassRepositoryImpl
    ): ClassRepository
    
    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository
    
    @Binds
    abstract fun bindReservationRepository(
        reservationRepositoryImpl: ReservationRepositoryImpl
    ): ReservationRepository
}
```

#### UseCase Module (Domain Layer)
```kotlin
// di/UseCaseModule.kt
@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    
    // Auth Use Cases
    @Provides
    fun provideSignInUseCase(
        userRepository: UserRepository
    ): SignInUseCase = SignInUseCase(userRepository)
    
    @Provides
    fun provideSignUpUseCase(
        userRepository: UserRepository
    ): SignUpUseCase = SignUpUseCase(userRepository)
    
    // Class Use Cases
    @Provides
    fun provideGetClassesUseCase(
        classRepository: ClassRepository
    ): GetClassesUseCase = GetClassesUseCase(classRepository)
    
    @Provides
    fun provideGetClassesByDayUseCase(
        classRepository: ClassRepository
    ): GetClassesByDayUseCase = GetClassesByDayUseCase(classRepository)
    
    // Reservation Use Cases
    @Provides
    fun provideReserveClassUseCase(
        reservationRepository: ReservationRepository,
        classRepository: ClassRepository
    ): ReserveClassUseCase = ReserveClassUseCase(reservationRepository, classRepository)
    
    @Provides
    fun provideGetUserReservationsUseCase(
        reservationRepository: ReservationRepository
    ): GetUserReservationsUseCase = GetUserReservationsUseCase(reservationRepository)
}
```

### 🎯 Application Class
```kotlin
// GymClassApplication.kt
@HiltAndroidApp
class GymClassApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // Configurar Firebase
        FirebaseApp.initializeApp(this)
        
        // Configurar Timber para logging
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        
        // Configurar modo nocturno
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        
        // Configurar notificaciones
        createNotificationChannels()
    }
    
    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val classRemindersChannel = NotificationChannel(
                "class_reminders",
                "Recordatorios de Clases",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notificaciones sobre próximas clases reservadas"
            }
            
            val reservationUpdatesChannel = NotificationChannel(
                "reservation_updates",
                "Actualizaciones de Reservas",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Confirmaciones y cambios en reservas"
            }
            
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(classRemindersChannel)
            notificationManager.createNotificationChannel(reservationUpdatesChannel)
        }
    }
}
```

---

## 💎 Convenciones de Código Kotlin

### 1. **Nomenclatura**
```kotlin
// ✅ Correcto
class UserRepository
interface GymClassService
data class ClassEntity
enum class ReservationStatus
const val MAX_CAPACITY = 30

// ❌ Incorrecto
class userRepository
interface gymclass_service
data class classEntity
```

### 2. **Organización de Clases**
```kotlin
class ClassRepository @Inject constructor(
    private val localDataSource: ClassLocalDataSource,
    private val remoteDataSource: ClassRemoteDataSource
) {
    // Propiedades públicas primero
    val classes: Flow<List<ClassEntity>> = localDataSource.getAllClasses()
    
    // Métodos públicos
    suspend fun refreshClasses(): Result<Unit> = try {
        val remoteClasses = remoteDataSource.getClasses()
        localDataSource.insertClasses(remoteClasses)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
    
    // Métodos privados al final
    private fun mapToEntity(dto: ClassDto): ClassEntity {
        // Implementación
    }
}
```

### 3. **Manejo de Null Safety**
```kotlin
// ✅ Uso apropiado de null safety
fun processUserName(user: User?): String {
    return user?.fullName?.takeIf { it.isNotBlank() } ?: "Usuario Anónimo"
}

// ✅ Uso de scope functions
user?.let { 
    updateProfile(it)
    logUserActivity(it.id)
}
```

### 4. **Corrutinas y Concurrencia**
```kotlin
class GetClassesUseCase @Inject constructor(
    private val repository: ClassRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(): Flow<Result<List<ClassEntity>>> = flow {
        try {
            repository.refreshClasses()
            emitAll(repository.classes.map { Result.success(it) })
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(dispatcher)
}
```

---

## 🗂️ Implementación Detallada por Capas

### � **DOMAIN LAYER** - Núcleo del Negocio

#### 📋 Entidades de Dominio (Models) - Contexto Universitario
```kotlin
// domain/model/GymClass.kt
data class GymClass(
    val id: String,
    val name: String,
    val description: String?,
    val instructor: Instructor,
    val schedule: ClassSchedule,
    val capacity: ClassCapacity,
    val difficulty: DifficultyLevel,
    val equipment: List<String> = emptyList(),
    val tags: List<String> = emptyList() // "cardio", "strength", "flexibility"
)

// domain/model/Student.kt (Entidad específica universitaria)
data class Student(
    val id: String,
    val universityId: String, // Matrícula universitaria
    val email: String,
    val fullName: String,
    val profileImage: String? = null,
    val faculty: String, // Facultad del estudiante
    val semester: Int, // Semestre actual
    val membershipType: MembershipType,
    val createdAt: LocalDateTime,
    val isActive: Boolean = true
)

// domain/model/Reservation.kt
data class Reservation(
    val id: String,
    val studentId: String,
    val classId: String,
    val classDate: LocalDate,
    val status: ReservationStatus,
    val createdAt: LocalDateTime,
    val reminderScheduled: Boolean = false,
    // Info denormalizada para queries eficientes
    val className: String,
    val instructorName: String,
    val startTime: LocalTime,
    val endTime: LocalTime
) {
    val canBeCancelled: Boolean 
        get() = status == ReservationStatus.ACTIVE && 
                LocalDateTime.of(classDate, startTime).isAfter(LocalDateTime.now().plusHours(2))
}

// domain/model/Schedule.kt (Entidad de horario)
data class WeeklySchedule(
    val classes: Map<DayOfWeek, List<GymClass>>
) {
    fun getClassesForDay(day: DayOfWeek): List<GymClass> = classes[day] ?: emptyList()
    
    fun getAllClasses(): List<GymClass> = classes.values.flatten()
    
    fun getPopularClasses(): List<GymClass> = getAllClasses()
        .filter { it.capacity.utilizationPercentage >= 80 }
        .sortedByDescending { it.capacity.utilizationPercentage }
}

// domain/model/ValueObjects.kt
data class ClassSchedule(
    val dayOfWeek: DayOfWeek,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val duration: Duration = Duration.between(startTime, endTime),
    val recurrence: RecurrenceType = RecurrenceType.WEEKLY
) {
    fun isConflictWith(other: ClassSchedule): Boolean {
        return dayOfWeek == other.dayOfWeek && (
            startTime.isBefore(other.endTime) && endTime.isAfter(other.startTime)
        )
    }
}

data class ClassCapacity(
    val current: Int,
    val maximum: Int,
    val waitingList: Int = 0
) {
    val available: Int get() = maximum - current
    val isAlmostFull: Boolean get() = available <= 3 // Más restrictivo para gym universitario
    val isFull: Boolean get() = available <= 0
    val utilizationPercentage: Int get() = (current * 100) / maximum
    val hasWaitingList: Boolean get() = waitingList > 0
}

data class Instructor(
    val id: String,
    val name: String,
    val specialties: List<String>,
    val certification: String?,
    val experience: Int, // años
    val rating: Float = 0f,
    val profileImage: String? = null
)

enum class ReservationStatus {
    ACTIVE, CANCELLED, COMPLETED, NO_SHOW, WAITING_LIST
}

enum class MembershipType {
    STUDENT_BASIC,    // Estudiante básico
    STUDENT_PREMIUM,  // Estudiante con acceso completo
    FACULTY,          // Personal docente
    STAFF,            // Personal administrativo
    GUEST             // Visitante
}

enum class DifficultyLevel {
    BEGINNER, INTERMEDIATE, ADVANCED, ALL_LEVELS
}

enum class RecurrenceType {
    DAILY, WEEKLY, MONTHLY, ONE_TIME
}
```

#### 🔄 Repository Interfaces (Contratos)
```kotlin
// domain/repository/ClassRepository.kt
interface ClassRepository {
    fun getClasses(): Flow<List<GymClass>>
    suspend fun getClassById(classId: String): GymClass?
    suspend fun refreshClasses(): Result<Unit>
    fun getClassesByDay(dayOfWeek: DayOfWeek): Flow<List<GymClass>>
    suspend fun searchClasses(query: String): List<GymClass>
}

// domain/repository/UserRepository.kt
interface UserRepository {
    suspend fun signIn(email: String, password: String): Result<User>
    suspend fun signUp(email: String, password: String, fullName: String): Result<User>
    suspend fun getCurrentUser(): User?
    suspend fun signOut(): Result<Unit>
    suspend fun updateProfile(user: User): Result<User>
}

// domain/repository/ReservationRepository.kt
interface ReservationRepository {
    fun getUserReservations(userId: String): Flow<List<Reservation>>
    suspend fun createReservation(classId: String, date: LocalDate): Result<Reservation>
    suspend fun cancelReservation(reservationId: String): Result<Unit>
    suspend fun getClassReservationCount(classId: String, date: LocalDate): Int
}
```

#### 🎯 Use Cases (Casos de Uso) - Funcionalidades Universitarias
```kotlin
// domain/usecase/schedule/GetWeeklyScheduleUseCase.kt
class GetWeeklyScheduleUseCase @Inject constructor(
    private val classRepository: ClassRepository
) {
    operator fun invoke(): Flow<Result<WeeklySchedule>> = flow {
        try {
            classRepository.refreshClasses().fold(
                onSuccess = { 
                    emitAll(classRepository.getClasses().map { classes ->
                        val groupedByDay = classes.groupBy { it.schedule.dayOfWeek }
                        Result.success(WeeklySchedule(groupedByDay))
                    })
                },
                onFailure = { error ->
                    // Emitir datos locales aunque falle la sincronización
                    emitAll(classRepository.getClasses().map { classes ->
                        val groupedByDay = classes.groupBy { it.schedule.dayOfWeek }
                        Result.success(WeeklySchedule(groupedByDay))
                    })
                }
            )
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}

// domain/usecase/schedule/GetDailyScheduleUseCase.kt
class GetDailyScheduleUseCase @Inject constructor(
    private val classRepository: ClassRepository
) {
    operator fun invoke(date: LocalDate = LocalDate.now()): Flow<List<GymClass>> {
        val dayOfWeek = date.dayOfWeek
        return classRepository.getClassesByDay(dayOfWeek)
            .map { classes ->
                classes.sortedBy { it.schedule.startTime }
            }
    }
}

// domain/usecase/reservations/ReserveClassUseCase.kt (Actualizado para universitarios)
class ReserveClassUseCase @Inject constructor(
    private val reservationRepository: ReservationRepository,
    private val classRepository: ClassRepository,
    private val studentRepository: StudentRepository,
    private val scheduleNotificationUseCase: ScheduleClassReminderUseCase
) {
    suspend operator fun invoke(
        classId: String,
        date: LocalDate,
        studentId: String
    ): Result<Reservation> = runCatching {
        
        // 1. Validar que el estudiante existe y está activo
        val student = studentRepository.getStudentById(studentId)
            ?: throw IllegalArgumentException("Student not found")
        
        if (!student.isActive) {
            throw IllegalStateException("Student membership is not active")
        }
        
        // 2. Validar que la clase existe
        val gymClass = classRepository.getClassById(classId)
            ?: throw IllegalArgumentException("Class not found")
        
        // 3. Verificar que no hay conflictos de horario
        val existingReservations = reservationRepository.getStudentReservationsForDate(studentId, date)
        val hasConflict = existingReservations.any { reservation ->
            val existingClass = classRepository.getClassById(reservation.classId)
            existingClass?.schedule?.isConflictWith(gymClass.schedule) == true
        }
        
        if (hasConflict) {
            throw IllegalStateException("Schedule conflict with existing reservation")
        }
        
        // 4. Verificar capacidad disponible
        val currentReservations = reservationRepository.getClassReservationCount(classId, date)
        if (currentReservations >= gymClass.capacity.maximum) {
            // Agregar a lista de espera si está disponible
            if (gymClass.capacity.hasWaitingList) {
                throw IllegalStateException("Class is full, added to waiting list")
            } else {
                throw IllegalStateException("Class is full")
            }
        }
        
        // 5. Verificar que el estudiante no ya reservó esta clase
        val existingReservation = reservationRepository.getStudentReservation(studentId, classId, date)
        if (existingReservation != null) {
            throw IllegalStateException("Student already has a reservation for this class")
        }
        
        // 6. Crear reserva
        val reservation = reservationRepository.createReservation(classId, studentId, date).getOrThrow()
        
        // 7. Programar recordatorio
        scheduleNotificationUseCase(reservation)
        
        reservation
    }
}

// domain/usecase/reservations/GetMyReservationsUseCase.kt
class GetMyReservationsUseCase @Inject constructor(
    private val reservationRepository: ReservationRepository
) {
    operator fun invoke(studentId: String): Flow<List<Reservation>> {
        return reservationRepository.getStudentActiveReservations(studentId)
            .map { reservations ->
                reservations
                    .filter { it.classDate >= LocalDate.now() } // Solo futuras
                    .sortedWith(compareBy({ it.classDate }, { it.startTime }))
            }
    }
}

// domain/usecase/reservations/CancelReservationUseCase.kt
class CancelReservationUseCase @Inject constructor(
    private val reservationRepository: ReservationRepository,
    private val cancelNotificationUseCase: CancelClassReminderUseCase
) {
    suspend operator fun invoke(reservationId: String): Result<Unit> = runCatching {
        
        // 1. Obtener reserva
        val reservation = reservationRepository.getReservationById(reservationId)
            ?: throw IllegalArgumentException("Reservation not found")
        
        // 2. Validar que se puede cancelar (mínimo 2 horas antes)
        if (!reservation.canBeCancelled) {
            throw IllegalStateException("Cannot cancel reservation less than 2 hours before class")
        }
        
        // 3. Cancelar reserva
        reservationRepository.cancelReservation(reservationId)
        
        // 4. Cancelar recordatorio programado
        cancelNotificationUseCase(reservationId)
        
        // 5. Procesar lista de espera si hay
        processWaitingList(reservation.classId, reservation.classDate)
    }
    
    private suspend fun processWaitingList(classId: String, date: LocalDate) {
        // Lógica para mover el primer estudiante de la lista de espera a reservado
        val waitingListStudent = reservationRepository.getFirstInWaitingList(classId, date)
        waitingListStudent?.let { student ->
            reservationRepository.moveFromWaitingListToReserved(student.id, classId, date)
            // Notificar al estudiante que su reserva fue confirmada
        }
    }
}

// domain/usecase/classes/GetClassDetailUseCase.kt
class GetClassDetailUseCase @Inject constructor(
    private val classRepository: ClassRepository,
    private val reservationRepository: ReservationRepository
) {
    suspend operator fun invoke(classId: String, date: LocalDate): Result<ClassDetailInfo> = runCatching {
        
        val gymClass = classRepository.getClassById(classId)
            ?: throw IllegalArgumentException("Class not found")
        
        val currentReservations = reservationRepository.getClassReservationCount(classId, date)
        val waitingListCount = reservationRepository.getWaitingListCount(classId, date)
        
        ClassDetailInfo(
            gymClass = gymClass,
            currentReservations = currentReservations,
            waitingListCount = waitingListCount,
            isClassFull = currentReservations >= gymClass.capacity.maximum,
            availableSpots = gymClass.capacity.maximum - currentReservations
        )
    }
}

// domain/usecase/classes/SearchClassesUseCase.kt
class SearchClassesUseCase @Inject constructor(
    private val classRepository: ClassRepository
) {
    suspend operator fun invoke(
        query: String,
        filters: ClassFilters = ClassFilters()
    ): Result<List<GymClass>> = runCatching {
        
        val classes = classRepository.searchClasses(query)
        
        // Aplicar filtros
        classes.filter { gymClass ->
            val matchesDifficulty = filters.difficulty?.let { 
                gymClass.difficulty == it || gymClass.difficulty == DifficultyLevel.ALL_LEVELS 
            } ?: true
            
            val matchesInstructor = filters.instructorName?.let {
                gymClass.instructor.name.contains(it, ignoreCase = true)
            } ?: true
            
            val matchesTime = filters.timeRange?.let { range ->
                gymClass.schedule.startTime >= range.start && gymClass.schedule.endTime <= range.end
            } ?: true
            
            val matchesTags = filters.tags.isEmpty() || filters.tags.any { tag ->
                gymClass.tags.contains(tag)
            }
            
            matchesDifficulty && matchesInstructor && matchesTime && matchesTags
        }
    }
}

// Data classes de apoyo
data class ClassDetailInfo(
    val gymClass: GymClass,
    val currentReservations: Int,
    val waitingListCount: Int,
    val isClassFull: Boolean,
    val availableSpots: Int
)

data class ClassFilters(
    val difficulty: DifficultyLevel? = null,
    val instructorName: String? = null,
    val timeRange: TimeRange? = null,
    val tags: List<String> = emptyList()
)

data class TimeRange(
    val start: LocalTime,
    val end: LocalTime
)
```

### 🟢 **DATA LAYER** - Gestión de Datos

#### 🗄️ Models (DTOs y Entities)
```kotlin
// data/model/remote/ClassDto.kt (API Response)
@Serializable
data class ClassDto(
    val id: String,
    val name: String,
    val description: String?,
    val instructor: String,
    @SerialName("day_of_week") val dayOfWeek: Int,
    @SerialName("start_time") val startTime: String,
    @SerialName("end_time") val endTime: String,
    @SerialName("max_capacity") val maxCapacity: Int,
    @SerialName("created_at") val createdAt: String
)

// data/model/local/ClassEntity.kt (Room Database)
@Entity(tableName = "gym_classes")
data class ClassEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String?,
    val instructor: String,
    val dayOfWeek: Int,
    val startTime: String,
    val endTime: String,
    val maxCapacity: Int,
    val createdAt: Long
)
```

#### 🔥 Firebase Services (Requerimientos)
```kotlin
// data/firebase/FirebaseAuthService.kt
class FirebaseAuthService @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun signIn(email: String, password: String): Result<FirebaseUser> = 
        suspendCancellableCoroutine { continuation ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    result.user?.let { user ->
                        continuation.resume(Result.success(user))
                    } ?: continuation.resume(Result.failure(Exception("Usuario no encontrado")))
                }
                .addOnFailureListener { exception ->
                    continuation.resume(Result.failure(exception))
                }
        }
    
    suspend fun signUp(email: String, password: String): Result<FirebaseUser> = 
        suspendCancellableCoroutine { continuation ->
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    result.user?.let { user ->
                        continuation.resume(Result.success(user))
                    } ?: continuation.resume(Result.failure(Exception("Error al crear usuario")))
                }
                .addOnFailureListener { exception ->
                    continuation.resume(Result.failure(exception))
                }
        }
    
    fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser
    
    fun signOut() = firebaseAuth.signOut()
}

// data/firebase/FirebaseStorageService.kt
class FirebaseStorageService @Inject constructor(
    private val storageReference: StorageReference
) {
    suspend fun uploadProfileImage(userId: String, imageUri: Uri): Result<String> = 
        suspendCancellableCoroutine { continuation ->
            val imageRef = storageReference.child("profile_images/$userId.jpg")
            
            imageRef.putFile(imageUri)
                .addOnSuccessListener {
                    imageRef.downloadUrl
                        .addOnSuccessListener { downloadUri ->
                            continuation.resume(Result.success(downloadUri.toString()))
                        }
                        .addOnFailureListener { exception ->
                            continuation.resume(Result.failure(exception))
                        }
                }
                .addOnFailureListener { exception ->
                    continuation.resume(Result.failure(exception))
                }
        }
    
    suspend fun deleteProfileImage(userId: String): Result<Unit> = 
        suspendCancellableCoroutine { continuation ->
            val imageRef = storageReference.child("profile_images/$userId.jpg")
            
            imageRef.delete()
                .addOnSuccessListener {
                    continuation.resume(Result.success(Unit))
                }
                .addOnFailureListener { exception ->
                    continuation.resume(Result.failure(exception))
                }
        }
}

// data/firebase/FirestoreService.kt
class FirestoreService @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val classesCollection = firestore.collection("classes")
    private val reservationsCollection = firestore.collection("reservations")
    private val usersCollection = firestore.collection("users")
    
    suspend fun getClasses(): List<ClassDto> = suspendCancellableCoroutine { continuation ->
        classesCollection.get()
            .addOnSuccessListener { documents ->
                val classes = documents.mapNotNull { doc ->
                    doc.toObject<ClassDto>().copy(id = doc.id)
                }
                continuation.resume(classes)
            }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }
    
    suspend fun createReservation(reservation: ReservationDto): Result<String> = 
        suspendCancellableCoroutine { continuation ->
            reservationsCollection.add(reservation)
                .addOnSuccessListener { documentReference ->
                    continuation.resume(Result.success(documentReference.id))
                }
                .addOnFailureListener { exception ->
                    continuation.resume(Result.failure(exception))
                }
        }
    
    suspend fun getUserReservations(userId: String): List<ReservationDto> = 
        suspendCancellableCoroutine { continuation ->
            reservationsCollection
                .whereEqualTo("userId", userId)
                .whereEqualTo("status", "ACTIVE")
                .get()
                .addOnSuccessListener { documents ->
                    val reservations = documents.mapNotNull { doc ->
                        doc.toObject<ReservationDto>().copy(id = doc.id)
                    }
                    continuation.resume(reservations)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
}
```

#### 🏢 DataSources
```kotlin
// data/datasource/local/ClassDao.kt
@Dao
interface ClassDao {
    @Query("SELECT * FROM gym_classes ORDER BY dayOfWeek, startTime")
    fun getAllClasses(): Flow<List<ClassEntity>>
    
    @Query("SELECT * FROM gym_classes WHERE id = :classId")
    suspend fun getClassById(classId: String): ClassEntity?
    
    @Query("SELECT * FROM gym_classes WHERE dayOfWeek = :day ORDER BY startTime")
    fun getClassesByDay(day: Int): Flow<List<ClassEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClasses(classes: List<ClassEntity>)
    
    @Query("DELETE FROM gym_classes")
    suspend fun clearClasses()
}

// data/datasource/remote/ClassRemoteDataSource.kt
class ClassRemoteDataSource @Inject constructor(
    private val api: GymClassApi
) {
    suspend fun getClasses(): List<ClassDto> = api.getClasses()
    suspend fun getClassById(classId: String): ClassDto = api.getClassById(classId)
    suspend fun searchClasses(query: String): List<ClassDto> = api.searchClasses(query)
}
```

#### 🔄 Repository Implementation con Firebase
```kotlin
// data/repository/UserRepositoryImpl.kt (Firebase Authentication)
@Singleton
class UserRepositoryImpl @Inject constructor(
    private val firebaseAuthService: FirebaseAuthService,
    private val firebaseStorageService: FirebaseStorageService,
    private val firestoreService: FirestoreService,
    private val userDao: UserDao,
    private val mapper: UserMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserRepository {
    
    override suspend fun signIn(email: String, password: String): Result<User> = withContext(dispatcher) {
        firebaseAuthService.signIn(email, password).mapCatching { firebaseUser ->
            val userDto = firestoreService.getUserProfile(firebaseUser.uid)
            val user = mapper.toDomain(userDto, firebaseUser)
            userDao.insertUser(mapper.toEntity(user))
            user
        }
    }
    
    override suspend fun signUp(email: String, password: String, fullName: String): Result<User> = 
        withContext(dispatcher) {
            firebaseAuthService.signUp(email, password).mapCatching { firebaseUser ->
                val userDto = UserDto(
                    id = firebaseUser.uid,
                    email = email,
                    fullName = fullName,
                    profileImageUrl = null,
                    membershipType = "BASIC",
                    createdAt = System.currentTimeMillis()
                )
                
                firestoreService.createUserProfile(userDto)
                val user = mapper.toDomain(userDto, firebaseUser)
                userDao.insertUser(mapper.toEntity(user))
                user
            }
        }
    
    override suspend fun updateProfileImage(userId: String, imageUri: Uri): Result<String> = 
        withContext(dispatcher) {
            firebaseStorageService.uploadProfileImage(userId, imageUri)
        }
    
    override suspend fun getCurrentUser(): User? = withContext(dispatcher) {
        firebaseAuthService.getCurrentUser()?.let { firebaseUser ->
            userDao.getUserById(firebaseUser.uid)?.let { entity ->
                mapper.toDomain(entity)
            }
        }
    }
    
    override suspend fun signOut(): Result<Unit> = withContext(dispatcher) {
        runCatching {
            firebaseAuthService.signOut()
            userDao.clearUsers()
        }
    }
}

// data/repository/ClassRepositoryImpl.kt (Firebase + Room)
@Singleton
class ClassRepositoryImpl @Inject constructor(
    private val localDataSource: ClassDao,
    private val firestoreService: FirestoreService,
    private val mapper: ClassMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ClassRepository {
    
    override fun getClasses(): Flow<List<GymClass>> {
        return localDataSource.getAllClasses()
            .map { entities -> entities.map(mapper::toDomain) }
            .flowOn(dispatcher)
    }
    
    override suspend fun refreshClasses(): Result<Unit> = withContext(dispatcher) {
        runCatching {
            val remoteClasses = firestoreService.getClasses()
            val entities = remoteClasses.map(mapper::toEntity)
            localDataSource.clearClasses()
            localDataSource.insertClasses(entities)
        }
    }
    
    override suspend fun searchClasses(query: String): List<GymClass> = withContext(dispatcher) {
        firestoreService.searchClasses(query).map(mapper::toDomain)
    }
}
```

### � **PRESENTATION LAYER** - UI y ViewModels

#### 🎯 ViewModels
```kotlin
// presentation/viewmodel/ClassListViewModel.kt
@HiltViewModel
class ClassListViewModel @Inject constructor(
    private val getClassesUseCase: GetClassesUseCase,
    private val getClassesByDayUseCase: GetClassesByDayUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ClassListUiState())
    val uiState: StateFlow<ClassListUiState> = _uiState.asStateFlow()
    
    init {
        loadClasses()
    }
    
    fun onDaySelected(day: DayOfWeek?) {
        _uiState.update { currentState ->
            currentState.copy(selectedDay = day)
        }
        
        if (day != null) {
            loadClassesByDay(day)
        } else {
            loadClasses()
        }
    }
    
    fun onRefresh() {
        loadClasses()
    }
    
    private fun loadClasses() {
        viewModelScope.launch {
            getClassesUseCase().collect { result ->
                _uiState.update { currentState ->
                    result.fold(
                        onSuccess = { classes ->
                            currentState.copy(
                                classes = classes,
                                isLoading = false,
                                error = null
                            )
                        },
                        onFailure = { error ->
                            currentState.copy(
                                isLoading = false,
                                error = error.message
                            )
                        }
                    )
                }
            }
        }
    }
    
    private fun loadClassesByDay(day: DayOfWeek) {
        viewModelScope.launch {
            getClassesByDayUseCase(day).collect { classes ->
                _uiState.update { currentState ->
                    currentState.copy(
                        classes = classes,
                        isLoading = false,
                        error = null
                    )
                }
            }
        }
    }
}

// presentation/viewmodel/ClassListUiState.kt
data class ClassListUiState(
    val classes: List<GymClass> = emptyList(),
    val selectedDay: DayOfWeek? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)
```

#### 🎨 Views (Jetpack Compose UI - Requerimiento)
```kotlin
// presentation/views/classes/ClassListScreen.kt
@Composable
fun ClassListScreen(
    viewModel: ClassListViewModel = hiltViewModel(),
    onNavigateToDetail: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    
    // 🎨 Jetpack Compose UI moderna
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top App Bar con Material 3
        TopAppBar(
            title = { 
                Text(
                    "GymClass",
                    style = MaterialTheme.typography.headlineMedium
                ) 
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        )
        
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                DayFilterChips(
                    selectedDay = uiState.selectedDay,
                    onDaySelected = viewModel::onDaySelected
                )
            }
            
            when {
                uiState.isLoading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                uiState.error != null -> {
                    item {
                        ErrorCard(
                            message = uiState.error,
                            onRetry = viewModel::onRefresh
                        )
                    }
                }
                else -> {
                    items(uiState.classes) { gymClass ->
                        ClassCard(
                            gymClass = gymClass,
                            onClick = { onNavigateToDetail(gymClass.id) }
                        )
                    }
                }
            }
        }
    }
}

// presentation/views/classes/components/ClassCard.kt (Material 3 Design)
@Composable
fun ClassCard(
    gymClass: GymClass,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = gymClass.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Text(
                        text = "Instructor: ${gymClass.instructor.name}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }
                
                // Badge de disponibilidad
                CapacityChip(capacity = gymClass.capacity)
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = "Horario",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${gymClass.schedule.startTime} - ${gymClass.schedule.endTime}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                FilledTonalButton(
                    onClick = onClick,
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text("Ver Detalles")
                }
            }
        }
    }
}

@Composable
fun CapacityChip(
    capacity: ClassCapacity,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, textColor, text) = when {
        capacity.isFull -> Triple(
            MaterialTheme.colorScheme.errorContainer,
            MaterialTheme.colorScheme.onErrorContainer,
            "Lleno"
        )
        capacity.isAlmostFull -> Triple(
            MaterialTheme.colorScheme.tertiaryContainer,
            MaterialTheme.colorScheme.onTertiaryContainer,
            "Últimos ${capacity.available}"
        )
        else -> Triple(
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.onPrimaryContainer,
            "${capacity.available} disponibles"
        )
    }
    
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            fontWeight = FontWeight.Medium
        )
    }
}
```

---

## 🔔 Sistema de Notificaciones (Requerimiento)

### 📱 Firebase Cloud Messaging Implementation

#### 🔧 Configuración de FCM Service
```kotlin
// data/firebase/GymClassFirebaseMessagingService.kt
class GymClassFirebaseMessagingService : FirebaseMessagingService() {
    
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        
        // Manejar notificaciones recibidas
        remoteMessage.notification?.let { notification ->
            when (remoteMessage.data["type"]) {
                "class_reminder" -> handleClassReminder(notification, remoteMessage.data)
                "reservation_update" -> handleReservationUpdate(notification, remoteMessage.data)
                "class_cancelled" -> handleClassCancellation(notification, remoteMessage.data)
                else -> showGenericNotification(notification)
            }
        }
    }
    
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        
        // Enviar token al servidor para notificaciones personalizadas
        sendTokenToServer(token)
    }
    
    private fun handleClassReminder(notification: RemoteMessage.Notification, data: Map<String, String>) {
        val classId = data["classId"] ?: return
        val className = data["className"] ?: "Tu clase"
        val startTime = data["startTime"] ?: ""
        
        showNotification(
            channelId = "class_reminders",
            title = "⏰ Recordatorio de Clase",
            message = "$className comienza en 30 minutos ($startTime)",
            pendingIntent = createClassDetailIntent(classId)
        )
    }
    
    private fun handleReservationUpdate(notification: RemoteMessage.Notification, data: Map<String, String>) {
        val reservationId = data["reservationId"] ?: return
        val status = data["status"] ?: ""
        
        val (title, message) = when (status) {
            "confirmed" -> "✅ Reserva Confirmada" to "Tu reserva ha sido confirmada exitosamente"
            "cancelled" -> "❌ Reserva Cancelada" to "Tu reserva ha sido cancelada"
            else -> "📋 Actualización de Reserva" to notification.body ?: ""
        }
        
        showNotification(
            channelId = "reservation_updates",
            title = title,
            message = message,
            pendingIntent = createReservationsIntent()
        )
    }
    
    private fun showNotification(
        channelId: String,
        title: String,
        message: String,
        pendingIntent: PendingIntent
    ) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
        
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
    
    private fun createClassDetailIntent(classId: String): PendingIntent {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("navigate_to", "class_detail")
            putExtra("class_id", classId)
        }
        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }
    
    private fun createReservationsIntent(): PendingIntent {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("navigate_to", "my_reservations")
        }
        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }
    
    private fun sendTokenToServer(token: String) {
        // Enviar token a Firebase Firestore para notificaciones personalizadas
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        
        FirebaseFirestore.getInstance()
            .collection("user_tokens")
            .document(userId)
            .set(mapOf("fcm_token" to token, "updated_at" to System.currentTimeMillis()))
    }
}
```

#### 📅 Notification Scheduler (WorkManager)
```kotlin
// data/notifications/NotificationScheduler.kt
class NotificationScheduler @Inject constructor(
    private val context: Context,
    private val workManager: WorkManager
) {
    
    fun scheduleClassReminder(reservation: Reservation, gymClass: GymClass) {
        val reminderTime = calculateReminderTime(reservation.classDate, gymClass.schedule.startTime)
        
        val workRequest = OneTimeWorkRequestBuilder<ClassReminderWorker>()
            .setInitialDelay(reminderTime, TimeUnit.MILLISECONDS)
            .setInputData(
                workDataOf(
                    "reservation_id" to reservation.id,
                    "class_id" to reservation.classId,
                    "class_name" to gymClass.name,
                    "start_time" to gymClass.schedule.startTime.toString()
                )
            )
            .build()
        
        workManager.enqueueUniqueWork(
            "class_reminder_${reservation.id}",
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }
    
    fun cancelClassReminder(reservationId: String) {
        workManager.cancelUniqueWork("class_reminder_$reservationId")
    }
    
    private fun calculateReminderTime(classDate: LocalDate, startTime: LocalTime): Long {
        val classDateTime = LocalDateTime.of(classDate, startTime)
        val reminderDateTime = classDateTime.minusMinutes(30) // 30 minutos antes
        val now = LocalDateTime.now()
        
        return if (reminderDateTime.isAfter(now)) {
            Duration.between(now, reminderDateTime).toMillis()
        } else {
            0L // No programar si ya pasó el tiempo
        }
    }
}

// data/notifications/ClassReminderWorker.kt
class ClassReminderWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val notificationManager: NotificationManagerCompat
) : CoroutineWorker(context, workerParams) {
    
    override suspend fun doWork(): Result {
        val reservationId = inputData.getString("reservation_id") ?: return Result.failure()
        val classId = inputData.getString("class_id") ?: return Result.failure()
        val className = inputData.getString("class_name") ?: return Result.failure()
        val startTime = inputData.getString("start_time") ?: return Result.failure()
        
        showClassReminderNotification(className, startTime, classId)
        
        return Result.success()
    }
    
    private fun showClassReminderNotification(className: String, startTime: String, classId: String) {
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            putExtra("navigate_to", "class_detail")
            putExtra("class_id", classId)
        }
        
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val notification = NotificationCompat.Builder(applicationContext, "class_reminders")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("⏰ Tu clase comienza pronto")
            .setContentText("$className empieza en 30 minutos ($startTime)")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("¡No olvides tu clase de $className que comienza a las $startTime! Recuerda llegar con tiempo suficiente."))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .addAction(
                R.drawable.ic_directions,
                "Ver Ubicación",
                createLocationIntent()
            )
            .build()
        
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
    
    private fun createLocationIntent(): PendingIntent {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("geo:0,0?q=gimnasio")
        }
        return PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
    
    @AssistedFactory
    interface Factory {
        fun create(context: Context, workerParams: WorkerParameters): ClassReminderWorker
    }
}
```

#### 🔔 Notification Use Cases
```kotlin
// domain/usecase/notifications/ScheduleClassReminderUseCase.kt
class ScheduleClassReminderUseCase @Inject constructor(
    private val notificationScheduler: NotificationScheduler,
    private val classRepository: ClassRepository
) {
    suspend operator fun invoke(reservation: Reservation): Result<Unit> = runCatching {
        val gymClass = classRepository.getClassById(reservation.classId)
            ?: throw IllegalArgumentException("Class not found")
        
        notificationScheduler.scheduleClassReminder(reservation, gymClass)
    }
}

// domain/usecase/notifications/CancelClassReminderUseCase.kt
class CancelClassReminderUseCase @Inject constructor(
    private val notificationScheduler: NotificationScheduler
) {
    operator fun invoke(reservationId: String) {
        notificationScheduler.cancelClassReminder(reservationId)
    }
}

// domain/usecase/notifications/RequestNotificationPermissionUseCase.kt
class RequestNotificationPermissionUseCase @Inject constructor(
    private val context: Context
) {
    fun invoke(activity: ComponentActivity): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED) {
                
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_REQUEST_CODE
                )
                false
            } else {
                true
            }
        } else {
            true // No se requiere permiso en versiones anteriores
        }
    }
    
    companion object {
        const val NOTIFICATION_PERMISSION_REQUEST_CODE = 100
    }
}
```

---

## 🧪 Testing Guidelines

### Unit Tests
```kotlin
@ExperimentalCoroutinesTest
class GetClassesUseCaseTest {
    
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    
    private val mockRepository = mockk<ClassRepository>()
    private val useCase = GetClassesUseCase(mockRepository)
    
    @Test
    fun `when repository returns classes, then emit success result`() = runTest {
        // Given
        val mockClasses = listOf(
            createMockGymClass(id = "1", name = "Yoga"),
            createMockGymClass(id = "2", name = "CrossFit")
        )
        every { mockRepository.getClasses() } returns flowOf(mockClasses)
        coEvery { mockRepository.refreshClasses() } returns Result.success(Unit)
        
        // When
        val result = useCase().first()
        
        // Then
        assertTrue(result.isSuccess)
        assertEquals(mockClasses, result.getOrNull())
    }
    
    private fun createMockGymClass(
        id: String,
        name: String
    ) = GymClass(
        id = id,
        name = name,
        description = null,
        instructor = "Test Instructor",
        schedule = ClassSchedule(
            dayOfWeek = DayOfWeek.MONDAY,
            startTime = LocalTime.of(9, 0),
            endTime = LocalTime.of(10, 0)
        ),
        capacity = ClassCapacity(current = 0, maximum = 20)
    )
}
```

---

## 🚀 Performance Best Practices

### 1. **Lazy Loading**
```kotlin
class MainActivity : ComponentActivity() {
    
    private val userRepository by lazy { 
        (application as GymClassApplication).appContainer.userRepository 
    }
    
    // O usando Hilt
    @Inject
    lateinit var userRepository: UserRepository
}
```

### 2. **Efficient Collections**
```kotlin
// ✅ Uso eficiente de collections
fun filterAvailableClasses(classes: List<GymClass>): List<GymClass> {
    return classes.asSequence()
        .filter { !it.capacity.isFull }
        .sortedBy { it.schedule.startTime }
        .toList()
}

// ✅ Usar flow para transformaciones
fun searchClasses(query: String): Flow<List<GymClass>> {
    return repository.getClasses()
        .map { classes ->
            classes.filter { 
                it.name.contains(query, ignoreCase = true) ||
                it.instructor.contains(query, ignoreCase = true)
            }
        }
        .distinctUntilChanged()
}
```

### 3. **Memory Management**
```kotlin
// ✅ Limpiar recursos en ViewModels
override fun onCleared() {
    super.onCleared()
    // Cancelar trabajos de corrutinas si es necesario
}

// ✅ Usar remember en Compose para evitar recomposiciones
@Composable
fun ExpensiveComponent(data: List<Item>) {
    val processedData = remember(data) {
        data.groupBy { it.category }.mapValues { it.value.size }
    }
    // UI implementation
}
```

---

## 🔒 Seguridad y Buenas Prácticas

### 1. **Manejo de Datos Sensibles**
```kotlin
// ✅ Usar EncryptedSharedPreferences para datos sensibles
class SecurePreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val sharedPreferences = EncryptedSharedPreferences.create(
        "secure_prefs",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}
```

### 2. **Validación de Input**
```kotlin
data class RegisterUserRequest(
    val email: String,
    val password: String,
    val fullName: String
) {
    init {
        require(email.isValidEmail()) { "Email format is invalid" }
        require(password.length >= 6) { "Password must be at least 6 characters" }
        require(fullName.isNotBlank()) { "Full name cannot be empty" }
    }
}

fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
```

---

## 📚 Recursos Adicionales

### Documentación Recomendada
- [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- [Android Architecture Guide](https://developer.android.com/topic/architecture)
- [Clean Architecture in Android](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

### Herramientas de Desarrollo
- **ktlint**: Linting automático para Kotlin
- **detekt**: Análisis estático de código
- **Leak Canary**: Detección de memory leaks
- **Flipper**: Debugging y análisis de red

---

## ✅ Checklist de Implementación - Gimnasio Universitario

### Configuración Inicial
- [ ] Configurar estructura de módulos
- [ ] **🔥 Configurar Firebase Project y google-services.json**
- [ ] **🔧 Implementar inyección de dependencias (Hilt)**
- [ ] **🗄️ Configurar base de datos local (Room)**
- [ ] **🌐 Configurar Firebase Authentication y Firestore**
- [ ] **🎨 Implementar navegación con Jetpack Compose**

### 🏫 Funcionalidades Universitarias Core
- [ ] **🔐 Sistema de autenticación para estudiantes**
  - [ ] Login con email institucional
  - [ ] Validación de matrícula universitaria
  - [ ] Gestión de tipos de membresía (Estudiante/Facultad/Personal)

- [ ] **� Sistema de Horarios**
  - [ ] Vista diaria de clases disponibles
  - [ ] Vista semanal completa
  - [ ] Filtros por día de la semana
  - [ ] Búsqueda de clases por nombre/instructor

- [ ] **� Pantalla de Detalle de Clase**
  - [ ] Información completa de la clase
  - [ ] Datos del instructor y especialidades
  - [ ] Disponibilidad en tiempo real
  - [ ] **🔘 Botón "Reservar" con validaciones**
  - [ ] **🔘 Botón "Cancelar" con restricciones**

- [ ] **📱 Sección "Mis Reservas"**
  - [ ] Lista de próximas clases reservadas
  - [ ] Estado de cada reserva (Activa/Cancelada)
  - [ ] Información de horarios y ubicación
  - [ ] Opción de cancelación con validaciones

### 🛠️ Habilidades Android Clave
- [ ] **� RecyclerView/LazyColumn Implementation**
  - [ ] Adaptador para lista de horarios
  - [ ] DiffUtil para actualizaciones eficientes
  - [ ] ViewHolder pattern optimizado
  - [ ] Pull-to-refresh functionality

- [ ] **🏗️ ViewModel Architecture**
  - [ ] ScheduleViewModel para horarios
  - [ ] ClassDetailViewModel para detalles
  - [ ] MyReservationsViewModel para reservas
  - [ ] Gestión de estados UI con StateFlow

- [ ] **�️ Room Database**
  - [ ] Entidades para clases, reservas y estudiantes
  - [ ] DAOs con queries complejas
  - [ ] Relaciones entre tablas
  - [ ] Migration strategies

- [ ] **☁️ Firebase Firestore**
  - [ ] Sincronización en tiempo real
  - [ ] Offline capability
  - [ ] Security rules para datos universitarios
  - [ ] Optimistic updates

- [ ] **⏰ AlarmManager/WorkManager**
  - [ ] Recordatorios 30 minutos antes de clase
  - [ ] Programación de alarmas exactas
  - [ ] BroadcastReceiver para notificaciones
  - [ ] Cancelación automática al cancelar reservas

### 🔔 Sistema de Notificaciones Universitarias
- [ ] **� Push Notifications con FCM**
  - [ ] Recordatorios de clases próximas
  - [ ] Confirmaciones de reservas
  - [ ] Alertas de cancelaciones
  - [ ] Notificaciones de lista de espera

- [ ] **⏰ Local Notifications**
  - [ ] Alarmas programadas con AlarmManager
  - [ ] Notificaciones persistentes
  - [ ] Deep linking a pantallas específicas
  - [ ] Gestión de permisos Android 13+

### 🎨 UI/UX con Jetpack Compose
- [ ] **📱 Pantallas Principales**
  - [ ] Pantalla de login universitario
  - [ ] Dashboard con horarios del día
  - [ ] Lista semanal de clases
  - [ ] Detalle de clase con reserva
  - [ ] "Mis Reservas" personalizada

- [ ] **� Material Design 3**
  - [ ] Theme universitario personalizado
  - [ ] Componentes accesibles
  - [ ] Navegación fluida entre pantallas
  - [ ] Estados de loading y error

### 📊 Funcionalidades Avanzadas
- [ ] **🔍 Búsqueda y Filtros**
  - [ ] Búsqueda por nombre de clase
  - [ ] Filtros por instructor
  - [ ] Filtros por nivel de dificultad
  - [ ] Filtros por horario disponible

- [ ] **📈 Analytics y Reportes**
  - [ ] Clases más populares
  - [ ] Horarios de mayor demanda
  - [ ] Estadísticas de asistencia
  - [ ] Métricas de uso por facultad

- [ ] **⚡ Optimizaciones**
  - [ ] Cache inteligente de datos
  - [ ] Lazy loading de imágenes
  - [ ] Paginación en listas largas
  - [ ] Gestión eficiente de memoria

### 🧪 Testing y Quality Assurance
- [ ] **🧪 Tests Unitarios**
  - [ ] ViewModels y Use Cases
  - [ ] Repository implementations
  - [ ] Business logic validation
  - [ ] Notification scheduling

- [ ] **� Tests de Integración**
  - [ ] Room Database operations
  - [ ] Firebase integration
  - [ ] End-to-end reservation flow
  - [ ] AlarmManager functionality

- [ ] **🎭 Tests de UI**
  - [ ] Compose UI testing
  - [ ] Navigation testing
  - [ ] User interaction flows
  - [ ] Accessibility testing

### 🔒 Seguridad y Compliance Universitario
- [ ] **�️ Data Security**
  - [ ] Cifrado de datos sensibles
  - [ ] Validación de inputs
  - [ ] Firestore security rules
  - [ ] Protección de datos estudiantiles

- [ ] **📋 Compliance**
  - [ ] FERPA compliance (datos educativos)
  - [ ] Políticas de privacidad universitaria
  - [ ] Gestión de consentimientos
  - [ ] Auditoría de acceso a datos

---

## 🎯 Funcionalidades Específicas por Requerimiento Universitario

### 🤖 **Kotlin Features para Gimnasio Universitario**
- **Corrutinas** para operaciones de red y base de datos
- **Extension Functions** para formateo de horarios universitarios
- **Sealed Classes** para estados de reserva
- **Data Classes** para entidades estudiantiles
- **Null Safety** para validaciones de membresía

### 🎨 **Jetpack Compose para UI Universitaria**
- **LazyColumn** para listas de horarios eficientes
- **Material 3** con colores institucionales
- **Navigation** entre pantallas de reservas
- **State Management** para disponibilidad en tiempo real
- **Animations** para feedback de reservas exitosas

### ☁️ **Firebase Features para Datos Universitarios**
- **Firestore** para sincronización de horarios
- **Storage** para fotos de instructores
- **Authentication** con emails institucionales
- **Cloud Functions** para lógica de lista de espera
- **Remote Config** para configuraciones por semestre

### 🔐 **Authentication Universitaria**
- **Validación de email institucional** (@universidad.edu)
- **Verificación de matrícula** activa
- **Roles diferenciados** (estudiante, facultad, personal)
- **Session management** por semestre académico
- **Password policies** institucionales

### 🔔 **Notifications para Estudiantes**
- **Recordatorios de clase** 30 min antes
- **Confirmaciones de reserva** inmediatas  
- **Alertas de cancelación** con alternativas
- **Notificaciones de lista de espera** cuando hay cupo
- **Recordatorios de políticas** del gimnasio universitario

### � **RecyclerView/LazyColumn para Horarios**
- **Performance optimizada** para listas de 100+ clases
- **ViewTypes diferentes** para clases disponibles/llenas
- **Pull-to-refresh** para actualizar horarios
- **Scroll infinito** para cargar más semanas
- **Search filtering** en tiempo real

### �️ **Room Database Universitaria**
- **Offline-first** para funcionar sin internet
- **Complex queries** para conflictos de horario
- **Relationships** entre estudiantes, clases y reservas
- **Migration strategies** para cambios de semestre
- **Data backup** y restauración

### ⏰ **AlarmManager para Recordatorios**
- **Exact alarms** para notificaciones críticas
- **Background restrictions** en Android moderno
- **Doze mode compatibility** para ahorro de batería
- **Timezone handling** para campus en diferentes zonas
- **Notification channels** específicos por tipo de alerta

---

*Este documento debe evolucionar junto con el proyecto, incorporando feedback específico del entorno universitario y nuevas necesidades estudiantiles.*