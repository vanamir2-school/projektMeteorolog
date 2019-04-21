package ppj.vana.projekt.controller.HTML;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeHTMLController {

    @RequestMapping("/")
    public String showHome() {
        return "home";
    }

}
