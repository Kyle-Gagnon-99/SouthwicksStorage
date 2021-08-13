package com.southwicksstorage.southwicksstorage.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.southwicksstorage.southwicksstorage.constants.Constants;
import com.southwicksstorage.southwicksstorage.constants.Roles;
import com.southwicksstorage.southwicksstorage.services.CustomUserDetailsService;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	private static final String MANAGER = Roles.MANAGER_ROLE.getRole();
	private static final String TEAM_MEMBER = Roles.TEAM_MEMBER_ROLE.getRole();
	
	@Autowired
	CustomUserDetailsService userDetailsService;
	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
	    return new HttpSessionEventPublisher();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if(!Constants.DEBUG_MODE) {
			http.csrf().disable().authorizeRequests()
			.antMatchers("/").hasAnyAuthority(MANAGER, TEAM_MEMBER)
			.antMatchers("/auth/resetpassword/**").hasAnyAuthority(MANAGER, TEAM_MEMBER)
			.antMatchers("/notification/**").hasAnyAuthority(MANAGER, TEAM_MEMBER)
			.antMatchers("/view/standItem/**").hasAnyAuthority(MANAGER, TEAM_MEMBER)
			.antMatchers("/settings/**").hasAnyAuthority(MANAGER, TEAM_MEMBER)
			.antMatchers("/view/storageItem/**").hasAnyAuthority(MANAGER)
			.antMatchers("/view/vendor/**").hasAnyAuthority(MANAGER)
			.antMatchers("/view/auditLog/**").hasAnyAuthority(MANAGER)
			.antMatchers("/view/users/**").hasAnyAuthority(MANAGER)
			.antMatchers("/view/stand/**").hasAnyAuthority(MANAGER)
			.antMatchers("/create/user/**").hasAnyAuthority(MANAGER)
			.antMatchers("/create/storageItem/**").hasAnyAuthority(MANAGER)
			.antMatchers("/create/stand/**").hasAnyAuthority(MANAGER)
			.antMatchers("/create/typeOfStorage/**").hasAnyAuthority(MANAGER)
			.antMatchers("/create/vendor/**").hasAnyAuthority(MANAGER)
			.antMatchers("/report/**").hasAnyAuthority(MANAGER)
			.and().formLogin().loginPage("/auth/login").permitAll().and().logout().permitAll();
		}
	}

}
