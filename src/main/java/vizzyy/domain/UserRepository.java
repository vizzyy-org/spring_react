package vizzyy.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByRole(String role);

    List<User> findByCommonName(String common_name);

    User findById(long user_id);

    List<User> findAll();
}
