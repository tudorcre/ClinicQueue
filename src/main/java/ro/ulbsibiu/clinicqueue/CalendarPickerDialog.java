package ro.ulbsibiu.clinicqueue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.YearMonth;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

/**
 * Dialog pentru selectarea unei date și ore folosind un calendar.
 */
public final class CalendarPickerDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    private LocalDateTime selectedDateTime;
    private boolean confirmed = false;
    private final JSpinner hourSpinner;
    private final JSpinner minuteSpinner;
    private final JComboBox<Integer> dayComboBox;
    private YearMonth currentMonth;
    private int selectedDay = 1;

    public CalendarPickerDialog(final JFrame parent) {
        super(parent, "Selectează data și ora", true);
        this.currentMonth = YearMonth.now();
        this.hourSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 23, 1));
        this.minuteSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 59, 15));
        this.dayComboBox = new JComboBox<>();
        setupUi();
    }

    private void setupUi() {
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        add(createCalendarPanel(), BorderLayout.NORTH);
        add(createTimePanel(), BorderLayout.CENTER);
        add(createButtonsPanel(), BorderLayout.SOUTH);

        setSize(400, 400);
        setLocationRelativeTo(getParent());
    }

    private JPanel createCalendarPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        // Header cu luna și an
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton prevMonthButton = new JButton("◄");
        JButton nextMonthButton = new JButton("►");
        JLabel monthYearLabel = new JLabel(currentMonth.toString());

        prevMonthButton.addActionListener(e -> {
            currentMonth = currentMonth.minusMonths(1);
            monthYearLabel.setText(currentMonth.toString());
            updateDayComboBox();
        });

        nextMonthButton.addActionListener(e -> {
            currentMonth = currentMonth.plusMonths(1);
            monthYearLabel.setText(currentMonth.toString());
            updateDayComboBox();
        });

        headerPanel.add(prevMonthButton);
        headerPanel.add(monthYearLabel);
        headerPanel.add(nextMonthButton);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Calendar grid
        JPanel calendarGrid = new JPanel(new GridLayout(0, 7, 2, 2));
        String[] dayNames = {"L", "M", "M", "J", "V", "S", "D"};
        for (String dayName : dayNames) {
            JLabel dayLabel = new JLabel(dayName, SwingConstants.CENTER);
            calendarGrid.add(dayLabel);
        }

        int firstDayOfWeek = currentMonth.atDay(1).getDayOfWeek().getValue() % 7;
        int daysInMonth = currentMonth.lengthOfMonth();

        // Days before month starts
        for (int i = 0; i < firstDayOfWeek; i++) {
            calendarGrid.add(new JLabel());
        }

        // Days of month
        for (int day = 1; day <= daysInMonth; day++) {
            final int dayNum = day;
            JButton dayButton = new JButton(String.valueOf(day));
            if (day == selectedDay) {
                dayButton.setText("  " + day + "  ");
            }
            dayButton.addActionListener(e -> {
                selectedDay = dayNum;
                updateDayComboBox();
            });
            calendarGrid.add(dayButton);
        }

        panel.add(calendarGrid, BorderLayout.CENTER);

        // Day selector combo box
        JPanel dayPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dayPanel.add(new JLabel("Ziua:"));
        updateDayComboBox();
        dayPanel.add(dayComboBox);
        panel.add(dayPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void updateDayComboBox() {
        dayComboBox.removeAllItems();
        int daysInMonth = currentMonth.lengthOfMonth();
        for (int day = 1; day <= daysInMonth; day++) {
            dayComboBox.addItem(day);
        }
        dayComboBox.setSelectedItem(selectedDay);
    }

    private JPanel createTimePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.add(new JLabel("Ora:"));
        panel.add(hourSpinner);
        panel.add(new JLabel(":"));
        panel.add(minuteSpinner);
        return panel;
    }

    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Anulează");

        okButton.addActionListener(e -> {
            int day = (int) dayComboBox.getSelectedItem();
            int hour = (int) hourSpinner.getValue();
            int minute = (int) minuteSpinner.getValue();
            selectedDateTime = LocalDateTime.of(
                    currentMonth.getYear(),
                    currentMonth.getMonth(),
                    day,
                    hour,
                    minute);
            confirmed = true;
            dispose();
        });

        cancelButton.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        panel.add(okButton);
        panel.add(cancelButton);
        return panel;
    }

    public LocalDateTime getSelectedDateTime() {
        return selectedDateTime;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
