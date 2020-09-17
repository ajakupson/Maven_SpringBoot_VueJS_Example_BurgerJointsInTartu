package BurgerJointsInTartu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SpringBootApplication
public class SpringBootMavenExampleBurgerJointsInTartuApplication {

	// for heroku deployment
	@GetMapping("/*")
	public String index() {
		return "index";
	}
    
	public static void main(String[] args) {
		SpringApplication.run(SpringBootMavenExampleBurgerJointsInTartuApplication.class, args);
	}

}
