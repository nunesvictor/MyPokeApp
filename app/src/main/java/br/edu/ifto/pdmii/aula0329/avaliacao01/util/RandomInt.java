package br.edu.ifto.pdmii.aula0329.avaliacao01.util;

import java.util.Random;

public class RandomInt {
    public static int between(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }
}
