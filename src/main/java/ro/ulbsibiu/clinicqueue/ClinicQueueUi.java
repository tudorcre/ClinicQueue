package ro.ulbsibiu.clinicqueue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 * Interfață grafică minimală pentru gestionarea programărilor.
 */
public final class ClinicQueueUi {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final String[] TABLE_COLUMNS = {"ID", "Pacient", "Serviciu", "Data și ora", "Status"};

    private final AppointmentService appointmentService;
    private final JFrame frame;
    private final JTextField patientNameField;
    private final JTextField serviceNameField;
    private final JTextField scheduledAtField;
    private final JTextField searchField;
    private final DefaultTableModel tableModel;
    private final JTable appointmentTable;

    private LocalDateTime selectedDateTime;

    public ClinicQueueUi(final AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
        frame = new JFrame("ClinicQueue - Programări cabinet medical");
        patientNameField = new JTextField(20);
        serviceNameField = new JTextField(20);
        scheduledAtField = new JTextField(16);
        scheduledAtField.setEditable(false);
        scheduledAtField.setText("Nicio dată selectată");
        searchField = new JTextField(18);
        tableModel = new DefaultTableModel(TABLE_COLUMNS, 0);
        appointmentTable = new JTable(tableModel);
        configureFrame();
    }

    public void show() {
        frame.setVisible(true);
    }

