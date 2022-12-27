import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class Car {
    // pytanie czy dane do wyświetlania podawać jako argumenty do metod czy tworzyć za każdym razem nowe instancje samochodów
    // i ustawiać wartości określonych pól?

    Random random = new Random();
    String name;
    int price;

    public Car(int i) {
        name = "Special car no. " + i;
        price = random.nextInt(200000)+50000;
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
        photoPanel.setPreferredSize(new Dimension(260, 140));
        URL url = new URL("https://www.ccarprice.com/products/Honda_Ridgeline_Black_Edition_2023.jpg");
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
