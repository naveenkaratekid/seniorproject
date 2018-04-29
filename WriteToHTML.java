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

public class WriteToHTML {

	public static void main(String[] args)
    {
        //testHTML("n", "k");
    }
	
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
			
            
            /*
            //define a HTML String Builder
            StringBuilder htmlStringBuilder=new StringBuilder();
            //append html header and title
            htmlStringBuilder.append("<html><head><title>Results </title></head>");
            //append body
            htmlStringBuilder.append("<body>");
            htmlStringBuilder.append("<h1>Results</h1>");
            htmlStringBuilder.append("<h2 align=\"right\"> Logged in as: " + displayName + "</h2>");
                       
            //append table
            htmlStringBuilder.append("<table border=\"1\" bordercolor=\"#000000\">");
            //append row
            htmlStringBuilder.append("<tr><td><b>TestId</b></td><td><b>TestName</b></td><td><b>TestResult</b></td></tr>");
            //append row
            htmlStringBuilder.append("<tr><td>001</td><td>Login</td><td>Passed</td></tr>");
            //append row
            htmlStringBuilder.append("<tr><td>002</td><td>Logout</td><td>Passed</td></tr>");
            //close html file
            htmlStringBuilder.append("</table></body></html>");
            //write html string content to a file
            String fileName = "testfile.html";
            WriteToFile(htmlStringBuilder.toString(),fileName);
            File f = new File(fileName);
            Desktop.getDesktop().browse(f.toURI());*/
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
    public static void WriteToFile(String fileContent, String fileName) throws IOException {
        String projectPath = System.getProperty("user.dir");
        String tempFile = projectPath + File.separator+fileName;
        File file = new File(tempFile);
        // if file does exists, then delete and create a new file
        /*if (file.exists()) {
            try {
                File newFileName = new File(projectPath + File.separator+ "backup_"+fileName);
                file.renameTo(newFileName);
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        //write to file with OutputStreamWriter
        OutputStream outputStream = new FileOutputStream(file.getAbsoluteFile());
        Writer writer=new OutputStreamWriter(outputStream);
        writer.write(fileContent);
        writer.close();

    }

}
	