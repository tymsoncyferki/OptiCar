import tech.tablesaw.api.Table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CarList extends JPanel implements ActionListener {
    // panel z listą kilku samochodów i przyciskami

    static int listCount;
    static int listNumber = 0;
    static AppGui frame;
    JPanel listPanel, buttonPanel;
    JButton backButton, forwardButton, menuButton;
    static Table Cars;

    public CarList() {

        setLayout(new BorderLayout());

        listPanel = new JPanel();
        listPanel.setPreferredSize(new Dimension(400, 800));
        GridLayout gridLayout = new GridLayout(0,1);
        gridLayout.setVgap(3);
        listPanel.setLayout(gridLayout);
        // każda kolejna instancja klasy będzie brać kolejne 10 (lub inną liczbę) samochodów, w tym celu pole statyczne 1
        for (int j = listCount *5; j < listCount *5+5; j++) {
            //Table carRow = Cars.rows(j);
            Car car = new Car(Cars.rows(j));
            try {
                listPanel.add(car.carInfo());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.add(listPanel, BorderLayout.NORTH);
        listCount++;
        listNumber++;

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
        this.add(buttonPanel, BorderLayout.SOUTH);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("back")) {
            if (listNumber == 1) {

                AppGui.mainLayout.first(frame.getContentPane());
                //AppGui.mainLayout.next(frame.getContentPane());
            } else {
                AppGui.listLayout.previous(frame.thirdPage);
                listNumber -= 1;
            }
        }
        if (e.getActionCommand().equals("next")) {
            if (listNumber < listCount) {
                AppGui.listLayout.next(frame.thirdPage);
                listNumber += 1;
            } else {
                CarList carsList = new CarList();
                frame.thirdPage.add(carsList);
                AppGui.listLayout.next(frame.thirdPage);
            }
        }
        if (e.getActionCommand().equals("menu")) {
            AppGui.mainLayout.first(frame.getContentPane());
        }
    }
}
