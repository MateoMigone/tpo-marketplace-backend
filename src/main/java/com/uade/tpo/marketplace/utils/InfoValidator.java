package com.uade.tpo.marketplace.utils;

import java.util.regex.Pattern;

public class InfoValidator {
    public static boolean isValidPassword(String password, String passwordRepeat) {
        // 1. Verificar si las contraseñas son iguales
        if (!password.equals(passwordRepeat)) {
            System.out.println("Error: Las contraseñas no coinciden.");
            return false;
        }

        // 2. Verificar el largo mínimo de 5 caracteres
        if (password.length() < 5) {
            System.out.println("Error: La contraseña debe tener al menos 5 caracteres.");
            return false;
        }

        // 3. Verificar si contiene al menos una letra y un número
        boolean tieneLetra = false;
        boolean tieneNumero = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                tieneLetra = true;
            }
            if (Character.isDigit(c)) {
                tieneNumero = true;
            }
            // Si ya encontramos una letra y un número, podemos salir del bucle
            if (tieneLetra && tieneNumero) {
                break;
            }
        }

        if (!tieneLetra) {
            System.out.println("Error: La contraseña debe contener al menos una letra.");
            return false;
        }
        if (!tieneNumero) {
            System.out.println("Error: La contraseña debe contener al menos un número.");
            return false;
        }

        // Si todas las validaciones pasan, la contraseña es válida
        return true;
    }

    public static boolean isValidEmail(String email) {
        // Expresión regular para validar el formato de email
        // ^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        // Compila regex a objeto Pattern
        Pattern pattern = Pattern.compile(regex);

        // Verifica si el email no es nulo y si coincide con el patrón
        return email != null && pattern.matcher(email).matches();
    }

    public static boolean isValidStock(Integer stock){
        return stock >= 0;
    }

    public static boolean isValidPrice(Double price){
        return price > 0;
    }

    public static boolean isValidDiscount(Double discount){
        return discount >= 0 & discount <= 1 ;
    }

}
