import tech.tablesaw.api.*;
import tech.tablesaw.io.csv.CsvReadOptions;

import java.io.IOException;
import java.util.ArrayList;

public abstract class CarData{

    static double minPrice = 0;
    static double maxPrice = 10000000;
    static ArrayList<String> traits;
    static String practicality;
    static ArrayList<String> fuel = new ArrayList<>();
    static ArrayList<String> gearBox = new ArrayList<>();
    static Table mainTable;
    static Table filteredCars;

    public CarData() {
    }

    public static void filterData() {
        filteredCars = mainTable
                .where(mainTable.numberColumn("Pricing").isBetweenInclusive(minPrice, maxPrice));

        filteredCars = filteredCars.where(filteredCars.stringColumn("Fuel").isIn(fuel));

        filteredCars = filteredCars.where(filteredCars.stringColumn("Gearbox").isIn(gearBox));
        //todo
        // Zmienić nazwę odpowiedniej kolumny z punktami z przeznaczeniem na Practicality (np. użytkownik wybierze
        // że chce do miasta to punkty_przeznaczenie_miasto ma mieć nazwę Practicality.

    }

    public static void findCars() {
        int size = traits.size();
        double change = 1.0 / size;
        Table result = filteredCars;
        for (int i = 0; i < size; i++) {
            result.replaceColumn("Result", result.numberColumn("Result").add(filteredCars.numberColumn(traits.get(i)).multiply(1-change*i)));
            result.column(27).setName("Result");
        }
        CarList.listCount = 0;
        CarList.listNumber = 0;
        CarList.Cars = result.sortDescendingOn("Result");
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


