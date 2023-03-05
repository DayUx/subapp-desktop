package fr.subapp.subappdesktop.model;

import fr.subapp.subappdesktop.utils.Epreuve;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CibleDTO {
    private int idCompetiteur;
    private String cheminImg;
    private String nom;
    private Epreuve epreuve;
    private List<ImpactDTO> impacts;
}
