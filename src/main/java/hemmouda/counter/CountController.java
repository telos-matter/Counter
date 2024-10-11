package hemmouda.counter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CountController {

    private final CountRepo repo;

    CountController(CountRepo repository) {
        this.repo = repository;
    }


    @GetMapping("/counts")
    List<Count> all() {
        return repo.findAll();
    }

}
