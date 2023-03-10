package fr.subapp.subappdesktop.model;

import fr.subapp.subappdesktop.utils.Zone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImpactDTO {
    private int points;
    private double angle;
    private Zone zone;


}
