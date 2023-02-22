package fr.subapp.subappdesktop.service.metier;

import fr.subapp.subappdesktop.dao.CompetiteurDAO;
import fr.subapp.subappdesktop.model.Competiteur;
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
