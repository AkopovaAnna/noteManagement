package com.project.system;

import com.project.dto.UserDto;
import com.project.security.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserPermissionEvaluator {

    public boolean hasReadUserPermission(final UserDetails userDetails, final UserDto user) {
        return userDetails.getId().equals(user.getUserId());
    }

    public boolean hasCreateUserPermission(final UserDetails userDetails, final UserDto user) {
        return true;
    }

    public boolean hasUpdateUserPermission(final UserDetails userDetails, final UserDto user) {

        return userDetails.getId().equals(user.getUserId());
    }

    public boolean hasDeleteUserPermission(final UserDetails userDetails, final Integer id) {
        return userDetails.getId().equals(id);
    }
}
