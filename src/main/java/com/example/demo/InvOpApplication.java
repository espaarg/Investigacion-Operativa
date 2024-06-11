package com.example.demo;

import com.example.demo.entidades.Articulo;
import com.example.demo.repositorios.ArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

@EnableJpaRepositories
@SpringBootApplication
public class InvOpApplication {

	public static void main(String[] args) {

		SpringApplication.run(InvOpApplication.class, args);
		System.out.println("Hola, estoy andando bien");
	}



}
