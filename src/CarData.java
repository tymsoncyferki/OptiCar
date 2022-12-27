import java.util.ArrayList;

public abstract class CarData{
    // klasa do przechowywania danych z metodą która agreguje ramkę

    static int minPrice = 0;
    static int maxPrice = 0;
    static ArrayList<String> traits;


    public CarData() {
    }

    // odpali się w nowym wątku
    public static void filterData() {
        System.out.println(minPrice + maxPrice);
    }

    public static void findCars() {
        // robi nową zagregowaną ramkę i przekazuje ją do klasy lista
        System.out.println(traits);
    }

    public static void setMinPrice(int minPrice) {
        CarData.minPrice = minPrice;
    }

    public static void setMaxPrice(int maxPrice) {
        CarData.maxPrice = maxPrice;
    }
}
