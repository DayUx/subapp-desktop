package fr.subapp.subappdesktop.service.applicatif;

import fr.subapp.subappdesktop.model.Competiteur;
import fr.subapp.subappdesktop.model.CompetiteurDTO;
import fr.subapp.subappdesktop.service.applicatif.transformateur.CompetiteurTransformateur;
import fr.subapp.subappdesktop.service.metier.CompetiteurServiceMetier;
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
