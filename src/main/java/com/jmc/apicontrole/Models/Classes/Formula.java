package com.jmc.apicontrole.Models.Classes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class Formula {
    Map<Double, Double> mapaFormula;

    public Formula(Map<Double, Double> mapaFormula) {
        this.mapaFormula = mapaFormula;
    }

    public void calcularValores(double pesoFinal) {
        BigDecimal peso = new BigDecimal(pesoFinal);

        for (Map.Entry<Double, Double> entry : mapaFormula.entrySet()) {
            BigDecimal chave = new BigDecimal(entry.getKey());
            BigDecimal valorCalculado = chave.multiply(peso);
            BigDecimal valorArredondado = valorCalculado.setScale(3, RoundingMode.HALF_UP);
            entry.setValue(valorArredondado.doubleValue());
        }
    }

    public Map<Double, Double> getMapaFormula() {
        return mapaFormula;
    }

}
