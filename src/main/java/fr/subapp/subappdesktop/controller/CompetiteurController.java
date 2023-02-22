package fr.subapp.subappdesktop.controller;


import fr.subapp.subappdesktop.model.CompetiteurDTO;
import fr.subapp.subappdesktop.service.applicatif.CompetiteurServiceApplicatif;
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
