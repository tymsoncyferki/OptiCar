library(dplyr)

dfraw <- read.csv("cars_new.csv")
colnames(dfraw)

# Wyposażenie
# Patrzę ile jest dodatkowego wyposażenia, normuje (od 15 bo większość ma tego wyposażenie 30-40, żeby były większe różnice)
# tak, żeby punkty były od 0 do 10
dfraw <- dfraw %>%
  mutate(Safety = rowSums(dfraw[, 7:59] == "Yes")) %>%
  mutate(Safety = ifelse(Safety < 15, 0, round((Safety - 15) / (max(Safety) - 15) * 10,1)))



