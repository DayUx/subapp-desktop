package fr.subapp.subappdesktop.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppAdressDTO {
    private String ip;
    private int port;
}
