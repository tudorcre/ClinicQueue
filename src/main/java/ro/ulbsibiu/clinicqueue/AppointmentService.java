package ro.ulbsibiu.clinicqueue;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Conține logica de business pentru programări.
 */
public final class AppointmentService {
    private final List<Appointment> appointments = new ArrayList<>();
    private final Clock clock;
    private int nextId = 1;

    public AppointmentService() {
        this(Clock.systemDefaultZone());
    }

    public AppointmentService(final Clock clock) {
        this.clock = clock;
    }

    public Appointment createAppointment(
            final String patientName,
            final String serviceName,
            final LocalDateTime scheduledAt) {
        String normalizedPatientName = requireText(patientName, "Numele pacientului");
        String normalizedServiceName = requireText(serviceName, "Denumirea serviciului");
        requireFutureDate(scheduledAt);
        ensureSlotAvailable(scheduledAt, 0);

        Appointment appointment = new Appointment(
                nextId++,
                normalizedPatientName,
                normalizedServiceName,
                scheduledAt,
                AppointmentStatus.SCHEDULED);
        appointments.add(appointment);
        return appointment;
    }

    public List<Appointment> listAppointments() {
        return appointments.stream()
                .sorted(Comparator.comparing(Appointment::getScheduledAt))
                .toList();
    }

    public Optional<Appointment> findById(final int id) {
        return appointments.stream()
                .filter(appointment -> appointment.getId() == id)
                .findFirst();
    }

    public List<Appointment> findByPatientName(final String searchTerm) {
        String normalizedSearchTerm = requireText(searchTerm, "Textul de căutare")
                .toLowerCase(Locale.ROOT);
        return appointments.stream()
                .filter(appointment -> appointment.getPatientName()
                        .toLowerCase(Locale.ROOT)
                        .contains(normalizedSearchTerm))
                .sorted(Comparator.comparing(Appointment::getScheduledAt))
                .toList();
    }

    public Appointment cancelAppointment(final int id) {
        Appointment appointment = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Programarea nu există."));
        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new IllegalStateException("Programarea este deja anulată.");
        }
        Appointment updatedAppointment = appointment.withStatus(AppointmentStatus.CANCELLED);
        replaceAppointment(updatedAppointment);
        return updatedAppointment;
    }

    public Appointment rescheduleAppointment(final int id, final LocalDateTime newScheduledAt) {
        requireFutureDate(newScheduledAt);
        Appointment appointment = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Programarea nu există."));
        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new IllegalStateException("O programare anulată nu poate fi reprogramată.");
        }
        ensureSlotAvailable(newScheduledAt, id);
        Appointment updatedAppointment = appointment.withScheduledAt(newScheduledAt);
        replaceAppointment(updatedAppointment);
        return updatedAppointment;
    }

    private void replaceAppointment(final Appointment updatedAppointment) {
        for (int index = 0; index < appointments.size(); index++) {
            if (appointments.get(index).getId() == updatedAppointment.getId()) {
                appointments.set(index, updatedAppointment);
                return;
            }
        }
    }

    private String requireText(final String value, final String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " este obligatoriu/obligatorie.");
        }
        return value.trim();
    }

    private void requireFutureDate(final LocalDateTime scheduledAt) {
        if (scheduledAt == null) {
            throw new IllegalArgumentException("Data programării este obligatorie.");
        }
        LocalDateTime now = LocalDateTime.now(clock);
        if (!scheduledAt.isAfter(now)) {
            throw new IllegalArgumentException("Programarea trebuie să fie în viitor.");
        }
    }

    private void ensureSlotAvailable(final LocalDateTime scheduledAt, final int ignoredAppointmentId) {
        boolean slotAlreadyBooked = appointments.stream()
                .anyMatch(appointment -> appointment.getId() != ignoredAppointmentId
                        && appointment.getStatus() == AppointmentStatus.SCHEDULED
                        && appointment.getScheduledAt().equals(scheduledAt));
        if (slotAlreadyBooked) {
            throw new IllegalArgumentException("Există deja o programare activă în acest interval.");
        }
    }
}
