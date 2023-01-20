import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;


public class DragAndDropList extends JPanel {

    static JList<String> dndList;
    static DefaultListModel<String> traitsList;
    String[] traits = new String[] { "Dynamics", "Practicality", "Safety", "Sport character", "Equipment", "Efficiency", "Price", "Off-road capabilities"};

    public DragAndDropList() {

        traitsList = new DefaultListModel<>();
        for (String trait : traits) {
            traitsList.addElement(trait);
        }
        dndList = new JList<>(traitsList);
        dndList.setOpaque(false);
        dndList.setCellRenderer(new buttonCellRenderer());
        DnDAdapter mouseAdapter = new DnDAdapter();
        dndList.addMouseListener(mouseAdapter);
        dndList.addMouseMotionListener(mouseAdapter);
        ListSelectionModel noSelection = new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {}
        };
        dndList.setSelectionModel(noSelection);
        this.add(dndList);
    }

    private static class DnDAdapter extends MouseInputAdapter {

        private boolean mouseDragging = false;
        private int dragSourceIndex;
        private int sourceIndex;
        private boolean wasDragged;

        @Override
        public void mousePressed(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                dragSourceIndex = dndList.locationToIndex(e.getPoint());
                sourceIndex = dndList.locationToIndex(e.getPoint());
                mouseDragging = true;
                wasDragged = false;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mouseDragging = false;
            int endIndex = dndList.locationToIndex(e.getPoint());
            if (endIndex == sourceIndex & !wasDragged) {
                if (dndList.isSelectedIndex(sourceIndex)) {
                    dndList.removeSelectionInterval(sourceIndex, sourceIndex);
                } else {
                    dndList.addSelectionInterval(sourceIndex, sourceIndex);
                }
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (mouseDragging) {
                int currentIndex = dndList.locationToIndex(e.getPoint());
                if (currentIndex != dragSourceIndex) {
                    wasDragged = true;
                    String dragElement = traitsList.get(dragSourceIndex);
                    boolean isSrc = dndList.isSelectedIndex(dragSourceIndex);
                    boolean isCur = dndList.isSelectedIndex(currentIndex);
                    traitsList.remove(dragSourceIndex);
                    traitsList.add(currentIndex, dragElement);
                    if (isSrc) {
                        dndList.addSelectionInterval(currentIndex, currentIndex);
                    } else {
                        dndList.removeSelectionInterval(currentIndex, currentIndex);
                    }
                    if (isCur) {
                        dndList.addSelectionInterval(dragSourceIndex, dragSourceIndex);
                    } else {
                        dndList.removeSelectionInterval(dragSourceIndex, dragSourceIndex);
                    }
                    dragSourceIndex = currentIndex;
                }
            }
        }
    }

    public static class buttonCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JToggleButton button = new JToggleButton(value.toString(), isSelected);
            button.setFont(new Font("Segoe UI Semilight",  Font.PLAIN, 20));
            button.setSize(new Dimension(100, 30));
            return button;
        }
    }

}