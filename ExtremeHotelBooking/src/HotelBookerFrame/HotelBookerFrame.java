/*  * HotelBookerFrame.java  */
package HotelBookerFrame;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import Calendar.DateAD;
import Calendar.BasicCalendar;
import static java.awt.Color.BLUE;

/**
 * * * @author Aleksandr Zayats
 */
public class HotelBookerFrame extends JFrame {

    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 400;
    private static final int MAXIMUM_ROW_COUNTS = 10;
    private static final String enterString = "Enter Start and End Dates";
    DateAD selectedDate = new DateAD();
    JButton[][] dayButton = new JButton[6][7];
    BasicCalendar cal = new BasicCalendar();

    /**
     * * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new HotelBookerFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public HotelBookerFrame() {
        setLayout(new BorderLayout());
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        today = new DateAD();
        setNorthPanel();
        add(northPanel, BorderLayout.NORTH);
        setCenterPanel();
        add(buttonPanel, BorderLayout.CENTER);
        setSouthPanel();
        add(southPanel, BorderLayout.SOUTH);
    }

    public void setNorthPanel() {

        northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout());
        dayLabel = new JLabel(Short.toString(today.getDayOfMonth()));
        enterLabel = new JLabel(enterString);
        monthComboBox = new JComboBox(DateAD.MONTHNAMES);
        monthComboBox.setSelectedIndex(today.getMonth());
        String[] years = new String[10];
        for (int i = 0; i < 10; i++) {
            years[i] = String.valueOf(today.getYear() + i);
        }
        yearsComboBox = new JComboBox(years);
        yearsComboBox.setMaximumRowCount(MAXIMUM_ROW_COUNTS);
        northPanel.add(dayLabel);
        northPanel.add(monthComboBox);
        classActionListener listener = new classActionListener();
        monthComboBox.addActionListener(listener);
        yearsComboBox.addActionListener(listener);
        northPanel.add(yearsComboBox);
        northPanel.add(enterLabel);

    }

    public void setCenterPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(7, 7));
        buttonPanel.setBackground(new Color(250, 235, 215));
        JLabel[] dayName = new JLabel[7];
        for (int i = 0; i < 7; i++) {
            dayName[i] = new JLabel(DateAD.WEEKDAYNAMES[i]);
            dayName[i].setForeground(Color.MAGENTA);
            dayName[i].setHorizontalAlignment(SwingConstants.CENTER);
            buttonPanel.add(dayName[i]);

        }

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                dayButton[i][j] = new JButton();
                if (!(cal.at(i, j) <= 0)) {
                    dayButton[i][j].setText(String.valueOf(cal.at(i, j)));
                    if (cal.at(i, j) < today.getDayOfMonth()) {
                        dayButton[i][j].setEnabled(false);
                    } else {
                    }
                } else {
                    dayButton[i][j].setEnabled(false);
                }
                buttonPanel.add(dayButton[i][j]);
            }
        }
    }

    public void setSouthPanel() {
//        JPane  JPanel upl southPanel = new JPanel();
        southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(4, 1));
        JPanel north = new JPanel();
        JPanel middle1 = new JPanel();
        JPanel middle2 = new JPanel();
        JPanel south = new JPanel();
        south.setLayout(new BorderLayout());

//        JPanel upperPanel = new JPanel();
//        JPanel lowerPanel = new JPanel();
        JLabel currentDate = new JLabel("Today:  " + today.toString());
        currentDate.setFont(new Font("Arial", 1, 16));
        JRadioButton startDate = new JRadioButton("Start Date: " + today.toString());
        startDate.setSelected(true);
        JRadioButton endDate = new JRadioButton("End Date: ");
        startDate.setFont(new Font("Arial", 1, 14));
        startDate.setForeground(Color.BLUE);
        endDate.setFont(new Font("Arial", 1, 14));
        endDate.setForeground(Color.BLUE);

        ButtonGroup group = new ButtonGroup();
        group.add(startDate);
        group.add(endDate);

        JLabel enter = new JLabel("Enter your name:");
        enter.setFont(new Font("Courier", 1, 14));
        JTextField enterField = new JTextField();
        enterField.setFont(new Font("Courier", Font.PLAIN, 14));
        JButton bookJButton = new JButton("Book it!");

        north.add(currentDate);
        middle1.add(startDate);
        middle2.add(endDate);
        south.add(enter, BorderLayout.WEST);
        south.add(enterField, BorderLayout.CENTER);
        south.add(bookJButton, BorderLayout.EAST);

        southPanel.add(north);
        southPanel.add(middle1);
        southPanel.add(middle2);
        southPanel.add(south);
    }

    class classActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == monthComboBox) {
                monthComboBoxActionPerformed(event);
            }
            if (event.getSource() == yearsComboBox)
                yearsComboBoxActionPerformed(event);
            }
            

        }

        public void monthComboBoxActionPerformed(ActionEvent event) {
            int month;
            int year;
            month = monthComboBox.getSelectedIndex();
            year = Integer.parseInt(yearsComboBox.getSelectedItem().toString());
            cal = new BasicCalendar(month, year);
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    dayButton[i][j].setText(Integer.toString(cal.at(i, j)));
                }
            }
        }
        public void yearsComboBoxActionPerformed(ActionEvent event){
            int month;
            int year;
            month = monthComboBox.getSelectedIndex();
            year = Integer.parseInt(yearsComboBox.getSelectedItem().toString());
            cal = new BasicCalendar(month, year);
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    dayButton[i][j].setText(Integer.toString(cal.at(i, j)));
                }
            }
    }

    //Instance Variables
    JButton Dates;
    JPanel northPanel;
    JPanel southPanel;
    JPanel buttonPanel;
    JLabel dayLabel;
    JLabel enterLabel;

    JComboBox monthComboBox;
    JComboBox yearsComboBox;
    DateAD today;
}
