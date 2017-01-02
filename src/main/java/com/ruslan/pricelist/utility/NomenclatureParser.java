package com.ruslan.pricelist.utility;

import com.ruslan.pricelist.beans.Nomenclature;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ruslan on 12/30/2016.
 */

public class NomenclatureParser {
    public static final String fileName = "nomenclature.txt";

    public static List<Nomenclature> getNomenclaturesFromFile() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(getFile().getPath()),"UTF8"));
        List<String> lines = bufferedReader.lines().collect(Collectors.toList());
        Long id = 0L;
        List<Nomenclature> nomenclatures = new ArrayList<>();

        for (String line : lines) {
            if(line == null || line.length() == 0)
                continue;
            id++;
            nomenclatures.add(new Nomenclature(id,line));
        }
        bufferedReader.close();
        return nomenclatures;
    }

    public static void addNewNomenclature(String nomenclatureName) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getFile().getPath(),true),"UTF8"));
        bufferedWriter.append(nomenclatureName);
        bufferedWriter.newLine();
        bufferedWriter.close();
    }

    public static void removeNomenclature(String nomenclatureName) throws IOException{
        try {
            File inFile = getFile();
            String file = inFile.getPath();
            if (!inFile.isFile()) {
                System.out.println("Parameter is not an existing file");
                return;
            }
            //Construct the new file that will later be renamed to the original filename.
            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF8"));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(tempFile),"UTF8"));
            String line ;
            //Read from the original file and write to the new
            //unless content matches data to be removed.
            while ((line = br.readLine()) != null) {
                if (!line.trim().equals(nomenclatureName)) {
                    pw.println(line);
                    pw.flush();
                }
            }
            pw.close();
            br.close();

            //Delete the original file
            if (!inFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }
            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inFile))
                System.out.println("Could not rename file");

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void clearNomenclatures() throws IOException {
        PrintWriter writer = new PrintWriter(getFile());
        writer.print("");
        writer.close();
    }

    private static File getFile(){
        return new File(NomenclatureParser.class.getClassLoader().getResource(fileName).getFile());
    }
}
