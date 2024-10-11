package hemmouda.counter;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CountRepo extends JpaRepository<Count, String> {
}
