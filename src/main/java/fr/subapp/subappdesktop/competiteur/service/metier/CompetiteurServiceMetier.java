package fr.subapp.subappdesktop.competiteur.service.metier;

import fr.subapp.subappdesktop.competiteur.dao.CompetiteurDAO;
import fr.subapp.subappdesktop.competiteur.model.Competiteur;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompetiteurServiceMetier {

    private final CompetiteurDAO competiteurDAO;
    public List<Competiteur> findAll() {
        return competiteurDAO.findAll();
    }
}
