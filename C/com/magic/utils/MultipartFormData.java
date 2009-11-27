package com.magic.utils;

import java.io.*;
import java.net.*;
import java.rmi.*;
import java.util.*;
import javax.naming.*;
import javax.rmi.*;
import java.io.File;

import javax.servlet.*;
import javax.servlet.http.*;

public class MultipartFormData {

  private static final String CONTENT_TYPE = "multipart/form-data";
  private static final String CHAR_ENCODING = "ISO-8859-1";
  private static final long MAX_FILE_SIZE_KB = 256L;
  private static final long MAX_TOTAL_FILE_SIZE_KB = 1000000L;
  private static final String DEFAULT_ATTACHMENT_DIR = "/jboss-3.2.5/server/default/deploy/99CRM.war/crmjsp/uploadfiles";
  
  private static long maxFileSize = MAX_FILE_SIZE_KB * 1024L;
  private static long maxTotalFileSize = MAX_TOTAL_FILE_SIZE_KB;
  private static String attachmentDirName = DEFAULT_ATTACHMENT_DIR;
  private static long spaceLeft = 0;
  private static boolean initialized = false;

  private Map fileInfos;
  private Map parameters;
  public MultipartFormData(HttpServletRequest request) throws IOException {
      this(request, maxFileSize,false,"");
  }

  public MultipartFormData(HttpServletRequest request,String put_dir) throws IOException {
      this(request, maxFileSize,true,put_dir);
  }
  
  public MultipartFormData(HttpServletRequest request, long byteLimit,boolean blnPut,String put_dir) throws IOException {
      if(! initialized) {
          setProperties(blnPut,put_dir);
      }

      if(request == null) {
          throw new NullPointerException("request");
      }
      if(byteLimit < 0) {
          throw new IllegalArgumentException("byteLimit: " + byteLimit);
      }

      this.fileInfos  = new TreeMap();
      this.parameters = new TreeMap();

      String contentType = getContentType(request);
      if(contentType == null || !contentType.equalsIgnoreCase(CONTENT_TYPE)) {
          throw new IllegalArgumentException("invalid content type: " + contentType);
      }

      byte[] boundary = getBoundary(request);
      if(boundary == null) {
          throw new IllegalArgumentException("missing boundary");
      }

      try {
          initFromStream(request.getInputStream(), boundary, byteLimit);
      } catch(RuntimeException exc) {
          this.cleanUp();
          throw exc;
      } catch(IOException exc) {
          this.cleanUp();
          throw exc;
      }
  }

  private void setProperties(boolean blnDir,String dir) {
      try {
				  if(blnDir){
            attachmentDirName = dir;
          } else {
					  ApplicationProperties ap = new ApplicationProperties();
            attachmentDirName = ap.getProperty("attachment_dir",DEFAULT_ATTACHMENT_DIR);
          }
          attachmentDirName = attachmentDirName.replace('/', File.separatorChar);
          File attachmentDir = new File(attachmentDirName);
          attachmentDir.mkdirs();

          long currentSpaceUsed = 0;
          File[] currentFiles = attachmentDir.listFiles();
          for(int i = 0; i < currentFiles.length; i++) {
              if(currentFiles[i].isFile()) {
                  currentSpaceUsed += currentFiles[i].length();
              }
          }
          spaceLeft = maxTotalFileSize - (currentSpaceUsed / 1024L);
          initialized = true;
      } catch(Exception ce) {
      }
  }
  protected void finalize() {
      this.cleanUp();
  }

  public void cleanUp() {
      this.fileInfos.clear();
  }

  private String getContentType(HttpServletRequest request) {
      String contentType = request.getContentType();
      if(contentType == null) {
          return null;
      }

      int semiColonIndex = contentType.indexOf(';');
      if(semiColonIndex == -1) {
          return contentType;
      }

      return contentType.substring(0, semiColonIndex);
  }

  private byte[] getBoundary(HttpServletRequest request) throws UnsupportedEncodingException {
      String contentType = request.getContentType();
      if(contentType == null) {
          return null;
      }

      int boundaryIndex = contentType.toLowerCase().indexOf("boundary");
      if(boundaryIndex == -1) {
          return null;
      }

      int startIndex = boundaryIndex + 9;
      int endIndex = startIndex;
      for(int i = startIndex; i < contentType.length() && contentType.charAt(i) != ';'; ++i) {
          ++endIndex;
      }

      if(contentType.charAt(startIndex) == '\"') {
          ++startIndex;
          --endIndex;
      }

      return contentType.substring(startIndex, endIndex).getBytes(CHAR_ENCODING);
  }


