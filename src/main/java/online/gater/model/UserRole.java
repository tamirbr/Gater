package online.gater.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {

    ADMIN(Code.ADMIN),
    USER(Code.USER);

    private final String authority;

    UserRole(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public class Code {
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String USER = "ROLE_USER";
    }
}
