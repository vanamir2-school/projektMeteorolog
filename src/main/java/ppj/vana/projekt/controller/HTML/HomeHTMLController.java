package ppj.vana.projekt.controller.HTML;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeHTMLController {

    @RequestMapping("/")
    public String showHome() {
        return "home";
    }
}
