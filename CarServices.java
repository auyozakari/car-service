import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.sql.*;

public class CarServices implements ActionListener{
    Frame myFrame;
    JPanel panelLeft;
    JPanel panelRight;
    JPanel panelBelow;
    JPanel panelTop;
    JPanel panel;
    boolean boolRent = false;
    boolean boolSell = false;
    JLabel labelName;
    JTextField textName;
    
    //panel left content    
    JLabel labelPick;
    JLabel labelDrop;
    JLabel labelDate1;
    JLabel labelPlace1;
    JLabel labelDate2;
    JLabel labelPlace2;
    JTextField textDate1;
    JTextField textPlace1;
    JTextField textDate2;
    JTextField textPlace2;
    JSpinner spinnerModel1;
    JSpinner spinnerType1;
    JButton buttonRent;
    
    //panel right content
    JCheckBox checkMechanical;
    JCheckBox checkWT;
    JCheckBox checkGap;
    JCheckBox checkWShield;
    JButton buttonRepair;
    
    //panel below content
    JSpinner spinnerModel2;
    JSpinner spinnerType2;
    JLabel labelPrice;
    JButton buttonSell;
    
     public CarServices (){
        myFrame = new Frame("Car Services");
        myFrame.setVisible(true);  
        panelLeft = new JPanel();
        panelRight = new JPanel();
        panelBelow = new JPanel();
        panelTop = new JPanel();
        panel = new JPanel();
        panelLeft.setPreferredSize(new Dimension(550,400));
        panelRight.setPreferredSize(new Dimension(400,400));
        panelBelow.setPreferredSize(new Dimension(400,200));
        panelTop.setPreferredSize(new Dimension(100,100));
        panel.setPreferredSize(new Dimension(200,200));
        panelLeft.setBackground(Color.red);
        panelRight.setBackground(Color.red);
        panelBelow.setBackground(Color.CYAN);
        panelTop.setBackground(Color.green);
        panel.setBackground(Color.BLUE);
       
        //panel left and below
        spinnerModel1 = new JSpinner();
        spinnerType1 =new JSpinner();
        spinnerModel1.setModel(new SpinnerListModel(new String[]{"BMW", "Audi", "Toyota", "Honda", "Mercedes"}));
        spinnerType1.setModel(new SpinnerListModel(new String[]{"X6", "X1", "7 Series"}));
        spinnerModel1.addChangeListener(listener);
        spinnerType1.addChangeListener(listener);
        spinnerModel2 = new JSpinner();
        spinnerType2 =new JSpinner();
        spinnerModel2.setModel(new SpinnerListModel(new String[]{"BMW", "Audi", "Toyota", "Honda", "Mercedes"}));
        spinnerType2.setModel(new SpinnerListModel(new String[]{"X6", "X1", "7 Series"}));
        spinnerModel2.addChangeListener(listener);
        spinnerType2.addChangeListener(listener);
        //panel left  content 
        labelPick = new JLabel("Pick Up Information");
        labelDate1 = new JLabel("Pick Up Date:");
        labelDate2 = new JLabel("Drop Up Date:");
        labelPlace1 = new JLabel("Pick Up Place:");
        labelPlace2 = new JLabel("Drop UpPlace:");
        textDate1 = new JTextField("dd mm yyyy",10);
        textDate2 = new JTextField("dd mm yyyy");
        textPlace1 = new JTextField();
        textPlace2 = new JTextField();
        buttonRent = new JButton("RENT");
        buttonRent.addActionListener(this);
        GridLayout layout = new GridLayout(0,2);
        layout.setHgap(50);
        layout.setVgap(50);
        panelLeft.setLayout(layout);
        panelLeft.add(labelDate1);
        panelLeft.add(textDate1);
        panelLeft.add(labelPlace1);
        panelLeft.add(textPlace1);
        panelLeft.add(labelDate2);
        panelLeft.add(textDate2);
        panelLeft.add(labelPlace2);
        panelLeft.add(textPlace2);
        panelLeft.add(spinnerModel1);
        panelLeft.add(spinnerType1);
        panelLeft.add(buttonRent);
            
        //panel right content
        checkMechanical = new JCheckBox("Mechanical");
        checkWT = new JCheckBox("Wheels and Tires");
        checkWShield = new JCheckBox("WindShield");
        checkGap = new JCheckBox("Gap");
        buttonRepair = new JButton("Repair");
        buttonRepair.addActionListener(this);
        GridLayout layoutRight = new GridLayout(0,1);
        layoutRight.setHgap(50);
        layoutRight.setVgap(50);
        panelRight.setLayout(layoutRight);
        panelRight.add(checkMechanical);
        panelRight.add(checkWT);
        panelRight.add(checkWShield);
        panelRight.add(checkGap);
        panelRight.add(buttonRepair);
        
        //panel Below content
        labelPrice = new JLabel("Price");
        buttonSell = new JButton("Sell");
        buttonSell.addActionListener(this);
        GridLayout layoutBelow = new GridLayout(0,1);
        layoutRight.setHgap(50);
        layoutRight.setVgap(50);
        panelBelow.setLayout(layoutBelow);
        panelBelow.add(spinnerModel2);
        panelBelow.add(spinnerType2);
        panelBelow.add(labelPrice);
        panelBelow.add(buttonSell);
        
        //panel Submit
        labelName = new JLabel("Enter the Customer Name");
        textName = new JTextField(20);
        panelTop.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelTop.add(labelName);
        panelTop.add(textName);
        
        
        //panel.add(panelBelow, BorderLayout.WEST);
        //panel.add(panelSubmit, BorderLayout.EAST);
        
        myFrame.add(panelLeft,BorderLayout.WEST);
        myFrame.add(panelRight,BorderLayout.EAST);
        //myFrame.add(panel,BorderLayout.SOUTH);
        myFrame.add(panelBelow,BorderLayout.SOUTH);
        myFrame.add(panelTop,BorderLayout.NORTH);
        //myFrame.setDefaultCloseOPeration(JFrame.EXIT_ON_CLOSE);
        myFrame.setTitle("Car Services");
        myFrame.pack();
        myFrame.setVisible(true);
     }
    