  private void initFromStream(ServletInputStream input, byte[] boundary, long byteLimit) throws IOException {
      long[] limit = new long[] { byteLimit };

      if(!firstBoundary(input, boundary, limit)) {
          return;
      }

      while(readPart(input, boundary, limit)) {}
  }

  private boolean firstBoundary(ServletInputStream input, byte[] boundary, long[] limit) throws IOException {
      int length = 0;
      byte[] buffer = new byte[ boundary.length + 4 ];
      while((length = input.readLine(buffer, 0, buffer.length)) != -1) {
          decrementLimit(limit, length);

          if(isBoundary(boundary, buffer, length)) {
              break;
          }
      }

      if(length == -1) {
          throw new EOFException();
      }

      return !isEndBoundary(boundary, buffer, length);
  }

  private boolean isBoundary(byte[] boundary, byte[] array, int length) {
      if(length - 2 < boundary.length) {
          return false;
      }

      if(array[0] != (char) '-' || array[1] != (char) '-') {
          return false;
      }

      for(int i = 0;  i < boundary.length;  ++i) {
          if(array[i + 2] != boundary[i]) {
              return false;
          }
      }

      return true;
  }

  private boolean isEndBoundary(byte[] boundary, byte[] array, int length) {
      if(length < boundary.length + 4) {
          return false;
      }

      return(array[ boundary.length + 2 ] == (byte) '-')
              &&(array[ boundary.length + 3 ] == (byte) '-');
  }

  private void addParameter(String name, String value) {
      List values = (List) parameters.get(name);
      if(values == null) {
          values = new LinkedList();
      }

      values.add(value);

      parameters.put(name, values);
  }

  public String getParameter(String name) {
      List values = (List) parameters.get(name);
      if(values == null || values.size() == 0) {
          return null;
      }

      return (String) values.get(0);
  }

  public String[] getParameterValues(String name) {
      List values = (List) parameters.get(name);
      if(values == null) {
          return null;
      }

      return (String[]) values.toArray(new String[0]);
  }

  public Enumeration getParameterNames() {
      return new IteratorEnumeration(parameters.keySet().iterator());
  }

  public Enumeration getFileFormFields() {
      return new IteratorEnumeration(fileInfos.keySet().iterator());
  }

  public String getLocalPath(String fieldName) {
      List infos = (List) fileInfos.get(fieldName);
      if(infos == null || infos.size() == 0) {
          return null;
      }

      return((FileInfo) infos.get(0)).getLocalPath();
  }
  public String[] getLocalPathValues(String fieldName) {
      List infos = (List) fileInfos.get(fieldName);
      if(infos == null || infos.size() == 0) {
          return null;
      }

      String[] paths = new String[ infos.size() ];
      Iterator iterator = infos.iterator();
      for(int i = 0;  iterator.hasNext();  ++i) {
          paths[i] =((FileInfo) iterator.next()).getLocalPath();
      }

      return paths;
  }

  public String getFileName(String fieldName) {
      List infos = (List) fileInfos.get(fieldName);
      if(infos == null || infos.size() == 0) {
          return null;
      }

      return((FileInfo) infos.get(0)).getFileName();
  }

  public String[] getFileNameValues(String fieldName) {
      List infos = (List) fileInfos.get(fieldName);
      if(infos == null || infos.size() == 0) {
          return null;
      }

      String[] names = new String[ infos.size() ];
      Iterator iterator = infos.iterator();
      for(int i = 0;  iterator.hasNext();  ++i) {
          names[i] =((FileInfo) iterator.next()).getFileName();
      }

      return names;
  }

  public long getFileSize(String fieldName) {
      List infos = (List) fileInfos.get(fieldName);
      if(infos == null || infos.size() == 0) {
          return -1L;
      }

      return((FileInfo) infos.get(0)).getFileSize();
  }

