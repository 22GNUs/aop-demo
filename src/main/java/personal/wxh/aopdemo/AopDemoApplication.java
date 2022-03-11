package personal.wxh.aopdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import personal.wxh.aopdemo.service.DemoService;

import java.util.Map;

@SpringBootApplication
public class AopDemoApplication implements CommandLineRunner {

	private DemoService demoService;

	@Autowired
	public void setDemoService(DemoService demoService) {
		this.demoService = demoService;
	}

	public static void main(String[] args) {
		SpringApplication.run(AopDemoApplication.class, args);
	}

	@Override
	public void run(String... args) {
		ApiResponse<TestModel> response = demoService.helloWorld();

		TestModel result = response.getResult();
		Map<String, String> translation = result.getTranslation();
		System.out.println(translation);
	}
}
