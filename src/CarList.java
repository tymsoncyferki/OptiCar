import tech.tablesaw.api.Table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class CarList extends JPanel implements ActionListener {

    static int listCount = 0;
    static int listNumber = 0;
    static AppGui frame;
    static Table Cars;
    JPanel listPanel, buttonPanel;
    JButton backButton, forwardButton, menuButton;
    JPanel loadingPanel;
    static final ImageIcon loading = new ImageIcon("loadgif.gif");

    public CarList() {

        setLayout(new BorderLayout());

        listPanel = new JPanel();
        listPanel.setPreferredSize(new Dimension(400, 3000));
        GridLayout gridLayout = new GridLayout(0,1);
        gridLayout.setVgap(3);
        listPanel.setLayout(gridLayout);
        for (int j = listCount * 20; j < listCount * 20 + 20; j++) {
            //todo
            // jak j jest większe niż liczba wierszy to już nic nie wyświetlać i usunąć przycisk next
            Car car = new Car(Cars.rows(j));
            try {
                listPanel.add(car.carInfo());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));
        layeredPane.add(scrollPane, new Integer(0));

        // loading
        loadingPanel = new JPanel();
        loadingPanel.setLayout(new BorderLayout());
        JLabel loadingGif = new JLabel(loading);
        loadingPanel.add(loadingGif);
        loadingPanel.setVisible(false);
        layeredPane.add(loadingPanel, new Integer(1));

        add(layeredPane);


        // buttons
        buttonPanel = new JPanel();
        menuButton = new JButton("Menu");
        menuButton.setActionCommand("menu");
        menuButton.setPreferredSize(new Dimension(90, 30));
        menuButton.addActionListener(this);
        buttonPanel.add(menuButton);
        backButton = new JButton("Back");
        backButton.setActionCommand("back");
        backButton.setPreferredSize(new Dimension(90, 30));
        backButton.addActionListener(this);
        buttonPanel.add(backButton);
        forwardButton = new JButton("Next");
        forwardButton.setActionCommand("next");
        forwardButton.setPreferredSize(new Dimension(90, 30));
        forwardButton.addActionListener(this);
        buttonPanel.add(forwardButton);
        add(buttonPanel, BorderLayout.SOUTH);

        listCount++;
        listNumber++;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("back")) {
            if (listNumber == 1) {
                frame.mainLayout.first(frame.getContentPane());
            } else {
                frame.listLayout.previous(frame.thirdPage);
                listNumber -= 1;
            }
            //System.out.println("count: " + listCount + ", number: " + listNumber);
        }
        if (e.getActionCommand().equals("next")) {
            if (listNumber < listCount) {
                frame.listLayout.next(frame.thirdPage);
                listNumber += 1;
            } else {
                loadingPanel.setVisible(true);
                new Thread(() -> {
                    CarList carsList = new CarList();
                    frame.thirdPage.add(carsList);
                    frame.listLayout.next(frame.thirdPage);
                    loadingPanel.setVisible(false);
                }).start();
            }
        }
        if (e.getActionCommand().equals("menu")) {
            frame.mainLayout.first(frame.getContentPane());
        }
    }
}
