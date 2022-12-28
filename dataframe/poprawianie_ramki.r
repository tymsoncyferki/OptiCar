library(stringi)
library(dplyr)
library(tidyverse)

#Zaczytanie
dfraw <- read.csv("cars_2022_23_new.csv")
View(dfraw)
#Wybór potrzebnych kolumn
sam <- dfraw %>%
  select(-Made.In, -Warranty, -Available.Colors,  -Charger,
         -Maximum.Speed, -Acceleration.Time..0.100.kmph., -Gear.Box, -Steering.Type, -Steering.Gear.Type, -Front.Suspension,
         -Back.Suspension,-Tyre.Size, -Wheel.Size, -Front.Brake, -Rear.Brake, -Minimum.Turning.Radius, -Tyre.Type, -Fuel.System,
         -Valve.Configuration, -Boot.Space, -Assembled.In, -Displacement..cc., -Shock.Absorbers.Type,
         -Introduction.Date)

#Obróbka Price
sam <- sam %>%
  mutate(Price = as.numeric(gsub("(,)", "", Price))) %>%
  drop_na(Price)

#Obróbka mocy
sam <- sam %>% 
  mutate(power_hp = as.numeric(stri_extract_first( gsub("(,)", "", Engine.Power) ,regex = "(\\d+)"))) %>%
  mutate(power_hp = ifelse(grepl("kw",stri_extract_first(Engine.Power,regex = "(bhp)|(hp)|(HP)|(kW)|(kw)|(KW)|(PS)"), ignore.case = TRUE),
                           round(power_hp * 1.34), power_hp)) %>%
  select(-Engine.Power)

#Pojemności
sam <- sam %>% 
  mutate(pojemnosc_L = as.numeric(stri_extract(Engine.Type ,regex = "([0-9])([.])([0-9])")))


#Moment obrotowy
sam <- sam %>% 
  mutate(moment_obrotowy_lb_ft = as.numeric(stri_extract_first( gsub("(,)", "", Torque) ,regex = "(\\d+)"))) %>%
  mutate(moment_obrotowy_lb_ft = ifelse(grepl("n",stri_extract_first(Torque,regex = "(Nm)|(nm)|(NM)|(l)|(L)|(pound)|(Newton)"), ignore.case = TRUE),
                                   round(moment_obrotowy_lb_ft * 0.74), moment_obrotowy_lb_ft)) %>%
  select(-Torque)

#Długość wyokość, szerokość, długość, prześwit, rozstaw osi
sam <- sam %>%
  mutate(wysokosc_in = as.numeric(stri_extract_first( gsub("(,)", "",Height ), regex = "(\\d+)[.](\\d+)|(\\d+)")),
         dlugosc_in = as.numeric(stri_extract_first( gsub("(,)", "",Length ), regex = "(\\d+)[.](\\d+)|(\\d+)")),
         szerokosc_in = as.numeric(stri_extract_first( gsub("(,)", "",Width ), regex = "(\\d+)[.](\\d+)|(\\d+)")),
         rozstaw_osi_in = as.numeric(stri_extract_first( gsub("(,)", "",Wheel.Base ), regex = "(\\d+)[.](\\d+)|(\\d+)")),
         przeswit_in = as.numeric(stri_extract_first( gsub("(,)", "",Ground.Clearance ), regex = "(\\d+)[.](\\d+)|(\\d+)"))) %>%
  mutate(wysokosc_in = ifelse(grepl("m",stri_extract_first(Height,regex = "(mm)|(MM)|(Mn)|(In)|(in)|(IN)"), ignore.case = TRUE),
                          round(wysokosc_in * 0.04), wysokosc_in),
        dlugosc_in = ifelse(grepl("m",stri_extract_first(Length,regex = "(mm)|(MM)|(Mn)|(In)|(in)|(IN)"), ignore.case = TRUE),
                          round(dlugosc_in * 0.04), dlugosc_in),
        szerokosc_in = ifelse(grepl("m",stri_extract_first(Width,regex = "(mm)|(MM)|(Mn)|(In)|(in)|(IN)"), ignore.case = TRUE),
                          round(szerokosc_in * 0.04), szerokosc_in),
        rozstaw_osi_in = ifelse(grepl("m",stri_extract_first(Wheel.Base,regex = "(mm)|(MM)|(Mn)|(In)|(in)|(IN)"), ignore.case = TRUE),
                          round(rozstaw_osi_in * 0.04), rozstaw_osi_in),
        przeswit_in = ifelse(grepl("m",stri_extract_first(Ground.Clearance,regex = "(mm)|(MM)|(Mn)|(In)|(in)|(IN)"), ignore.case = TRUE),
                          round(przeswit_in * 0.04), przeswit_in)) %>%
  select(-c(Length, Height, Width, Wheel.Base, Ground.Clearance))

