package com.lucassabit.projetomatricula.security;

import java.util.UUID;

public class TokenGenerate {
    public static String generateToken() {
    	UUID uuid = UUID.randomUUID();
    	return uuid.toString();
    }
}

