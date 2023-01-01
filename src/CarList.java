import tech.tablesaw.api.Table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CarList extends JPanel implements ActionListener {

    static int listCount = 0;
    static int listNumber = 0;
    static AppGui frame;
    JPanel listPanel, buttonPanel;
    JButton backButton, forwardButton, menuButton;
    static Table Cars;

    public CarList() {

        setLayout(new BorderLayout());

        listPanel = new JPanel();
        listPanel.setPreferredSize(new Dimension(400, 3000));
        GridLayout gridLayout = new GridLayout(0,1);
        gridLayout.setVgap(3);
        listPanel.setLayout(gridLayout);
        for (int j = listCount * 20; j < listCount * 20 + 20; j++) {
            Car car = new Car(Cars.rows(j));
            try {
                listPanel.add(car.carInfo());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        this.add(scrollPane);

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

        listCount++;
        listNumber++;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("back")) {
            if (listNumber == 1) {
                AppGui.mainLayout.first(frame.getContentPane());
            } else {
                AppGui.listLayout.previous(frame.thirdPage);
                listNumber -= 1;
            }
            //System.out.println("count: " + listCount + ", number: " + listNumber);
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
