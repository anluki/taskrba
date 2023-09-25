      
    DROP TABLE IF EXISTS osoba;  
    
    CREATE TABLE osoba (  
    id INT NOT NULL,
    ime VARCHAR(30) NOT NULL,
    prezime VARCHAR(50) NOT NULL,
    oib VARCHAR(11) NOT NULL,
    status VARCHAR(30) NOT NULL,
    PRIMARY KEY (id)
    );      
    ALTER TABLE osoba ADD CONSTRAINT oibUniqueConstraint UNIQUE(oib);
    
    
    
    
    
    
    
    