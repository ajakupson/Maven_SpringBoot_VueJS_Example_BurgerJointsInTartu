package BurgerJointsInTartu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EntryPointController {

	@GetMapping("/*")
	public String index() {
		return "index";
	}

}