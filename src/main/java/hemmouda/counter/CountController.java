package hemmouda.counter;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        return CollectionModel.of(counts,
                linkTo(methodOn(CountController.class).all()).withSelfRel());
    }

    @GetMapping("/counts/{id}")
    EntityModel<Count> one(@PathVariable String id) {

        Count count = repo.findById(id)
                .orElseThrow(() -> new CountNotFoundException(id));

        return assembler.toModel(count);
    }

    @PostMapping("/counts")
    ResponseEntity<?> create (@RequestBody Count count) {

        if (count.getName() == null || count.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .body(Problem.create()
                            .withTitle("Insufficient data")
                            .withDetail("A name for the count is required"));
        }

        if (repo.findByName(count.getName()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .body(Problem.create()
                            .withTitle("Erroneous data")
                            .withDetail("A count with this name already exists"));
        }

        count.setValue(0);
        Count newCount = repo.save(count);

        return ResponseEntity
                .created(linkTo(methodOn(CountController.class).one(newCount.getId())).toUri())
                .body(assembler.toModel(newCount));
    }

    @PutMapping("/counts/{id}/inc")
    ResponseEntity<EntityModel<Count>> inc (@PathVariable String id) {

        Count count = repo.findById(id)
                .orElseThrow(() -> new CountNotFoundException(id));

        count.inc();

        return ResponseEntity.ok(assembler.toModel(repo.save(count)));
    }

    @PutMapping("/counts/{id}/dec")
    ResponseEntity<?> dec (@PathVariable String id) {

        Count count = repo.findById(id)
                .orElseThrow(() -> new CountNotFoundException(id));

        if (count.getValue() > 0) {
            count.dec();
            return ResponseEntity.ok(assembler.toModel(repo.save(count)));
        }

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("You can't decrement a counter below zero"));
    }

    @PutMapping("/counts/{id}/reset")
    ResponseEntity<?> reset (@PathVariable String id) {

        Count count = repo.findById(id)
                .orElseThrow(() -> new CountNotFoundException(id));

        if (count.getValue() != 0) {
            count.reset();
            return ResponseEntity.ok(assembler.toModel(repo.save(count)));
        }

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("You can't reset a counter that is at zero"));
    }

    @DeleteMapping("/counts/{id}")
    ResponseEntity<?> delete(@PathVariable String id) {

        repo.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
