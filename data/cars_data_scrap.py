from bs4 import BeautifulSoup
import requests
from IPython.display import display
import pandas as pd

# Stworzenie ramki danych z kolumn z przykładowego samochodu
headers_url = 'https://www.ccarprice.com/us/bmw-m6-2022-price-in-usa-15757'
headers_page = requests.get(headers_url)
headers_soup = BeautifulSoup(headers_page.text, 'lxml')
specs = headers_soup.find('div', {'id': 'spec'})
headers = ['Brand', 'Price', 'Photo']
for row in specs.find_all('div', {'class':'tr'}):
    header = row.find('div', {'class':'td'})
    headers.append(header.text.strip())
df = pd.DataFrame(columns=headers)

# Załadowanie głownej strony
main_url = 'https://www.ccarprice.com/us/'
main_page = requests.get(main_url)
main_soup = BeautifulSoup(main_page.text, 'lxml')
brand_table = main_soup.find('div', {'class': 'show1'})

# Przejście po wszystkich markach
for brand in brand_table.find_all('a'):
    brand_url = brand.get('href')
    print(brand_url)
    brand_page = requests.get(brand_url)
    brand_soup = BeautifulSoup(brand_page.text, 'lxml')

    # Przejście po wszystkich samochodach
    car_table = brand_soup.find_all('div', {'class': 'price-cover'})[1]
    for car in car_table.find_all('div', {'class': 'listing'}):

        # Wejście w link do specyfikacji
        for link in car.find_all('a'):
            print(link.get('href'))
            url = link.get('href')
            page = requests.get(url)
            soup = BeautifulSoup(page.text, 'lxml')
            specification = soup.find('div', {'id': 'spec'})

            # Stworzenie nowego wiersza
            n_row = len(df)
            n_col = len(headers)
            new_row = [None] * len(headers)
            new_col = [None] * (n_row + 1)
            df.loc[n_row] = new_row

            # marka, cena (nie ma w ich tabeli specyfikacji)
            brand = soup.find('span', {'itemprop': 'brand'})
            df.at[n_row, 'Brand'] = brand.text.strip()
            price = soup.find('div', {'id': 'pbox', 'class': 'detail-price'})
            df.at[n_row, 'Price'] = price.text.strip()[14:]
            photo = soup.find('img', {'itemprop': 'image'})
            df.at[n_row, 'Photo'] = photo.get('src')

            # Zebranie danych technicznych
            for row in specification.find_all('div', {'class': 'tr'}):
                data = row.find_all('div')
                row_data = [td.text.strip() for td in data]

                # Dodanie nowej danej do ramki
                if row_data[0] in headers: 
                    df.at[n_row, row_data[0]] = row_data[1]
                else:  # Dodanie nowych kolumn jeśli jeszcze nie istnieją
                    df[row_data[0]] = new_col
                    headers.append(row_data[0])
                    df.at[n_row, row_data[0]] = row_data[1]

display(df)

df.to_csv("cars_2022_23_new.csv", index=False, encoding='utf8')
