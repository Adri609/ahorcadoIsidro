import java.util.Scanner;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;

public class Juego {

    // Declaro los atributos fuera del main para poder utilizarlos en varias funciones
    private static String palabra;
    private static StringBuilder progresoPalabra;
    private static int intentos;
    private static HashSet<Character> letrasUsadas = new HashSet<>(); // Inicializamos el HashSet
    private static Scanner read = new Scanner(System.in);
    private static int nivel;
    private static ArrayList<String> marcas = new ArrayList<>();

    
    // Función para probar una letra
    public static boolean probarLetra(char letra) {
        if (letrasUsadas.contains(letra)) { // Comprueba si la letra ya fue usada
            System.out.println("Ya has intentado esa letra.");
            return false; 
        }

        // Añadimos la letra utilizada al array
        letrasUsadas.add(letra);
        boolean aciertos = false;

        // Se recorre la palabra para ver si la letra está en la palabra oculta
        for (int i = 0; i < palabra.length(); i++) {
            if (palabra.charAt(i) == letra) {
                progresoPalabra.setCharAt(i, letra);
                aciertos = true;
            }
        }

        // Si no se ha acertado, se reduce el número de intentos
        if (!aciertos) {
            intentos--;
        }

        return aciertos; // Devolvemos si la letra es correcta o no
    }

    public static void main(String[] args) {
    
        // Añadimos palabras al ArrayList
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

        // Se selecciona una palabra aleatoria del array
        palabra = marcas.get((int) (Math.random() * marcas.size()));

        // Paso la palabra a minúscula
        palabra = palabra.toLowerCase();

        // Reemplazamos la palabra por guiones bajos y la guardamos en el StringBuilder
        progresoPalabra = new StringBuilder("_".repeat(palabra.length()));

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
                System.out.println("Incorrecto. Intentos restantes: " + intentos);
            }

            System.out.println("Letras utilizadas: " + letrasUsadas);
        }

        if (progresoPalabra.toString().equals(palabra)) {
            System.out.println("Felicidades, has salvado al muñeco, tu palabra era: " + palabra);
        } else {
            System.out.println("El muñeco ha muerto por tu culpa, la palabra era: " + palabra);
        }
    }
}