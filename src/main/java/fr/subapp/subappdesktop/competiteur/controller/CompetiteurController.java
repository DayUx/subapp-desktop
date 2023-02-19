package fr.subapp.subappdesktop.competiteur.controller;


import fr.subapp.subappdesktop.competiteur.model.CompetiteurDTO;
import fr.subapp.subappdesktop.competiteur.service.applicatif.transformateur.CompetiteurServiceApplicatif;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/competiteur")
@RequiredArgsConstructor
public class CompetiteurController {

    private final CompetiteurServiceApplicatif competiteurServiceApplicatif;
    @GetMapping("getAll")
    public List<CompetiteurDTO> getAll() {
        return competiteurServiceApplicatif.findAll();
    }

}
