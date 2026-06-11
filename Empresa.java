/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package binarios;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author hermi
 */
public class Empresa {
 
    public static void main(String[] args) {
        Scanner lea = new Scanner(System.in);
        EmpleadoManager mg = new EmpleadoManager();
        int opcion = 0;
 
        do {
            System.out.println("\n\nMENU\n");
            System.out.println("1- Agregar Empleado");
            System.out.println("2- Listar Empleados No Despedidos");
            System.out.println("3- Agregar Venta a Empleado");
            System.out.println("4- Pagar Empleado");
            System.out.println("5- Despedir Empleado");
            System.out.println("6- Imprimir Reporte de Empleado");
            System.out.println("7- Salir");
            System.out.print("Escoja una opcion: ");
 
            try {
                opcion = lea.nextInt();
                switch (opcion) {
                    case 1:
                        System.out.print("Nombre del empleado: ");
                        lea.nextLine();
                        String nombre = lea.nextLine();
                        System.out.print("Salario base: ");
                        double salario = lea.nextDouble();
                        mg.addEmployee(nombre, salario);
                        System.out.println("Empleado agregado exitosamente.");
                        break;
                    case 2:
                        mg.employeeList();
                        break;
                    case 3:
                        System.out.print("Codigo del empleado: ");
                        int codVenta = lea.nextInt();
                        System.out.print("Monto de la venta: ");
                        double monto = lea.nextDouble();
                        mg.addSaleToEmployee(codVenta, monto);
                        System.out.println("Venta registrada.");
                        break;
                    case 4:
                        System.out.print("Codigo del empleado a pagar: ");
                        int codPago = lea.nextInt();
                        mg.payEmployee(codPago);
                        break;
                    case 5:
                        System.out.print("Codigo del empleado a despedir: ");
                        int codFuego = lea.nextInt();
                        if (mg.fireEmployee(codFuego)) {
                            System.out.println("Proceso de despido completado.");
                        } else {
                            System.out.println("No se pudo despedir (codigo no existe o ya esta despedido).");
                        }
                        break;
                    case 6:
                        System.out.print("Codigo del empleado para reporte: ");
                        int codReporte = lea.nextInt();
                        mg.printEmployee(codReporte);
                        break;
                    case 7:
                        System.out.println("Saliendo del sistema..");
                        break;
                    default:
                        System.out.println("Opcion no valida.");
                }
            } catch (IOException e) {
                System.out.println("Error en el archivo: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: Ingrese un dato valido.");
                lea.nextLine();
            }
 
        } while (opcion != 7);
    }
}
