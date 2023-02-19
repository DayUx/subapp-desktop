package fr.subapp.subappdesktop.competiteur.utils;

public enum Sexe {
    MASCULIN("Masculin"),
    FEMININ("Féminin");

    private String libelle;

    Sexe(String libelle) {
        this.libelle = libelle;
    }

    public static Sexe getSexe(String libelle) {
        for (Sexe sexe : Sexe.values()) {
            if (sexe.getLibelle().equals(libelle)) {
                return sexe;
            }
        }
        return null;
    }

    public String getLibelle() {
        return libelle;
    }
}
