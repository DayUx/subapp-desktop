package fr.subapp.subappdesktop.controller;

import fr.subapp.subappdesktop.model.CibleDTO;
import fr.subapp.subappdesktop.service.applicatif.CibleServiceApplicatif;
import fr.subapp.subappdesktop.utils.Epreuve;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("api/cible")
public class CibleController {
    private final CibleServiceApplicatif cibleServiceApplicatif;
    @PostMapping("upload")
    public void saveUser(@RequestBody MultipartFile image, @RequestParam int idCompetiteur, @RequestParam Epreuve epreuve) throws IOException {

        String fileName = StringUtils.cleanPath(image.getOriginalFilename());

        String uploadDir = "user-photos/" + idCompetiteur + "/" + epreuve;

        cibleServiceApplicatif.saveImage(uploadDir, fileName, image);

    }
    @GetMapping("getAll")
    public List<CibleDTO> getAllCible() {
        return cibleServiceApplicatif.getAllCible();
    }
}
