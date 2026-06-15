package ro.ulbsibiu.clinicqueue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Dialog simplu pentru alegerea datei din calendar și a orei din liste.
 */
public final class DateTimePickerDialog {
    private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("MMMM yyyy");
    private static final DateTimeFormatter SELECTED_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String[] WEEK_DAYS = {"Lu", "Ma", "Mi", "Jo", "Vi", "Sâ", "Du"};

    private final JDialog dialog;
    private final JLabel monthLabel;
    private final JLabel selectedDateLabel;
    private final JPanel daysPanel;
    private final JComboBox<Integer> hourComboBox;
    private final JComboBox<Integer> minuteComboBox;

    private LocalDate selectedDate;
    private YearMonth displayedMonth;
    private boolean confirmed;

    private DateTimePickerDialog(
            final Window owner,
            final String title,
            final LocalDateTime initialValue) {
        selectedDate = initialValue.toLocalDate();
        displayedMonth = YearMonth.from(selectedDate);
        dialog = new JDialog(owner, title, JDialog.ModalityType.APPLICATION_MODAL);
        monthLabel = new JLabel("", SwingConstants.CENTER);
        selectedDateLabel = new JLabel("", SwingConstants.CENTER);
        daysPanel = new JPanel(new GridLayout(0, 7, 4, 4));
        hourComboBox = new JComboBox<>(createNumberRange(0, 23));
        minuteComboBox = new JComboBox<>(createMinuteRange());
        hourComboBox.setSelectedItem(initialValue.getHour());
        minuteComboBox.setSelectedItem(initialValue.getMinute());
        configureDialog();
    }

    public static Optional<LocalDateTime> showDialog(
            final Window owner,
            final String title,
            final LocalDateTime initialValue) {
        DateTimePickerDialog picker = new DateTimePickerDialog(owner, title, initialValue);
        picker.dialog.setVisible(true);
        if (!picker.confirmed) {
            return Optional.empty();
        }
        return Optional.of(picker.getSelectedDateTime());
    }

    private void configureDialog() {
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.add(createHeaderPanel(), BorderLayout.NORTH);
        dialog.add(daysPanel, BorderLayout.CENTER);
        dialog.add(createFooterPanel(), BorderLayout.SOUTH);
        dialog.setSize(390, 360);
        dialog.setLocationRelativeTo(dialog.getOwner());
        refreshCalendar();
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JButton previousButton = new JButton("<");
        JButton nextButton = new JButton(">");
        previousButton.addActionListener(event -> changeMonth(-1));
        nextButton.addActionListener(event -> changeMonth(1));

        JPanel navigationPanel = new JPanel(new BorderLayout(8, 8));
        navigationPanel.add(previousButton, BorderLayout.WEST);
        navigationPanel.add(monthLabel, BorderLayout.CENTER);
        navigationPanel.add(nextButton, BorderLayout.EAST);

        panel.add(navigationPanel, BorderLayout.NORTH);
        panel.add(selectedDateLabel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createFooterPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        panel.add(createTimePanel(), BorderLayout.NORTH);
        panel.add(createButtonPanel(), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createTimePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(new JLabel("Ora:"));
        panel.add(hourComboBox);
        panel.add(new JLabel(":"));
        panel.add(minuteComboBox);
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelButton = new JButton("Renunță");
        JButton confirmButton = new JButton("Confirmă");
        cancelButton.addActionListener(event -> dialog.dispose());
        confirmButton.addActionListener(event -> confirmSelection());
        panel.add(cancelButton);
        panel.add(confirmButton);
        return panel;
    }

    private void refreshCalendar() {
        monthLabel.setText(displayedMonth.format(MONTH_FORMATTER));
        selectedDateLabel.setText("Data selectată: " + selectedDate.format(SELECTED_DATE_FORMATTER));
        daysPanel.removeAll();
        addWeekDayLabels();
        addBlankDaysBeforeMonth();
        addMonthDays();
        daysPanel.revalidate();
        daysPanel.repaint();
    }

    private void addWeekDayLabels() {
        for (String weekDay : WEEK_DAYS) {
            JLabel label = new JLabel(weekDay, SwingConstants.CENTER);
            daysPanel.add(label);
        }
    }

    private void addBlankDaysBeforeMonth() {
        int blanks = displayedMonth.atDay(1).getDayOfWeek().getValue() - 1;
        for (int index = 0; index < blanks; index++) {
            daysPanel.add(new JLabel(""));
        }
    }

    private void addMonthDays() {
        for (int day = 1; day <= displayedMonth.lengthOfMonth(); day++) {
            LocalDate date = displayedMonth.atDay(day);
            JButton dayButton = new JButton(String.valueOf(day));
            dayButton.setEnabled(!date.isBefore(LocalDate.now()));
            dayButton.addActionListener(event -> selectDate(date));
            daysPanel.add(dayButton);
        }
    }

    private void changeMonth(final int delta) {
        displayedMonth = displayedMonth.plusMonths(delta);
        refreshCalendar();
    }

    private void selectDate(final LocalDate date) {
        selectedDate = date;
        refreshCalendar();
    }

    private void confirmSelection() {
        confirmed = true;
        dialog.dispose();
    }

    private LocalDateTime getSelectedDateTime() {
        Integer hour = (Integer) hourComboBox.getSelectedItem();
        Integer minute = (Integer) minuteComboBox.getSelectedItem();
        return selectedDate.atTime(hour, minute);
    }

    private Integer[] createNumberRange(final int start, final int end) {
        Integer[] values = new Integer[end - start + 1];
        for (int index = 0; index < values.length; index++) {
            values[index] = start + index;
        }
        return values;
    }

    private Integer[] createMinuteRange() {
        Integer[] values = new Integer[12];
        for (int index = 0; index < values.length; index++) {
            values[index] = index * 5;
        }
        return values;
    }
}
