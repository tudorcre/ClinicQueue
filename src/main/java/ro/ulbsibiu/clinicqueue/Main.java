package ro.ulbsibiu.clinicqueue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Punctul principal de pornire al aplicației.
 */
public final class Main {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private Main() {
    }

    public static void main(final String[] args) {
        if (args.length == 0 || "--ui".equals(args[0])) {
            ClinicQueueUi.launch();
            return;
        }
        if (!"--cli".equals(args[0])) {
            System.out.println("Argument necunoscut. Folosește --ui sau --cli.");
            return;
        }
        AppointmentService service = new AppointmentService();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("ClinicQueue - management programări cabinet medical");
        while (running) {
            printMenu();
            String option = scanner.nextLine().trim();
            try {
                switch (option) {
                    case "1" -> createAppointment(scanner, service);
                    case "2" -> listAppointments(service);
                    case "3" -> searchAppointment(scanner, service);
                    case "4" -> cancelAppointment(scanner, service);
                    case "5" -> rescheduleAppointment(scanner, service);
                    case "0" -> running = false;
                    default -> System.out.println("Opțiune invalidă.");
                }
            } catch (IllegalArgumentException | IllegalStateException exception) {
                System.out.println("Eroare: " + exception.getMessage());
            }
        }
        System.out.println("Aplicația s-a închis.");
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("1. Creează programare");
        System.out.println("2. Afișează programări");
        System.out.println("3. Caută după pacient");
        System.out.println("4. Anulează programare");
        System.out.println("5. Reprogramează");
        System.out.println("0. Ieșire");
        System.out.print("Alege opțiunea: ");
    }

    private static void createAppointment(
            final Scanner scanner,
            final AppointmentService service) {
        System.out.print("Nume pacient: ");
        String patientName = scanner.nextLine();
        System.out.print("Serviciu medical: ");
        String serviceName = scanner.nextLine();
        LocalDateTime scheduledAt = readDate(scanner);
        Appointment appointment = service.createAppointment(patientName, serviceName, scheduledAt);
        System.out.println("Programare creată: " + appointment);
    }

    private static void listAppointments(final AppointmentService service) {
        if (service.listAppointments().isEmpty()) {
            System.out.println("Nu există programări.");
            return;
        }
        service.listAppointments().forEach(System.out::println);
    }

    private static void searchAppointment(
            final Scanner scanner,
            final AppointmentService service) {
        System.out.print("Text căutare: ");
        String searchTerm = scanner.nextLine();
        service.findByPatientName(searchTerm).forEach(System.out::println);
    }

    private static void cancelAppointment(
            final Scanner scanner,
            final AppointmentService service) {
        System.out.print("ID programare: ");
        int id = Integer.parseInt(scanner.nextLine());
        Appointment appointment = service.cancelAppointment(id);
        System.out.println("Programare anulată: " + appointment);
    }

    private static void rescheduleAppointment(
            final Scanner scanner,
            final AppointmentService service) {
        System.out.print("ID programare: ");
        int id = Integer.parseInt(scanner.nextLine());
        LocalDateTime scheduledAt = readDate(scanner);
        Appointment appointment = service.rescheduleAppointment(id, scheduledAt);
        System.out.println("Programare actualizată: " + appointment);
    }

    private static LocalDateTime readDate(final Scanner scanner) {
        System.out.print("Data și ora, format yyyy-MM-dd HH:mm: ");
        String value = scanner.nextLine();
        try {
            return LocalDateTime.parse(value, FORMATTER);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("Format invalid pentru dată.");
        }
    }
}
