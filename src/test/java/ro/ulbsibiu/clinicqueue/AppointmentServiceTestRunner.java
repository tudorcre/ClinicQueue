package ro.ulbsibiu.clinicqueue;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * Rulează testele unitare fără biblioteci externe.
 */
public final class AppointmentServiceTestRunner {
    private static final Clock FIXED_CLOCK = Clock.fixed(
            Instant.parse("2026-01-01T10:00:00Z"),
            ZoneId.of("UTC"));

    private AppointmentServiceTestRunner() {
    }

    public static void main(final String[] args) {
        testCreateAppointment();
        testRejectPastAppointment();
        testRejectOverlappingAppointments();
        testSearchByPatientName();
        testCancelAppointment();
        testRescheduleAppointment();
        System.out.println("All unit tests passed.");
    }

    private static void testCreateAppointment() {
        AppointmentService service = new AppointmentService(FIXED_CLOCK);
        Appointment appointment = service.createAppointment(
                "Ana Popescu",
                "Consultație generală",
                LocalDateTime.of(2026, 1, 3, 9, 0));

        TestSupport.assertEquals(1, appointment.getId(), "ID-ul primei programări trebuie să fie 1.");
        TestSupport.assertEquals("Ana Popescu", appointment.getPatientName(), "Numele trebuie salvat corect.");
        TestSupport.assertEquals(
                AppointmentStatus.SCHEDULED,
                appointment.getStatus(),
                "Statusul inițial este SCHEDULED.");
    }

    private static void testRejectPastAppointment() {
        AppointmentService service = new AppointmentService(FIXED_CLOCK);
        TestSupport.assertThrows(
                IllegalArgumentException.class,
                () -> service.createAppointment(
                        "Mihai Ionescu",
                        "Control",
                        LocalDateTime.of(2025, 12, 31, 9, 0)),
                "Nu trebuie acceptate programări în trecut.");
    }

    private static void testRejectOverlappingAppointments() {
        AppointmentService service = new AppointmentService(FIXED_CLOCK);
        LocalDateTime slot = LocalDateTime.of(2026, 1, 4, 12, 0);
        service.createAppointment("Ana Popescu", "Consultație", slot);

        TestSupport.assertThrows(
                IllegalArgumentException.class,
                () -> service.createAppointment("Dan Pavel", "Control", slot),
                "Nu trebuie acceptate două programări active în același interval.");
    }

    private static void testSearchByPatientName() {
        AppointmentService service = new AppointmentService(FIXED_CLOCK);
        service.createAppointment("Ana Popescu", "Consultație", LocalDateTime.of(2026, 1, 5, 9, 0));
        service.createAppointment("Ioana Radu", "Control", LocalDateTime.of(2026, 1, 5, 10, 0));

        List<Appointment> results = service.findByPatientName("ana");
        TestSupport.assertEquals(2, results.size(), "Căutarea trebuie să fie case-insensitive și parțială.");
    }

    private static void testCancelAppointment() {
        AppointmentService service = new AppointmentService(FIXED_CLOCK);
        Appointment appointment = service.createAppointment(
                "George Stan",
                "Consultație",
                LocalDateTime.of(2026, 1, 6, 14, 0));

        Appointment cancelled = service.cancelAppointment(appointment.getId());
        TestSupport.assertEquals(AppointmentStatus.CANCELLED, cancelled.getStatus(), "Programarea trebuie anulată.");
    }

    private static void testRescheduleAppointment() {
        AppointmentService service = new AppointmentService(FIXED_CLOCK);
        Appointment appointment = service.createAppointment(
                "Elena Marin",
                "Analize",
                LocalDateTime.of(2026, 1, 7, 8, 0));

        LocalDateTime newSlot = LocalDateTime.of(2026, 1, 8, 8, 30);
        Appointment updated = service.rescheduleAppointment(appointment.getId(), newSlot);

        TestSupport.assertEquals(newSlot, updated.getScheduledAt(), "Programarea trebuie replanificată.");
        TestSupport.assertTrue(
                service.findById(appointment.getId()).isPresent(),
                "Programarea trebuie să rămână disponibilă.");
    }
}
