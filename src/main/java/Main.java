/*
Program sprawdza poprawność wpisywanego imienia. W przypadku wystąpienia spacji w imieniu, funkcja wyrzuca zdefiniowany wyjątek WrongStudentName, który jest wyłapywany w pętli głównej Commit6_0.
Poniższe zadania będą się sprowadzały do modyfikacji bazowego kodu. Proces modyfikacji ogólnie może wyglądać następująco:
• Ustalenie jaki błąd chcę się sprawdzić i wyłapać.
• Decyzja, czy użyje się własnej klasy wyjątku, czy wykorzysta już istniejące (np. Exception, IOException).
• Napisanie kodu sprawdzającego daną funkcjonalność. W przypadku warunku błędu wyrzucany będzie wyjątek: throw new WrongStudentName().
• W definicji funkcji, która zawiera kod wyrzucania wyjątku dopisuje się daną nazwę wyjątku, np. public static String ReadName() throws WrongStudentName.
• We wszystkich funkcjach, które wywołują powyższą funkcję także należy dopisać, że one wyrzucają ten wyjątek – inaczej program się nie skompiluje.
• W pętli głównej, w main’ie, w zdefiniowanym już try-catch dopisuje się Nazwę wyjątku i go obsługuje, np. wypisuje w konsoli co się stało.
*/
//Commit6_1. Na podstawie analogii do wyjątku WrongStudentName utwórz i obsłuż wyjątki WrongAge oraz WrongDateOfBirth. 
//Niepoprawny wiek – gdy jest mniejszy od 0 lub większy niż 100. Niepoprawna data urodzenia – gdy nie jest zapisana w formacie DD-MM-YYYY, np. 28-02-2023.
import java.io.IOException;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
class WrongStudentName extends Exception { }
class WrongAge extends Exception { }
class WrongDateOfBirth extends Exception { }
class Main {
    public static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        while(true) {
            try {
                int ex = menu();
                switch(ex) {
                    case 1: exercise1(); break;
                    case 2: exercise2(); break;
                    case 3: exercise3(); break;
                    default: System.out.println("Nieprawidłowy wybór"); break;
                }
            } catch(IOException e) {
            } catch(WrongStudentName e) {
                System.out.println("Błędne imie studenta!");
            } catch(WrongAge e) {
                System.out.println("Niepoprawny wiek studenta!");
            } catch(WrongDateOfBirth e) {
                System.out.println("Niepoprawna data urodzenia studenta!");
            } catch (IllegalArgumentException e) {
                System.out.println("Wiek nie zgadza się z datą urodzenia!");
            }
        }
    }
    public static int menu() {
        System.out.println("Wciśnij:");
        System.out.println("1 - aby dodać studenta");
        System.out.println("2 - aby wypisać wszystkich studentów");
        System.out.println("3 - aby wyszukać studenta po imieniu");
        System.out.println("0 - aby wyjść z programu");
        return scan.nextInt();
    }
    public static String ReadName() throws WrongStudentName {
        scan.nextLine();
        System.out.println("Podaj imie: ");
        String name = scan.nextLine();
        if(name.contains(" "))
            throw new WrongStudentName();
        return name;
    }
     /// wiek 6.1
    public static int ReadAge() throws WrongAge {
        System.out.println("Podaj wiek: ");
        var age = scan.nextInt();
        if(age < 0 || age > 100)
            throw new WrongAge();
        scan.nextLine();
        return age;
    }
    // data urodzenia 6.2
    public static String ReadDate() throws WrongDateOfBirth {
        System.out.println("Podaj datę urodzenia DD-MM-YYY");
        var date = scan.nextLine();
        if(date.length() != 10 || !date.contains("-") || !Character.isDigit(date.charAt(0)) || !Character.isDigit(date.charAt(1)) || !Character.isDigit(date.charAt(3)) || !Character.isDigit(date.charAt(4)) || !Character.isDigit(date.charAt(6)) || !Character.isDigit(date.charAt(7)) || !Character.isDigit(date.charAt(8)) || !Character.isDigit(date.charAt(9)))
            throw new WrongDateOfBirth();
        return date;
    }
    public static void exercise1() throws IOException, WrongStudentName, WrongAge, WrongDateOfBirth {
        var name = ReadName();
        var age = ReadAge();
        var date = ReadDate();
    
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dob = LocalDate.parse(date, formatter);
        
        long calculatedAge = ChronoUnit.YEARS.between(dob, LocalDate.now());
      
        if (calculatedAge != age) {
            throw new IllegalArgumentException();
        }
      
        (new Service()).addStudent(new Student(name, age, date));
    }
    public static void exercise2() throws IOException {
        var students = (new Service()).getStudents();
        for(Student current : students) {
            System.out.println(current.ToString());
        }
    }
    public static void exercise3() throws IOException {
        scan.nextLine();
        System.out.println("Podaj imie: ");
        var name = scan.nextLine();
        var wanted = (new Service()).findStudentByName(name);
        if(wanted == null)
            System.out.println("Nie znaleziono...");
        else {
            System.out.println("Znaleziono: ");
            System.out.println(wanted.ToString());
        }
    }
}