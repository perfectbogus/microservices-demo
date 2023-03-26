package com.microservices.demo.elastic.query.service.transformer;

import com.microservices.demo.elastic.query.service.dataaccess.entity.UserPermission;
import com.microservices.demo.elastic.query.service.security.PermissionType;
import com.microservices.demo.elastic.query.service.security.TwitterQueryUser;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Log4j2
public class UserPermissionsToUserDetailTransformer {

    public TwitterQueryUser getUserDetails(List<UserPermission> userPermissions) {
        log.traceEntry("()");
        userPermissions.forEach(UserPermission::toString);
        return TwitterQueryUser.builder()
                .username(userPermissions.get(0).getUsername())
                .permissions(userPermissions.stream()
                        .collect(
                                Collectors.toMap(
                                UserPermission::getDocumentId,
                                permission -> PermissionType.valueOf(permission.getPermissionType()))
                        )
                )
                .build();

    }
}
