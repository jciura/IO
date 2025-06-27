package pl.agh.edu.io.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import pl.agh.edu.io.User.UserRepository;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Configuration
    @Order(1)
    public static class AdministratorSecurityConfig {
        @Bean
        public SecurityFilterChain administratorChain(HttpSecurity http, HandlerMappingIntrospector introspector, CorsConfigurationSource corsConfigurationSource) throws Exception {
            MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

            http.cors(cors -> cors.configurationSource(corsConfigurationSource))
                    .csrf(csrf -> csrf.disable())
                    .securityMatcher("/admin/**", "/admin*")
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(mvcMatcherBuilder.pattern("/admin/**")).hasRole("ADMINISTRATOR")
                            .requestMatchers(mvcMatcherBuilder.pattern("/admin*")).hasRole("ADMINISTRATOR")
                    )
                    .formLogin(form -> form
                            .loginPage("/loginAdmin")
                            .loginProcessingUrl("/admin_login")
                            .failureUrl("/loginAdmin?error=loginError")
                            .defaultSuccessUrl("/adminPage")
                    )
                    .logout(logout -> logout
                            .logoutUrl("/admin_logout")
                            .logoutSuccessUrl("/protectedLinks")
                            .deleteCookies("JSESSIONID")
                    )
                    .exceptionHandling(ex -> ex
                            .accessDeniedPage("/403")
                    );

            return http.build();
        }
    }

    @Configuration
    @Order(2)
    public static class CoordinatorSecurityConfig {
        @Bean
        public SecurityFilterChain coordinatorChain(HttpSecurity http, HandlerMappingIntrospector introspector, CorsConfigurationSource corsConfigurationSource) throws Exception {
            MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

            http.cors(cors -> cors.configurationSource(corsConfigurationSource))
                    .csrf(csrf -> csrf.disable())
                    .securityMatcher("/koordynator/**", "/koordynator*")
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(mvcMatcherBuilder.pattern("/koordynator/**")).hasRole("KOORDYNATOR")
                            .requestMatchers(mvcMatcherBuilder.pattern("/koordynator*")).hasRole("KOORDYNATOR")
                    )
                    .formLogin(form -> form
                            .loginPage("/loginKoordynator")
                            .loginProcessingUrl("/koordynator_login")
                            .failureUrl("/loginKoordynator?error=loginError")
                            .defaultSuccessUrl("/koordynatorPage")
                    )
                    .logout(logout -> logout
                            .logoutUrl("/koordynator_logout")
                            .logoutSuccessUrl("/protectedLinks")
                            .deleteCookies("JSESSIONID")
                    )
                    .exceptionHandling(ex -> ex
                            .accessDeniedPage("/403")
                    );

            return http.build();
        }
    }

    @Configuration
    @Order(3)
    public static class RepresentativeSecurityConfig {
        @Bean
        public SecurityFilterChain representativeChain(HttpSecurity http, HandlerMappingIntrospector introspector, CorsConfigurationSource corsConfigurationSource) throws Exception {
            MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

            http.cors(cors -> cors.configurationSource(corsConfigurationSource))
                    .csrf(csrf -> csrf.disable())
                    .securityMatcher("/starosta/**", "/starosta*")
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(mvcMatcherBuilder.pattern("/starosta/**")).hasRole("STAROSTA")
                            .requestMatchers(mvcMatcherBuilder.pattern("/starosta*")).hasRole("STAROSTA")
                    )
                    .formLogin(form -> form
                            .loginPage("/loginStarosta")
                            .loginProcessingUrl("/starosta_login")
                            .failureUrl("/loginStarota?error=loginError")
                            .defaultSuccessUrl("/starostaPage")
                    )
                    .logout(logout -> logout
                            .logoutUrl("/starota_logout")
                            .logoutSuccessUrl("/protectedLinks")
                            .deleteCookies("JSESSIONID")
                    )
                    .exceptionHandling(ex -> ex
                            .accessDeniedPage("/403")
                    );

            return http.build();
        }
    }

    @Configuration
    @Order(4)
    public static class LecturerSecurityConfig {
        @Bean
        public SecurityFilterChain lecturerChain(HttpSecurity http, HandlerMappingIntrospector introspector, CorsConfigurationSource corsConfigurationSource) throws Exception {
            MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

            http.cors(cors -> cors.configurationSource(corsConfigurationSource))
                    .csrf(csrf -> csrf.disable())
                    .securityMatcher("/prowadzacy/**", "/prowadzacy*")
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(mvcMatcherBuilder.pattern("/prowadzacy/**")).hasRole("PROWADZACY")
                            .requestMatchers(mvcMatcherBuilder.pattern("/prowadzacy*")).hasRole("PROWADZACY")
                    )
                    .formLogin(form -> form
                            .loginPage("/loginProwadzacy")
                            .loginProcessingUrl("/prowadzacy_login")
                            .failureUrl("/loginProwadzacy?error=loginError")
                            .defaultSuccessUrl("/prowadzacyPage")
                    )
                    .logout(logout -> logout
                            .logoutUrl("/prowadzacy_logout")
                            .logoutSuccessUrl("/protectedLinks")
                            .deleteCookies("JSESSIONID")
                    )
                    .exceptionHandling(ex -> ex
                            .accessDeniedPage("/403")
                    );

            return http.build();
        }
    }

    @Configuration
    @Order(99)
    public static class DefaultSecurityConfig {
        @Bean
        public SecurityFilterChain defaultChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
            http.cors(cors -> cors.configurationSource(corsConfigurationSource))
                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth
                            .anyRequest().permitAll()
                    );

            return http.build();
        }
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            pl.agh.edu.io.User.User appUser = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            return org.springframework.security.core.userdetails.User
                    .withUsername(appUser.getEmail())
                    .password(appUser.getPassword())
                    .roles(appUser.getRole().name())
                    .build();
        };
    }

    @Bean
    public static PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "https://io-75z46jiff-jciuras-projects-bb0ec7c8.vercel.app"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}