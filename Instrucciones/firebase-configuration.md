# 🔥 Firebase Security Rules

## Firestore Security Rules

```javascript
// firestore.rules
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    
    // Helper functions
    function isAuthenticated() {
      return request.auth != null;
    }
    
    function isOwner(userId) {
      return isAuthenticated() && request.auth.uid == userId;
    }
    
    function isValidEmail() {
      return isAuthenticated() && 
             request.auth.token.email.matches('.*@universidad\\.edu.*');
    }

    // Users collection
    match /users/{userId} {
      allow read, write: if isOwner(userId) && isValidEmail();
      allow read: if isAuthenticated(); // Para instructores y lista de usuarios
    }

    // Classes collection
    match /classes/{classId} {
      allow read: if isAuthenticated();
      allow write: if false; // Solo admin puede crear/editar clases
    }

    // Instructors collection
    match /instructors/{instructorId} {
      allow read: if isAuthenticated();
      allow write: if false; // Solo admin puede crear/editar instructores
    }

    // Reservations collection
    match /reservations/{reservationId} {
      allow read: if isAuthenticated() && 
                     (resource.data.userId == request.auth.uid ||
                      request.auth.token.role == 'admin');
      
      allow create: if isAuthenticated() && 
                       isValidEmail() &&
                       resource.data.userId == request.auth.uid &&
                       resource.data.createdAt == request.time;
      
      allow update: if isOwner(resource.data.userId) &&
                       request.writeFields.hasOnly(['status', 'updatedAt']) &&
                       resource.data.updatedAt == request.time;
      
      allow delete: if isOwner(resource.data.userId) ||
                       request.auth.token.role == 'admin';
    }

    // Notifications collection  
    match /notifications/{notificationId} {
      allow read: if isAuthenticated() && 
                     resource.data.userId == request.auth.uid;
      
      allow update: if isOwner(resource.data.userId) &&
                       request.writeFields.hasOnly(['isRead', 'readAt']);
      
      allow write: if false; // Solo el sistema puede crear notificaciones
    }

    // Admin-only collections
    match /admin/{document=**} {
      allow read, write: if isAuthenticated() && 
                           request.auth.token.role == 'admin';
    }
  }
}
```

## Firebase Storage Rules

```javascript
// storage.rules
rules_version = '2';
service firebase.storage {
  match /b/{bucket}/o {
    
    // Profile pictures
    match /profile_pictures/{userId}/{fileName} {
      allow read: if true; // Las imágenes de perfil son públicas
      allow write: if request.auth != null && 
                      request.auth.uid == userId &&
                      request.resource.size < 5 * 1024 * 1024 && // 5MB max
                      request.resource.contentType.matches('image/.*');
    }
    
    // Class images
    match /class_images/{fileName} {
      allow read: if true; // Las imágenes de clases son públicas
      allow write: if false; // Solo admin puede subir imágenes de clases
    }
    
    // Instructor images
    match /instructor_images/{fileName} {
      allow read: if true; // Las imágenes de instructores son públicas  
      allow write: if false; // Solo admin puede subir imágenes de instructores
    }
  }
}
```

## Firestore Data Structure

```json
// users/{userId}
{
  "id": "userId",
  "email": "estudiante@universidad.edu",
  "firstName": "Juan",
  "lastName": "Pérez", 
  "studentId": "2021-0001",
  "phone": "+1234567890",
  "profilePictureUrl": "https://...",
  "preferences": {
    "notifications": true,
    "reminderMinutes": 30,
    "favoriteActivities": ["yoga", "spinning"]
  },
  "stats": {
    "totalReservations": 15,
    "completedClasses": 12,
    "cancelledClasses": 2,
    "noShowClasses": 1
  },
  "createdAt": "2024-01-15T10:00:00Z",
  "updatedAt": "2024-01-15T10:00:00Z",
  "isActive": true
}

// classes/{classId}
{
  "id": "classId",
  "name": "Yoga Matutino",
  "description": "Clase de yoga para comenzar el día con energía",
  "activity": "yoga",
  "instructorId": "instructorId",
  "capacity": 20,
  "duration": 60, // minutos
  "difficulty": "beginner", // beginner, intermediate, advanced
  "imageUrl": "https://...",
  "requirements": ["mat de yoga", "ropa cómoda"],
  "benefits": ["flexibilidad", "relajación", "fuerza core"],
  "schedule": {
    "dayOfWeek": "monday", // monday, tuesday, etc.
    "startTime": "07:00",
    "endTime": "08:00",
    "room": "Sala A",
    "building": "Gimnasio Principal"
  },
  "isActive": true,
  "createdAt": "2024-01-15T10:00:00Z"
}

// instructors/{instructorId}
{
  "id": "instructorId",
  "firstName": "María",
  "lastName": "González",
  "email": "maria.gonzalez@universidad.edu",
  "phone": "+1234567890",
  "profilePictureUrl": "https://...",
  "bio": "Instructora certificada con 5 años de experiencia",
  "specialties": ["yoga", "pilates", "stretching"],
  "certifications": ["RYT-200", "Pilates Mat Certification"],
  "experience": "5 años",
  "rating": 4.8,
  "totalClasses": 150,
  "isActive": true,
  "createdAt": "2024-01-15T10:00:00Z"
}

// reservations/{reservationId}
{
  "id": "reservationId",
  "userId": "userId",
  "classId": "classId", 
  "classDate": "2024-01-20", // Fecha específica de la clase
  "status": "confirmed", // confirmed, cancelled, completed, no_show
  "reminderScheduled": true,
  "reminderSentAt": "2024-01-20T06:30:00Z",
  "checkInTime": null, // Para futuras funcionalidades
  "checkOutTime": null,
  "rating": null, // Calificación post-clase
  "feedback": null,
  "createdAt": "2024-01-15T10:00:00Z",
  "updatedAt": "2024-01-15T10:00:00Z",
  "cancelledAt": null,
  "cancelReason": null
}

// notifications/{notificationId}
{
  "id": "notificationId",
  "userId": "userId",
  "type": "class_reminder", // class_reminder, reservation_confirmed, class_cancelled
  "title": "Recordatorio de Clase",
  "message": "Tu clase de Yoga Matutino comienza en 30 minutos",
  "data": {
    "classId": "classId",
    "reservationId": "reservationId",
    "actionUrl": "/classes/classId"
  },
  "isRead": false,
  "createdAt": "2024-01-20T06:30:00Z",
  "readAt": null,
  "scheduledFor": "2024-01-20T06:30:00Z"
}
```

## Environment Variables (local.properties)

```properties
# local.properties
FIREBASE_PROJECT_ID=webprogramingfinalproyect
FIREBASE_APP_ID=1:123456789:android:abcdef123456
FIREBASE_API_KEY=AIzaSyC...
FIREBASE_AUTH_DOMAIN=webprogramingfinalproyect.firebaseapp.com
FIREBASE_STORAGE_BUCKET=webprogramingfinalproyect.appspot.com
```