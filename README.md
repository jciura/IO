# Instrukcja uruchomienia aplikacji

## 1. Uruchomienie aplikacji

Aplikacja wymaga uruchomienia serwera bazodanowego i serwisu internetowego, aby mogła poprawnie działać.

Możliwe sposoby uruchomienia serwera bazodanowego:

### a) Z poziomu IDE (np. IntelliJ IDEA, Eclipse)

1. Otwórz klasę główną `IoApplication.java`
2. Uruchom metodę `main()` (np. klikając prawym przyciskiem myszy → "Run IoApplication")

### b) Z linii poleceń (Gradle)

./gradlew bootRun

Sposób uruchomienia serwisu internetowego:
1. Otwórz terminal (np. cmd lub Windows PowerShell na Windowsie).
2. Przejdź do głównego katalogu projektu (uruchamiając terminal w środowisku, w którym uruchomiony jest projekt, można pominąć ten punkt).
3. Za pomocą polecenia `cd/src/main/frontend/io-app` wejdź do katalogu zawierającego pliki serwisu.
4. Uruchom serwis używając polecenia `npm start`.  

## 2. Dostępne interfejsy

### a) Z poziomu IDE (np. IntelliJ IDEA, Eclipse)

1. Endpointy REST http://localhost:8080/{endpoint_name}

2. Dokumentacja API (Swagger UI) http://localhost:8080/swagger-ui/index.html#

3. Konsola H2 (baza danych w pamięci) http://localhost:8080/h2-console
   ### Dane logowania do bazy H2:
   ##### JDBC URL: jdbc:h2:mem:testdb
   ##### User Name: sa
   ##### Password: (puste - pozostaw puste pole)
