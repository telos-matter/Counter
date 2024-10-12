package hemmouda.counter;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CountRepository extends MongoRepository<Count, String> {

    /**
     * @return a Count document with the name <code>name</code>
     * if found, otherwise <code>null</code>. Case insensitive
     * search.
     */
    @Query("{ 'name': { $regex: ?0, $options: 'i' } }")
    Count findByName (String name) ;

}
