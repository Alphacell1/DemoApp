#DemoApp

#Aplikacija koristi RAWG API za dohvat podataka o žanrovima igrica i samim igricama.

#Korištenje Retrofit i OkHttpClients omogućuje komunikaciju između klijenta i RAWG API-ja.

#Komunikacija je organizirana u klasu CommunicationHandler, implementiranu kao Singleton radi jednostavnosti inicijalizacije.

#Korištena je Model-View-ViewModel arhitektura za preglednost i funkcionalnost.

#Prilikom prvog pokretanja, korisnik odabire žanrove igrica s pomoću Grid Layouta za prikaz slika žanrova.

#Room lokalna baza podataka koristi se za pohranu odabranih žanrova, omogućujući lokalno spremanje i izmjenu odabranih žanrova.

#Repository klase dodane kako bi olakšale dohvat i unos podataka

#Firebase se koristi za označavanje "favorite" igrica i njihovo filtriranje po želji korisnika.

#Dizajn je tematski usmjeren na video igre, s naglaskom na upečatljivost prvog ekrana i funkcionalnost i preglednost ostalih.

#Informacije o igricama prikazuju se kroz dijaloge za jednostavan pregled detaljnijih opisa i tehničkih specifikacija.
