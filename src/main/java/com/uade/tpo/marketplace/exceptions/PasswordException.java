package com.uade.tpo.marketplace.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Las contraseñas deben incluir al menos una letra, un número, minimo 5 caracteres y deben coincidir.")
public class PasswordException extends Exception{
}
