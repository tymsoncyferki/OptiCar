library(dplyr)

dfraw <- read.csv("cars_new.csv")
colnames(dfraw)

# Wyposażenie
# Patrzę ile jest dodatkowego wyposażenia, normuje (od 15 bo większość ma tego wyposażenie 30-40, żeby były większe różnice)
# tak, żeby punkty były od 0 do 10
dfraw <- dfraw %>%
  mutate(Safety = rowSums(dfraw[, 7:59] == "Yes")) %>%
  mutate(Safety = ifelse(Safety < 15, 0, round((Safety - 15) / (max(Safety) - 15) * 10,1)))



# Spalanie miasto, trasa, średnie
dfraw <- dfraw %>% 
  mutate(Punkty_spalanie_miasto = ifelse(is.na(city_mpg), 
                                         0, 
                                         10 - round((city_mpg - min(city_mpg, na.rm = T)) / (max(city_mpg, na.rm = T) - min(city_mpg, na.rm = T)) * 10,1))) %>% 
  mutate(Punkty_spalanie_trasa = ifelse(is.na(highway_mpg), 
                                         0, 
                                         10 - round((highway_mpg - min(highway_mpg, na.rm = T)) / (max(highway_mpg, na.rm = T) - min(highway_mpg, na.rm = T)) * 10,1))) %>% 
  mutate(Punkty_spalanie_srednie = round((Punkty_spalanie_miasto + Punkty_spalanie_trasa) / 2,1))



# Moc/masa
dfraw <- dfraw %>% 
  mutate(moc_masa = ifelse(is.na(waga_lbs) | is.na(power_hp), 
                           0, 
                           power_hp / waga_lbs))


# Dynamika 
dfraw <- dfraw %>%
  mutate(Punkty_moc_masa = ifelse(moc_masa > 1, 10,
                                  round((moc_masa - min(moc_masa)) / (0.5997713 - min(moc_masa)) * 10,1))) %>%
  mutate(Punkty_moment_obrotowy = ifelse(is.na(moment_obrotowy_lb_ft), 
                                        0,
                                        ifelse( moment_obrotowy_lb_ft >= 4000,
                                                10,
                                                round((moment_obrotowy_lb_ft - min(moment_obrotowy_lb_ft, na.rm = T)) / (2581 - min(moment_obrotowy_lb_ft, na.rm = T)) * 10,1)))) %>% 
  mutate(Punkty_dynamika = round((Punkty_moc_masa + Punkty_moment_obrotowy) / 2, 1)) %>% 
  mutate(Punkty_dynamika = round((Punkty_dynamika - min(Punkty_dynamika)) / (max(Punkty_dynamika) - min(Punkty_dynamika)) * 10,1))
  



# Właściwości terenowe
dfraw <- dfraw %>% 
  mutate(Punkty_naped_terenowy = case_when(naped == "rear" ~ 10,
                                  naped == "all" ~ 7,
                                  naped == "front" ~ 3,
                                  T ~ 0)) %>% 
  mutate(Punkty_nadwozie_terenowe = case_when(nadwozie == "Truck" ~ 10,
                                     nadwozie == "SUV" ~ 8,
                                     nadwozie == "Hatchback" ~ 6.5,
                                     nadwozie == "Van" ~ 5,
                                     nadwozie %in% c("Wagon", "Sedan") ~ 3,
                                     nadwozie == "Coupe" ~ 2,
                                     nadwozie == "Convertible" ~ 1,
                                     T ~ 0)) %>% 
  mutate(Punkty_przeswit = case_when(is.na(przeswit_in) ~ 0,
                                     przeswit_in > 20 ~ 10,
                                     T ~ round((przeswit_in - min(przeswit_in, na.rm = T)) / (16 - min(przeswit_in, na.rm = T)) * 10,1))) %>% 
  mutate(Punkty_wlasciwosci_terenowe = 
           (Punkty_naped_terenowy + Punkty_nadwozie_terenowe + Punkty_przeswit)/3) %>% 
  mutate(Punkty_wlasciwosci_terenowe = 
           round((Punkty_wlasciwosci_terenowe - min(Punkty_wlasciwosci_terenowe)) / (max(Punkty_wlasciwosci_terenowe) - min(Punkty_wlasciwosci_terenowe)) * 10, 1))
  

