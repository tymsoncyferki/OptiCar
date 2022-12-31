library(dplyr)

df <- read.csv("cars_test.csv")
colnames(df)

df <- dfraw %>%
  select(Brand, Price, Photo, Model.Number, Engine.Type, power_hp,
         moment_obrotowy_lb_ft, skrzynia_biegow, naped, siedzenia,
         drzwi, waga_lbs, paliwo, city_mpg, highway_mpg, nadwozie, Safety,
         Punkty_dynamika, Punkty_wlasciwosci_terenowe, Punkty_bezpieczenstwo,
         Punkty_sportowy_charakter, Punkty_cena, Punkty_miasto, Punkty_trasa,
         Punkty_rodzinny, Punkty_uniwersalny, Punkty_spalanie_srednie)

colnames(df) <- c("Brand", "Price", "Photo", "Model", "Engine", "Power",
                  "Torque_lb_ft", "Gearbox", "Drivetrain", "Seats",
                  "Doors", "Weight_lbs", "Fuel", "Consumption_city", 
                  "Consumption_highway", "Body", "Punkty_wyposazenie", 
                  "Punkty_dynamika",             "Punkty_wlasciwosci_terenowe",
                  "Punkty_bezpieczenstwo" ,      "Punkty_sportowy_charakter"  ,
                  "Punkty_cena" ,                "Punkty_miasto"              ,
                  "Punkty_trasa" ,               "Punkty_rodzinny"            ,
                  "Punkty_uniwersalny",          "Punkty_spalanie_srednie" )

colnames(df)
View(df)

write.csv(df, "cars.csv", row.names=FALSE)
