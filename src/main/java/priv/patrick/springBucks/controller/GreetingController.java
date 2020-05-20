package priv.patrick.springBucks.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import priv.patrick.springBucks.pojo.Coffee;
import priv.patrick.springBucks.pojo.Greeting;
import priv.patrick.springBucks.pojo.Result;
import priv.patrick.springBucks.service.CoffeeService;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@Slf4j
public class GreetingController extends BaseController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private CoffeeService coffeeService;

    @RequestMapping("/greeting")
    public Result<Greeting> greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return returnSuccess(new Greeting(counter.incrementAndGet(), String.format(template, name)));
    }

    @RequestMapping("/testCoffee")
    public Result<String> testCoffee(Coffee coffee) {
//        coffeeService.save(coffee);
        coffeeService.findByName(coffee.getName());
        return returnSuccess();
    }
}