  public long[] getFileSizeValues(String fieldName) {
      List infos = (List) fileInfos.get(fieldName);
      if(infos == null || infos.size() == 0) {
          return null;
      }

      long[] sizes = new long[ infos.size() ];
      Iterator iterator = infos.iterator();
      for(int i = 0;  iterator.hasNext();  ++i) {
          sizes[i] =((FileInfo) iterator.next()).getFileSize();
      }

      return sizes;
  }

  public String getFileType(String fieldName) {
      List infos = (List) fileInfos.get(fieldName);
      if(infos == null || infos.size() == 0) {
          return null;
      }

      return((FileInfo) infos.get(0)).getFileType();
  }

  public String[] getFileTypeValues(String fieldName) {
      List infos = (List) fileInfos.get(fieldName);
      if(infos == null || infos.size() == 0) {
          return null;
      }

      String[] types = new String[ infos.size() ];
      Iterator iterator = infos.iterator();
      for(int i = 0;  iterator.hasNext();  ++i) {
          types[i] =((FileInfo) iterator.next()).getFileType();
      }

      return types;
  }

  public boolean writeFile(String fieldName, String fileName) {
      if(fieldName == null || fileName == null || fieldName.equals("") || fileName.equals("")) {
          return false;
      }

      List infos = (List) fileInfos.get(fieldName);
      if(infos == null || infos.size() == 0) {
          return false;
      }

      FileInfo info = (FileInfo) infos.get(0);
      if(info != null) {
          try {
              long fileSize = info.getFileSize() / 1024L;
              if((spaceLeft - fileSize) < 0) {
                  return false;
              }
              spaceLeft = spaceLeft - fileSize;

              fileName = attachmentDirName + File.separator + fileName;

              FileOutputStream out = new FileOutputStream(fileName);
              out.write(info.getFileContents());
              out.close();
              return true;
          } catch(IOException ioe) {
          }
      }
      return false;
  }

  private boolean readPart(ServletInputStream input, byte[] boundary, long[] limit) throws IOException {
      String formFieldName = null;
      String filePath = null;
      String type = null;

      Header[] headers = readHeaders(input, limit);
      for(int i = 0;  i < headers.length;  ++i) {
          if(headers[i].getName().equalsIgnoreCase("Content-Disposition")) {
              formFieldName = extractFormFieldName(headers[i].getValue());
              filePath = extractFilePath(headers[i].getValue());
          } else if(headers[i].getName().equalsIgnoreCase("Content-Type")) {
              type = headers[i].getValue();
          }
      }

      boolean hasMore = false;

      if(filePath == null) {
          String[] value = new String[1];
          hasMore = readValue(input, boundary, value, limit);

          if(formFieldName != null) {
              this.addParameter(formFieldName, value[0]);
          }
      } else {
          String localName[] = new String[1];
          if(formFieldName != null) {
              FileInfo info = new FileInfo(filePath, type);
              hasMore = readData(input, boundary, info, limit);
              addFileInfo(formFieldName, info);
          } else {
              new File(localName[0]).delete();
          }
      }

      return hasMore;
  }

  private Header[] readHeaders(ServletInputStream input, long[] limit)
          throws IOException {
      List headers = new Vector();

      Header header = null;
      while((header = readHeader(input, limit)) != null) {
          headers.add(header);
      }

      return (Header[]) headers.toArray(new Header[0]);
  }

  private Header readHeader(ServletInputStream input, long[] limit) throws IOException {
      byte[] buffer = new byte[4096];
      int length = input.readLine(buffer, 0, buffer.length);

      if(length == -1) {
          throw new EOFException();
      }

      decrementLimit(limit, length);

      String header = new String(buffer, 0, length, CHAR_ENCODING).trim();

      if(header.length() == 0) {
          return null;
      }

      return new Header(header);
  }


  private String extractFormFieldName(String string) {
      return getField(string, "name");
  }

  private String extractFilePath(String string) {
      return getField(string, "filename");
  }

  private String getField(String string, String name) {
      int baseIndex = string.toLowerCase().indexOf(name);
      if(baseIndex == -1) {
          return null;
      }

      int startIndex = baseIndex + name.length() + 2;
      int endIndex   = startIndex;

      for(int i = startIndex; i < string.length() && string.charAt(i) != '\"'; ++i) {
          ++endIndex;
      }

      return string.substring(startIndex, endIndex);
  }

