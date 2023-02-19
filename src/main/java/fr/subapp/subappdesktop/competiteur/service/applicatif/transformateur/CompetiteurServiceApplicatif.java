package fr.subapp.subappdesktop.competiteur.service.applicatif.transformateur;

import fr.subapp.subappdesktop.competiteur.model.Competiteur;
import fr.subapp.subappdesktop.competiteur.model.CompetiteurDTO;
import fr.subapp.subappdesktop.competiteur.service.metier.CompetiteurServiceMetier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompetiteurServiceApplicatif {

    private final CompetiteurServiceMetier competiteurServiceMetier;
    private final CompetiteurTransformateur competiteurTransformateur;

    public List<CompetiteurDTO> findAll() {
        List<Competiteur> competiteurs = competiteurServiceMetier.findAll();
        return competiteurs.stream().map(competiteurTransformateur::transformeCompetiteurEnCompetiteurDTO).toList();
    }
}
