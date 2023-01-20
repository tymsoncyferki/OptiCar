import Exceptions.EmptyDataException;
import Exceptions.MaxPriceException;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AppGui extends JFrame implements ActionListener {

    CardLayout mainLayout = new CardLayout();
    CardLayout listLayout = new CardLayout();
    JPanel firstPage, secondPage, thirdPage;
    // firstPage
    JPanel dataPanel, buttonPanel1, practicalityPanel, fuelPanel, errorPanel, gearBoxPanel;
    JTextField minPrice, maxPrice;
    JButton forwardButton;
    JLabel price, practicalityLabel, fuelLabel, errorlabel, gearBoxLabel;
    JComboBox<String> practicalityCombo;
    JCheckBox petrolCheck, hybridCheck, electricCheck, dieselCheck, cvtCheck, automaticCheck, manulaCheck;
    // secondPage
    JPanel buttonPanel2, dragPanel, loadingPanel, infoPanel;
    JButton backButton, searchButton;
    JLayeredPane layeredPane;
    JLabel loadingGif, listInfo;
    // menu
    JMenuBar menuBar;
    JMenu aboutMenu, helpMenu;
    JMenuItem aboutApp, aboutProject;

    public AppGui() {
        super("Findcar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 600));
        setLocation(0, 0);
        setLayout(mainLayout);
        ImageIcon icon = new ImageIcon("res/car-icon.png");
        setIconImage(icon.getImage());
        setFont(new Font("Segoe UI",  Font.PLAIN, 18));

        // menu
        menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        aboutMenu = new JMenu("About");
        menuBar.add(aboutMenu);
        helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);
        aboutApp = new JMenuItem("App info");
        aboutMenu.add(aboutApp);
        aboutMenu.addSeparator();
        aboutProject = new JMenuItem("Project info");
        aboutMenu.add(aboutProject);
        setJMenuBar(menuBar);

        // panel 1
        firstPage = new JPanel();
        firstPage.setPreferredSize(new Dimension(1000, 600));
        firstPage.setLayout(new BoxLayout(firstPage, BoxLayout.PAGE_AXIS));

        // collecting data
        dataPanel = new JPanel();
        //dataPanel.setBorder(BorderFactory.createTitledBorder("Price"));
        minPrice = new JTextField("0");
        minPrice.setPreferredSize(new Dimension(90, 30));
        maxPrice = new JTextField("0");
        maxPrice.setPreferredSize(new Dimension(90, 30));
        price = new JLabel("Enter price");
        dataPanel.add(price);
        dataPanel.add(minPrice);
        dataPanel.add(maxPrice);
        dataPanel.setPreferredSize(new Dimension(100,60));
        dataPanel.setMaximumSize(new Dimension(400,100));
        firstPage.add(Box.createRigidArea(new Dimension(0,50)));
        firstPage.add(dataPanel);
        firstPage.add(Box.createRigidArea(new Dimension(0,50)));





        // combo box

        practicalityCombo = new JComboBox<>();
        practicalityCombo.addItem("Universal");
        practicalityCombo.addItem("City");
        practicalityCombo.addItem("Route");
        practicalityCombo.addItem("Family");
        practicalityCombo.setPreferredSize(new Dimension(160,50));
        practicalityCombo.addActionListener(this);
        practicalityLabel = new JLabel("Destiny: ");
        practicalityPanel = new JPanel();
        practicalityPanel.add(practicalityLabel);
        practicalityPanel.add(practicalityCombo);
        practicalityPanel.setMaximumSize(new Dimension(300,100));
        firstPage.add(practicalityPanel);
        firstPage.add(Box.createRigidArea(new Dimension(0,50)));


        // fuel panel
        fuelPanel = new JPanel();
        fuelLabel = new JLabel("Fuel type: ");
        fuelLabel.setPreferredSize(new Dimension(60,20));
        fuelPanel.add(fuelLabel);

        petrolCheck = new JCheckBox("Petrol",false);
        hybridCheck = new JCheckBox("Hybrid",false);
        electricCheck = new JCheckBox("Electric",false);
        dieselCheck = new JCheckBox("Diesel",false);

        petrolCheck.setPreferredSize(new Dimension(70,40));
        hybridCheck.setPreferredSize(new Dimension(70,40));
        electricCheck.setPreferredSize(new Dimension(70,40));
        dieselCheck.setPreferredSize(new Dimension(70,40));

        petrolCheck.addActionListener(this);
        hybridCheck.addActionListener(this);
        electricCheck.addActionListener(this);
        dieselCheck.addActionListener(this);


        fuelPanel.add(petrolCheck);
        fuelPanel.add(hybridCheck);
        fuelPanel.add(electricCheck);
        fuelPanel.add(dieselCheck);
        fuelPanel.setMaximumSize(new Dimension(700,30));

        firstPage.add(fuelPanel);
        firstPage.add(Box.createRigidArea(new Dimension(0,50)));


        // GearBox
        gearBoxPanel = new JPanel();

        gearBoxLabel = new JLabel("Gearbox: ");
        gearBoxLabel.setPreferredSize(new Dimension(60,20));

        cvtCheck = new JCheckBox("CVT", false);
        automaticCheck = new JCheckBox("Automatic", false);
        manulaCheck = new JCheckBox("Manual", false);

        cvtCheck.setPreferredSize(new Dimension(50,20));
        automaticCheck.setPreferredSize(new Dimension(85,20));
        manulaCheck.setPreferredSize(new Dimension(85,20));

        cvtCheck.addActionListener(this);
        automaticCheck.addActionListener(this);
        manulaCheck.addActionListener(this);

        gearBoxPanel.add(gearBoxLabel);
        gearBoxPanel.add(cvtCheck);
        gearBoxPanel.add(automaticCheck);
        gearBoxPanel.add(manulaCheck);
        gearBoxPanel.setMaximumSize(new Dimension(700,30));

        firstPage.add(gearBoxPanel);
        firstPage.add(Box.createVerticalGlue());


        // error
        errorlabel = new JLabel("");
        errorlabel.setFont(new Font("Verdana", Font.BOLD, 15));
        errorlabel.setForeground(Color.red);
        errorPanel = new JPanel();
        errorPanel.setMaximumSize(new Dimension(600,20));
        errorPanel.add(errorlabel);

        firstPage.add(errorPanel);

        // buttons
        forwardButton = new JButton("Next");
        forwardButton.setActionCommand("forward");
        forwardButton.setPreferredSize(new Dimension(90, 30));
        forwardButton.addActionListener(this);
        buttonPanel1 = new JPanel();
        buttonPanel1.add(forwardButton);
        buttonPanel1.setMaximumSize(new Dimension(90,30));

        firstPage.add(buttonPanel1);


        add(firstPage);
        // end panel 1

        // panel 2
        secondPage = new JPanel();
        secondPage.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
        secondPage.setLayout(new BorderLayout());

        // info
        infoPanel = new JPanel();
        listInfo = new JLabel("Choose traits and sort them by importance:");
        listInfo.setFont(new Font("Segoe UI Semilight",  Font.PLAIN, 18));
        infoPanel.add(listInfo);
        secondPage.add(infoPanel, BorderLayout.NORTH);
        // drag and drop
        dragPanel = new DragAndDropList();
        secondPage.add(dragPanel, BorderLayout.CENTER);

        // buttons
        backButton = new JButton("Back");
        backButton.setActionCommand("back");
        backButton.setPreferredSize(new Dimension(90, 30));
        backButton.addActionListener(this);
        buttonPanel2 = new JPanel();
        buttonPanel2.add(backButton);
        searchButton = new JButton("Search");
        searchButton.setActionCommand("search");
        searchButton.setPreferredSize(new Dimension(90, 30));
        searchButton.addActionListener(this);
        buttonPanel2.add(searchButton);
        secondPage.add(buttonPanel2, BorderLayout.SOUTH);

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));
        layeredPane.add(secondPage, new Integer(0));

        loadingPanel = new JPanel();
        loadingPanel.setLayout(new BorderLayout());
        ImageIcon loading = new ImageIcon("res/loadgif.gif");
        loadingGif = new JLabel(loading);
        loadingPanel.add(loadingGif);
        loadingPanel.setVisible(false);
        layeredPane.add(loadingPanel, new Integer(1));
        add(layeredPane);
        // end panel2

        // panel3
        thirdPage = new JPanel();
        thirdPage.setLayout(listLayout);
        add(thirdPage);
        // end panel3

        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("forward")) {
            try {
                CarData.setMinPrice(Integer.parseInt(minPrice.getText()));
                CarData.setMaxPrice(Integer.parseInt(maxPrice.getText()));
                if (CarData.maxPrice == 0 & CarData.minPrice == 0){
                    CarData.maxPrice = 15000000;
                }
                else if (CarData.maxPrice < 1550 | CarData.minPrice > 15000000){
                    throw new MaxPriceException();
                }


                CarData.practicality = (String) practicalityCombo.getSelectedItem();
                boolean fuelFlag = false;
                boolean gearFlag = false;
                if (CarData.fuel.size() == 0){
                    //hrow new FuelException();
                    CarData.fuel.add("Petrol");
                    CarData.fuel.add("Hybrid");
                    CarData.fuel.add("Electric");
                    CarData.fuel.add("Diesel");
                    fuelFlag = true;
                }
                if (CarData.gearBox.size() == 0){
                    //throw new GearBoxException();
                    CarData.gearBox.add("CVT");
                    CarData.gearBox.add("Automatic");
                    CarData.gearBox.add("Manual");
                    gearFlag = true;
                }
                CarData.filterData();
                if (CarData.filteredCars.isEmpty()){
                    throw new EmptyDataException();
                }
                if (fuelFlag) {
                    CarData.fuel.removeAll(CarData.fuel);
                }
                if (gearFlag) {
                    CarData.gearBox.removeAll(CarData.gearBox);
                }
                mainLayout.next(this.getContentPane());
                errorlabel.setText("");
            }
            catch (NumberFormatException p) {

                errorlabel.setText("Please enter a correct price!");

            }
            catch (MaxPriceException p){
                errorlabel.setText("There are no cars at such a price!");
            }
//            catch (FuelException p){
//                errorlabel.setText("You have to select some type of fuel!");
//            }
//            catch (GearBoxException p){
//                errorlabel.setText("You have to select some type of gearbox!");
//            }
            catch (EmptyDataException p){
                errorlabel.setText("There are no such cars!");
            }


        }

        if (e.getActionCommand().equals("back")) {
            mainLayout.previous(this.getContentPane());
        }
        if (e.getActionCommand().equals("search")) {
            loadingPanel.setVisible(true);
            Thread listingCars = new Thread(new Runnable() {
                @Override
                public void run() {
                    thirdPage.removeAll();
                    CarData.traits = new ArrayList<>(DragAndDropList.dndList.getSelectedValuesList());
                    CarData.findCars();
                    CarList.frame = AppGui.this;
                    CarList carsList = new CarList();
                    thirdPage.add(carsList);
                    mainLayout.next(AppGui.this.getContentPane());
                    loadingPanel.setVisible(false);
                }
            });
            listingCars.start();

        }
        if (e.getSource().equals(petrolCheck)){
            if (petrolCheck.isSelected()){
                CarData.fuel.add(petrolCheck.getText());
            }else {
                CarData.fuel.remove(petrolCheck.getText());
            }

        }
        if (e.getSource().equals(hybridCheck)){
            if (hybridCheck.isSelected()){
                CarData.fuel.add(hybridCheck.getText());
            }else {
                CarData.fuel.remove(hybridCheck.getText());
            }

        }
        if (e.getSource().equals(dieselCheck)){
            if (dieselCheck.isSelected()){
                CarData.fuel.add(dieselCheck.getText());
            }else {
                CarData.fuel.remove(dieselCheck.getText());
            }

        }
        if (e.getSource().equals(electricCheck)){
            if (electricCheck.isSelected()){
                CarData.fuel.add(electricCheck.getText());
            }else {
                CarData.fuel.remove(electricCheck.getText());
            }

        }
        if (e.getSource().equals(cvtCheck)){
            if (cvtCheck.isSelected()){
                CarData.gearBox.add(cvtCheck.getText());
            }else {
                CarData.gearBox.remove(cvtCheck.getText());
            }
        }
        if (e.getSource().equals(automaticCheck)){
            if (automaticCheck.isSelected()){
                CarData.gearBox.add(automaticCheck.getText());
            }else {
                CarData.gearBox.remove(automaticCheck.getText());
            }
        }
        if (e.getSource().equals(manulaCheck)){
            if (manulaCheck.isSelected()){
                CarData.gearBox.add(manulaCheck.getText());
            }else {
                CarData.gearBox.remove(manulaCheck.getText());
            }
        }


    }

    public void showGui() {
        this.setVisible(true);
    }

    public static void main(String[] args) {
        CarData.loadData();
        FlatLightLaf.setup();
        AppGui app = new AppGui();
        app.showGui();

//        String fonts[] =
//                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
//        for (int i = 0; i < fonts.length; i++) {
//            System.out.println(fonts[i]);
//        }

    }
}