package oop.java;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class RegistrationForm extends JFrame {

    // ========================= FIELDS =========================
    private JTextField firstName, lastName, email, confirmEmail;
    private JPasswordField password, confirmPassword;

    private JComboBox<Integer> yearBox, dayBox;
    private JComboBox<String> monthBox;

    private JRadioButton male, female;
    private JRadioButton civil, cse, electrical, ec, mechanical;

    private JTextArea outputArea;

    private static final String CSV_FILE = "students.csv";
    private static final String DB_URL = "jdbc:ucanaccess://./students.accdb";

    // ========================= MAIN =========================
    public static void main(String[] args) {

        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            System.out.println("UCanAccess driver loaded ✓");
        } catch (Exception e) {
            System.out.println("Database driver not found (CSV will still work)");
        }

        SwingUtilities.invokeLater(RegistrationForm::new);
    }

    // ========================= CONSTRUCTOR =========================
    public RegistrationForm() {

        setTitle("New Student Registration Form");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout(20, 20));
        add(createFormPanel(), BorderLayout.WEST);
        add(createOutputPanel(), BorderLayout.CENTER);

        setVisible(true);
    }

    // ========================= FORM PANEL =========================
    private JPanel createFormPanel() {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(240, 240, 240));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 6, 6, 6);
        g.fill = GridBagConstraints.HORIZONTAL;

        firstName = new JTextField(14);
        lastName = new JTextField(14);
        email = new JTextField(14);
        confirmEmail = new JTextField(14);
        password = new JPasswordField(14);
        confirmPassword = new JPasswordField(14);

        addRow(panel, g, 0, "Student First Name", firstName);
        addRow(panel, g, 1, "Student Last Name", lastName);
        addRow(panel, g, 2, "Email Address", email);
        addRow(panel, g, 3, "Confirm Email Address", confirmEmail);
        addRow(panel, g, 4, "Password", password);
        addRow(panel, g, 5, "Confirm Password", confirmPassword);

        // DOB
        yearBox = new JComboBox<>();
        for (int y = 1960; y <= LocalDate.now().getYear(); y++)
            yearBox.addItem(y);

        monthBox = new JComboBox<>(new String[]{
                "JAN","FEB","MAR","APR","MAY","JUN",
                "JUL","AUG","SEP","OCT","NOV","DEC"
        });

        dayBox = new JComboBox<>();
        updateDays();

        yearBox.addActionListener(e -> updateDays());
        monthBox.addActionListener(e -> updateDays());

        JPanel dob = new JPanel();
        dob.add(yearBox);
        dob.add(monthBox);
        dob.add(dayBox);

        addRow(panel, g, 6, "Date of Birth", dob);

        // Gender
        male = new JRadioButton("Male");
        female = new JRadioButton("Female");

        ButtonGroup gender = new ButtonGroup();
        gender.add(male);
        gender.add(female);

        JPanel gp = new JPanel();
        gp.add(male); gp.add(female);

        addRow(panel, g, 7, "Gender", gp);

        // Department
        civil = new JRadioButton("Civil");
        cse = new JRadioButton("CSE");
        electrical = new JRadioButton("Electrical");
        ec = new JRadioButton("E & C");
        mechanical = new JRadioButton("Mechanical");

        ButtonGroup dep = new ButtonGroup();
        dep.add(civil); dep.add(cse); dep.add(electrical); dep.add(ec); dep.add(mechanical);

        JPanel dp = new JPanel(new GridLayout(3,2));
        dp.add(civil); dp.add(cse); dp.add(electrical); dp.add(ec); dp.add(mechanical);

        addRow(panel, g, 8, "Department", dp);

        JButton submit = new JButton("Submit");
        JButton cancel = new JButton("Cancel");

        submit.addActionListener(e -> handleSubmit());
        cancel.addActionListener(e -> resetForm());

        JPanel bp = new JPanel();
        bp.add(submit); bp.add(cancel);

        g.gridx=0; g.gridy=9; g.gridwidth=2;
        panel.add(bp, g);

        return panel;
    }

    // ========================= OUTPUT =========================
    private JPanel createOutputPanel() {

        JPanel p = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Your Data is Below:");
        title.setFont(new Font("Arial", Font.BOLD, 15));

        outputArea = new JTextArea();
        outputArea.setEditable(false);

        p.add(title, BorderLayout.NORTH);
        p.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        return p;
    }

    // ========================= UTIL ROW =========================
    private void addRow(JPanel p, GridBagConstraints g, int y, String label, Component c) {
        g.gridx=0; g.gridy=y;
        p.add(new JLabel(label), g);
        g.gridx=1;
        p.add(c, g);
    }

    // ========================= DAYS =========================
    private void updateDays() {

        dayBox.removeAllItems();

        int year = (Integer)yearBox.getSelectedItem();
        int month = monthBox.getSelectedIndex()+1;

        int days = LocalDate.of(year,month,1).lengthOfMonth();

        for(int d=1; d<=days; d++)
            dayBox.addItem(d);
    }

    // ========================= SUBMIT =========================
    private void handleSubmit() {

        String err = validateForm();
        if(!err.isEmpty()){
            JOptionPane.showMessageDialog(this, err);
            return;
        }

        String id = generateId();

        String gender = male.isSelected() ? "M" : "F";
        String dept = getDepartment();

        int y=(Integer)yearBox.getSelectedItem();
        int m=monthBox.getSelectedIndex()+1;
        int d=(Integer)dayBox.getSelectedItem();

        String dob = String.format("%04d-%02d-%02d", y,m,d);

        String record = id+" | "+firstName.getText()+" "+lastName.getText()
                +" | "+gender+" | "+dept+" | "+dob+" | "+email.getText();

        outputArea.append(record+"\n");

        saveToCSV(record);
        saveToDatabase(id, firstName.getText(), lastName.getText(),
                email.getText(), gender, dept, dob);

        resetForm();
    }

    // ========================= VALIDATION =========================
    private String validateForm() {

        StringBuilder e = new StringBuilder();

        if(firstName.getText().trim().isEmpty()) e.append("First name required\n");
        if(lastName.getText().trim().isEmpty()) e.append("Last name required\n");

        if(!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email.getText()))
            e.append("Invalid email\n");

        if(!email.getText().equals(confirmEmail.getText()))
            e.append("Emails do not match\n");

        String pass = new String(password.getPassword());

        if(pass.length()<8 || pass.length()>20)
            e.append("Password 8-20 chars\n");

        if(!pass.matches(".*[A-Za-z].*") || !pass.matches(".*\\d.*"))
            e.append("Password needs letter & number\n");

        if(!pass.equals(new String(confirmPassword.getPassword())))
            e.append("Passwords mismatch\n");

        LocalDate dob = LocalDate.of(
                (Integer)yearBox.getSelectedItem(),
                monthBox.getSelectedIndex()+1,
                (Integer)dayBox.getSelectedItem());

        int age = Period.between(dob, LocalDate.now()).getYears();

        if(age<16 || age>60) e.append("Age must be 16-60\n");

        if(getDepartment()==null) e.append("Select department\n");

        return e.toString();
    }

    // ========================= HELPERS =========================
    private String getDepartment(){
        if(civil.isSelected()) return "Civil";
        if(cse.isSelected()) return "CSE";
        if(electrical.isSelected()) return "Electrical";
        if(ec.isSelected()) return "E&C";
        if(mechanical.isSelected()) return "Mechanical";
        return null;
    }

    // ========================= CSV =========================
    private void saveToCSV(String record){
        try(BufferedWriter w = new BufferedWriter(new FileWriter(CSV_FILE,true))){
            w.write(record);
            w.newLine();
        }catch(Exception ignored){}
    }

    // ========================= ACCESS DB =========================
    private void saveToDatabase(String id,String fn,String ln,String email,
                                String gender,String dept,String dob){

        try(Connection conn = DriverManager.getConnection(DB_URL)) {

            String sql = "INSERT INTO Students VALUES (?,?,?,?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1,id);
            ps.setString(2,fn);
            ps.setString(3,ln);
            ps.setString(4,email);
            ps.setString(5,gender);
            ps.setString(6,dept);
            ps.setString(7,dob);

            ps.executeUpdate();

        } catch(Exception ignored){}
    }

    // ========================= ID =========================
    private String generateId(){
        int year = LocalDate.now().getYear();
        int count = outputArea.getLineCount()+1;
        return year+"-"+String.format("%05d",count);
    }

    // ========================= RESET =========================
    private void resetForm(){

        firstName.setText("");
        lastName.setText("");
        email.setText("");
        confirmEmail.setText("");
        password.setText("");
        confirmPassword.setText("");

        male.setSelected(false);
        female.setSelected(false);

        civil.setSelected(false);
        cse.setSelected(false);
        electrical.setSelected(false);
        ec.setSelected(false);
        mechanical.setSelected(false);

        yearBox.setSelectedIndex(0);
        monthBox.setSelectedIndex(0);
        updateDays();
    }
}
