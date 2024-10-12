package hemmouda.counter;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CountModelAssembler implements RepresentationModelAssembler<Count, EntityModel<Count>> {

    @Override
    public EntityModel<Count> toModel(Count count) {
        EntityModel<Count> entityModel = EntityModel.of(count,
            linkTo(methodOn(CountController.class).one(count.getId())).withSelfRel(),
            linkTo(methodOn(CountController.class).inc(count.getId())).withRel("inc"),
            linkTo(methodOn(CountController.class).all()).withRel("counts")
        );

        if (count.getValue() > 0) {
            entityModel.add(linkTo(methodOn(CountController.class).dec(count.getId())).withRel("dec"));
        }
        if (count.getValue() != 0) {
            entityModel.add(linkTo(methodOn(CountController.class).reset(count.getId())).withRel("reset"));
        }

        return entityModel;
    }

}
