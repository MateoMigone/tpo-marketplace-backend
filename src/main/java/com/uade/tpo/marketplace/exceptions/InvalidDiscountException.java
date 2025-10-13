package com.uade.tpo.marketplace.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "El campo discount debe ser un numero entre 0 y 1.")
public class InvalidDiscountException extends Exception{
}
