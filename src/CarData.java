import tech.tablesaw.api.*;
import tech.tablesaw.io.csv.CsvReadOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

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

        if (Objects.equals(practicality, "City")){
            filteredCars.column("Punkty_miasto").setName("Practicality");
        }
        else if (Objects.equals(practicality, "Route")){
            filteredCars.column("Punkty_trasa").setName("Practicality");
        }
        else if (Objects.equals(practicality, "Family")){
            filteredCars.column("Punkty_rodzinny").setName("Practicality");
        }
        else{
            filteredCars.column("Punkty_uniwersalny").setName("Practicality");
        }


    }

    public static void findCars() {
        int size = traits.size();
        double change = 1.0 / size;
        Table result = filteredCars;
        for (int i = 0; i < size; i++) {
            result.replaceColumn("Result", result.numberColumn("Result").add(filteredCars.numberColumn(traits.get(i)).multiply(1-change*i)));
            result.column(30).setName("Result");
        }
        CarList.listCount = 0;
        CarList.listNumber = 0;
        CarList.Cars = result.sortDescendingOn("Result");
    }


    public static void loadData(){
        ColumnType[] types = {ColumnType.STRING, ColumnType.DOUBLE, ColumnType.STRING, ColumnType.STRING, ColumnType.STRING, ColumnType.DOUBLE, ColumnType.DOUBLE, ColumnType.STRING, ColumnType.STRING, ColumnType.DOUBLE, ColumnType.DOUBLE, ColumnType.DOUBLE, ColumnType.DOUBLE,ColumnType.DOUBLE,ColumnType.DOUBLE,ColumnType.STRING, ColumnType.DOUBLE, ColumnType.DOUBLE, ColumnType.STRING, ColumnType.DOUBLE, ColumnType.DOUBLE, ColumnType.DOUBLE, ColumnType.DOUBLE, ColumnType.DOUBLE, ColumnType.DOUBLE, ColumnType.DOUBLE, ColumnType.DOUBLE, ColumnType.DOUBLE, ColumnType.DOUBLE, ColumnType.DOUBLE, ColumnType.DOUBLE};
        try {
            mainTable = Table.read().csv(CsvReadOptions
                    .builder("res/cars.csv")
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