  private boolean readValue(ServletInputStream input, byte[] boundary, String[] value, long[] limit) throws IOException {
      byte[] lastLine = new byte[4096];
      byte[] buffer   = new byte[4096];

      ByteArrayOutputStream byteOut = new ByteArrayOutputStream();

      int lastLength = -1;
      int length = input.readLine(buffer, 0, buffer.length);
      while(length >= 0 && !isBoundary(boundary, buffer, length)) {
          decrementLimit(limit, length);

          if(lastLength > -1) {
              byteOut.write(lastLine, 0, lastLength);
          }

          lastLength = length;
          System.arraycopy(buffer, 0, lastLine, 0, length);

          length = input.readLine(buffer, 0, buffer.length);
      }

      if(length == -1) {
          throw new EOFException();
      }

      decrementLimit(limit, length);

      lastLength = stripNewline(lastLine, lastLength);

      byteOut.write(lastLine, 0, lastLength);
      byteOut.close();

      value[0] = new String(byteOut.toByteArray(), CHAR_ENCODING);

      return !isEndBoundary(boundary, buffer, length);
  }

  private boolean readData(ServletInputStream input, byte[] boundary, FileInfo info, long[] limit) throws IOException {
      byte[] lastLine = new byte[4096];
      byte[] buffer   = new byte[4096];

      int lastLength = -1;
      int length = input.readLine(buffer, 0, buffer.length);
      while(length >= 0 && !isBoundary(boundary, buffer, length)) {
          decrementLimit(limit, length);

          if(lastLength > -1) {
              info.addContents(lastLine, 0, lastLength);
          }

          lastLength = length;
          System.arraycopy(buffer, 0, lastLine, 0, length);

          length = input.readLine(buffer, 0, buffer.length);
      }

      if(length == -1) {
          throw new EOFException();
      }

      decrementLimit(limit, length);

      lastLength = stripNewline(lastLine, lastLength);

      info.addContents(lastLine, 0, lastLength);

      return !isEndBoundary(boundary, buffer, length);
  }

  private void decrementLimit(long[] limit, long amount) {
      limit[0] -= amount;
  }

  private int stripNewline(byte[] array, int length) {
      if(length > 0 && array[length - 1] == (byte) '\n') {
          --length;
      }

      if(length > 0 && array[length - 1] == (byte) '\r') {
          --length;
      }

      return length;
  }

  private void addFileInfo(String name, FileInfo info) {
      List infos = (List) fileInfos.get(name);
      if(infos == null) {
          infos = new LinkedList();
      }

      infos.add(info);

      fileInfos.put(name, infos);
  }

  private class IteratorEnumeration implements Enumeration {
      private Iterator iterator;

      public IteratorEnumeration(Iterator iterator) {
          this.iterator = iterator;
      }

      public boolean hasMoreElements() {
          return iterator.hasNext();
      }

      public Object nextElement() {
          return iterator.next();
      }
  }

  private class Header {
      private String name;
      private String value;

      public Header(String header) {
          int colonIndex = header.indexOf(':');
          if(colonIndex == -1) {
              throw new IllegalArgumentException("invalid header: " + header);
          }

          this.name  = header.substring(0, colonIndex).trim();
          this.value = header.substring(colonIndex + 1).trim();
      }

      public String getName() {
          return name;
      }

      public String getValue() {
          return value;
      }

  }
  public String getAttachmentDirName(){
    return attachmentDirName;
  }

  private class FileInfo {
      private String localPath;
      private String fileName;
      private long   fileSize;
      private String fileType;
      private ByteArrayOutputStream fileContents = new ByteArrayOutputStream();

      public FileInfo(String localPath, String fileType) {
          this.localPath = localPath;
          this.fileType  = fileType == null ? "" : fileType;
      }

      public String getLocalPath() {
          return localPath;
      }

      public String getFileName() {
          return fileName;
      }

      public void setFileName(String value) {
          fileName = value;
      }

      public long getFileSize() {
          return (fileContents.toByteArray() != null ? fileContents.toByteArray().length : 0);
      }

      public String getFileType() {
          return fileType;
      }

      public byte[] getFileContents() {
          return fileContents.toByteArray();
      }

      public void addContents(byte[] data) throws IOException {
          fileContents.write(data);
      }

      public void addContents(byte[] data, int start, int length) throws IOException {
          fileContents.write(data, start, length);
      }

  }

}