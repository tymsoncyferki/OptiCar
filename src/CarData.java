public abstract class CarData {
    // klasa do przechowywania danych z metodą która agreguje ramkę

    static int minPrice = 0;
    static int maxPrice = 0;

    public CarData() {
    }

    public static void filterData() {
        // robi nową zagregowaną ramkę i przekazuje ją do klasy lista
        // filter price >= minimum
        // filter price <= maksimum

    }

    public static void setMinPrice(int minPrice) {
        CarData.minPrice = minPrice;
    }

    public static void setMaxPrice(int maxPrice) {
        CarData.maxPrice = maxPrice;
    }
}
