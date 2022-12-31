import tech.tablesaw.api.*;
import tech.tablesaw.columns.Column;
import tech.tablesaw.io.csv.CsvReadOptions;

import java.io.IOException;
import java.util.ArrayList;

public abstract class CarData{
    // klasa do przechowywania danych z metodą która agreguje ramkę

    static double minPrice = 0;
    static double maxPrice = 10000000;
    static ArrayList<String> traits;
    static Table mainTable;
    static Table filteredCars;

    public CarData() {

    }

    // odpali się w nowym wątku
    public static void filterData() {
        filteredCars = mainTable.where(mainTable.numberColumn("Price").isIn(minPrice, maxPrice));
        // Zmienić nazwę odpowiedniej kolumny z punktami z przeznaczeniem na Practicality (np. użytkownik wybierze
        // że chce do miasta to przeznaczenie miasto ma mieć nazwę Pracicality.
    }

    public static void findCars() {
        // robi nową zagregowaną ramkę i przekazuje ją do klasy lista
        System.out.println(traits);
    }


    public static void findCars2() {
        ArrayList<String> columnNames = traits;
        int n = columnNames.size();
        double h = 1.0 / n;
        Table result = filteredCars;
        for (int i = 0; i < n; i++) {
            result.numberColumn("Wynik").add(filteredCars.numberColumn(columnNames.get(i)).multiply(1-h*i));
        }
        CarList.Cars = result.sortDescendingOn("Wynik");
    }

    public static void loadData(){
        ColumnType[] types = {ColumnType.STRING, ColumnType.DOUBLE, ColumnType.STRING, ColumnType.STRING, ColumnType.STRING, ColumnType.DOUBLE, ColumnType.DOUBLE, ColumnType.STRING, ColumnType.STRING, ColumnType.DOUBLE, ColumnType.DOUBLE, ColumnType.DOUBLE, ColumnType.STRING, ColumnType.DOUBLE, ColumnType.DOUBLE, ColumnType.STRING, ColumnType.DOUBLE, ColumnType.DOUBLE, ColumnType.DOUBLE, ColumnType.DOUBLE, ColumnType.DOUBLE, ColumnType.DOUBLE, ColumnType.DOUBLE, ColumnType.DOUBLE, ColumnType.DOUBLE, ColumnType.DOUBLE, ColumnType.DOUBLE, ColumnType.DOUBLE};
        try {
            mainTable = Table.read().csv(CsvReadOptions
                    .builder("cars.csv")
                    .columnTypes(types));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setMinPrice(int minPrice) {
        CarData.minPrice = minPrice;
    }

    public static void setMaxPrice(int maxPrice) {
        CarData.maxPrice = maxPrice;
    }

}