    public static void main(String[] args) {
        // create database
        try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/","root","");
                Statement stmt=con.createStatement();
                stmt.executeUpdate("create database if not exists carServices");
                con.close();
                }catch(Exception ex){ System.out.println(ex);}
        //create tables
        try{
                Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/carServices","root","");
                 Statement stmt=con.createStatement();
                stmt.executeUpdate("CREATE TABLE if not exists repair (id int(11) auto_increment primary key,CNames varchar(50),mechanical varchar(50),"
                        + "wt varchar(50),windShield varchar(50),gap varchar(50),repairAmount varchar(50))");
                stmt.executeUpdate("CREATE TABLE if not exists sell(id int(11) auto_increment primary key,CNames varchar(50),type varchar(50),model varchar(50),"
                +"sellAmount varchar(50))");
                stmt.executeUpdate("CREATE TABLE if not exists rent(id int(11) auto_increment primary key,CNames varchar(50),pickupDate varchar(50),"
                        + "dropDate varchar(50),pickupPlace varchar(50),DropPlace varchar(50),rentAmount varchar(50),carType varchar(50),carModel varchar(50))");
                con.close();
                }catch(Exception ex){ System.out.println(ex);}
        
            
        new CarServices();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == buttonRent){
            SimpleDateFormat mf = new SimpleDateFormat("dd mm yyyy");
            String pDate = textDate1.getText();
            String dDate = textDate2.getText();
            try{
                Date fd = mf.parse(pDate);
                Date sd = mf.parse(dDate);
                long mylong = sd.getTime()-fd.getTime();
                //JOptionPane.showMessageDialog(null, ""+TimeUnit.DAYS.convert(mylong, TimeUnit.MILLISECONDS));
           
                JOptionPane.showMessageDialog(null, "Days:"+(mylong/(24*60*60*1000))+"\n Price per Day: $20\n Total Charges:"+((mylong/(24*60*60*1000))*20));
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/carServices","root","");
                    Statement stmt=con.createStatement();
                    stmt.executeUpdate("insert into rent(CNames,pickupDate,dropDate,pickupPlace,DropPlace,rentAmount,carType,carModel) values('"+textName.getText()+"','"+textDate1.getText()+"','"+textDate2.getText()+"','"+textPlace1.getText()+"','"+textPlace2.getText()+"','"+((mylong/(24*60*60*1000))*20)+"','"+spinnerType1.getValue()+"','"+spinnerModel1.getValue()+"')");
                    con.close();
                }catch(Exception ex){ System.out.println(ex);}
            } catch (ParseException ex) {
                Logger.getLogger(CarServices.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        if(e.getSource() == buttonRepair){
            //repair
            int repairAmount= 0;
            boolean m,wt,ws,g;
            m=wt=ws=g=false;
            if(checkMechanical.isSelected()){
                repairAmount+=100;
                m=true;
            }
            if(checkWT.isSelected()){
                repairAmount+=80;
                wt=true;
            }
            if(checkWShield.isSelected()){
                repairAmount+=70;
                ws=true;
            }
            if(checkGap.isSelected()){
                repairAmount+=110;
                g=true;
            }
            String message; 
            message = "Mechanical $"+( m == true ? 100:0);
            message += "\nWheel & Tire $"+( wt == true ? 80:0);
            message += "\nWindshield $"+( ws == true ? 70:0);
            message += "\nGap $"+( g == true ? 110:0);
            message += "\nTotal $"+repairAmount;
            JOptionPane.showMessageDialog(null, message);
            boolRent = true;
            try{
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/carServices","root","");
                    Statement stmt=con.createStatement();
                    stmt.executeUpdate("insert into repair(CNames,mechanical,wt,windshield,gap,repairAmount) values('"+textName.getText()+"','"+( m == true ? 100:0)+"','"+( wt == true ? 80:0)+"','"+( ws == true ? 70:0)+"','"+( g == true ? 110:0)+"','"+repairAmount+"')");
                    con.close();
                }catch(Exception ex){ System.out.println(ex);}
        }
        if(e.getSource() == buttonSell){
            JOptionPane.showMessageDialog(null, "Car Type: "+spinnerModel2.getValue().toString()+"\nModel: "+spinnerType2.getValue().toString()
                +"\nPrice: "+labelPrice.getText().toString());
            boolSell = true;
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/carServices","root","");
                    Statement stmt=con.createStatement();
                    stmt.executeUpdate("insert into sell(cNames,type,model,sellAmount) values('"+textName.getText()+"','"+spinnerModel2.getValue()+"','"+spinnerType2.getValue()+"','"+labelPrice.getText()+"')");
                    con.close();
                }catch(Exception ex){ System.out.println(ex);}
        }
    }
     ChangeListener listener = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            if(e.getSource() == spinnerModel1){
                String car = spinnerModel1.getValue().toString();
                if(car == "Toyota"){
                    spinnerType1.setModel(new javax.swing.SpinnerListModel(new String[] {"Camry", "Corolla", "Sienna"}));
                }else if(car == "BMW"){
                    spinnerType1.setModel(new javax.swing.SpinnerListModel(new String[] {"X6", "X1", "7 Series"}));
                }else if(car == "Audi"){
                    spinnerType1.setModel(new javax.swing.SpinnerListModel(new String[] {"A3 Sportback", "A6 allroad quattro", "A4 Cabriolet"}));
                }else if (car == "Honda"){
                    spinnerType1.setModel(new javax.swing.SpinnerListModel(new String[] {"Brio", "City", "Civic"}));
                }else if(car == "Mercedes"){
                    spinnerType1.setModel(new javax.swing.SpinnerListModel(new String[] {"A-Class", "E-Class", "C-Class"}));
                }
            }
            if(e.getSource() == spinnerModel2){
                String car = spinnerModel2.getValue().toString();
                if(car == "Toyota"){
                    spinnerType2.setModel(new javax.swing.SpinnerListModel(new String[] {"Camry", "Corolla", "Sienna"}));
                }else if(car == "BMW"){
                    spinnerType2.setModel(new javax.swing.SpinnerListModel(new String[] {"X6", "X1", "7 Series"}));
                }else if(car == "Audi"){
                    spinnerType2.setModel(new javax.swing.SpinnerListModel(new String[] {"A3 Sportback", "A6 allroad quattro", "A4 Cabriolet"}));
                }else if (car == "Honda"){
                    spinnerType2.setModel(new javax.swing.SpinnerListModel(new String[] {"Brio", "City", "Civic"}));
                }else if(car == "Mercedes"){
                    spinnerType2.setModel(new javax.swing.SpinnerListModel(new String[] {"A-Class", "E-Class", "C-Class"}));
                }
            }
            if(e.getSource() == spinnerType2){
                String model = spinnerType2.getValue().toString();
                switch(model){
                    case "Camry":labelPrice.setText("$"+1400);
                        break;
                    case "Corolla":labelPrice.setText("$"+2500);
                        break;
                    case "Sienna":labelPrice.setText("$"+1100);
                        break;
                    case "7 Series":labelPrice.setText("$"+5500);
                        break;
                    case "X1":labelPrice.setText("$"+7500);
                        break;
                    case "X6":labelPrice.setText("$"+2500);
                        break;
                    case "A3 Sportback":labelPrice.setText("$"+5600);
                        break;
                    case "A6 allroad quattro":labelPrice.setText("$"+4500);
                        break;
                    case "A4 Cabriolet":labelPrice.setText("$"+3500);
                        break;
                    case "Brio":labelPrice.setText("$"+9500);
                        break;
                    case "City":labelPrice.setText("$"+6000);
                        break;
                    case "Civic":labelPrice.setText("$"+4000);
                        break;
                    case "A-Class":labelPrice.setText("$"+5000);
                        break;
                    case "E-Class":labelPrice.setText("$"+2000);
                        break;
                    case "C-Class":labelPrice.setText("$"+6700);
                        break;    
                    } 
            }
        }
    };
}
