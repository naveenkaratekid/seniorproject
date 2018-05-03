package com.cogswell.seniorproject;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class WriteToHTML 
{

	public static void testHTML(String displayName, String item, ArrayList<String> placeInfo)
	{
		try 
		{
			StringBuilder hsb = new StringBuilder();
			String fileName = "testfile.html";
			hsb.append("<h2 align=\"right\"> Logged in as: " + displayName + "</h2>");
			hsb.append("<html><head><title>Results </title></head>");
            //append body
            hsb.append("<body>");
            hsb.append("<h1>Results</h1>");
            hsb.append("<p>__________________________________________________________</p>");           
            //append table
            hsb.append("<h2>Place information</h2>");
            hsb.append("</body></html>");
            for(String info: placeInfo)
			{
	            hsb.append("<p>" + info + "</p>");
			}
            hsb.append("<br></br>");
            //append row
            hsb.append("<br></br>");
            hsb.append("<h2>Reviews</h2>");
            hsb.append("<p>"+ item + "</p>");
			WriteToFile(hsb.toString(),fileName);
            File f = new File(fileName);
            Desktop.getDesktop().browse(f.toURI());

        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
    public static void WriteToFile(String fileContent, String fileName) throws IOException {
        String projectPath = System.getProperty("user.dir");
        String tempFile = projectPath + File.separator+fileName;
        File file = new File(tempFile);
        
        //write to file with OutputStreamWriter
        OutputStream outputStream = new FileOutputStream(file.getAbsoluteFile());
        Writer writer=new OutputStreamWriter(outputStream);
        writer.write(fileContent);
        writer.close();

    }

}
	