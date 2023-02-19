package fr.subapp.subappdesktop.competiteur.utils;

public enum Categorie {
    MINIME("Minime"),
    CADET("Cadet"),
    JUNIOR("Junior"),
    SENIOR("Senior"),
    MASTER("Master");
    String libelle;

    Categorie(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
    public boolean isMinime() {
        return this == MINIME;
    }
    public boolean isCadet() {
        return this == CADET;
    }
    public boolean isJunior() {
        return this == JUNIOR;
    }
    public boolean isSenior() {
        return this == SENIOR;
    }
    public boolean isMaster() {
        return this == MASTER;
    }
}
