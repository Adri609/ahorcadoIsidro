import java.util.Scanner;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;

public class Juego {

    // Declaro los atributos fuera del main para poder utilizarlos en varias funciones
    private static String palabra;
    private static StringBuilder progresoPalabra;
    private static int intentos;
    private static HashSet<Character> letrasUsadas = new HashSet<>(); // Se inicia el HashSet
    private static Scanner read = new Scanner(System.in);
    private static int nivel;
    private static ArrayList<String> marcas = new ArrayList<>();

    
    // Función para probar una letra
    public static boolean probarLetra(char letra) {
        if (letrasUsadas.contains(letra)) { // Comprueba si la letra ya fue usada
            System.out.println("Ya has intentado esa letra.");
            return false;
        }

        // Se añade la letra utilizada al array
        letrasUsadas.add(letra);
        boolean aciertos = false;

        // Se recorre la palabra para ver si la letra está en la palabra oculta
        for (int i = 0; i < palabra.length(); i++) {
            if (palabra.charAt(i) == letra) {
                progresoPalabra.setCharAt(i, letra); // Si la letra está en la palabra se sustituye la letra de la posición en la que coincida con la letra introducida
                aciertos = true; // En caso de acierto no se restan intentos
            }
        }

        // Se reduce el núemero de intentos en caso de que se falle la letra
        if (!aciertos) {
            intentos--;
        }

        return aciertos;
    }

    public static void main(String[] args) {
    
        // Añadimos palabras al ArrayList utilizando Collections para añadirlo todo a la vez y no poner 40 líneas de .add
        Collections.addAll(marcas, 
            "Abarth", "Acura", "Alfa Romeo", "Aston Martin", "Audi", "Bentley", "BMW", "Bugatti", 
            "Buick", "Cadillac", "Changan", "Chevrolet", "Chrysler", "Citroën", "Cupra", "Dacia", 
            "Daewoo", "Daihatsu", "Dodge", "DS Automobiles", "Ferrari", "Fiat", "Fisker", "Ford", 
            "GAC", "Geely", "Genesis", "GMC", "GreatWall", "Haval", "Honda", "Hummer", "Hyundai", 
            "Infiniti", "Isuzu", "Jaguar", "Jeep", "Karma", "Kia", "Koenigsegg", "Lada", "Lamborghini", 
            "Lancia", "Land Rover", "Lexus", "Lincoln", "Lotus", "Lucid", "Maserati", "Maybach", "Mazda", 
            "McLaren", "Mercedes-Benz", "MG", "Mini", "Mitsubishi", "Nissan", "Opel", "Pagani", "Peugeot", 
            "Polestar", "Porsche", "Proton", "RAM", "Renault", "Rezvani", "Rimac", "Rolls Royce", "Rover", 
            "Saab", "SEAT", "Skoda", "Smart", "SsangYong", "Subaru", "Suzuki", "Tata", "Tesla", "Toyota", 
            "Vauxhall", "Volkswagen", "Volvo", "Wiesmann", "Zotye"
        );

        boolean rendirse = false;
        int elegir;

        // Se selecciona una palabra aleatoria del array
        palabra = marcas.get((int) (Math.random() * marcas.size()));

        // Paso la palabra a minúscula
        palabra = palabra.toLowerCase();

        // Reemplazamos la palabra por guiones bajos y la guardamos en el StringBuilder
        progresoPalabra = new StringBuilder("_".repeat(palabra.length()));

        //Menú para elegir una dificultad con un bucle para asegurar que se seleccione una opción correcta
        do {
            System.out.println("Elige una dificultad:");
            System.out.println();
            System.out.println("1- Fácil");
            System.out.println("2- Normal");
            System.out.println("3- Difícil");
            System.out.print("Opción: ");
            nivel = read.nextInt();

            if (nivel > 1 || nivel < 3) {
                System.out.println("Por favor, elige una opción válida");
            }

            // Se le cambia el valor a los intentos según la dificultad seleccionada
            switch (nivel) {
                case 1 -> Juego.intentos = 8;
                case 2 -> Juego.intentos = 6;
                case 3 -> Juego.intentos = 3;
            }

        } while (nivel < 1 || nivel > 3);

        // Mostramos el progreso inicial
        System.out.println("Palabra a adivinar: " + progresoPalabra);
        System.out.println("Una pista: Marca de coches.");

        // Bucle del juego
        while (intentos > 0 && progresoPalabra.toString().contains("_")) {
            System.out.print("Introduce una letra: ");
            char letra = read.next().toLowerCase().charAt(0);
            
            if (probarLetra(letra)) {
                System.out.println("¡Correcto! " + progresoPalabra);
            } else {
                System.out.println("Incorrecto. Intentos restantes: " + intentos + "\n" + progresoPalabra);
            }

            System.out.println("Letras utilizadas: " + letrasUsadas);
            
            do {
                System.out.println("Te rindes?");
                System.out.println("1- Si");
                System.out.println("2- No");
                elegir = read.nextInt();

                switch (elegir) {
                    case 1 -> {
                        System.out.println("Te has rendido.");
                        rendirse = true;
                        intentos = 0;
                    }
                                       
                    case 2 -> {
                        rendirse = false;
                        continue;
                    }
                    
                    default -> {
                        System.out.println("Opción incorrecta, por favor, elige 1 o 2");
                    }
                }

            } while (elegir != 1 && elegir != 2);
        
        }

        if (progresoPalabra.toString().equals(palabra)) {
            System.out.println("Felicidades, has salvado al muñeco, tu palabra era: " + palabra);
        } else {
            System.out.println("El muñeco ha muerto por tu culpa, la palabra era: " + palabra);
        }
    }
}