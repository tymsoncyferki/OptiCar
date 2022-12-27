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
        name = "Optymalny samochód" + i;
        price = random.nextInt(200000)+50000;
    }

    public JPanel carInfo() throws IOException {
        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(400, 100));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        mainPanel.setLayout(new BorderLayout());

        JLabel carName = new JLabel("Model: " + name);
        mainPanel.add(carName, BorderLayout.NORTH);
        JLabel carPrice = new JLabel("Price: " + price);
        carPrice.setPreferredSize(new Dimension(200, 20));
        mainPanel.add(carPrice, BorderLayout.SOUTH);

        URL url = new URL("https://www.ccarprice.com/products/Honda_Ridgeline_Black_Edition_2023.jpg");
        BufferedImage c = ImageIO.read(url);
        ImageIcon image = new ImageIcon(c);
        JLabel carPhoto = new JLabel(image);
        mainPanel.add(carPhoto, BorderLayout.WEST);
        return mainPanel;
    }

    public JPanel carInfoDetailed() {
        return null;
    }

}
