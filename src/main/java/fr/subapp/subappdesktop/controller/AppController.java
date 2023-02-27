package fr.subapp.subappdesktop.controller;

import fr.subapp.subappdesktop.model.AppAdressDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@CrossOrigin
@RequestMapping("api/app")
public class AppController {
    @Value("${server.port}")
    int port;


    @GetMapping("getIpAndPort")
    public AppAdressDTO getIpAndPort() throws UnknownHostException {
        return new AppAdressDTO(InetAddress.getLocalHost().getHostAddress(), port);
    }
}
