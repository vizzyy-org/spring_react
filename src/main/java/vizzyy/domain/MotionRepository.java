package vizzyy.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface MotionRepository extends PagingAndSortingRepository<Motion, String> {

    @Query(value = "select * from motion.images order by Time desc limit :spot,1", nativeQuery=true)
    Motion findImageAtPlace(@Param("spot") int spot);

}
