import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// nasze główne okno, będą w nim zaimplementowane strony do filtrowania i ustalania cech
public class AppGui extends JFrame implements ActionListener {

    static CardLayout cardLayout = new CardLayout();
    JPanel firstPage, secondPage;
    // firstPage
    JPanel dataPanel, buttonPanel1;
    JTextField minPrice, maxPrice;
    JButton forwardButton;
    JLabel price;
    // secondPage
    JPanel buttonPanel2, dragPanel;
    JButton backButton, searchButton;

    public AppGui() {
        super("findcar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1200, 900));
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocation(0, 0);
        setLayout(cardLayout);

        // panel 1
        firstPage = new JPanel();
        firstPage.setPreferredSize(new Dimension(1600, 900));
        firstPage.setLayout(new BorderLayout());

        // collecting data
        // tutaj zostanie wrzucony panel ze wszystkimi komponentami zbierającymi dane
        dataPanel = new JPanel();
        minPrice = new JTextField("0");
        minPrice.setPreferredSize(new Dimension(90, 30));
        maxPrice = new JTextField("0");
        maxPrice.setPreferredSize(new Dimension(90, 30));
        price = new JLabel("Enter price");
        dataPanel.add(price);
        dataPanel.add(minPrice);
        dataPanel.add(maxPrice);
        firstPage.add(dataPanel, BorderLayout.NORTH);

        // buttons
        forwardButton = new JButton("Next");
        forwardButton.setActionCommand("forward");
        forwardButton.setPreferredSize(new Dimension(90, 30));
        forwardButton.addActionListener(this);
        buttonPanel1 = new JPanel();
        buttonPanel1.add(forwardButton);
        firstPage.add(buttonPanel1, BorderLayout.SOUTH);

        add(firstPage);
        // end panel 1

        // panel 2
        secondPage = new JPanel();
        secondPage.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
        secondPage.setLayout(new BorderLayout());

        // information
//        traitPanel = new TraitTest();
//        traitPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 50, 100));
//        secondPage.add(traitPanel, BorderLayout.NORTH);

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

        add(secondPage);
        // end panel2

        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("forward")) {
            // zczytuje dane które podał użytkownik i ustawia w odpowiednich polach klasy dane
            CarData.setMinPrice(Integer.parseInt(minPrice.getText()));
            CarData.setMaxPrice(Integer.parseInt(maxPrice.getText()));
            CarData.filterData();
            cardLayout.next(this.getContentPane());
        }

        if (e.getActionCommand().equals("back")) {
            cardLayout.previous(this.getContentPane());
        }
        if (e.getActionCommand().equals("search")) {
            CarData.traits = new ArrayList<>(DragAndDropList.dndList.getSelectedValuesList());
            CarData.findCars();
            CarList carsList = new CarList(this);
            this.add(carsList);
            cardLayout.next(this.getContentPane());
        }
    }

    public void showGui() {
        this.setVisible(true);
    }

    public static void main(String[] args) {
        FlatLightLaf.setup();
        AppGui app = new AppGui();
        app.showGui();

    }
}