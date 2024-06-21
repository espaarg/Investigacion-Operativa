package com.example.demo.controladores;

import com.example.demo.entidades.PrediccionDemanda;
import com.example.demo.servicios.PrediccionDemandaServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/PrediccionDemanda")

public class PrediccionDemandaController extends BaseControllerImpl<PrediccionDemanda, PrediccionDemandaServiceImpl> {
}
