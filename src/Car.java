import org.imgscalr.Scalr;
import tech.tablesaw.api.Table;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

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
    String height;
    String weight;
    String length;
    String width;
    String doors;
    String seats;

    public Car(Table carRow) {
        mainPanel = new JPanel();
        carInfoDetailed = new JFrame();
        photo = carRow.get(0, "Photo");
        brand = carRow.get(0, "Brand");
        model = carRow.get(0, "Model");
        body = carRow.get(0, "Body");
        price = (int) Double.parseDouble(carRow.get(0, "Pricing"));
        engineType = carRow.get(0,"Engine");
        fuelType = carRow.get(0,"Fuel");
        torque = carRow.get(0,"Torque_lb_ft");
        power = carRow.get(0,"Power");
        gearBox = carRow.get(0,"GearBox");
        driveType = carRow.get(0,"Drivetrain");
        cityConsumption = carRow.get(0,"Consumption_city");
        highwayConsumption = carRow.get(0,"Consumption_highway");
        height = carRow.get(0,"Height");
        weight = carRow.get(0,"Weight_lbs");
        length = carRow.get(0,"Length");
        width = carRow.get(0,"Width");
        doors = carRow.get(0,"Doors");
        seats = carRow.get(0,"Seats");
    }

    public JPanel carInfo() throws IOException {
        //todo
        // zmienić, żeby się ładnie wyświetlało
        Color myColor = Color.decode("#55acee");
        mainPanel.setPreferredSize(new Dimension(400, 150));
        mainPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
        mainPanel.setLayout(new BorderLayout());
        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    carInfoDetailed = CarInfoDetailed();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                carInfoDetailed.setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                mainPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, myColor));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                mainPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
            }
        });
        mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
        infoPanel.setPreferredSize(new Dimension(400, 150));
        JLabel carName = new JLabel(brand + " " + model);
        carName.setFont(new Font("Segoe UI Semibold",  Font.PLAIN, 20));
        infoPanel.add(carName, Component.LEFT_ALIGNMENT);
        JLabel carInfo = new JLabel(engineType + " | " + fuelType);
        infoPanel.add(carInfo, Component.LEFT_ALIGNMENT);
        //String sPrice = insertSpaces(String.valueOf(price));
        JLabel carPrice = new JLabel(price + "$");
        carPrice.setForeground(myColor);
        carPrice.setFont(new Font("Segoe UI Semibold",  Font.PLAIN, 20));
        infoPanel.add(carPrice, Component.LEFT_ALIGNMENT);


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

        JLabel searchWeb = new JLabel("Search web");
        searchWeb.setBorder(new EmptyBorder(10, 10, 10, 10));
        Font searchFont = searchWeb.getFont();
        searchWeb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchWeb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String string = "https://www.google.com/search?q=" + brand + " " + model;
                String uri = string.replaceAll(" ", "+").toLowerCase();
                openWebpage(URI.create(uri));

            }
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                Map attributes = searchFont.getAttributes();
                attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                searchWeb.setFont(searchFont.deriveFont(attributes));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                searchWeb.setFont(searchFont);
            }
        });

//        JButton moreButton = new JButton("Search web");
//        moreButton.setActionCommand("more");
//        moreButton.addActionListener(this);
//        moreButton.setMargin(new Insets(0, 0, 0, 0));
//        moreButton.setContentAreaFilled(false);
//        moreButton.setOpaque(false);
//        moreButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//        moreButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        JPanel morePanel = new JPanel();
        morePanel.setLayout(new BoxLayout(morePanel, BoxLayout.PAGE_AXIS));
        morePanel.add(Box.createRigidArea(new Dimension(5,0)), Component.RIGHT_ALIGNMENT);
        morePanel.add(searchWeb, Component.RIGHT_ALIGNMENT);
        mainPanel.add(morePanel, BorderLayout.EAST);
        mainPanel.add(infoPanel, BorderLayout.CENTER);


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
            }
        };
        tableBasic.getTableHeader().setReorderingAllowed(false);
        tableBasic.getTableHeader().setResizingAllowed(false);

        if (torque.length() > 0) {
            torque = String.valueOf((int) (Double.parseDouble(torque) * 1.356));
        }
        if (power.length() > 0) {
            power = String.valueOf((int) Double.parseDouble(power));
        }

        String dataEngine[][]={
                {"Engine type", engineType},
                {"Engine Power", power + " HP"},
                {"Torque", torque + " Nm"},
                {"Fuel Type", fuelType}};
        String columnEngine[]={"Engine", ""};
        JTable tableEngine = new JTable(dataEngine,columnEngine) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableEngine.getTableHeader().setReorderingAllowed(false);
        tableEngine.getTableHeader().setResizingAllowed(false);

        String dataTransmission[][]={
                {"Gear box", gearBox},
                {"Drive type", driveType.substring(0, 1).toUpperCase() + driveType.substring(1)}};
        String columnTransmission[]={"Transmission", ""};
        JTable tableTransmission = new JTable(dataTransmission,columnTransmission) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableTransmission.getTableHeader().setReorderingAllowed(false);
        tableTransmission.getTableHeader().setResizingAllowed(false);

        String dataConsuption[][]={
                {"Mileage in city", cityConsumption + " miles per gallon"},
                {"Mileage on highway", highwayConsumption + " miles per gallon"}};
        String columnConsuption[]={"Mileage", ""};
        JTable tableConsuption = new JTable(dataConsuption,columnConsuption) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableConsuption.getTableHeader().setReorderingAllowed(false);
        tableConsuption.getTableHeader().setResizingAllowed(false);

        if (doors.length() > 0) {
            doors = String.valueOf((int) Double.parseDouble(doors));
        }
        if (seats.length() > 0) {
            seats = String.valueOf((int) Double.parseDouble(seats));
        }

        String dataPhysical [][]={
                {"Number of doors", doors},
                {"Number of seats", seats},
                {"Weight", weight + " pounds"},
                {"Height", height + " inches"},
                {"Length", length + " inches"},
                {"Width", width + " inches"}};
        String columnPhysical[]={"Physical parameters", ""};
        JTable tablePhysical = new JTable(dataPhysical,columnPhysical) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablePhysical.getTableHeader().setReorderingAllowed(false);
        tablePhysical.getTableHeader().setResizingAllowed(false);


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

    public static boolean openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean openWebpage(URL url) {
        try {
            return openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String insertSpaces(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = s.length() - 1; i >= 0; i -= 3) {
            if (i >= 2) {
                sb.append(s.substring(i-2, i+1));
                sb.append(" ");
            } else {
                sb.append(s.substring(0, i + 1));
            }
        }
        return sb.reverse().toString();
    }

}
