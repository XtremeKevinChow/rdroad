package com.magic.crm.util;


import javax.print.PrintService;
import javax.print.PrintServiceLookup;


public class PrintThread  extends Thread{
  private String file_dir;
  private String fileName;

  public PrintThread( String fileDir,String fileName) {
    file_dir = fileDir;
    this.fileName = fileName;
  }

  public void run()   {
    PrintService printService = PrintServiceLookup
        .lookupDefaultPrintService();
    if (printService == null) {
     return;
    }

    String[] command = new String[2];
    command[0] = file_dir + "\\pdfprint.exe";
    command[1] = fileName;
    try {
      Runtime.getRuntime().exec(command);
    }
    catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());

    }

  }
}
