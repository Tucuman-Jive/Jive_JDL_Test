package rocks.zipcode.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import rocks.zipcode.domain.UserProfile;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class UserProfileRepositoryWithBagRelationshipsImpl implements UserProfileRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<UserProfile> fetchBagRelationships(Optional<UserProfile> userProfile) {
        return userProfile.map(this::fetchChannels);
    }

    @Override
    public Page<UserProfile> fetchBagRelationships(Page<UserProfile> userProfiles) {
        return new PageImpl<>(
            fetchBagRelationships(userProfiles.getContent()),
            userProfiles.getPageable(),
            userProfiles.getTotalElements()
        );
    }

    @Override
    public List<UserProfile> fetchBagRelationships(List<UserProfile> userProfiles) {
        return Optional.of(userProfiles).map(this::fetchChannels).orElse(Collections.emptyList());
    }

    UserProfile fetchChannels(UserProfile result) {
        return entityManager
            .createQuery(
                "select userProfile from UserProfile userProfile left join fetch userProfile.channels where userProfile is :userProfile",
                UserProfile.class
            )
            .setParameter("userProfile", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<UserProfile> fetchChannels(List<UserProfile> userProfiles) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, userProfiles.size()).forEach(index -> order.put(userProfiles.get(index).getId(), index));
        List<UserProfile> result = entityManager
            .createQuery(
                "select distinct userProfile from UserProfile userProfile left join fetch userProfile.channels where userProfile in :userProfiles",
                UserProfile.class
            )
            .setParameter("userProfiles", userProfiles)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
