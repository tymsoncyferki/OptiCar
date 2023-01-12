import org.imgscalr.Scalr;
import tech.tablesaw.api.Table;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class Car {

    String name;
    int price;
    String photo;
    String engineInfo;
    int power;
    String fuelType;
    String body;
    //todo
    // i wszystkie inne potrzebne...

    public Car(Table carRow) {
        name = carRow.get(0, "Model");
        price = (int) Double.parseDouble(carRow.get(0, "Pricing"));
        photo = carRow.get(0, "Photo");
    }

    public JPanel carInfo() throws IOException {
        //todo
        // zmienić, żeby się ładnie wyświetlało
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

    //todo
    // po kliknięciu odpowiedniego przycisku z carInfo wyświetli się panel z szczegółowymi danymi który będzie można
    // zamknąc "iksem"
    public JPanel carInfoDetailed() {
        return null;
    }

}
