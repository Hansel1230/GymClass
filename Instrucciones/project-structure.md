# 📁 GymClass - Project Structure

## Estructura Clean Architecture + MVVM

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/universidad/gymclass/
│   │   │   ├── 🏗️ GymClassApplication.kt
│   │   │   │
│   │   │   ├── 📱 presentation/          # UI Layer (MVVM)
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── navigation/
│   │   │   │   │   ├── GymClassNavigation.kt
│   │   │   │   │   └── Screen.kt
│   │   │   │   ├── theme/
│   │   │   │   │   ├── Color.kt
│   │   │   │   │   ├── Theme.kt
│   │   │   │   │   └── Type.kt
│   │   │   │   ├── components/          # Shared UI Components
│   │   │   │   │   ├── LoadingButton.kt
│   │   │   │   │   ├── ClassCard.kt
│   │   │   │   │   ├── CustomTextField.kt
│   │   │   │   │   └── ConfirmationDialog.kt
│   │   │   │   ├── auth/               # Authentication Module
│   │   │   │   │   ├── AuthViewModel.kt
│   │   │   │   │   ├── LoginScreen.kt
│   │   │   │   │   ├── RegisterScreen.kt
│   │   │   │   │   └── ForgotPasswordScreen.kt
│   │   │   │   ├── home/               # Home Dashboard
│   │   │   │   │   ├── HomeViewModel.kt
│   │   │   │   │   ├── HomeScreen.kt
│   │   │   │   │   └── DashboardCard.kt
│   │   │   │   ├── classes/            # Classes Module
│   │   │   │   │   ├── ClassesViewModel.kt
│   │   │   │   │   ├── ClassListScreen.kt
│   │   │   │   │   ├── ClassDetailScreen.kt
│   │   │   │   │   └── ClassFilterBottomSheet.kt
│   │   │   │   ├── reservations/       # Reservations Module
│   │   │   │   │   ├── ReservationsViewModel.kt
│   │   │   │   │   ├── MyReservationsScreen.kt
│   │   │   │   │   ├── ReservationDetailScreen.kt
│   │   │   │   │   └── CancelReservationDialog.kt
│   │   │   │   ├── schedule/           # Schedule Module
│   │   │   │   │   ├── ScheduleViewModel.kt
│   │   │   │   │   ├── WeeklyScheduleScreen.kt
│   │   │   │   │   ├── CalendarScreen.kt
│   │   │   │   │   └── TimeSlotCard.kt
│   │   │   │   ├── profile/            # Profile Module
│   │   │   │   │   ├── ProfileViewModel.kt
│   │   │   │   │   ├── ProfileScreen.kt
│   │   │   │   │   ├── EditProfileScreen.kt
│   │   │   │   │   └── SettingsScreen.kt
│   │   │   │   └── notifications/      # Notifications Module
│   │   │   │       ├── NotificationsViewModel.kt
│   │   │   │       ├── NotificationScreen.kt
│   │   │   │       └── NotificationCard.kt
│   │   │   │
│   │   │   ├── 🔧 domain/              # Business Logic Layer
│   │   │   │   ├── model/              # Domain Models
│   │   │   │   │   ├── User.kt
│   │   │   │   │   ├── GymClass.kt
│   │   │   │   │   ├── Reservation.kt
│   │   │   │   │   ├── Instructor.kt
│   │   │   │   │   ├── Schedule.kt
│   │   │   │   │   ├── Notification.kt
│   │   │   │   │   └── AuthResult.kt
│   │   │   │   ├── repository/         # Repository Interfaces
│   │   │   │   │   ├── AuthRepository.kt
│   │   │   │   │   ├── ClassRepository.kt
│   │   │   │   │   ├── ReservationRepository.kt
│   │   │   │   │   ├── UserRepository.kt
│   │   │   │   │   ├── NotificationRepository.kt
│   │   │   │   │   └── ScheduleRepository.kt
│   │   │   │   └── usecase/           # Use Cases (Business Rules)
│   │   │   │       ├── auth/
│   │   │   │       │   ├── LoginUseCase.kt
│   │   │   │       │   ├── RegisterUseCase.kt
│   │   │   │       │   ├── LogoutUseCase.kt
│   │   │   │       │   └── GetCurrentUserUseCase.kt
│   │   │   │       ├── classes/
│   │   │   │       │   ├── GetClassesUseCase.kt
│   │   │   │       │   ├── GetClassDetailUseCase.kt
│   │   │   │       │   ├── SearchClassesUseCase.kt
│   │   │   │       │   └── FilterClassesUseCase.kt
│   │   │   │       ├── reservations/
│   │   │   │       │   ├── CreateReservationUseCase.kt
│   │   │   │       │   ├── GetUserReservationsUseCase.kt
│   │   │   │       │   ├── CancelReservationUseCase.kt
│   │   │   │       │   └── ValidateReservationUseCase.kt
│   │   │   │       ├── schedule/
│   │   │   │       │   ├── GetWeeklyScheduleUseCase.kt
│   │   │   │       │   ├── GetAvailableTimeSlotsUseCase.kt
│   │   │   │       │   └── CheckScheduleConflictUseCase.kt
│   │   │   │       └── notifications/
│   │   │   │           ├── ScheduleReminderUseCase.kt
│   │   │   │           ├── GetNotificationsUseCase.kt
│   │   │   │           └── MarkNotificationReadUseCase.kt
│   │   │   │
│   │   │   ├── 🗄️ data/               # Data Layer
│   │   │   │   ├── repository/        # Repository Implementations
│   │   │   │   │   ├── AuthRepositoryImpl.kt
│   │   │   │   │   ├── ClassRepositoryImpl.kt
│   │   │   │   │   ├── ReservationRepositoryImpl.kt
│   │   │   │   │   ├── UserRepositoryImpl.kt
│   │   │   │   │   ├── NotificationRepositoryImpl.kt
│   │   │   │   │   └── ScheduleRepositoryImpl.kt
│   │   │   │   ├── datasource/        # Data Sources
│   │   │   │   │   ├── local/         # Room Database
│   │   │   │   │   │   ├── GymClassDatabase.kt
│   │   │   │   │   │   ├── entity/
│   │   │   │   │   │   │   ├── UserEntity.kt
│   │   │   │   │   │   │   ├── ClassEntity.kt
│   │   │   │   │   │   │   ├── ReservationEntity.kt
│   │   │   │   │   │   │   ├── InstructorEntity.kt
│   │   │   │   │   │   │   └── NotificationEntity.kt
│   │   │   │   │   │   ├── dao/
│   │   │   │   │   │   │   ├── UserDao.kt
│   │   │   │   │   │   │   ├── ClassDao.kt
│   │   │   │   │   │   │   ├── ReservationDao.kt
│   │   │   │   │   │   │   ├── InstructorDao.kt
│   │   │   │   │   │   │   └── NotificationDao.kt
│   │   │   │   │   │   └── converters/
│   │   │   │   │   │       ├── DateConverters.kt
│   │   │   │   │   │       └── ListConverters.kt
│   │   │   │   │   └── remote/        # Firebase
│   │   │   │   │       ├── firebase/
│   │   │   │   │       │   ├── FirebaseAuthDataSource.kt
│   │   │   │   │       │   ├── FirestoreDataSource.kt
│   │   │   │   │       │   ├── FirebaseStorageDataSource.kt
│   │   │   │   │       │   └── GymClassFirebaseMessagingService.kt
│   │   │   │   │       └── dto/       # Data Transfer Objects
│   │   │   │   │           ├── UserDto.kt
│   │   │   │   │           ├── ClassDto.kt
│   │   │   │   │           ├── ReservationDto.kt
│   │   │   │   │           ├── InstructorDto.kt
│   │   │   │   │           └── NotificationDto.kt
│   │   │   │   ├── mapper/            # Entity ↔ Domain Mappers
│   │   │   │   │   ├── UserMapper.kt
│   │   │   │   │   ├── ClassMapper.kt
│   │   │   │   │   ├── ReservationMapper.kt
│   │   │   │   │   ├── InstructorMapper.kt
│   │   │   │   │   └── NotificationMapper.kt
│   │   │   │   └── notifications/     # Local Notifications
│   │   │   │       ├── NotificationManager.kt
│   │   │   │       ├── ClassReminderReceiver.kt
│   │   │   │       ├── BootReceiver.kt
│   │   │   │       └── NotificationChannels.kt
│   │   │   │
│   │   │   └── 🔌 di/                 # Dependency Injection (Hilt)
│   │   │       ├── DatabaseModule.kt
│   │   │       ├── FirebaseModule.kt
│   │   │       ├── RepositoryModule.kt
│   │   │       ├── UseCaseModule.kt
│   │   │       └── NetworkModule.kt
│   │   │
│   │   ├── res/
│   │   │   ├── values/
│   │   │   │   ├── colors.xml
│   │   │   │   ├── strings.xml
│   │   │   │   ├── themes.xml
│   │   │   │   └── dimens.xml
│   │   │   ├── drawable/
│   │   │   ├── mipmap-*/
│   │   │   └── xml/
│   │   │       ├── backup_rules.xml
│   │   │       ├── data_extraction_rules.xml
│   │   │       └── network_security_config.xml
│   │   │
│   │   └── AndroidManifest.xml
│   │
│   ├── test/                          # Unit Tests
│   │   └── java/com/universidad/gymclass/
│   │       ├── repository/
│   │       ├── usecase/
│   │       ├── viewmodel/
│   │       └── mapper/
│   │
│   └── androidTest/                   # Integration Tests
│       └── java/com/universidad/gymclass/
│           ├── database/
│           ├── ui/
│           └── navigation/
│
├── google-services.json               # Firebase Config
├── build.gradle.kts                   # App Dependencies
├── proguard-rules.pro                 # Obfuscation Rules
└── libs.versions.toml                 # Version Catalog
```

## 🏗️ Creación Paso a Paso

### Orden de Creación Recomendado:

1. **🔧 Core Setup**
   ```bash
   1. GymClassApplication.kt
   2. Dependency Injection Modules
   3. Database Setup (Room)
   4. Firebase Configuration
   ```

2. **🎨 UI Foundation**
   ```bash
   1. Theme & Colors
   2. Navigation Setup
   3. MainActivity
   4. Common Components
   ```

3. **📊 Domain Layer**
   ```bash
   1. Domain Models
   2. Repository Interfaces
   3. Use Cases
   ```

4. **🗄️ Data Layer**
   ```bash
   1. DTOs & Entities
   2. Mappers
   3. Data Sources
   4. Repository Implementations
   ```

5. **📱 Presentation Layer**
   ```bash
   1. ViewModels
   2. Screens (por módulo)
   3. Components específicos
   ```

## 📋 Checklist Pre-Desarrollo

- [ ] ✅ Firebase proyecto creado
- [ ] ⚡ Authentication configurado
- [ ] 🗄️ Firestore Database creado  
- [ ] 📱 Android Studio proyecto iniciado
- [ ] 📄 google-services.json descargado
- [ ] 🔧 Dependencies configuradas
- [ ] 📁 Estructura de carpetas creada
- [ ] 🏗️ Application class configurada
- [ ] 🔌 Hilt modules preparados
- [ ] 🎨 Theme y colores definidos