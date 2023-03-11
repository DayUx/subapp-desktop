package fr.subapp.subappdesktop.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FormData {

    private MultipartFile file;
}