#Cylindry
sam <- sam %>%
  mutate(cylindry = as.numeric(stri_extract_first( No..of.Cylinders,regex = "(\\d+)" ))) %>%
  select(-No..of.Cylinders)



#Pojemnosc baku
sam <- sam %>%
  mutate(bak = gsub("[^a-zA-Z]", "", Fuel.Tank.Capacity..Litres.)) %>%
  mutate(bak = case_when(
    bak %in% c("Liters", "L", "liters", "Litres", "litres", "Liter",
               "LITRES", "l", "", "lgal") ~ round(as.numeric(stri_extract(Fuel.Tank.Capacity..Litres., regex="\\d+(?=\\D|\\s)"))),
    bak %in% c("gallons", "gal", "Gallon", "LiterGallon",
               "Gallons", "gallon") ~ round(as.numeric(stri_extract(Fuel.Tank.Capacity..Litres., regex="\\d+(?=\\D|\\s)")) * 3.785),
    is.na(bak) ~ 0,
    TRUE ~ 0)) %>%
  select(-Fuel.Tank.Capacity..Litres.)


#Skrzynia Biegow
sam <- sam %>%
  mutate(gear = ifelse(stri_extract(Transmission.Type,regex = "(Continuously)|(CVT)|(continuously)") 
                       %in% c("Continuously", "CVT", "continuously"),
                       "CVT", Transmission.Type)) %>%
  mutate(gear = ifelse(stri_extract(Transmission.Type,regex = "(Manual)|(manual)") 
                       %in% c("Manual", "manual"), "Manual", gear)) %>%
  mutate(skrzynia_biegow = ifelse(gear %in% c("Manual", "CVT"), gear, "Automatic")) %>%
  select(-Transmission.Type, -gear)

#Naped
sam <- sam %>%
  mutate(naped = stri_extract_first(Drive.Type , regex = "(2)|(Front)|(FWD)|(Rear)|(Real)|(RWD)|(All)|(Al)|(AWD)|(4)|(Four)")) %>%
  mutate(naped = case_when(naped == "2" | naped == "Front" |naped == "FWD" ~ "front",
                           naped == "Rear" | naped == "Real" | naped == "RWD"  ~ "rear",
                           naped == "All" | naped == "Al" | naped == "AWD"| naped == "4"| naped == "Four"  ~  "all",
                                       TRUE ~ "")) %>%
  select(-Drive.Type)

#siedzenia, drzwi
sam <- sam %>%
  mutate(siedzenia = as.numeric(stri_extract_first(Seating.Capacity , regex = "(\\d+)")),
         drzwi = as.numeric(stri_extract_first(No..of.Doors , regex = "(\\d+)"))) %>%
  select(-c(No..of.Doors, Seating.Capacity))

#waga
#chyba do popraway
sam <- sam %>%
  mutate(waga_lbs = as.numeric(stri_extract_first( gsub("(,)", "",Kerb.Weight ), regex = "(\\d+)[.](\\d+)|(\\d+)"))) %>%
  mutate(waga_lbs = ifelse(grepl("k",stri_extract_first(Kerb.Weight,regex = "(l)|(L)|(K)|(k)"), ignore.case = TRUE),
                              round(waga_lbs * 2.2), waga_lbs)) %>%
  select(-Kerb.Weight)


# Paliwo
sam <- sam %>%
  mutate(paliwo = case_when(Fuel.Type %in% c("Gasoline", "Premium", "Petrol", "Premium Unleaded", "Premium unleaded",
                                             "Regular", "Unleaded Premium", "Regular, Premium", "Gas", "Premium unleaded gasoline",
                                             "Premium, Regular", "Pertol", "Regular unleaded", "CNG") ~ "Petrol",
                            Fuel.Type %in% c("Electric", "Electricity", "Electric Fuel") ~ "Electric",
                            Fuel.Type %in% c("Hybrid", "Plug-in Hybrid", "Plug-In Electric/Gas",
                                             "Gas/Hybrid", "Plug-In Hybrid") ~ "Hybrid",
                            Fuel.Type %in% c("Diesel", "diesel") ~ "Diesel",
                            Engine.Type == "Electric" ~ "Electric",
                            TRUE ~ "Unknown"))

