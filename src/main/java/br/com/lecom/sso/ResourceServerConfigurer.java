package br.com.lecom.sso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

@EnableResourceServer
@Configuration
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {
	
	@Autowired
	private HttpSessionSecurityContextRepository httpSessionSecurityContextRepository;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.securityContext().securityContextRepository(httpSessionSecurityContextRepository)
			.and()
			.requestMatchers()
				.antMatchers("/api/**")
			.and()
				.authorizeRequests()
					.anyRequest().authenticated();
	}
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.stateless(false);
	}

}
