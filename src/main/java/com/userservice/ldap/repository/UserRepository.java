package com.userservice.ldap.repository;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.List;

import javax.naming.Name;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.ldap.LdapName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.core.support.BaseLdapNameAware;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements BaseLdapNameAware {

    @Autowired
    private LdapTemplate ldapTemplate;
    private LdapName baseLdapPath;

    @Override
    public void setBaseLdapPath(LdapName baseLdapPath) {
        this.baseLdapPath = baseLdapPath;
    }


    public List<User> findBy(String username, String password) {
        LdapQuery q = query()
            .where("objectclass").is("person")
            .and("cn").whitespaceWildcardsLike(username)
            .and("sn").whitespaceWildcardsLike(password);
        return ldapTemplate.search(q, new UserContextMapper());
    }

    private static class UserContextMapper extends AbstractContextMapper<User> {
        public User doMapFromContext(DirContextOperations context) {
            User person = new User();
            person.setUsername(context.getStringAttribute("cn"));
            person.setPassword(context.getStringAttribute("sn"));
            person.setUid(context.getStringAttribute("uid"));
            return person;
        }
    }

}
