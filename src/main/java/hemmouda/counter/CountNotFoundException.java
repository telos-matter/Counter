package hemmouda.counter;

public class CountNotFoundException extends RuntimeException {

    CountNotFoundException (String id) {
        super("Couldn't find Count with id `%s`".formatted(id));
    }

}
