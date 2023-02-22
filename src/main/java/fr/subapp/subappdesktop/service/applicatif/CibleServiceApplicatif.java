package fr.subapp.subappdesktop.service.applicatif;

import fr.subapp.subappdesktop.model.CibleDTO;
import fr.subapp.subappdesktop.utils.Epreuve;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class CibleServiceApplicatif {

        public static void saveImage(String uploadDir, String fileName,
                                    MultipartFile multipartFile) throws IOException {
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = multipartFile.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioe) {
                throw new IOException("Could not save image file: " + fileName, ioe);
            }
        }

    public List<CibleDTO> getAllCible() {
            List<CibleDTO> cibleDTOList = new ArrayList<>();
            cibleDTOList.add(new CibleDTO(1, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSUpJZbu_WLqjpbMF1N85Yvpa29uDhw9DRpeVYYkZ_ISAGN72G4coOHPEMLSTUziY8Yvo&usqp=CAU", "Jean Bon", Epreuve.BIATHLON));
            cibleDTOList.add(new CibleDTO(2, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSUpJZbu_WLqjpbMF1N85Yvpa29uDhw9DRpeVYYkZ_ISAGN72G4coOHPEMLSTUziY8Yvo&usqp=CAU", "Jean Bon", Epreuve.RELAIS));
            cibleDTOList.add(new CibleDTO(3, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSUpJZbu_WLqjpbMF1N85Yvpa29uDhw9DRpeVYYkZ_ISAGN72G4coOHPEMLSTUziY8Yvo&usqp=CAU", "Jean Bon", Epreuve.PRECISION));
            cibleDTOList.add(new CibleDTO(4, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSUpJZbu_WLqjpbMF1N85Yvpa29uDhw9DRpeVYYkZ_ISAGN72G4coOHPEMLSTUziY8Yvo&usqp=CAU", "Jean Bon", Epreuve.SUPER_BIATHLON));
            cibleDTOList.add(new CibleDTO(5, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSUpJZbu_WLqjpbMF1N85Yvpa29uDhw9DRpeVYYkZ_ISAGN72G4coOHPEMLSTUziY8Yvo&usqp=CAU", "Jean Bon", Epreuve.BIATHLON));
            cibleDTOList.add(new CibleDTO(6, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSUpJZbu_WLqjpbMF1N85Yvpa29uDhw9DRpeVYYkZ_ISAGN72G4coOHPEMLSTUziY8Yvo&usqp=CAU", "Jean Bon", Epreuve.PRECISION));


            return cibleDTOList;
    }
}
