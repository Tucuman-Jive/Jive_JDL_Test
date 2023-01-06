package rocks.zipcode.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import rocks.zipcode.domain.UserProfile;

public interface UserProfileRepositoryWithBagRelationships {
    Optional<UserProfile> fetchBagRelationships(Optional<UserProfile> userProfile);

    List<UserProfile> fetchBagRelationships(List<UserProfile> userProfiles);

    Page<UserProfile> fetchBagRelationships(Page<UserProfile> userProfiles);
}
