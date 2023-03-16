package com.my.contactbook.util;

import com.my.contactbook.entity.UserEntity;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CodeGenerator implements IdentifierGenerator {

    private Integer maxId = 0;

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        IdentifierGenerator.super.configure(type, params, serviceRegistry);
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj)
            throws HibernateException {

        UserEntity user = (UserEntity) obj;
        List<String> rolePrefixes = user.getRoles().stream().map(role -> role.getRolePrefix()).collect(Collectors.toList());
        String finalPrefix;
        if (rolePrefixes.size() > 0) {
            finalPrefix = (rolePrefixes.size() > 1) ? "GV" : rolePrefixes.get(0).toUpperCase();
        } else {
            finalPrefix = "HS";
        }
        String query = "from UserEntity u where u.userCode like '" +
                finalPrefix + "%' order by u.userCode desc";
        List<UserEntity> list = session.createQuery(query).setMaxResults(1).getResultList();

        Integer maxId = list.stream().map(a -> String.valueOf(a.getUserCode())
                        .replace(finalPrefix, ""))
                .mapToInt(a -> Integer.parseInt(a)).max().orElse(0);

        return finalPrefix + String.format("%04d", (maxId + 1));
    }

}
