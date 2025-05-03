# Instrukcja uruchomienia aplikacji

## 1. Uruchomienie aplikacji

Możliwe sposoby uruchomienia:

### a) Z poziomu IDE (np. IntelliJ IDEA, Eclipse)

1. Otwórz klasę główną `IoApplication.java`
2. Uruchom metodę `main()` (np. klikając prawym przyciskiem myszy → "Run IoApplication")

### b) Z linii poleceń (Gradle)

./gradlew bootRun

## 2. Dostępne interfejsy

### a) Z poziomu IDE (np. IntelliJ IDEA, Eclipse)

1. Endpointy REST http://localhost:8080/{endpoint_name}

2. Dokumentacja API (Swagger UI) http://localhost:8080/swagger-ui/index.html#

3. Konsola H2 (baza danych w pamięci) http://localhost:8080/h2-console
   ### Dane logowania do bazy H2:
   ##### JDBC URL: jdbc:h2:mem:testdb
   ##### User Name: sa
   ##### Password: (puste - pozostaw puste pole)
