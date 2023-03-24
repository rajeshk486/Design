package com.reactive.springboot.restServices;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class fluxexampleTest {
    fluxexample testObj= new fluxexample();

    @Test
    void fluxTest() {
        var nameflux = testObj.nameFlux();
        StepVerifier.create(nameflux).expectNext("rajesh","ramya","rathna").verifyComplete();
    }

    @Test
    void fluxTest1() {
        var nameflux = testObj.nameFlux();
        List<String> str=new ArrayList<String>();
        StepVerifier.create(nameflux).expectNext("rajesh").expectNextCount(2).verifyComplete();
    }
}