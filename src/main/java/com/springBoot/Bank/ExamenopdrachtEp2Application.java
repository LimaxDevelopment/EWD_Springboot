package com.springBoot.Bank;

import java.util.Locale;

import org.springframework.boot.SpringApplication;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import perform.PerformRestSports;
import service.MyUserDetailsService;
import validator.DateTimeRangeValidator;
import validator.DifferentFirstLastDigitValidator;

@SpringBootApplication
@EnableJpaRepositories("repository")
@EntityScan("domain")
public class ExamenopdrachtEp2Application implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(ExamenopdrachtEp2Application.class, args);
		
		try {
			new PerformRestSports();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/sports/overview");
	}
	
	@Bean
	UserDetailsService myUserDetailsService() {
		return new MyUserDetailsService();
	}
	
	@Bean
	DateTimeRangeValidator dateRangeValidator() {
		return new DateTimeRangeValidator();
	}
	
	@Bean
	DifferentFirstLastDigitValidator digitValidator() {
		return new DifferentFirstLastDigitValidator();
	}
	
	@Bean
	LocaleResolver localeResolver() {
	    SessionLocaleResolver slr = new SessionLocaleResolver();
	    slr.setDefaultLocale(Locale.ENGLISH);
	    return slr;
	}
	
	@Bean
	LocaleChangeInterceptor localeChangeInterceptor() {
	   LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
	   lci.setParamName("lang");
	   return lci;
	}
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
