package com.mailsoft.repository;
import com.mailsoft.domain.User;
import org.joda.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByLogin(String login);

    @Query("select u from User u where (u.login = ?1 or u.email = ?1)")
    User findByLoginOrEmail(String loginOrEmail);

    /*@Query("select user from User user left join fetch user.roles")
    List<User> findAllWithEagerRelationships();*/

    @Override
    void delete(User user);

    @Query("SELECT u FROM User u WHERE u.role.name = ?1")
    List<User> findAllByRole(String roleName);

    @Query("SELECT u FROM User u WHERE u.nom like ?1 or u.prenom like ?1")
    List<User> findByMc(String mc);

    Optional<User> findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndCreatedDateBefore(LocalDate date);

    Optional<User> findOneByResetKey(String resetKey);

    Optional<User> findOneByEmail(String email);

    Optional<User> findOneByLogin(String login);

    Optional<User> findOneById(Long userId);

    @Query("select u from User u "
            + "where (u.nom like ?1 or u.prenom like ?1 or ?1 is null) "
            + "and (u.email like ?2) "
            + "and u.role.name in ?3 ")
    Page<User> findAll(String login, String email, String[] roles, Pageable pageable);

    @Query("select u from User u "
            + "where u.nom like ?1 or u.prenom like ?1 or ?1 is null")
    Page<User> findAll(String name, Pageable pageable);

}
