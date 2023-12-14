package View;

import Model.CRUD;
import java.util.Scanner;

public class Menu {

    private static Scanner scanner = new Scanner(System.in);

    public static void menuPrincipal() {
        CRUD.iniciarSession();

        String eleccion = "";

        do {
            System.out.println("A. Autor");
            System.out.println("B. Libro");
            System.out.println("X. Salir");
            System.out.print("Elige una opción: ");

            eleccion = scanner.nextLine().toUpperCase();

            switch(eleccion) {
                case "A":
                    baseDatosCRUD("autor");
                    break;
                case "B":
                    baseDatosCRUD("libro");
                    break;
                case "X":
                    System.out.println("Has salido de la APP");
                    break;
                default:
                    System.out.print("Elije una opción válida");
            }
        } while (!eleccion.equals("X"));

        CRUD.cerrarSession();

    }

    private static void menuSecundario(String eleccion) {
        System.out.println("\nA. Crear " + eleccion);
        System.out.println("B. Actualizar " + eleccion);
        System.out.println("C. Eliminar " + eleccion);
        System.out.println("X. Volver");
        System.out.print("Elige una opcón: ");
    }

    private static void baseDatosCRUD(String opcion) {
        String eleccion = "";

        do {
            System.out.print("\n\n---------- TABLA " + opcion.toUpperCase() + " ----------\n");
            CRUD.mostrarRegistros(opcion);

            menuSecundario(opcion);

            eleccion = scanner.nextLine().toUpperCase();

            switch(eleccion) {

                case "A": // Crear
                    System.out.print("\n\n---------- TABLA " + opcion.toUpperCase() + " ----------\n");
                    CRUD.mostrarRegistros(opcion);

                    if (opcion.matches("autor")){
                        System.out.print("Inserta el nombre del autor: ");
                        String autor = scanner.nextLine().toUpperCase();
                        CRUD.crearRegistro(opcion, autor, "");
                    } else {
                        System.out.print("Inserta el título del libro: ");
                        String titulo = scanner.nextLine().toUpperCase();
                        System.out.print("\nAUTORES REGISTRADOS:\n");
                        CRUD.mostrarRegistros("autor");
                        System.out.print("Inserta el nombre del autor en caso de no existir se creará uno nuevo: ");
                        String autor = scanner.nextLine().toUpperCase();
                        CRUD.crearRegistro(opcion, autor, titulo);
                    }

                    break;
                case "B": // Actualizar
                    System.out.print("\n\n---------- TABLA " + opcion.toUpperCase() + " ----------\n");
                    CRUD.mostrarRegistros(opcion);
                    if (opcion.matches("autor")){
                        System.out.print("ID del AUTOR a modificar: ");
                        String idRegistro = scanner.nextLine().toUpperCase();
                        System.out.print("Nuevo NOMBRE del AUTOR: ");
                        String autor = scanner.nextLine().toUpperCase();
                        CRUD.actualizarRegistro(opcion, idRegistro, autor);
                    } else {
                        System.out.print("ID del TITULO a modificar: ");
                        String idRegistro = scanner.nextLine().toUpperCase();
                        System.out.print("Nuevo TITULO del LIBRO: ");
                        String titulo = scanner.nextLine().toUpperCase();
                        CRUD.actualizarRegistro(opcion, idRegistro, titulo);
                    }
                    break;
                case "C": // Eliminar
                    System.out.print("\n\n---------- TABLA " + opcion.toUpperCase() + " ----------\n");
                    CRUD.mostrarRegistros(opcion);

                    if (opcion.matches("autor")){
                        System.out.print("ID de autor a eliminar: ");
                        String autor = scanner.nextLine().toUpperCase();
                        CRUD.eliminar(autor, opcion);
                    } else {
                        System.out.print("ID del libro a eliminar: ");
                        String libro = scanner.nextLine().toUpperCase();
                        CRUD.eliminar(libro, opcion);
                    }
                    break;
                default:
                    if (!eleccion.matches("X")){
                    System.out.println("Elije una opción válida");
                    }
            }
        } while (!eleccion.equals("X"));
    }
}
