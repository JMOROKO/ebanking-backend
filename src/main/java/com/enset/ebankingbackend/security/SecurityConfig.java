package com.enset.ebankingbackend.security;

import com.enset.ebankingbackend.security.services.UserDetailServiceImpl;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    /**
     * Suite pour la sec
     * 1- JDBC Authentication : https://www.youtube.com/watch?v=Haz3wLiQ5-0
     * 2- UserDetailService : https://www.youtube.com/watch?v=RTiS9ygyYs4
     * Role et Authorities : https://www.youtube.com/watch?v=2HYeoCvFmbI
     */
    @Value("${jwt.secret}")
    private String secretKey;

    private UserDetailServiceImpl userDetailServiceImpl;

    public SecurityConfig(UserDetailServiceImpl userDetailServiceImpl) {
        this.userDetailServiceImpl = userDetailServiceImpl;
    }


    // strategie in memory
    //@Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(){
        PasswordEncoder passwordEncoder = passwordEncoder();
        return new InMemoryUserDetailsManager(
                User.withUsername("user1").password(passwordEncoder.encode("12345")).authorities("USER").build(),
                User.withUsername("admin").password(passwordEncoder.encode("12345")).authorities("USER", "ADMIN").build()
        );
    }

    //strategie JDBC
    //@Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource){ //DataSource dataSource represente ici la base de données
        // pour utiliser JWT il faut creer deux tables dans la base de données USER et ROLE
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // @TODO 8. partie de la stratégie userdetails modification de cette méthode
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .sessionManagement( sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
                .csrf( csrf -> csrf.disable() )
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests( ar -> ar.requestMatchers(
                        "/auth/login/**",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/swagger-ui.html").permitAll() )
                .authorizeHttpRequests( ar -> ar.anyRequest().authenticated() )
                //.httpBasic( Customizer.withDefaults() )
                .oauth2ResourceServer(oa -> oa.jwt(Customizer.withDefaults()))
                .userDetailsService(userDetailServiceImpl) //une fois qu'un utilisateur saisi son username et son password spring sec fait appel à l'implémentation de userDetailServiceImpl il va ensuite comparer les mot de passe saisi par l'utilisateur afin de le laisser passer ou non
                .build();
    }

    @Bean
    JwtEncoder jwtEncoder(){
        return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey.getBytes()));
    }

    @Bean
    JwtDecoder jwtDecoder(){
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "RSA");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService){ //UserDetailsService userDetailsService permet d'injecter le userdetails service qui existe dans le contexte
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder()); //definition du password encoder à utiliser
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); //accepter toutes les origins de requete
        corsConfiguration.addAllowedMethod("*"); //accepter toutes les méthodes
        corsConfiguration.addAllowedHeader("*"); //
        //corsConfiguration.setExposedHeaders(List.of("x-auth-token")); //authoriser javascript à lire un header spécifique
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
