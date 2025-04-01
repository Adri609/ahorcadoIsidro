import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

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

    // Constructor para el juego donde irán las palabras del arraylist
    public Juego () {
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
    }

    // Función para elegir jugadores
    public void jugadores() {
        int opcion;
        do {
            System.out.println("Elige el número de jugadores:");
            System.out.println("1- 1 jugador");
            System.out.println("2- 2 jugadores");

            opcion = read.nextInt();
            read.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    // Se recoge la palabra aleatoria del array en minúsculas
                    palabra = marcas.get((int) (Math.random() * marcas.size())).toLowerCase();
                    break;

                case 2:
                    JPasswordField passwordField = new JPasswordField();
                    JFrame frame = new JFrame("Introducir palabra oculta");
                    frame.setAlwaysOnTop(true); // Lo hace visible por sobre el IDE.

                    int option = JOptionPane.showConfirmDialog(frame, passwordField, "Introduce la palabra oculta", JOptionPane.OK_CANCEL_OPTION);

    
                    if (option == JOptionPane.OK_OPTION) {
                    palabra = new String(passwordField.getPassword()).toLowerCase();
                    } else {
                    System.out.println("No se ha introducido ninguna palabra. Volviendo al menú.");
                    opcion = 0; // Esto fuerza la repetición del bucle para elegir de nuevo.
                    }

                break;

                default:
                    System.out.println("Por favor, elige una opción válida");
            }
        } while (opcion < 1 || opcion > 2);
    }

    // Función para elegir una dificultad
    public void seleccionarDificultad() {   
        do {
            System.out.println("Elige una dificultad:");
            System.out.println("1- Fácil");
            System.out.println("2- Normal");
            System.out.println("3- Difícil");
            System.out.print("Opción: ");
            try {
                nivel = Integer.parseInt(read.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Y la de elegir algo que exista te la sabes?");
                continue;
            }

            if (nivel < 1 || nivel > 3) {
                System.out.println("Por dios, elige una opción válida y deja de hacer el tonto...");
            }
        } while (nivel < 1 || nivel > 3);

        intentos = switch (nivel) {
            case 1 -> 8;
            case 2 -> 6;
            case 3 -> 3;
            default -> 6;
        };
    }

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

    // Función para iniciar la partida
    public void partida () {

        jugadores(); // Llamo a la función para elegir el número de jugadores
        seleccionarDificultad(); // Llamo a la función para elegir la dificultad

        progresoPalabra = new StringBuilder();
        for (char c : palabra.toCharArray()) {
            progresoPalabra.append(c == ' ' ? ' ' : '_');
        }

        System.out.println("Palabra a adivinar: " + progresoPalabra);

        // Bucle del juego
        while (intentos > 0 && progresoPalabra.toString().contains("_")) {
            System.out.print("Introduce una letra (o escribe 'rendirse' para abandonar): ");
            String entrada = read.next().toLowerCase();

            if (entrada.equals("rendirse")) {
            System.out.println("Cobarde!, la palabra era: " + palabra);
            return; // Se termina la partida
            }

            // Compruebo si se ha introducido alguna letra
            if (entrada.isEmpty()) {
                System.out.println("No has ingresado ninguna letra, prueba otra vez:");
            continue;
            }

            char letra = entrada.charAt(0);

            if (probarLetra(letra)) {
                System.out.println("¡Correcto! " + progresoPalabra);
            } else {
                System.out.println("Incorrecto. Intentos restantes: " + intentos);
            }
        }

        // Muestro un mensaje de victoria o derrota al finalizar la partida
        if (!progresoPalabra.toString().contains("_")) {

        System.out.println("Felicidades, has salvado a Billy, la palabra era: " + palabra);

        } else {
            System.out.println("Desgraciado, has matado a Billy! la palabra era: " + palabra);
        }
    }

    public static void main(String[] args) {
    
        Juego juego = new Juego();
        juego.partida();
        
    }
}