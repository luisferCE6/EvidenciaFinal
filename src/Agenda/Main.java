/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agenda;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author ferna
 */
public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //CONSTANTES
        final String PACIENTES_FILE = "listaPacientes.txt";
        final String DOCTORES_FILE = "listaDoctores.txt";
        final String CITAS_FILE = "listaCitas.txt";
        final String PASS_ADMIN_FILE = "listaPassAdmin.txt";
        final String CITAS_RELACIONADAS_FILE = "listaCitasRelacionadas.txt";
        // JAVA UTIL
        ArrayList<Paciente> listaPacientes = new ArrayList();
        ArrayList<Doctor> listaDoctores = new ArrayList();
        ArrayList<Cita> listaCitas = new ArrayList();
        HashMap<String, String> passAdmin = new HashMap();
        HashMap<Cita, Doctor> citasRelacionadas = new HashMap<>();

        //SERIALIZADOR
        WriterReader writerReader = new WriterReader();
        //VARIABLES
        int opc;
        boolean flag = false;

        //CARGAMOS ARCHIVOS EXISTENTES
        if (new File(PACIENTES_FILE).exists()) {
            listaPacientes = (ArrayList<Paciente>) writerReader.leerObjeto(PACIENTES_FILE);
            System.out.println("Se cargo el fichero: " + PACIENTES_FILE);
        }
        if (new File(DOCTORES_FILE).exists()) {
            listaDoctores = (ArrayList<Doctor>) writerReader.leerObjeto(DOCTORES_FILE);
            System.out.println("Se cargo el fichero: " + DOCTORES_FILE);
        }
        if (new File(CITAS_FILE).exists()) {
            listaCitas = (ArrayList<Cita>) writerReader.leerObjeto(CITAS_FILE);
            System.out.println("Se cargo el fichero: " + CITAS_FILE);
        }
        if (new File(PASS_ADMIN_FILE).exists()) {
            passAdmin = (HashMap<String, String>) writerReader.leerObjeto(PASS_ADMIN_FILE);
            System.out.println("Se cargo el fichero: " + PASS_ADMIN_FILE);
        }
        if (new File(PASS_ADMIN_FILE).exists() == false) {
            System.out.println("Por favor registre un usario: ");
            String usuario = scanner.nextLine();
            System.out.println("Por favor registre una contraseña: ");
            String contraseña = scanner.nextLine();
            passAdmin.put(usuario, contraseña);
            writerReader.escribirObjeto(passAdmin, PASS_ADMIN_FILE);
            System.out.println("Se ha guardado con éxito. Reinice el programa.");
            System.exit(0);
        }
        if (new File(CITAS_RELACIONADAS_FILE).exists()) {
            citasRelacionadas = (HashMap<Cita, Doctor>) writerReader.leerObjeto(CITAS_RELACIONADAS_FILE);
            System.out.println("Se cargo el fichero: " + CITAS_RELACIONADAS_FILE);
        }

        //INICIAMOS PROGRAMA
        if (solicitarCredenciales(passAdmin) == true) {
            do {

                opc = desplegarMenu();
                switch (opc) {
                    case 1:
                        System.out.println("Doctores en lista: " + listaDoctores.toString());
                        listaDoctores.add(altaDoctor());
                        writerReader.escribirObjeto(listaDoctores, DOCTORES_FILE);
                        break;
                    case 2:
                        System.out.println("Pacientes en lista: " + listaPacientes.toString());
                        listaPacientes.add(altaPaciente());
                        writerReader.escribirObjeto(listaPacientes, PACIENTES_FILE);
                        break;
                    case 3:
                        System.out.println("Citas en lista: " + listaCitas.toString());
                        listaCitas.add(crearCita());
                        writerReader.escribirObjeto(listaCitas, CITAS_FILE);
                        break;
                    case 4:
                        System.out.println("Citas relacionadas con Doctores: " + citasRelacionadas.toString());
                        Cita cita = relacionarCita(listaCitas);
                        Doctor doctor = relacionarDoctor(listaDoctores);
                        if (cita != null && doctor != null) {
                            citasRelacionadas.put(cita, doctor);
                            System.out.println("Se ha relacionado exitosamente");
                            writerReader.escribirObjeto(citasRelacionadas, CITAS_RELACIONADAS_FILE);
                        } else {
                            System.out.println("No se pudo relacionar la cita");
                        }
                        break;
                    case 5:
                        flag = true;
                        break;
                }
            } while (flag == false);
        }

    }

    public static int desplegarMenu() {
        String entrada;
        System.out.println("¡Bienvenido!\nPor favor selecciona una opcion del menú.\n"
                + "1. Dar de alta doctores.\n"
                + "2. Dar de alta pacientes.\n"
                + "3. Crear una cita con fecha y hora.\n"
                + "4. Relacionar una cita con un doctor.\n"
                + "5. Salir.");
        entrada = scanner.nextLine();
        if (procesarEntrada(entrada) == true) {
            return Integer.parseInt(entrada);
        } else {
            return 0;
        }
    }

    public static boolean procesarEntrada(String opc) {
        try {
            Integer.parseInt(opc);
            return true;
        } catch (NumberFormatException ex) {
            System.out.println("Ingresa un numero");
            return false;
        }
    }

    public static Doctor altaDoctor() {
        Doctor doctor = new Doctor();
        System.out.println("Por favor ingresa el id: ");
        doctor.setId(scanner.nextLine());
        System.out.println("Por favor ingresa el nombre: ");
        doctor.setNombre(scanner.nextLine());
        System.out.println("Por favor ingresa la especialidad: ");
        doctor.setEspecialidad(scanner.nextLine());
        System.out.println("Se ha dado de alta exitosamente.");
        return doctor;
    }

    public static Paciente altaPaciente() {
        Paciente paciente = new Paciente();
        System.out.println("Por favor ingresa el id: ");
        paciente.setId(scanner.nextLine());
        System.out.println("Por favor ingresa el nombre: ");
        paciente.setNombre(scanner.nextLine());
        System.out.println("Se ha dado de alta exitosamente.");
        return paciente;
    }

    public static Cita crearCita() {
        Cita cita = new Cita();
        System.out.println("Por favor ingresa el id: ");
        cita.setId(scanner.nextLine());
        System.out.println("Por favor ingresa la fecha con formato dd/mm/aaaa: ");
        cita.setFecha(scanner.nextLine());
        System.out.println("Por favor ingresa la hora con formato 24 hrs: ");
        cita.setHora(scanner.nextLine());
        System.out.println("Por favor ingresa el motivo: ");
        cita.setMotivo(scanner.nextLine());
        System.out.println("Se ha dado de alta exitosamente.");
        return cita;
    }

    public static Cita relacionarCita(ArrayList<Cita> listaCitas) {
        Cita cita;

        System.out.println("Lista de citas agendadas: " + listaCitas.toString());
        System.out.println("Escribe el id de la cita a relacionar: ");
        String entrada = scanner.nextLine();
        for (int i = 0; i < listaCitas.size(); i++) {
            cita = listaCitas.get(i);
            if (cita.getId().equals(entrada)) {
                System.out.println("Se ha encontrado.");
                return cita;
            }
        }
        System.out.println("No se encontro la cita.");
        return null;
    }

    public static Doctor relacionarDoctor(ArrayList<Doctor> listaDoctores) {
        Doctor doctor;
        System.out.println("Lista de Doctores: " + listaDoctores.toString());
        System.out.println("Escribe el id del Doctor: ");
        String entrada = scanner.nextLine();
        for (int i = 0; i < listaDoctores.size(); i++) {
            doctor = listaDoctores.get(i);
            if (doctor.getId().equals(entrada)) {
                System.out.println("Se ha encontrado.");
                return doctor;
            }
        }
        System.out.println("No se encontro el Doctor.");
        return null;
    }

    public static boolean solicitarCredenciales(HashMap passAdmin) {
        String usuario;
        String contraseña;
        System.out.println("Por favor introduce un usuario: ");
        usuario = scanner.nextLine();
        System.out.println("Por favor introduce la contraseña: ");
        contraseña = scanner.nextLine();
        if (passAdmin.containsKey(usuario) == true) {
            if (passAdmin.containsValue(contraseña) == true) {
                System.out.println("Acceso correcto");
                return true;
            } else {
                System.out.println("Acceso incorrecto");
                return false;
            }
        } else {
            System.out.println("Acceso incorrecto");
            return false;
        }
    }
}
