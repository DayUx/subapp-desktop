package fr.subapp.subappdesktop.utils;

public enum Epreuve {
    SUPER_BIATHLON("Super Biathlon"),
    BIATHLON("Biathlon"),
    PRECISION("Précision"),
    RELAIS("Relais");

    private String libelle;

    Epreuve(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    public Epreuve getEpreuve(String libelle) {
        for (Epreuve epreuve : Epreuve.values()) {
            if (epreuve.getLibelle().equals(libelle)) {
                return epreuve;
            }
        }
        return null;
    }
}
