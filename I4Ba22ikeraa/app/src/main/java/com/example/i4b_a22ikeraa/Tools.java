package com.example.i4b_a22ikeraa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Tools {


    public Tools(){}

    //lee las lineas que tiene el fichero y pude devolver 2 valores
    // null = significa que le fichero está vacio
    // String[] = las lineas del fichero
    public String[] readFileLines(File file){
        ArrayList<String> lines = new ArrayList<>();
        String[] linesArray;
        String line;
        try{
            BufferedReader br = new BufferedReader( new FileReader(file));
            line = br.readLine();
            while(line != null){
                lines.add(line);
                line = br.readLine();
            }
            br.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        if(lines.isEmpty()){
            return null;
        }

        linesArray = new String[lines.size()];

        for(int i = 0 ; i < linesArray.length ; i++){
            linesArray[i] = lines.get(i);
        }
        return linesArray;
    }

    //Escribe el elemento en el Fichero
    public void writeIntoFile(File file , String txt){
        try{
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file , true));
            osw.write(txt + "\n");
            osw.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    //valida Si el texto insertado por el usuario es valido para ser escrito o no
    public boolean isElementValid(String txt){
        return !txt.isEmpty() && txt.trim().length() != 0;
    }

    //formatea el texto que será mostrado en el textView
    public String formatText(String[] elements , boolean[] elementsToShow){
        StringBuilder builder = new StringBuilder();

        for(int i = 0 ; i < elements.length ; i++){
            if(elementsToShow[i])
                builder.append(elements[i]).append(" ");
        }

        return builder.toString().trim();
    }

    //metodo que escribe los primeros valores en el fichero si es la primera vez que se ejecuta la aplicacion o he a borrado el fichero
    public void firstUse(File file ,String[] animals){
        if (file.length() == 0){
            for(String animal : animals)
                writeIntoFile(file , animal);
        }
    }

    public void saveResources(File file , ArrayList<Boolean> data){
        try{
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file , false));
            osw.write("");
            osw.close();

            osw = new OutputStreamWriter(new FileOutputStream(file , true));

            for(boolean item : data){
                if(item){
                    osw.write("true\n");
                } else {
                    osw.write("false\n");
                }
            }
            osw.close();
        }catch (IOException ioe){ioe.printStackTrace();}
    }

    public ArrayList<Boolean> loadResources(File file){
        ArrayList<Boolean> booleanArrayList = new ArrayList<>();
        String line;
        try{
            BufferedReader br = new BufferedReader( new FileReader(file));
            line = br.readLine();
            while(line != null){
                if(line.equalsIgnoreCase("true")){
                    booleanArrayList.add(true);
                } else {
                    booleanArrayList.add(false);
                }
                line = br.readLine();
            }
            br.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        if (booleanArrayList.isEmpty()) return null;

        return booleanArrayList;
    }

}
