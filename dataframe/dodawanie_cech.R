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
  # To jeszcze do przekminienia bo kilka ogormnych wartości a reszta strasznie niska
  mutate(Punkty_moc_masa = round((moc_masa - min(moc_masa)) / (max(moc_masa) - min(moc_masa)) * 10,1)) %>%
  mutate(Punkty_moment_obrotowy = ifelse(is.na(moment_obrotowy_lb_ft), 
                                        0, 
                                        round((moment_obrotowy_lb_ft - min(moment_obrotowy_lb_ft, na.rm = T)) / (max(moment_obrotowy_lb_ft, na.rm = T) - min(moment_obrotowy_lb_ft, na.rm = T)) * 10,1))) %>% 
  mutate(punkty_dynamika = round((Punkty_moc_masa + Punkty_moment_obrotowy) / 2, 1))
  


