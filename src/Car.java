import org.imgscalr.Scalr;
import tech.tablesaw.api.Table;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class Car {

    Random random = new Random();
    String name;
    int price;
    String photo;
    String engineInfo;
    int power;
    String fuelType;
    String body;

    public Car(Table carRow) {
        name = carRow.get(0, "Model");
        price = (int) Double.parseDouble(carRow.get(0, "Pricing"));
        photo = carRow.get(0, "Photo");
    }

    public JPanel carInfo() throws IOException {
        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(400, 150));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        mainPanel.setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel();
        JLabel carName = new JLabel("Model: " + name);
        infoPanel.add(carName);
        JLabel carPrice = new JLabel("Price: " + price);
        carPrice.setPreferredSize(new Dimension(200, 20));
        infoPanel.add(carPrice);
        mainPanel.add(infoPanel, BorderLayout.EAST);

        JPanel photoPanel = new JPanel();
        photoPanel.setSize(new Dimension(260, 140));
        URL url = new URL(photo);
        BufferedImage originalImage = ImageIO.read(url);
        ImageIcon icon =  new ImageIcon(Scalr.resize(originalImage, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, 260, 140, Scalr.OP_ANTIALIAS));
        JLabel carPhoto = new JLabel(icon);
        photoPanel.add(carPhoto);
        mainPanel.add(photoPanel, BorderLayout.WEST);

        return mainPanel;
    }


    public JPanel carInfoDetailed() {
        return null;
    }

}
