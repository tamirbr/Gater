package online.gater.config.security;

public class AllowedPages {
	
    public static final String[] PAGES = {
    		"/resources/**",
    		"/static/materialize/**",
    		"/materialize/**",
    		"/resources/",
    		"/login**",
    		"/",
    		"/register",
    		"/about",
    		"/contact"
    		};
    
    public static final String[] ADMIN = {
    		"/admin-panel/**"
    		};
    
    public static final String[] USER = {
    		"/panel/**",
    		"/profile/**"
    		};
}