    private void configureFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));
        frame.add(createFormPanel(), BorderLayout.NORTH);
        frame.add(createTablePanel(), BorderLayout.CENTER);
        frame.add(createActionsPanel(), BorderLayout.SOUTH);
        frame.setSize(900, 560);
        frame.setLocationRelativeTo(null);
        refreshTable(appointmentService.listAppointments());
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = createBaseConstraints();

        addFormRow(panel, constraints, 0, "Nume pacient:", patientNameField);
        addFormRow(panel, constraints, 1, "Serviciu medical:", serviceNameField);
        addDateRow(panel, constraints);
        addCreateButton(panel, constraints);
        addDateHint(panel, constraints);
        return panel;
    }

    private GridBagConstraints createBaseConstraints() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.WEST;
        return constraints;
    }

    private void addFormRow(
            final JPanel panel,
            final GridBagConstraints constraints,
            final int row,
            final String labelText,
            final JTextField textField) {
        constraints.gridx = 0;
        constraints.gridy = row;
        constraints.gridwidth = 1;
        panel.add(new JLabel(labelText), constraints);

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        panel.add(textField, constraints);
        constraints.fill = GridBagConstraints.NONE;
    }

    private void addDateRow(final JPanel panel, final GridBagConstraints constraints) {
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        panel.add(new JLabel("Data și ora:"), constraints);

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JButton chooseDateButton = new JButton("Alege data din calendar");
        chooseDateButton.addActionListener(event -> chooseAppointmentDate());
        datePanel.add(scheduledAtField);
        datePanel.add(new JLabel("  "));
        datePanel.add(chooseDateButton);

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        panel.add(datePanel, constraints);
        constraints.fill = GridBagConstraints.NONE;
    }

    private void addCreateButton(final JPanel panel, final GridBagConstraints constraints) {
        JButton addButton = new JButton("Adaugă programare");
        addButton.addActionListener(event -> createAppointment());
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridheight = 3;
        constraints.fill = GridBagConstraints.VERTICAL;
        panel.add(addButton, constraints);
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.NONE;
    }

    private void addDateHint(final JPanel panel, final GridBagConstraints constraints) {
        JLabel dateHint = new JLabel("Data se selectează din calendar; nu se introduce manual.");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 3;
        panel.add(dateHint, constraints);
        constraints.gridwidth = 1;
    }

    private JScrollPane createTablePanel() {
        appointmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        appointmentTable.setAutoCreateRowSorter(true);
        return new JScrollPane(appointmentTable);
    }

    private JPanel createActionsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton listButton = new JButton("Afișează toate");
        JButton searchButton = new JButton("Caută pacient");
        JButton cancelButton = new JButton("Anulează selectată");
        JButton rescheduleButton = new JButton("Reprogramează selectată");
        JButton sampleButton = new JButton("Adaugă date exemplu");

        listButton.addActionListener(event -> refreshTable(appointmentService.listAppointments()));
        searchButton.addActionListener(event -> searchAppointments());
        cancelButton.addActionListener(event -> cancelSelectedAppointment());
        rescheduleButton.addActionListener(event -> rescheduleSelectedAppointment());
        sampleButton.addActionListener(event -> addSampleData());

        panel.add(new JLabel("Căutare:"));
        panel.add(searchField);
        panel.add(searchButton);
        panel.add(listButton);
        panel.add(cancelButton);
        panel.add(rescheduleButton);
        panel.add(sampleButton);
        return panel;
    }

    private void chooseAppointmentDate() {
        LocalDateTime initialValue = selectedDateTime == null ? LocalDateTime.now().plusDays(1) : selectedDateTime;
        Optional<LocalDateTime> value = DateTimePickerDialog.showDialog(
                frame,
                "Alege data programării",
                initialValue);
        value.ifPresent(this::setSelectedDateTime);
    }

    private void createAppointment() {
        if (selectedDateTime == null) {
            showError("Alege data și ora din calendar înainte de a crea programarea.");
            return;
        }
        try {
            Appointment appointment = appointmentService.createAppointment(
                    patientNameField.getText(),
                    serviceNameField.getText(),
                    selectedDateTime);
            clearForm();
            refreshTable(appointmentService.listAppointments());
            showInfo("Programare creată: #" + appointment.getId());
        } catch (IllegalArgumentException exception) {
            showError(exception.getMessage());
        }
    }

    private void searchAppointments() {
        try {
            List<Appointment> appointments = appointmentService.findByPatientName(searchField.getText());
            refreshTable(appointments);
            if (appointments.isEmpty()) {
                showInfo("Nu există programări pentru căutarea introdusă.");
            }
        } catch (IllegalArgumentException exception) {
            showError(exception.getMessage());
        }
    }

    private void cancelSelectedAppointment() {
        Integer selectedAppointmentId = getSelectedAppointmentId();
        if (selectedAppointmentId == null) {
            showError("Selectează o programare din tabel.");
            return;
        }
        try {
            appointmentService.cancelAppointment(selectedAppointmentId);
            refreshTable(appointmentService.listAppointments());
            showInfo("Programarea a fost anulată.");
        } catch (IllegalArgumentException | IllegalStateException exception) {
            showError(exception.getMessage());
        }
    }

    private void rescheduleSelectedAppointment() {
        Integer selectedAppointmentId = getSelectedAppointmentId();
        if (selectedAppointmentId == null) {
            showError("Selectează o programare din tabel.");
            return;
        }
        LocalDateTime initialValue = getSelectedAppointmentDate(selectedAppointmentId)
                .orElse(LocalDateTime.now().plusDays(1));
        Optional<LocalDateTime> newDateTime = DateTimePickerDialog.showDialog(
                frame,
                "Alege noua dată",
                initialValue);
        if (newDateTime.isEmpty()) {
            return;
        }
        try {
            appointmentService.rescheduleAppointment(selectedAppointmentId, newDateTime.get());
            refreshTable(appointmentService.listAppointments());
            showInfo("Programarea a fost reprogramată.");
        } catch (IllegalArgumentException | IllegalStateException exception) {
            showError(exception.getMessage());
        }
    }

    private void addSampleData() {
        LocalDateTime firstDate = LocalDateTime.now().plusDays(2).withHour(10).withMinute(0);
        LocalDateTime secondDate = LocalDateTime.now().plusDays(3).withHour(12).withMinute(30);
        try {
            appointmentService.createAppointment("Maria Popescu", "Consultație generală", firstDate);
            appointmentService.createAppointment("Andrei Ionescu", "Control stomatologic", secondDate);
            refreshTable(appointmentService.listAppointments());
            showInfo("Au fost adăugate două programări demonstrative.");
        } catch (IllegalArgumentException exception) {
            showError("Datele exemplu există deja sau intervalul este ocupat.");
        }
    }

    private void refreshTable(final List<Appointment> appointments) {
        tableModel.setRowCount(0);
        for (Appointment appointment : appointments) {
            tableModel.addRow(new Object[] {
                    appointment.getId(),
                    appointment.getPatientName(),
                    appointment.getServiceName(),
                    appointment.getScheduledAt().format(FORMATTER),
                    appointment.getStatus()
            });
        }
    }

    private Integer getSelectedAppointmentId() {
        int selectedRow = appointmentTable.getSelectedRow();
        if (selectedRow < 0) {
            return null;
        }
        int modelRow = appointmentTable.convertRowIndexToModel(selectedRow);
        Object value = tableModel.getValueAt(modelRow, 0);
        if (value instanceof Integer appointmentId) {
            return appointmentId;
        }
        return null;
    }

    private Optional<LocalDateTime> getSelectedAppointmentDate(final int selectedAppointmentId) {
        return appointmentService.findById(selectedAppointmentId)
                .map(Appointment::getScheduledAt);
    }

    private void setSelectedDateTime(final LocalDateTime value) {
        selectedDateTime = value;
        scheduledAtField.setText(value.format(FORMATTER));
    }

    private void clearForm() {
        patientNameField.setText("");
        serviceNameField.setText("");
        selectedDateTime = null;
        scheduledAtField.setText("Nicio dată selectată");
    }

    private void showInfo(final String message) {
        JOptionPane.showMessageDialog(frame, message, "ClinicQueue", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(final String message) {
        JOptionPane.showMessageDialog(frame, message, "Eroare", JOptionPane.ERROR_MESSAGE);
    }

    public static void launch() {
        SwingUtilities.invokeLater(() -> new ClinicQueueUi(new AppointmentService()).show());
    }
}
