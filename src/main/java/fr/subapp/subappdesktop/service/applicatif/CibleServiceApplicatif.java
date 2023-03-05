package fr.subapp.subappdesktop.service.applicatif;

import fr.subapp.subappdesktop.model.CibleDTO;
import fr.subapp.subappdesktop.model.ImpactDTO;
import fr.subapp.subappdesktop.utils.Epreuve;
import fr.subapp.subappdesktop.utils.Zone;
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

            List<ImpactDTO> impactDTOList = new ArrayList<>();
            impactDTOList.add(new ImpactDTO(570, 10, Zone.TOP_LEFT));
            impactDTOList.add(new ImpactDTO(570, 67, Zone.BOTTOM_RIGHT));
            impactDTOList.add(new ImpactDTO(570, 10, Zone.CENTER));
            impactDTOList.add(new ImpactDTO(471, 352, Zone.TOP_RIGHT));
            impactDTOList.add(new ImpactDTO(570, 12, Zone.BOTTOM_LEFT));
            impactDTOList.add(new ImpactDTO(570, 10, Zone.TOP_LEFT));
            impactDTOList.add(new ImpactDTO(511, 78, Zone.BOTTOM_RIGHT));
            impactDTOList.add(new ImpactDTO(411, 234, Zone.CENTER));
            impactDTOList.add(new ImpactDTO(540, 43, Zone.TOP_RIGHT));
            impactDTOList.add(new ImpactDTO(500, 250, Zone.BOTTOM_LEFT));

            List<CibleDTO> cibleDTOList = new ArrayList<>();
            cibleDTOList.add(new CibleDTO(1, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSUpJZbu_WLqjpbMF1N85Yvpa29uDhw9DRpeVYYkZ_ISAGN72G4coOHPEMLSTUziY8Yvo&usqp=CAU", "Jean Bon", Epreuve.BIATHLON,impactDTOList));
            cibleDTOList.add(new CibleDTO(2, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSUpJZbu_WLqjpbMF1N85Yvpa29uDhw9DRpeVYYkZ_ISAGN72G4coOHPEMLSTUziY8Yvo&usqp=CAU", "Jean Bon", Epreuve.RELAIS,impactDTOList));
            cibleDTOList.add(new CibleDTO(3, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSUpJZbu_WLqjpbMF1N85Yvpa29uDhw9DRpeVYYkZ_ISAGN72G4coOHPEMLSTUziY8Yvo&usqp=CAU", "Jean Bon", Epreuve.PRECISION,impactDTOList));
            cibleDTOList.add(new CibleDTO(4, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSUpJZbu_WLqjpbMF1N85Yvpa29uDhw9DRpeVYYkZ_ISAGN72G4coOHPEMLSTUziY8Yvo&usqp=CAU", "Jean Bon", Epreuve.SUPER_BIATHLON,impactDTOList));
            cibleDTOList.add(new CibleDTO(5, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSUpJZbu_WLqjpbMF1N85Yvpa29uDhw9DRpeVYYkZ_ISAGN72G4coOHPEMLSTUziY8Yvo&usqp=CAU", "Jean Bon", Epreuve.BIATHLON,impactDTOList));
            cibleDTOList.add(new CibleDTO(6, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSUpJZbu_WLqjpbMF1N85Yvpa29uDhw9DRpeVYYkZ_ISAGN72G4coOHPEMLSTUziY8Yvo&usqp=CAU", "Jean Bon", Epreuve.PRECISION,impactDTOList));

            return cibleDTOList;
    }
}
