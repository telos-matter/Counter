package hemmouda.counter;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CountRepo extends MongoRepository<Count, String> {

}
