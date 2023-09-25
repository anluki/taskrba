# taskrba
#rest call cli

http GET http://localhost:8080/osoba/get-osoba-by-oib/11111111111

http POST http://localhost:8080/osoba/add-osoba ime=IVICA prezime=IVICA oib=44444444444 status=NEMA_KARTICU

http POST http://localhost:8080/osoba/generate-card?oib=11111111111

http POST http://localhost:8080/osoba/deactivate-card?oib=11111111111
