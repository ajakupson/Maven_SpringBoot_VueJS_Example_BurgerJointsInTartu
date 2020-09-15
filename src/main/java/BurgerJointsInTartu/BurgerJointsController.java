package BurgerJointsInTartu;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BurgerJointsController {

	@GetMapping("/api/burger-joints")
	public String GetBurgerJoints() {
		return "OK";
	}
	
}
