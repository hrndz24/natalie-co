package natalie;

import java.util.ArrayList;

public class AutoPanel {

    private String manufacturer;
    private String model;
    private String year;
    private ArrayList<Automobile> automobiles = new ArrayList<>();
    private ArrayList<String> colors = new ArrayList<>();
    private ArrayList<String> vins = new ArrayList<>();

    private void addAutomobile(Automobile automobile) {
        automobiles.add(automobile);
        if (automobiles.size() == 1) {
            this.manufacturer = automobiles.get(0).getManufacturer();
            this.model = automobiles.get(0).getModel();
            this.year = automobiles.get(0).getYear();
            this.colors.add(automobiles.get(0).getColor().toUpperCase());
            this.vins.add(automobiles.get(0).getVin().toUpperCase());
        } else {
            this.colors.add(automobiles.get(automobiles.size() - 1).getColor().toUpperCase());
            this.vins.add(automobiles.get(automobiles.size() - 1).getVin().toUpperCase());
        }
    }

    // fills an autopanel until new model is met
    public static ArrayList<AutoPanel> makePanels(ArrayList<Automobile> automobiles) {
        ArrayList<AutoPanel> autoPanels = new ArrayList<>();
        String current = automobiles.get(0).getManufacturer() + automobiles.get(0).getModel();
        AutoPanel autoPanel = new AutoPanel();
        autoPanel.addAutomobile(automobiles.get(0));
        autoPanels.add(autoPanel);
        for (int i = 1; i < automobiles.size(); i++) {
            Automobile automobile = automobiles.get(i);
            String reading = automobile.getManufacturer() + automobile.getModel();
            if (reading.equals(current)) {
                autoPanels.get(autoPanels.size() - 1).addAutomobile(automobile);
            } else {
                current = reading;
                AutoPanel autoPanel1 = new AutoPanel();
                autoPanel1.addAutomobile(automobile);
                autoPanels.add(autoPanel1);
            }
        }
        return autoPanels;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public String getYear() {
        return year;
    }

    public ArrayList<String> getVins() {
        return vins;
    }
}
