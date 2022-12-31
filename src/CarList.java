import tech.tablesaw.api.Table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CarList extends JPanel implements ActionListener {
    // panel z listą kilku samochodów i przyciskami

    static int i = 0;
    static AppGui appFrame;
    JPanel listPanel, buttonPanel;
    JButton backButton, forwardButton, menuButton;
    static Table Cars;

    public CarList(AppGui frame) {
        if (i == 0) {
            CarList.appFrame = frame;
        }
        setLayout(new BorderLayout());

        listPanel = new JPanel();
        listPanel.setPreferredSize(new Dimension(400, 800));
        GridLayout gridLayout = new GridLayout(0,1);
        gridLayout.setVgap(3);
        listPanel.setLayout(gridLayout);
        // każda kolejna instancja klasy będzie brać kolejne 10 (lub inną liczbę) samochodów, w tym celu pole statyczne 1
        for (int j = i*5 + 1; j <= i*5+5; j++) {
            Car car = new Car(j); // zamiast j będzie j-oty wiersz ramki
            try {
                listPanel.add(car.carInfo());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.add(listPanel, BorderLayout.NORTH);
        i++;

        // buttons
        buttonPanel = new JPanel();
        menuButton = new JButton("Main Menu");
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
        this.add(buttonPanel, BorderLayout.SOUTH);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("back")) {
            AppGui.cardLayout.previous(appFrame.getContentPane());
        }
        if (e.getActionCommand().equals("next")) {
            CarList carsList = new CarList(appFrame);
            appFrame.add(carsList);
            AppGui.cardLayout.next(appFrame.getContentPane());
        }
        if (e.getActionCommand().equals("menu")) {
            AppGui.cardLayout.first(appFrame.getContentPane());
        }
    }
}
