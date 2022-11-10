package ch.zli.m223.service;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;


import ch.zli.m223.model.CwSUser;

@ApplicationScoped
public class CwSUserService {
    @Inject
    EntityManager entityManager;

    @Transactional
    public CwSUser createUser(CwSUser user) {
        return entityManager.merge(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        var entity = entityManager.find(CwSUser.class, id);
        entityManager.remove(entity);
    }

    @Transactional
    public CwSUser updatUser(Long id, CwSUser user) {
        user.setId(id);
        return entityManager.merge(user);
    }

    public List<CwSUser> findAll() {
        var query = entityManager.createQuery("FROM CwSUser", CwSUser.class);
        return query.getResultList();
    }

    public Optional<CwSUser> findByEmail(String email) {
        return entityManager
                .createQuery("CwSUser.findbyEmail", CwSUser.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst();
    }

}
