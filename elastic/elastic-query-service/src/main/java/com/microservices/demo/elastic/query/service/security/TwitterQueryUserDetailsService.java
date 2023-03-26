package com.microservices.demo.elastic.query.service.security;

import com.microservices.demo.elastic.query.service.business.QueryUserService;
import com.microservices.demo.elastic.query.service.transformer.UserPermissionsToUserDetailTransformer;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class TwitterQueryUserDetailsService implements UserDetailsService {
    private final QueryUserService queryUserService;
    private final UserPermissionsToUserDetailTransformer userPermissionsToUserDetailTransformer;

    public TwitterQueryUserDetailsService(QueryUserService queryUserService, UserPermissionsToUserDetailTransformer userPermissionsToUserDetailTransformer) {
        this.queryUserService = queryUserService;
        this.userPermissionsToUserDetailTransformer = userPermissionsToUserDetailTransformer;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.traceEntry();
        return queryUserService
                .findAllPermissionByUsername(username)
                .map(userPermissionsToUserDetailTransformer::getUserDetails)
                .orElseThrow(
                        () -> new UsernameNotFoundException("No user found with name " + username)
                );
    }
}