#spalanie
sam <- sam %>%
  mutate(city_mpg = gsub("[^a-zA-Z]", "", Mileage.in.City)) %>%
  mutate(city_mpg = case_when(city_mpg %in% c("LKm","Lkm","lKm","lkm") ~ 
                                round(235 / as.numeric(stri_extract_first(Mileage.in.City , regex = "(\\d+)[.](\\d+)|(\\d+)"))),
                              grepl("k",stri_extract_first(city_mpg , regex = "(K)|(k)"),ignore.case = TRUE) ~ 
                                round(as.numeric(stri_extract_first(Mileage.in.City , regex = "(\\d+)[.](\\d+)|(\\d+)")) * 2.35),
                              TRUE ~ round(as.numeric(stri_extract_first(Mileage.in.City , regex = "(\\d+)[.](\\d+)|(\\d+)"))))) %>%
  mutate(city_mpg = ifelse(paliwo == "Electric", 0, city_mpg)) %>%
  mutate(city_mpg = ifelse(city_mpg > 70, 0, city_mpg))

sam <- sam %>%
  mutate(highway_mpg = gsub("[^a-zA-Z]", "", Mileage.on.Highway)) %>%
  mutate(highway_mpg = case_when(highway_mpg %in% c("LKm","Lkm","lKm","lkm") ~ 
                                   round(235 / as.numeric(stri_extract_first(Mileage.on.Highway , regex = "(\\d+)[.](\\d+)|(\\d+)"))),
                                 grepl("k",stri_extract_first(highway_mpg , regex = "(K)|(k)"),ignore.case = TRUE) ~ 
                                   round(as.numeric(stri_extract_first(Mileage.on.Highway , regex = "(\\d+)[.](\\d+)|(\\d+)")) * 2.35),
                                 TRUE ~ round(as.numeric(stri_extract_first(Mileage.on.Highway , regex = "(\\d+)[.](\\d+)|(\\d+)"))))) %>%
  mutate(highway_mpg = ifelse(paliwo == "Electric", 0, highway_mpg)) %>%
  mutate(highway_mpg = ifelse(highway_mpg > 70, 0, highway_mpg)) %>%
  select(-c( Mileage.in.City, Mileage.on.Highway))


sam <- sam %>% 
  mutate(nadwozie = case_when(Body.Type %in% c("Sport Utility", "Crossover", "SUV    Category: Exotic SUV",
                                               "Mini-SUV", "Body Code: S , Body Style: Sport Utility",
                                               "crossover", "Luxury midsize SUV", "Mid-size luxury crossover 4-door SUV",
                                               "Midsize SUV",  "MUV") ~ "SUV",
                              Body.Type %in% c("Sedan   Category: Exotic", "4-door sedan luxury car",
                                               "luxury sedan", "Sedan Body") ~ "Sedan",
                              Body.Type %in% c("Sports Car", "Roadster", "Coupe   Category: Exotic",
                                               "Coupe   Category: Sports Car", "Coupe, Hatchback   Category: Sports Car",
                                               "Coupe Convertible", "Roadster   Category: Exotic",
                                               "Spider", "Sport Car", "Sports","Spyder", "Luxury coupe",
                                               "Roadster   Category: Sports Car") ~ "Coupe",
                              Body.Type %in% c("Hatchback   Category: City Car   Assembly: Toluca, MX",
                                               "5-door hatchback", "Hatchback", "Hatchback   Category: City Car",
                                               "Hatchback   Category: Sport car", "Mini","‎Hatchback", 
                                               "Hatchback   Category: Sports Car") ~ "Hatchback",
                              Body.Type %in% c("Soft Top Convertible", "Hard Top Convertible",
                                               "Convertible   Category: City Car, Compact sports car, Convertible",
                                               "Convertible   Category: Sports Car   Assembly: Toluca, MX") ~ "Convertible",
                              Body.Type %in% c("Heavy Duty Truck", "Pickup Truck", "Electric Truck") ~ "Truck",
                              Body.Type %in% c("Minivan", "Passenger Van") ~ "Van",
                              Body.Type %in% c("Wagen") ~ "Wagon",
                              Body.Type %in% c("None", "99+") ~ "",
                              TRUE ~ Body.Type)) %>%
  select(-Body.Type)

View(sam)
write.csv(sam, "cars_new.csv", row.names=FALSE)

