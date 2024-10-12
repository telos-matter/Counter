package hemmouda.counter;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CountRepository extends MongoRepository<Count, String> {

    /**
     * Checks if a Count document with the specified name exists in the database.
     * Case-insensitive search.
     *
     * @return true if a Count document with the given name exists, false otherwise.
     */
    @Query(value = "{ 'name': { $regex: ?0, $options: 'i' } }", exists = true)
    boolean existsByName(String name);

}
