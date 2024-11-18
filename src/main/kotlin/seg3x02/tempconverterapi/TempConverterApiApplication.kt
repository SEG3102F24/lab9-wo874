package seg3x02.tempconverterapi

import org.springframework.boot.autoconfigure.SpringBootApplication

import org.springframework.context.annotation.Bean

import org.springframework.security.config.annotation.web.builders.HttpSecurity

import org.springframework.security.core.userdetails.User

import org.springframework.security.web.SecurityFilterChain

import org.springframework.security.core.userdetails.UserDetailsService

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import org.springframework.security.crypto.password.PasswordEncoder

import org.springframework.security.core.authority.SimpleGrantedAuthority

import org.springframework.security.provisioning.InMemoryUserDetailsManager

import org.springframework.boot.runApplication



@SpringBootApplication
class TempConverterApiApplicationclass TemperatureConversionApplication {

    @Bean
	
    fun getEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }


    @Bean
    fun setupUsers(encoder: PasswordEncoder): UserDetailsService {
	    
        val firstUser = User.withUsername("firstUser")
            .password(encoder.encode("password1"))
            .authorities(SimpleGrantedAuthority("ROLE_USER"))
            .build()

        val secondUser = User.withUsername("secondUser")
            .password(encoder.encode("password2"))
            .authorities(SimpleGrantedAuthority("ROLE_USER"))
            .build()

        return InMemoryUserDetailsManager(firstUser, secondUser)
    }

    @Bean
    fun securityConfig(security: HttpSecurity): SecurityFilterChain {
        security
	    
            .csrf().disable()
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .httpBasic()
        return security.build()
    }
}

fun main(args: Array<String>) {
	runApplication<TempConverterApiApplication>(*args)
}

