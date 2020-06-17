package com.olmedilla.training.containers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GeneradorUsuarios {
	   public static String generadorPasswords() {
	        int leftLimit = 48; // numeral '0'
	        int rightLimit = 122; // letter 'z'
	        int targetStringLength = 10;
	        Random random = new Random();
	        return random.ints(leftLimit, rightLimit + 1)
	                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	                .limit(targetStringLength)
	                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	                .toString();
	    }

	    public static void main(String[] args) {
		// write your code here
	        // numero de usuarios
	        // nombre inicial
	        int numeroUsuarios = 0;
	        String nombreUsuarios = "";
	        String descripcion="curso";
	        if (args.length>1) {
	            try {
	                numeroUsuarios = Integer.parseInt(args[0]);
	            } catch (NumberFormatException e) {
	                System.err.println("Argumento" + args[0] + " debe ser un entero.");
	                System.exit(1);
	            }
	            nombreUsuarios = args[1];
	        } else {
	            System.err.println("argumentos: <numero de usuarios> <nombreusuarios>");
	            System.exit(1);
	        }
	        try {
	            File fichero = new File("salida");
	            if (fichero.createNewFile()) {
	                System.out.println("Fichero salida creado: " + fichero.getName());
	                FileWriter writer = new FileWriter("salida");
	                for (int i=0;i<numeroUsuarios;i++) {
	                    //curso2:passwd:userID:groupID:curso:/home/curso2:/bin/dosh
	                    int userID = 40000+i;
	                    int groupID = 994;
	                    writer.write(nombreUsuarios+i+":" +
	                            generadorPasswords()+":" +
	                            userID+":" +
	                            groupID+":" +
	                            descripcion+":" +
	                            "/home/"+nombreUsuarios+i+":" +
	                            "/bin/dosh\n");
	                }
	                writer.close();
	            } else {
	                System.out.println("Fichero salida existe.");
	            }
	        } catch (IOException e) {
	            System.out.println("Ha ocurrido un error");
	            e.printStackTrace();
	        }
	        for (int i=0;i<numeroUsuarios;numeroUsuarios++) {

	        }

	    }
}
