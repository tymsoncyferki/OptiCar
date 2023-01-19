import org.imgscalr.Scalr;
import tech.tablesaw.api.Table;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    String torque;
    String power;
    String gearBox;
    String driveType;
    String cityConsumption;
    String highwayConsumption;


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
        torque = carRow.get(0,"Torque_lb_ft");
        power = carRow.get(0,"Power");
        gearBox = carRow.get(0,"GearBox");
        driveType = carRow.get(0,"Drivetrain");
        cityConsumption = carRow.get(0,"Consumption_city");
        highwayConsumption = carRow.get(0,"Consumption_highway");
    }

    public JPanel carInfo() throws IOException {
        //todo
        // zmienić, żeby się ładnie wyświetlało

        mainPanel.setPreferredSize(new Dimension(400, 150));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        mainPanel.setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel();
        JLabel carName = new JLabel("Model: " + brand+ " " + model);
        infoPanel.add(carName);
        JLabel carPrice = new JLabel("Price: " + price);
        carPrice.setPreferredSize(new Dimension(200, 20));
        infoPanel.add(carPrice);


        JPanel photoPanel = new JPanel();
        photoPanel.setSize(new Dimension(260, 140));
        URL url = new URL(photo);
        BufferedImage originalImage = ImageIO.read(url);
        ImageIcon icon =  new ImageIcon(Scalr.resize(originalImage, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, 260, 140, Scalr.OP_ANTIALIAS));
        JLabel carPhoto = new JLabel(icon);
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

        JFrame carInfoDetailed  = new JFrame();
        carInfoDetailed.setMinimumSize(new Dimension(1000, 600));
        carInfoDetailed.setLocation(0, 0);

        String dataBasic[][]={
                {"Model", brand + " " + model},
                {"Price", price + "$"},
                {"Body Type", body}};
        String columnBasic[]={"Basic info", ""};
        JTable tableBasic = new JTable(dataBasic,columnBasic);


        String dataEngine[][]={
                {"Engine type", engineType},
                {"Engine Power", power + " horse power"},
                {"Torque", torque + " pound-foot"},
                {"Fuel Type", fuelType}};
        String columnEngine[]={"Engine", ""};
        JTable tableEngine = new JTable(dataEngine,columnEngine);

        String dataTransmission[][]={
                {"Gear box", gearBox},
                {"Drive type", driveType}};
        String columnTransmission[]={"Engine", ""};
        JTable tableTransmission = new JTable(dataTransmission,columnTransmission);

        String dataConsuption[][]={
                {"Mileage in city", cityConsumption + " miles per galon"},
                {"Mileage on highway", highwayConsumption + " miles per galon"}};
        String columnConsuption[]={"Mileage", ""};
        JTable tableConsuption = new JTable(dataConsuption,columnConsuption);


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




        return carInfoDetailed;

    }

}
