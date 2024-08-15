package br.com.lecom.sso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		// @formatter:off
//        http.requestMatchers()
//            .antMatchers("/login", "/oauth/authorize")
//            .and()
//            .authorizeRequests()
//            .anyRequest().authenticated()
//            .and()
//            .formLogin().permitAll()
//            .and()
//            .logout().permitAll();		
//        // @formatter:on
//	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// @formatter:off
		http			
			.securityContext().securityContextRepository(this.httpSessionSecurityContextRepository()).and()
			.requestMatchers()
//				.antMatchers("/login**", "/logout**", "/oauth/**")
				.anyRequest()
			.and()
			.authorizeRequests()
				.anyRequest().authenticated()
				.and()		        
			.formLogin()
				.permitAll()
				.and()
			.logout()
				.permitAll();
		// @formatter:on

	}
	
	@Bean
	public HttpSessionSecurityContextRepository httpSessionSecurityContextRepository() {
		return new HttpSessionSecurityContextRepository();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new MessageDigestPasswordEncoder("SHA-256");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
