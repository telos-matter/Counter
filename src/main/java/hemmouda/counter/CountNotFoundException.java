package hemmouda.counter;

public class CountNotFoundException extends RuntimeException {

    CountNotFoundException (String id) {
        super("Couldn't find count " +id);
    }

}
