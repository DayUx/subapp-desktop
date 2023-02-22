package fr.subapp.subappdesktop.model;

import fr.subapp.subappdesktop.utils.Epreuve;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CibleDTO {
    int idCompetiteur;
    String cheminImg;
    String nom;

    Epreuve epreuve;
}
