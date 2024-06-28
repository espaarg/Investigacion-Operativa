package com.example.demo.controladores;

import com.example.demo.enums.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins =  "*")
@RequestMapping(path = "/enum")
public class EnumController {

    @GetMapping("/ModInv")
    public List<String> getModInvValues() {
        return Arrays.stream(ModeloInventario.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @GetMapping("/CantPer")
    public List<String> getCantPerValues() {
        return Arrays.stream(CantidadPeriodo.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }


    @GetMapping("/MetPred")
    public List<String> getMetPredValues() {
        return Arrays.stream(MetodoPrediccion.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}