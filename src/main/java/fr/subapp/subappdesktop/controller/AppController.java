package fr.subapp.subappdesktop.controller;

import fr.subapp.subappdesktop.model.AppAdressDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

@RestController
@CrossOrigin
@RequestMapping("api/app")
public class AppController {
    @Value("${server.port}")
    int port;

    @GetMapping("getIpAndPort")
    public AppAdressDTO getIpAndPort() throws UnknownHostException {

        String ip = null;
        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ip = socket.getLocalAddress().getHostAddress();
            return new AppAdressDTO(ip, port);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }
}
