package hemmouda.counter;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

@RestController
public class CountController {

    private final CountRepository repo;
    private final CountModelAssembler assembler;

    CountController(CountRepository repository, CountModelAssembler assembler) {
        this.repo = repository;
        this.assembler = assembler;
    }

    @GetMapping("/counts")
    CollectionModel<EntityModel<Count>> all() {

        List<EntityModel<Count>> counts = repo.findAll().stream()
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(counts, linkTo(methodOn(CountController.class).all()).withSelfRel());
    }

    @GetMapping("/counts/{id}")
    EntityModel<Count> one(@PathVariable String id) {

        Count count = repo.findById(id)
                .orElseGet(() -> new Count(id)); // TODO make it return null

        return assembler.toModel(count);
    }

    @DeleteMapping("/counts/{id}")
    ResponseEntity<?> delete(@PathVariable String id) {

        repo.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
