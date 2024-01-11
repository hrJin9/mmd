package com.mmd.security.util;

import com.mmd.security.MemberDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ClientMemberLoader {
    public static String getClientId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication instanceof MemberDetails) ? ((MemberDetails) authentication).getId() : null;
    }
}
