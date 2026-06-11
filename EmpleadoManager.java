/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package binarios;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author hermi
 */
public class EmpleadoManager {
    
    private RandomAccessFile rcods, remps;
    
    /*
    Formato:
    1- Codigo.emp
    int code;
    
    2- Empleados
    int code;
    String name;
    double salary;
    long cdate -fecha contratacion-
    long tdate -fecha despido-
    */
    
    public EmpleadoManager() {
        File mf = new File("company");
        mf.mkdir();
        
        try {  
            rcods = new RandomAccessFile("company/codigo.emp", "rw");
            remps = new RandomAccessFile("company/empleado.emp", "rw");
            
        } catch (IOException e) {
            System.out.println("Error al inicializar los archivos: " + e.getMessage());
        }
    }
    
    private void initCodes() throws IOException {
        if(rcods.length()==0){
            //P=> 0
            rcods.writeInt(1);
            //P=> 4
        }
    }
    
    private int getCode() throws IOException{
        rcods.seek(0);
        int code= rcods.readInt();
        rcods.seek(0);
        rcods.writeInt(code+1);
        
        return code;
    }
    
    public void addEmployee(String name, double salary) throws IOException{
        remps.seek(remps.length());
        int code = getCode();
        remps.writeInt(code);
        remps.writeUTF(name);
        remps.writeDouble(salary);
        remps.writeLong(Calendar.getInstance().getTimeInMillis());
        remps.writeLong(0);
        //Crear folder
    }
    
    private String employeeFolder(int code){
        return "company/empleado"+code;
    }
    
    private RandomAccessFile salesFilefor(int code) throws IOException{
        String dirPadre=employeeFolder(code);
        int yearActual=Calendar.getInstance().get(Calendar.YEAR);
        String path=dirPadre+"/ventas"+yearActual+".emp";
        return new RandomAccessFile(path,"rw");
    }
    
    /*
    Formato
    ventaAno.emp
    double monto;
    boolean pago;
    */
    
    private void createSalesFileFor(int code) throws IOException{
        RandomAccessFile ryear= salesFilefor(code);
        if(ryear.length()==0){
            for(int mes=0;mes<12;mes++){
                ryear.writeDouble(0);
                ryear.writeBoolean(false);
            }
        }
    }
    
    private void createEmployeeFolder(int code) throws IOException{
        File edir=new File(employeeFolder(code));
        edir.mkdir();
        createSalesFileFor(code);
    }
    
    public void employeeList() throws IOException{
        remps.seek(0);
        while(remps.getFilePointer()<remps.length()){
            int code=remps.readInt();
            String name=remps.readUTF();
            double sal=remps.readDouble();
            Date fecha=new Date(remps.readLong());
            if(remps.readLong()==0){
                System.out.println(code+"-"+name+"-"+" - Lps. "+sal+
                        "Contratado el: "+fecha);
            }
        }
    }
    
    private boolean isEmployeeActive(int code) throws IOException{
        remps.seek(0);
        while(remps.getFilePointer()<remps.length()){
            int codeI=remps.readInt();
            long pos=remps.getFilePointer();
            remps.readUTF();
            remps.skipBytes(16);
            if(remps.readLong()==0 && codeI==code){
                remps.seek(pos);
                return true;
            }
        }
        return false;
    }
    
    private boolean fireEmployee(int code) throws IOException{
        if(isEmployeeActive(code)){
            String name=remps.readUTF();
            remps.skipBytes(16);
            remps.writeLong(new Date().getTime());
            System.out.println("Despidiendo a "+name);
            return true;
        }
        return false;
    }
    
    public void addSaleToEmployee(int code, double ven) throws IOException{
        if(isEmployeeActive(code)){
            RandomAccessFile sales=salesFilefor(code);
            
            int pos=Calendar.getInstance().get(Calendar.MONTH)*9;
            sales.seek(pos);
            
            double monto = sales.readDouble();
            sales.seek(pos);
            sales.writeDouble(monto+ven);
            sales.close();
        }
    }

}
