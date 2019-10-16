package natalie;

public class Automobile {

    private String manufacturer;
    private String model;
    private String color;
    private String year;
    private String vin;
    private int serialNumber;

    public Automobile(String manufacturer, String model, String year,
                      String color, String vin, int serialNumber){
        this.manufacturer = manufacturer;
        this.model = model;
        this.color = color;
        this.year = year;
        this.vin = vin;
        this.serialNumber = serialNumber;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public String getYear() {
        return year;
    }

    public String getVin() {
        return vin;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Automobile) {
            return this.manufacturer.equals(((Automobile) obj).manufacturer) &&
                    this.model.equals(((Automobile) obj).model);
        } return super.equals(obj);
    }

    @Override
    public String toString() {
        return manufacturer+", "+model+", "+year+", "+color+", "+vin;
    }
}