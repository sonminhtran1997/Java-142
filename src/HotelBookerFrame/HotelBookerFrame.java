/*  * HotelBookerFrame.java  */
package HotelBookerFrame;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import Calendar.DateAD;
import Calendar.BasicCalendar;
import javax.swing.JOptionPane;
import static java.awt.Color.BLUE;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * * * @author Aleksandr Zayats, Son Tran
 */
public class HotelBookerFrame extends JFrame {

    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 400;
    private static final int MAXIMUM_ROW_COUNTS = 10;
    private static final String enterString = "Enter Start and End Dates";
    DateAD today = new DateAD();
    DateAD selectedDate = today.clone();
    DateAD ostartDate = new DateAD();
    DateAD oendDate = new DateAD(ostartDate.getTomorrow().toString());
    DateAD oldSelectedDate = new DateAD();
    JButton[][] dayButton = new JButton[6][7];
    BasicCalendar cal = new BasicCalendar();
    classActionListener listener = new classActionListener();

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
                    dayButton[i][j].setForeground(Color.BLACK);
                    if (cal.at(i, j) < today.getDayOfMonth()) {
                        dayButton[i][j].setForeground(Color.GRAY);
                    } 
                    else {}
                } 
                else 
                {
                    dayButton[i][j].setText(null);
                }
                dayButton[i][j].addActionListener(listener);
                buttonPanel.add(dayButton[i][j]);
            }
        }
        for (int i = 0; i < 6; i++) 
        {
            for (int j = 0; j < 7; j++) {
                if ((cal.at(i, j) == selectedDate.getDayOfMonth()))
                    {
                        dayButton[i][j].setForeground(Color.RED);
                    }
            }
        }
    }

    public void setSouthPanel() {
//        JPane  JPanel upl southPanel = new JPanel();


        southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(4, 1));
        JPanel north = new JPanel();
        JPanel middle1 = new JPanel();
        middle1.setLayout(new FlowLayout());
        JPanel middle2 = new JPanel();
        middle2.setLayout(new FlowLayout());
        JPanel south = new JPanel();
        south.setLayout(new BorderLayout());

        currentDate = new JLabel("Today:   " + today.toString());
        currentDate.setFont(new Font("Arial", 1, 14));
        currentDate.setForeground(Color.RED);
        startDate = new JRadioButton();
        startDate.addActionListener(listener);
        startLabel = new JLabel("Start Date: " + today.toString());
        startLabel.setFont(new Font("Arial", 1, 14));
        startLabel.setForeground(Color.BLUE);
        startDate.setSelected(true);
        endDate = new JRadioButton();
        endDate.addActionListener(listener);
        endLabel = new JLabel("End Date: " + today.getTomorrow().toString());
        endLabel.setFont(new Font("Arial", 1, 14));
        endLabel.setForeground(Color.BLUE);
        startDate.setFont(new Font("Arial", 1, 14));
        startDate.setForeground(Color.BLUE);
        endDate.setFont(new Font("Arial", 1, 14));
        endDate.setForeground(Color.BLUE);

        ButtonGroup group = new ButtonGroup();
        group.add(startDate);
        group.add(endDate);

        JLabel enter = new JLabel("Enter your name:");
        enter.setFont(new Font("Courier", 1, 14));
        enterField = new JTextField();
        enterField.setFont(new Font("Courier", Font.PLAIN, 14));
        bookJButton = new JButton("Book it!");
        bookJButton.addActionListener(listener);

        north.add(currentDate);
        middle1.add(startDate);
        middle1.add(startLabel);
        middle2.add(endDate);
        middle2.add(endLabel);
        south.add(enter, BorderLayout.WEST);
        south.add(enterField, BorderLayout.CENTER);
        south.add(bookJButton, BorderLayout.EAST);

        southPanel.add(north);
        southPanel.add(middle1);
        southPanel.add(middle2);
        southPanel.add(south);
    }

    class classActionListener implements ActionListener {
        short dateSelected;
        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == monthComboBox) {
                monthComboBoxActionPerformed(event);
            }
            if (event.getSource() == yearsComboBox) {
                yearsComboBoxActionPerformed(event);
            }
            if (event.getSource() == startDate) {
                startRadioButtonListener(event);
            }
            if (event.getSource() == endDate) {
                endRadioButtonListener(event);
            }
            if (event.getSource() == bookJButton) {
                bookIt();
            }
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    if (event.getSource() == dayButton[i][j])
                    {
                        try
                        {
                            if (cal.at(i, j) < today.getDayOfMonth() && 
                                    Integer.parseInt(yearsComboBox.getSelectedItem().toString()) == today.getYear() && monthComboBox.getSelectedIndex() == today.getMonth()) {
                                
                            }
                            else{
                                dateSelected = Short.parseShort(dayButton[i][j].getText());
                                buttonArrayListener(event);
                            }
                        }
                        catch(NumberFormatException exception)
                        {}
                        
                    }
                }
            }
            

        }
        int previousMonthChoice = today.getMonth();
        public void monthComboBoxActionPerformed(ActionEvent event) {
            int month;
            int year;
            if (Integer.parseInt(yearsComboBox.getSelectedItem().toString()) == today.getYear()) {
                if ((monthComboBox.getSelectedIndex()) + 1 <= today.getMonth()) 
                {
                    monthComboBox.setSelectedIndex(previousMonthChoice);
                }
            }
            month = monthComboBox.getSelectedIndex();
            year = Integer.parseInt(yearsComboBox.getSelectedItem().toString());
            oldSelectedDate = selectedDate.clone();
            selectedDate.setMonth((short)month);
            selectedDate.setYear((short) year);
            if (getTotalDays(oldSelectedDate.getMonth(), oldSelectedDate.getYear()) == 31  && oldSelectedDate.getDayOfMonth() == 31) {
                if (getTotalDays(selectedDate.getMonth(), selectedDate.getYear()) == 31) {
                }
                else if (getTotalDays(selectedDate.getMonth(), selectedDate.getYear()) == 30)
                {
                    selectedDate.setDayOfMonth((short)30);
                }
                else if (getTotalDays(selectedDate.getMonth(), selectedDate.getYear()) == 29)
                {
                    selectedDate.setDayOfMonth((short)29);
                }
                else
                {
                    selectedDate.setDayOfMonth((short)28);
                }
            }
            else if(getTotalDays(oldSelectedDate.getMonth(), oldSelectedDate.getYear()) == 30 && oldSelectedDate.getDayOfMonth() == 30)
            {
                if (getTotalDays(selectedDate.getMonth(), selectedDate.getYear()) == 31) 
                {}
                else if (getTotalDays(selectedDate.getMonth(), selectedDate.getYear()) == 30)
                {}
                else if (getTotalDays(selectedDate.getMonth(), selectedDate.getYear()) == 29)
                {
                    selectedDate.setDayOfMonth((short)29);
                }
                else
                {
                    selectedDate.setDayOfMonth((short)28);
                }
            }
            else if(getTotalDays(oldSelectedDate.getMonth(), oldSelectedDate.getYear()) == 29 && oldSelectedDate.getDayOfMonth() == 29)
            {
                if (getTotalDays(selectedDate.getMonth(), selectedDate.getYear()) == 31) 
                {}
                else if (getTotalDays(selectedDate.getMonth(), selectedDate.getYear()) == 30)
                {}
                else if (getTotalDays(selectedDate.getMonth(), selectedDate.getYear()) == 29)
                {}
                else
                {
                    selectedDate.setDayOfMonth((short)28);
                }
            }
            else{}
            dayLabel.setText(Short.toString(selectedDate.getDayOfMonth()));
            if (startDate.isSelected()) {
                ostartDate = selectedDate.clone();
                if (ostartDate.compareTo(oendDate) == -1) {

                } else {
                    oendDate = ostartDate.getTomorrow();
                }
                startLabel.setText("Start Date: " + ostartDate.toString());
                endLabel.setText("End Date: " + oendDate.toString());
            }
            else
            {
                oendDate = selectedDate.clone();
                if (ostartDate.compareTo(oendDate) == -1) {

                } else {
                    oendDate = ostartDate.getTomorrow();
                }
                startLabel.setText("Start Date: " + ostartDate.toString());
                endLabel.setText("End Date: " + oendDate.toString());
            }
            cal = new BasicCalendar(month, year);
            
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) 
                {
                    if (cal.at(i, j) <= 0) 
                    {
                        dayButton[i][j].setText(null);
                    }
                    else
                    {
                        dayButton[i][j].setText(Integer.toString(cal.at(i, j)));
                        dayButton[i][j].setForeground(Color.BLACK);
                    }
                }
            }
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) 
                {
                    if (Integer.parseInt(yearsComboBox.getSelectedItem().toString()) == today.getYear()) 
                    {
                        if (monthComboBox.getSelectedIndex() == (today.getMonth())) {
                            if (cal.at(i, j) < today.getDayOfMonth()) {
                                dayButton[i][j].setForeground(Color.GRAY);
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) 
                {
                    if ((cal.at(i, j) == selectedDate.getDayOfMonth()))
                    {
                        dayButton[i][j].setForeground(Color.RED);
                    }
                }
            }
            previousMonthChoice = monthComboBox.getSelectedIndex();
            
            
        }
        int previousYearChoice;
        public void yearsComboBoxActionPerformed(ActionEvent event) {
            int month;
            int year;
            if (monthComboBox.getSelectedIndex() < today.getMonth()) {
                if (Integer.parseInt(yearsComboBox.getSelectedItem().toString()) == today.getYear()) 
                {
                    yearsComboBox.setSelectedIndex(previousYearChoice);
                }
            }
            month = monthComboBox.getSelectedIndex();
            year = Integer.parseInt(yearsComboBox.getSelectedItem().toString());
            selectedDate.setMonth((short)month);
            selectedDate.setYear((short) year);
            if (getTotalDays(oldSelectedDate.getMonth(), oldSelectedDate.getYear()) == 31) {
                if (getTotalDays(selectedDate.getMonth(), selectedDate.getYear()) == 31) {
                }
                else if (getTotalDays(selectedDate.getMonth(), selectedDate.getYear()) == 30)
                {
                    selectedDate.setDayOfMonth((short)30);
                }
                else if (getTotalDays(selectedDate.getMonth(), selectedDate.getYear()) == 29)
                {
                    selectedDate.setDayOfMonth((short)29);
                }
                else
                {
                    selectedDate.setDayOfMonth((short)28);
                }
            }
            else if(getTotalDays(oldSelectedDate.getMonth(), oldSelectedDate.getYear()) == 30)
            {
                if (getTotalDays(selectedDate.getMonth(), selectedDate.getYear()) == 31) 
                {}
                else if (getTotalDays(selectedDate.getMonth(), selectedDate.getYear()) == 30)
                {}
                else if (getTotalDays(selectedDate.getMonth(), selectedDate.getYear()) == 29)
                {
                    selectedDate.setDayOfMonth((short)29);
                }
                else
                {
                    selectedDate.setDayOfMonth((short)28);
                }
            }
            else if(getTotalDays(oldSelectedDate.getMonth(), oldSelectedDate.getYear()) == 29)
            {
                if (getTotalDays(selectedDate.getMonth(), selectedDate.getYear()) == 31) 
                {}
                else if (getTotalDays(selectedDate.getMonth(), selectedDate.getYear()) == 30)
                {}
                else if (getTotalDays(selectedDate.getMonth(), selectedDate.getYear()) == 29)
                {}
                else
                {
                    selectedDate.setDayOfMonth((short)28);
                }
            }
            else{}
            dayLabel.setText(Short.toString(selectedDate.getDayOfMonth()));
            if (startDate.isSelected()) {
                ostartDate = selectedDate.clone();
                if (ostartDate.compareTo(oendDate) == -1) {

                } else {
                    oendDate = ostartDate.getTomorrow();
                }
                startLabel.setText("Start Date: " + ostartDate.toString());
                endLabel.setText("End Date: " + oendDate.toString());
            }
            else
            {
                oendDate = selectedDate.clone();
                if (ostartDate.compareTo(oendDate) == -1) {

                } else {
                    oendDate = ostartDate.getTomorrow();
                }
                startLabel.setText("Start Date: " + ostartDate.toString());
                endLabel.setText("End Date: " + oendDate.toString());
            }
            month = monthComboBox.getSelectedIndex();
            year = Integer.parseInt(yearsComboBox.getSelectedItem().toString());
            cal = new BasicCalendar(month, year);
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) 
                {
                    if (cal.at(i, j) <= 0) 
                    {
                        dayButton[i][j].setText(null);
                    }
                    else
                    {
                        dayButton[i][j].setText(Integer.toString(cal.at(i, j)));
                        dayButton[i][j].setForeground(Color.BLACK);
                    }
                }
            }
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) 
                {
                    if (Integer.parseInt(yearsComboBox.getSelectedItem().toString()) == today.getYear()) 
                    {
                        if (monthComboBox.getSelectedIndex() == (today.getMonth())) {
                            if (cal.at(i, j) < today.getDayOfMonth()) {
                                dayButton[i][j].setForeground(Color.GRAY);
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) 
                {
                    if ((cal.at(i, j) == selectedDate.getDayOfMonth()))
                    {
                        dayButton[i][j].setForeground(Color.RED);
                    }
                }
            }
            previousYearChoice = yearsComboBox.getSelectedIndex();
        }
        public void buttonArrayListener(ActionEvent event)
        {
            selectedDate.setDayOfMonth(dateSelected);
            selectedDate.setMonth((short) (monthComboBox.getSelectedIndex()));
            selectedDate.setYear((short)(Integer.parseInt(yearsComboBox.getSelectedItem().toString())));
            
            //startDate.setActionCommand(selectedDate.toString());
            if (startDate.isSelected()) 
            {
                ostartDate = selectedDate.clone();
                startRadioButtonListener(event);
                dayLabel.setText(Short.toString(ostartDate.getDayOfMonth()));
                startLabel.setText("Start Date: " + ostartDate.toString());
                endLabel.setText("End Date: " + oendDate.toString());
            }
            else 
            {
                oendDate = selectedDate.clone();
                endRadioButtonListener(event);
                dayLabel.setText(Short.toString(oendDate.getDayOfMonth()));
                startLabel.setText("Start Date: " + ostartDate.toString());
                endLabel.setText("End Date: " + oendDate.toString());
            }
            
           
            
        }
        public void startRadioButtonListener(ActionEvent event)
        {
            int month, year;
            if (ostartDate.compareTo(oendDate) == -1) {

            } else {
                oendDate = ostartDate.getTomorrow();
            }
            selectedDate = ostartDate.clone();
            dayLabel.setText(Short.toString(ostartDate.getDayOfMonth()));
            monthComboBox.setSelectedIndex(ostartDate.getMonth());
            yearsComboBox.setSelectedItem(ostartDate.getYear());
            month = monthComboBox.getSelectedIndex();
            year = ostartDate.getYear();
            cal = new BasicCalendar(month, year);
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) 
                {
                    if (cal.at(i, j) <= 0) 
                    {
                        dayButton[i][j].setText(null);
                    }
                    else
                    {
                        dayButton[i][j].setText(Integer.toString(cal.at(i, j)));
                        dayButton[i][j].setForeground(Color.BLACK);
                    }
                }
            }
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) 
                {
                    if (Integer.parseInt(yearsComboBox.getSelectedItem().toString()) == today.getYear()) 
                    {
                        if (monthComboBox.getSelectedIndex() == (today.getMonth())) {
                            if (cal.at(i, j) < today.getDayOfMonth()) {
                                dayButton[i][j].setForeground(Color.GRAY);
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) 
                {
                    if ((cal.at(i, j) == selectedDate.getDayOfMonth()))
                    {
                        dayButton[i][j].setForeground(Color.RED);
                    }
                }
            }

        }
        
        public void endRadioButtonListener(ActionEvent event)
        {
            int month, year;
            if (oendDate.compareTo(ostartDate) == 1) {

            } else {
                oendDate = ostartDate.getTomorrow();
            }
            selectedDate = oendDate.clone();
            dayLabel.setText(Short.toString(oendDate.getDayOfMonth()));
            monthComboBox.setSelectedIndex(oendDate.getMonth());
            yearsComboBox.setSelectedItem(oendDate.getYear());
            month = monthComboBox.getSelectedIndex();
            year = oendDate.getYear();
            cal = new BasicCalendar(month, year);
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) 
                {
                    if (cal.at(i, j) <= 0) 
                    {
                        dayButton[i][j].setText(null);
                    }
                    else
                    {
                        dayButton[i][j].setText(Integer.toString(cal.at(i, j)));
                        dayButton[i][j].setForeground(Color.BLACK);
                    }
                }
            }
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) 
                {
                    if (Integer.parseInt(yearsComboBox.getSelectedItem().toString()) == today.getYear()) 
                    {
                        if (monthComboBox.getSelectedIndex() == (today.getMonth())) {
                            if (cal.at(i, j) < today.getDayOfMonth()) {
                                dayButton[i][j].setForeground(Color.GRAY);
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) 
                {
                    if ((cal.at(i, j) == selectedDate.getDayOfMonth()))
                    {
                        dayButton[i][j].setForeground(Color.RED);
                    }
                }
            }
        }
        public void bookIt(){
            if (enterField.getText().equals("")) 
            {
                enterField.setText("Enter Name");
                return;
            }
            if (enterField.getText().equals("Enter Name")) 
                return;
            if (isInteger(enterField.getText())) {
                JOptionPane.showMessageDialog(null, "Enter a suitable name", "Error",
                        JOptionPane.WARNING_MESSAGE);
            }
            else {
                try {
                    PrintWriter fileOutput = new PrintWriter(new FileOutputStream("Bookings.txt", true));
                    fileOutput.println(enterField.getText());
                    fileOutput.println("Arrive: " + ostartDate);
                    fileOutput.println("Depart: " + oendDate);
                    fileOutput.println();
                    fileOutput.close();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error Saving file", "Error",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
    public int getTotalDays(short month, short year) {
        int totalDays = 0;
        if (DateAD.isLeapYear(year)) {
            switch (month) {
                case 0:
                    totalDays = 31;
                    break;
                case 1:
                    totalDays = 29;
                    break;
                case 2:
                    totalDays = 31;
                    break;
                case 3:
                    totalDays = 30;
                    break;
                case 4:
                    totalDays = 31;
                    break;
                case 5:
                    totalDays = 30;
                    break;
                case 6:
                    totalDays = 31;
                    break;
                case 7:
                    totalDays = 31;
                    break;
                case 8:
                    totalDays = 30;
                    break;
                case 9:
                    totalDays = 31;
                    break;
                case 10:
                    totalDays = 30;
                    break;
                case 11:
                    totalDays = 31;
                    break;
            }
        } else {
            switch (month) {
                case 0:
                    totalDays = 31;
                    break;
                case 1:
                    totalDays = 28;
                    break;
                case 2:
                    totalDays = 31;
                    break;
                case 3:
                    totalDays = 30;
                    break;
                case 4:
                    totalDays = 31;
                    break;
                case 5:
                    totalDays = 30;
                    break;
                case 6:
                    totalDays = 31;
                    break;
                case 7:
                    totalDays = 31;
                    break;
                case 8:
                    totalDays = 30;
                    break;
                case 9:
                    totalDays = 31;
                    break;
                case 10:
                    totalDays = 30;
                    break;
                case 11:
                    totalDays = 31;
                    break;
            }
        }
        return totalDays;
    }
    public boolean isInteger(String input){
        try
        {
            Integer.parseInt(input);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    //Instance Variables
    JButton Dates;
    JPanel northPanel;
    JPanel southPanel;
    JPanel buttonPanel;
    JLabel dayLabel;
    JLabel enterLabel;
    JLabel startLabel;
    JLabel endLabel;
    JLabel currentDate;
    JTextField enterField;
    JRadioButton startDate;
    JRadioButton endDate;
    JButton bookJButton;
    JComboBox monthComboBox;
    JComboBox yearsComboBox;
}
