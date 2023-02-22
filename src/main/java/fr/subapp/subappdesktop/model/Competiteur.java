package fr.subapp.subappdesktop.model;

import fr.subapp.subappdesktop.utils.Categorie;
import fr.subapp.subappdesktop.utils.Sexe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Competiteur {
    private String nom;
    private String prenom;
    private Sexe sexe;
    private LocalDate dateNaissance;
    private Categorie categorie;
    private String club;
    private int licence;
}
