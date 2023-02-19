package fr.subapp.subappdesktop.competiteur.model;

import fr.subapp.subappdesktop.competiteur.utils.Categorie;
import fr.subapp.subappdesktop.competiteur.utils.Sexe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetiteurDTO {
    private String nom;
    private String prenom;
    private Sexe sexe;
    private LocalDate dateNaissance;
    private Categorie categorie;
    private String club;
    private int licence;
}
