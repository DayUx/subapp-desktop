package fr.subapp.subappdesktop.service.applicatif.transformateur;

import fr.subapp.subappdesktop.model.Competiteur;
import fr.subapp.subappdesktop.model.CompetiteurDTO;
import org.springframework.stereotype.Service;

@Service
public class CompetiteurTransformateur {

    public CompetiteurDTO transformeCompetiteurEnCompetiteurDTO(Competiteur competiteur) {
        CompetiteurDTO competiteurDTO = new CompetiteurDTO();
        competiteurDTO.setNom(competiteur.getNom());
        competiteurDTO.setPrenom(competiteur.getPrenom());
        competiteurDTO.setSexe(competiteur.getSexe());
        competiteurDTO.setDateNaissance(competiteur.getDateNaissance());
        competiteurDTO.setCategorie(competiteur.getCategorie());
        competiteurDTO.setClub(competiteur.getClub());
        competiteurDTO.setLicence(competiteur.getLicence());
        return competiteurDTO;
    }
    public Competiteur transformeCompetiteurDTOEnCompetiteur(CompetiteurDTO competiteurDTO) {
        Competiteur competiteur = new Competiteur();
        competiteur.setNom(competiteurDTO.getNom());
        competiteur.setPrenom(competiteurDTO.getPrenom());
        competiteur.setSexe(competiteurDTO.getSexe());
        competiteur.setDateNaissance(competiteurDTO.getDateNaissance());
        competiteur.setCategorie(competiteurDTO.getCategorie());
        competiteur.setClub(competiteurDTO.getClub());
        competiteur.setLicence(competiteurDTO.getLicence());
        return competiteur;
    }
}
