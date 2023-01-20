import org.imgscalr.Scalr;
import tech.tablesaw.api.Table;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Car implements ActionListener{
    JPanel mainPanel;
    JFrame carInfoDetailed;
    String photo;
    String brand;
    String model;
    String body;
    int price;
    String engineType;
    String fuelType;
    int torque;
    int power;
    String gearBox;
    String driveType;
    String cityConsumption;
    String highwayConsumption;
    String height;
    String weight;
    String length;
    String width;
    int doors;
    int seats;

    public Car(Table carRow) {
        mainPanel = new JPanel();
        carInfoDetailed = new JFrame();
        photo = carRow.get(0, "Photo");
        brand = carRow.get(0, "Brand");
        model = carRow.get(0, "Model");
        body =carRow.get(0, "Body");
        price = (int) Double.parseDouble(carRow.get(0, "Pricing"));
        engineType = carRow.get(0,"Engine");
        fuelType = carRow.get(0,"Fuel");
        torque = (int) Double.parseDouble(carRow.get(0,"Torque_lb_ft"));
        power = (int) Double.parseDouble(carRow.get(0,"Power"));
        gearBox = carRow.get(0,"GearBox");
        driveType = carRow.get(0,"Drivetrain");
        cityConsumption = carRow.get(0,"Consumption_city");
        highwayConsumption = carRow.get(0,"Consumption_highway");
        height = carRow.get(0,"Height");
        weight = carRow.get(0,"Weight_lbs");
        length = carRow.get(0,"Length");
        width = carRow.get(0,"Width");
        doors = (int) Double.parseDouble(carRow.get(0,"Doors"));
        seats = (int) Double.parseDouble(carRow.get(0,"Seats"));
    }

    public JPanel carInfo() throws IOException {
        //todo
        // zmienić, żeby się ładnie wyświetlało

        mainPanel.setPreferredSize(new Dimension(400, 150));
        mainPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
        mainPanel.setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel();
        JLabel carName = new JLabel("Model: " + brand+ " " + model);
        infoPanel.add(carName);
        JLabel carPrice = new JLabel("Price: " + price + "$");
        carPrice.setPreferredSize(new Dimension(200, 20));
        infoPanel.add(carPrice);


        JPanel photoPanel = new JPanel();
        photoPanel.setSize(new Dimension(260, 140));
        URL url = new URL(photo);
        BufferedImage originalImage = ImageIO.read(url);
        originalImage = Scalr.resize(originalImage, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, 260, 140, Scalr.OP_ANTIALIAS);
        if (originalImage.getHeight() > 140) {
            int h = (originalImage.getHeight() - 140) / 2;
            originalImage = originalImage.getSubimage(0, h, 260, 140);
        }
        BufferedImage proccessedImage = makeRoundedCorner(originalImage, 10);
        ImageIcon icon = new ImageIcon(proccessedImage);
        JLabel carPhoto = new JLabel(icon);
        //carPhoto.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        photoPanel.add(carPhoto);
        mainPanel.add(photoPanel, BorderLayout.WEST);
        mainPanel.setMaximumSize(new Dimension(3000,165));


        JButton moreButton = new JButton("More info");
        moreButton.setActionCommand("more");
        moreButton.addActionListener(this);
        infoPanel.add(moreButton);
        mainPanel.add(infoPanel, BorderLayout.EAST);


        return mainPanel;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("more")) {
            try {
                carInfoDetailed = CarInfoDetailed();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            carInfoDetailed.setVisible(true);
        }
    }


    public JFrame CarInfoDetailed() throws IOException{

        JFrame carInfoDetailed  = new JFrame("Technical specifications");
        carInfoDetailed.setMinimumSize(new Dimension(1000, 500));
        carInfoDetailed.setResizable(false);
        carInfoDetailed.setLocation(0, 30);

        String dataBasic[][]={
                {"Model", brand + " " + model},
                {"Price", price + " $"},
                {"Body Type", body}};
        String columnBasic[]={"Basic info", ""};
        JTable tableBasic = new JTable(dataBasic,columnBasic)  {
            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };;


        String dataEngine[][]={
                {"Engine type", engineType},
                {"Engine Power", power + " HP"},
                {"Torque", (int) (torque * 1.356) + " Nm"},
                {"Fuel Type", fuelType}};
        String columnEngine[]={"Engine", ""};
        JTable tableEngine = new JTable(dataEngine,columnEngine) {
            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };

        String dataTransmission[][]={
                {"Gear box", gearBox},
                {"Drive type", driveType.substring(0, 1).toUpperCase() + driveType.substring(1)}};
        String columnTransmission[]={"Transmission", ""};
        JTable tableTransmission = new JTable(dataTransmission,columnTransmission) {
            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };;

        String dataConsuption[][]={
                {"Mileage in city", cityConsumption + " miles per gallon"},
                {"Mileage on highway", highwayConsumption + " miles per gallon"}};
        String columnConsuption[]={"Mileage", ""};
        JTable tableConsuption = new JTable(dataConsuption,columnConsuption) {
            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };;

        String dataPhysical [][]={
                {"Number of doors", String.valueOf(doors)},
                {"Number of seats", String.valueOf(seats)},
                {"Weight", weight + " pounds"},
                {"Height", height + " inch"},
                {"Length", length + " inch"},
                {"Width", width + " inch"}};
        String columnPhysical[]={"Physical parameters", ""};
        JTable tablePhysical = new JTable(dataPhysical,columnPhysical) {
            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };;


        Container c = carInfoDetailed.getContentPane();
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
        c.add(tableBasic.getTableHeader());
        c.add(tableBasic);
        c.add(tableEngine.getTableHeader());
        c.add(tableEngine);
        c.add(tableTransmission.getTableHeader());
        c.add(tableTransmission);
        c.add(tableConsuption.getTableHeader());
        c.add(tableConsuption);
        c.add(tablePhysical.getTableHeader());
        c.add(tablePhysical);




        return carInfoDetailed;

    }

    public static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = output.createGraphics();

        // This is what we want, but it only does hard-clipping, i.e. aliasing
        // g2.setClip(new RoundRectangle2D ...)

        // so instead fake soft-clipping by first drawing the desired clip shape
        // in fully opaque white with antialiasing enabled...
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));

        // ... then compositing the image on top,
        // using the white shape from above as alpha source
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);

        g2.dispose();

        return output;
    }

}