# Bezpieczeństwo
dfraw <- dfraw %>%
  mutate(Punkty_bezpieczenstwo_wyposazenie = rowSums(dfraw[, c("AntiLock.Braking.System",
                                    "Power.Steering",
                                    "Anti.Lock.Braking",
                                    "Brake.Assist",
                                    "Central.Locking.1", 
                                    "Night.Rear.View.Mirror",
                                    "Rear.Seat.Belts",
                                    "Door.Ajar.Warning",
                                    "Crash.Sensor", 
                                    "Engine.Check.Warning",
                                    "Rear.Camera",
                                    "Electric.Folding.Rear.View.Mirror",
                                    "Automatic.Climate.Control",
                                    "Parking.Sensors",
                                    "Seat.Belt.Warning",
                                    "Vehicle.Stability.Control.System",
                                    "Engine.Immobilizer",
                                    "Fog.Lights.Front...Back", 
                                    "Outside.Temperature.Display",
                                    "Tyre.Pressure.Monitor")] == "Yes")) %>% 
  mutate(Punkty_bezpieczenstwo_wyposazenie = 
           round((Punkty_bezpieczenstwo_wyposazenie - 2)/(20-2) * 10, 1)) %>% 
  mutate(rozmiar = wysokosc_in * dlugosc_in * szerokosc_in) %>%
  mutate(Punkty_rozmiar = case_when(is.na(rozmiar) ~ 0,
                                    rozmiar >= 3000000 ~ 10,
                                    T ~ round((rozmiar - min(rozmiar, na.rm = T)) / (2170800 - min(rozmiar, na.rm = T)) * 10, 1))) %>% 
  mutate(Punkty_bezpieczenstwo = round((Punkty_rozmiar + Punkty_bezpieczenstwo_wyposazenie) / 2,1)) %>% 
  mutate(Punkty_bezpieczenstwo = round((Punkty_bezpieczenstwo - min(Punkty_bezpieczenstwo)) / (max(Punkty_bezpieczenstwo) - min(Punkty_bezpieczenstwo)) * 10,1))
  


# Sportowy charakter

dfraw <- dfraw %>% 
  mutate(Punkty_rozmiar_silnika = case_when(is.na(pojemnosc_L) ~ 0,
                                            T ~ round((pojemnosc_L - min(pojemnosc_L, na.rm = T)) / (max(pojemnosc_L, na.rm = T) - min(pojemnosc_L, na.rm = T)) * 10, 1))) %>% 
  mutate(Punkty_naped_sportowy = case_when(naped == "rear" ~ 10,
                                  naped == "all" ~ 8,
                                  naped == "front" ~ 2,
                                  T ~ 0)) %>% 
  mutate(Punkty_cylindry = case_when(is.na(cylindry) ~ 0,
                                     cylindry > 30 ~ 10,
                                     T ~ round((cylindry - min(cylindry, na.rm = T)) / (16 - min(cylindry, na.rm = T)) * 10, 1))) %>% 
  mutate(Punkty_nadwozie_sportowe = case_when(nadwozie %in% c("Coupe", "Convertible") ~ 10,
                                     nadwozie == "Hatchback" ~ 8,
                                     nadwozie == "Sedan" ~ 6,
                                     nadwozie == "SUV" ~ 4,
                                     nadwozie %in% c("Wagon", "Van") ~ 2,
                                     nadwozie == "Truck" ~ 1,
                                     T ~ 0)) %>% 
  mutate(Punkty_sportowy_charakter = round((Punkty_rozmiar_silnika + Punkty_naped_sportowy + Punkty_cylindry + Punkty_nadwozie_sportowe + Punkty_moc_masa) / 5 , 1)) %>% 
  mutate(Punkty_sportowy_charakter = 
           round((Punkty_sportowy_charakter - min(Punkty_sportowy_charakter)) / (max(Punkty_sportowy_charakter) - min(Punkty_sportowy_charakter)) * 10, 1))
  
 

# Cena 
dfraw <- dfraw %>% 
  mutate(Punkty_cena = case_when(is.na(Price) ~ 0,
                                 T ~ 10 - round((Price - min(Price, na.rm = T)) / (max(Price, na.rm = T) - min(Price, na.rm = T)) * 10, 1)))

View(dfraw)
colnames(dfraw)

