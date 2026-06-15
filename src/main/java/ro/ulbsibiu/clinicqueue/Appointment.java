package ro.ulbsibiu.clinicqueue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Reprezintă o programare la cabinet.
 */
public final class Appointment {
    private final int id;
    private final String patientName;
    private final String serviceName;
    private final LocalDateTime scheduledAt;
    private final AppointmentStatus status;

    public Appointment(
            final int id,
            final String patientName,
            final String serviceName,
            final LocalDateTime scheduledAt,
            final AppointmentStatus status) {
        this.id = id;
        this.patientName = patientName;
        this.serviceName = serviceName;
        this.scheduledAt = scheduledAt;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public Appointment withStatus(final AppointmentStatus newStatus) {
        return new Appointment(id, patientName, serviceName, scheduledAt, newStatus);
    }

    public Appointment withScheduledAt(final LocalDateTime newScheduledAt) {
        return new Appointment(id, patientName, serviceName, newScheduledAt, status);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "#" + id + " | " + patientName + " | " + serviceName
                + " | " + scheduledAt.format(formatter) + " | " + status;
    }
}
