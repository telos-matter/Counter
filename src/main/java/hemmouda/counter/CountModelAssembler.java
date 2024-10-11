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
        return EntityModel.of(count,
            linkTo(methodOn(CountController.class).one(count.getName())).withSelfRel(),
            linkTo(methodOn(CountController.class).all()).withRel("counts")
        );
    }

}
