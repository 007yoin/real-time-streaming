package com.live_stream.common.veiw;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/streaming-viewer")
    public String streamingViewer() {
        return "streaming-viewer";
    }

    @GetMapping("/camera-management-list")
    public String cameraManagement() {
        return "camera-management-list";
    }

    @GetMapping("/camera-insert")
    public String cameraInsert() {
        return "camera-insert";
    }

    @GetMapping("/user-management-list")
    public String userManagement() {
        return "user-management-list";
    }

    @GetMapping("/user-management")
    public String userInsert() {
        return "user-management";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
