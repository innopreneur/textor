package org.textor.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;



public class Operator {
    

    
    
    /** manipulates all oldText and newText in a given file
     * @param filePath - page in which needs to be manipulated
     * @param oldText - old text that is needed to be manipulated
     * @param newText -new text to be used
     * @param operation - main operation to be performed
     * @param position - position where operation needs to be performed
     * @return - new page with manipulated text
     */
    public String doOperation(String filePath,String oldText,String newText,String operation,String position){
        
        
        File file=new File(filePath);
        String done="";
        
        if (file.canRead()) {
            for (File temp : file.listFiles()) {
                if (temp.isDirectory()) {
                    doOperation(filePath,oldText,newText,operation,position);
                }
                if(temp.isFile()){
                    
                    String path=temp.getAbsolutePath();
                    if(operation.equalsIgnoreCase("add")){
                        
                        if(position.equalsIgnoreCase("before")){
                            
                           done=this.insertAllBeforeText(path, oldText, newText);
                        }
                        else if(position.equalsIgnoreCase("after")){
                            
                           done=this.insertAfterTextInNextLine(path, oldText, newText);
                        }
                        else if(position.equalsIgnoreCase("at")){
                            
                          done=this.insertAtGivenLine(path, Integer.parseInt(oldText.trim()), newText);
                        }
                        else{
                            
                            System.err.println("Unable to get correct position details for add operation");
                        }
                        
                    }
                    
                    if(operation.equalsIgnoreCase("delete")){
                        
                        if(position.equalsIgnoreCase("before")){
                            
                            done=this.deleteAllBeforeText(path, oldText);
                        }
                        else if(position.equalsIgnoreCase("after")){
                            
                            done=this.deleteAfterTextInNextLine(path, oldText);
                            
                        }
                        else if(position.equalsIgnoreCase("at")){
                            
                            done=this.deleteAGivenLine(path, Integer.parseInt(oldText.trim()));
                        }
                        else{
                            
                            System.err.println("Unable to get correct position details for delete operation");
                        }
                        
                    }
                    if(operation.equalsIgnoreCase("replace")){
                        
                        
                        if(position.equalsIgnoreCase("by")){
                            
                            done=this.replaceText(path, newText, oldText);
                        }
                        else if(position.equalsIgnoreCase("at")){
                            
                            done=this.replaceAtGivenLine(path, Integer.parseInt(oldText), newText);
                        }
                        else{
                            
                            System.err.println("Unable to get correct operational details");
                        }
                    }
                    
                }
            }
        }
        
        else {
            System.out.println(file.getAbsoluteFile() + "Permission Denied");
        }
        
       
        
        return done;
    }
    
    
    /** replaces all oldText with newText in a given page
     * @param page - source in which text needs to be replaced
     * @param oldText - text needed to be replaced
     * @param newText - text needed to be used
     * @return - new page with replaced text
     */
    public String replaceText(String filePath,String oldText,String newText){
        
        FileWriter fw=null;
        Scanner scan=null;
        ArrayList<String> lines=new ArrayList<String>();
        String done="";
        
        try {
            
            File f=new File(filePath);
            
            File out=new File(filePath);
            
            
            scan=new Scanner(f);
            
            while(scan.hasNext()){
                
                lines.add(scan.nextLine());
            }
            
            
            
            ListIterator<String> it= lines.listIterator();
            
            while(it.hasNext()){
                
                if(it.next().contains(oldText)){
                    
                    it.set(newText);
                }
            }
            
            
            int q=0;
            fw = new FileWriter(out.getAbsoluteFile(),false);
            BufferedWriter bw = new BufferedWriter(fw);
            for(String s : lines){
//System.out.println("in writer");
//System.out.println("Line["+q+"] :"+s);
                bw.write(s);
                bw.write("\r\n");
                q++;
            }
            
            System.out.println("Completed");
            done="ok";
            bw.close();
            
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        return done;
    }
    
    /** replaces only first oldText with newText in a given page
     * @param page - source in which text needs to be replaced
     * @param oldText - text needed to be replaced
     * @param newText - text needed to be used
     * @return - new page with replaced text
     */
    public String replaceFirst(String filePath,String oldText,String newText){
        
        FileWriter fw=null;
        Scanner scan=null;
        List<String> lines=new ArrayList<String>();
        String done="";
        
        try {
            
            File f=new File(filePath);
            
            File out=new File(filePath);
            
            
            scan=new Scanner(f);
            System.out.println("Doneeeeeeeeeee");
            while(scan.hasNext()){
                
                lines.add(scan.nextLine());
            }
            
            int p=0,k=0;
            for(String s:lines){
                
                System.out.println("Line["+p+"] :"+s);
                
                
                if(s.contains(oldText)){
                    
                    k++;
                    break;
                }
                
                p++;
            }
            
            if(k>0){
                
                lines.set(p,newText);
            }
            
            int q=0;
            fw = new FileWriter(out.getAbsoluteFile(),false);
            BufferedWriter bw = new BufferedWriter(fw);
            for(String s : lines){
                
                System.out.println("Line["+q+"] :"+s);
                bw.write(s);
                bw.write("\r\n");
                q++;
            }
            
            System.out.println("Completed");
            done="ok";
            bw.close();
            
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        return done;
    }
    
    /** inserts text at particular line number in a given file
     * @param filePath - path of the file
     * @param position - line number where text is needed to be inserted
     * @param extraText - text to be inserted
     */
    public String insertAtGivenLine(String filePath,int position,String extraText){
        
        
        FileWriter fw=null;
        Scanner scan=null;
        List<String> lines=new ArrayList<String>();
        String done="";
        
        try {
            
            File f=new File(filePath);
            
            File out=new File(filePath);
            
            
            scan=new Scanner(f);
            System.out.println("Doneeeeeeeeeee");
            while(scan.hasNext()){
                
                lines.add(scan.nextLine());
            }
            
            if(position<=lines.size()){
                
           
            lines.add(position-1,extraText);
            
            
            int p=0;
            for(String s:lines){
                
                System.out.println("Line["+p+"] :"+s);
                p++;
                
                
            }
            int q=0;
            fw = new FileWriter(out.getAbsoluteFile(),false);
            BufferedWriter bw = new BufferedWriter(fw);
            for(String s : lines){
                
                System.out.println("Line["+q+"] :"+s);
                bw.write(s);
                bw.write("\r\n");
                q++;
            }
            
            System.out.println("Completed");
            done="ok";
            bw.close();
            
        }
            else{
               
                System.err.println("Line number "+position+" is greater than total lines "+lines.size()+ " in file "+filePath);
                done="Line number "+position+" is greater than total lines "+lines.size()+ " in file "+filePath;
           
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        return done;
    }
    
    /** deletes text at particular line number in a given file
     * @param filePath - path of the file
     * @param position - line number where text is needed to be deleted
     */
    public String deleteAGivenLine(String filePath,int position){
        
        
        FileWriter fw=null;
        Scanner scan=null;
        List<String> lines=new ArrayList<String>();
        String done="";
        
        try {
            
            File f=new File(filePath);
            
            File out=new File(filePath);
            
            
            scan=new Scanner(f);
            System.out.println("Doneeeeeeeeeee");
            while(scan.hasNext()){
                
                lines.add(scan.nextLine());
            }
            if(position <=lines.size()){
            lines.remove(position - 1);
            
            
            int p=0;
            for(String s:lines){
                
                System.out.println("Line["+p+"] :"+s);
                p++;
                
                
            }
            int q=0;
            fw = new FileWriter(out.getAbsoluteFile(),false);
            BufferedWriter bw = new BufferedWriter(fw);
            for(String s : lines){
                
                System.out.println("Line["+q+"] :"+s);
                bw.write(s);
                bw.write("\r\n");
                q++;
            }
            
            System.out.println("Completed");
            done="ok";
            bw.close();
            
        }else{
                
                System.err.println("Line number "+position+" is greater than total lines "+lines.size()+ " in file "+filePath);
                done="Line number "+position+" is greater than total lines "+lines.size()+ " in file "+filePath;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return done;
    }
    
    /** inserts text in next line after particular text in a given file
     * @param filePath - path of the file
     * @param textToBeSearched - text after which new text is to be inserted
     * @param extraText - text to be inserted
     */
    public String insertAfterTextInNextLine(String filePath,String textToBeSearched,String extraText){
        
        
        FileWriter fw=null;
        Scanner scan=null;
        ArrayList<String> lines=new ArrayList<String>();
        String done="";
        
        try {
            
            File f=new File(filePath);
            
            File out=new File(filePath);
            
            
            scan=new Scanner(f);
            
            while(scan.hasNext()){
                
                lines.add(scan.nextLine());
            }
            
            
            
            ListIterator<String> it= lines.listIterator();
            
            while(it.hasNext()){
                
                if(it.next().trim().contains(textToBeSearched.trim())){
                    
                    it.add(extraText);
                }
            }
            
            
            int q=0;
            fw = new FileWriter(out.getAbsoluteFile(),false);
            BufferedWriter bw = new BufferedWriter(fw);
            for(String s : lines){
//System.out.println("in writer");
//System.out.println("Line["+q+"] :"+s);
                bw.write(s);
                bw.write("\r\n");
                q++;
            }
            
            System.out.println("Completed");
            done="ok";
            bw.close();
            
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        return done;
    }
    
    /** deletes text at particular line number in a given file
     * @param filePath - path of the file
     * @param textToBeSearched - text after which next line text is to be deleted
     */
    public String deleteAfterTextInNextLine(String filePath,String textToBeSearched){
        
        String done="";
        FileWriter fw=null;
        Scanner scan=null;
        ArrayList<String> lines=new ArrayList<String>();
        
        try {
            
            File f=new File(filePath);
            
            File out=new File(filePath);
            
            
            scan=new Scanner(f);
            
            while(scan.hasNext()){
                
                lines.add(scan.nextLine());
            }
            
            
            
            ListIterator<String> it= lines.listIterator();
            
            while(it.hasNext()){
                
                if(it.next().trim().contains(textToBeSearched.trim())){
                    
                    it.next();
                    it.set("");
                }
            }
            
            
            int q=0;
            fw = new FileWriter(out.getAbsoluteFile(),false);
            BufferedWriter bw = new BufferedWriter(fw);
            for(String s : lines){
//System.out.println("in writer");
//System.out.println("Line["+q+"] :"+s);
                bw.write(s);
                bw.write("\r\n");
                q++;
            }
            
            System.out.println("Completed");
            done="ok";
            bw.close();
            
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        return done;
    }
    
    /** replace text at particular line number in a given file
     * @param filePath - path of the file
     * @param position - line number where text is needed to be replaced
     * @param extraText - text to be replaced
     */
    public String replaceAtGivenLine(String filePath,int position,String extraText){
        
        
        FileWriter fw=null;
        Scanner scan=null;
        ArrayList<String> lines=new ArrayList<String>();
        String done="";
        
        try {
            
            File f=new File(filePath);
            
            File out=new File(filePath);
            
            
            scan=new Scanner(f);
            
            while(scan.hasNext()){
                
                lines.add(scan.nextLine());
            }
            
            if(position<=lines.size()){
                
            lines.set(position-1, extraText);
            
            
            int q=0;
            fw = new FileWriter(out.getAbsoluteFile(),false);
            BufferedWriter bw = new BufferedWriter(fw);
            for(String s : lines){
//System.out.println("in writer");
//System.out.println("Line["+q+"] :"+s);
                bw.write(s);
                bw.write("\r\n");
                q++;
            }
            
            System.out.println("Completed");
            done="ok";
            bw.close();
            }
            else{
                
                System.err.println("Line number "+position+" is greater than total lines "+lines.size()+ " in file "+filePath);
                done="Line number "+position+" is greater than total lines "+lines.size()+ " in file "+filePath;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        return done;
    }
    
    /** inserts text in next line after given text for one time
     * @param filePath - path of the file
     * @param position - line number where text is needed to be inserted
     * @param extraText - text to be inserted
     */
    public String insertFirstAfterText(String filePath,String textToBeSearched,String extraText){
        
        
        FileWriter fw=null;
        Scanner scan=null;
        List<String> lines=new ArrayList<String>();
        String done="";
        
        try {
            
            File f=new File(filePath);
            
            File out=new File(filePath);
            
            
            scan=new Scanner(f);
            System.out.println("Doneeeeeeeeeee");
            while(scan.hasNext()){
                
                lines.add(scan.nextLine());
            }
            
            int p=0,k=0;
            for(String s:lines){
                
                System.out.println("Line["+p+"] :"+s);
                
                
                if(s.trim().contains(textToBeSearched.trim())){
                    
                    p++;
                    k++;
                    break;
                }
                
                p++;
            }
            
            if(k>0){
                
                lines.add(p, extraText);
            }
            
            int q=0;
            fw = new FileWriter(out.getAbsoluteFile(),false);
            BufferedWriter bw = new BufferedWriter(fw);
            for(String s : lines){
                
                System.out.println("Line["+q+"] :"+s);
                bw.write(s);
                bw.write("\r\n");
                q++;
            }
            
            System.out.println("Completed");
            done="ok";
            bw.close();
            
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        return done;
    }
    
    /** inserts text in next line before given text for one time
     * @param filePath - path of the file
     * @param position - line number where text is needed to be inserted
     * @param extraText - text to be inserted
     */
    public String insertFirstBeforeText(String filePath,String textToBeSearched,String extraText){
        
        
        FileWriter fw=null;
        Scanner scan=null;
        List<String> lines=new ArrayList<String>();
        String done="";
        
        try {
            
            File f=new File(filePath);
            
            File out=new File(filePath);
            
            
            scan=new Scanner(f);
            System.out.println("Doneeeeeeeeeee");
            while(scan.hasNext()){
                
                lines.add(scan.nextLine());
            }
            
            int p=0,k=0;
            for(String s:lines){
                
                System.out.println("Line["+p+"] :"+s);
                
                
                if(s.trim().contains(textToBeSearched.trim())){
                    
                    p--;
                    k++;
                    break;
                }
                
                p++;
            }
            
            if(k>0){
                
                lines.add(p, extraText);
            }
            
            int q=0;
            fw = new FileWriter(out.getAbsoluteFile(),false);
            BufferedWriter bw = new BufferedWriter(fw);
            for(String s : lines){
                
                System.out.println("Line["+q+"] :"+s);
                bw.write(s);
                bw.write("\r\n");
                q++;
            }
            
            System.out.println("Completed");
            done="ok";
            bw.close();
            
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        return done;
    }
    
    /** deletes text in next line after given text for one time
     * @param filePath - path of the file
     * @param position - line number where text is needed to be deleted
     */
    public String deleteFirstAfterText(String filePath,String textToBeSearched){
        
        
        FileWriter fw=null;
        Scanner scan=null;
        List<String> lines=new ArrayList<String>();
        String done="";
        
        try {
            
            File f=new File(filePath);
            
            File out=new File(filePath);
            
            
            scan=new Scanner(f);
            System.out.println("Doneeeeeeeeeee");
            while(scan.hasNext()){
                
                lines.add(scan.nextLine());
            }
            
            int p=0,k=0;
            for(String s:lines){
                
                System.out.println("Line["+p+"] :"+s);
                
                
                if(s.trim().contains(textToBeSearched.trim())){
                    
                    p++;
                    k++;
                    break;
                }
                
                p++;
            }
            
            if(k>0){
                
                lines.set(p,"");
            }
            
            int q=0;
            fw = new FileWriter(out.getAbsoluteFile(),false);
            BufferedWriter bw = new BufferedWriter(fw);
            for(String s : lines){
                
                System.out.println("Line["+q+"] :"+s);
                bw.write(s);
                bw.write("\r\n");
                q++;
            }
            
            System.out.println("Completed");
            done="ok";
            bw.close();
            
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        return done;
    }
    
    /** deletes text in previous line before given text
     * @param filePath - path of the file
     * @param position - line number where text is needed to be deleted
     */
    public String deleteAllBeforeText(String filePath,String textToBeSearched){
        
        
        FileWriter fw=null;
        Scanner scan=null;
        List<String> lines=new ArrayList<String>();
        String done="";
        
        try {
            
            File f=new File(filePath);
            
            File out=new File(filePath);
            
            
            scan=new Scanner(f);
            System.out.println("Doneeeeeeeeeee");
            while(scan.hasNext()){
                
                lines.add(scan.nextLine());
            }
            
            int p=0;
            
            this.iterate(p, lines, textToBeSearched.trim(),"delete","");
            
            int q=0;
            fw = new FileWriter(out.getAbsoluteFile(),false);
            BufferedWriter bw = new BufferedWriter(fw);
            for(String s : lines){
                
                System.out.println("Line["+q+"] :"+s);
                bw.write(s);
                bw.write("\r\n");
                q++;
            }
            
            System.out.println("Completed");
            done="ok";
            bw.close();
            
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        return done;
    }
    
    /** insert text in previous line before given text
     * @param filePath - path of the file
     * @param position - line number where text is needed to be deleted
     */
    public String insertAllBeforeText(String filePath,String textToBeSearched,String textToBeInserted){
        
        
        FileWriter fw=null;
        Scanner scan=null;
        List<String> lines=new ArrayList<String>();
        String done="";
        
        try {
            
            File f=new File(filePath);
            
            File out=new File(filePath);
            
            
            scan=new Scanner(f);
            System.out.println("insert operation");
            while(scan.hasNext()){
                
                lines.add(scan.nextLine());
            }
            
            int p=0;
            
            this.iterate(p, lines, textToBeSearched.trim(),"insert",textToBeInserted);
            
            int q=0;
            fw = new FileWriter(out.getAbsoluteFile(),false);
            BufferedWriter bw = new BufferedWriter(fw);
            for(String s : lines){
                
                System.out.println("Line["+q+"] :"+s);
                bw.write(s);
                bw.write("\r\n");
                q++;
            }
            
            System.out.println("Completed");
            done="ok";
            bw.close();
            
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        return done;
    }
    /** deletes text in next line before given text for one time
     * @param filePath - path of the file
     * @param position - line number where text is needed to be deleted
     */
    public String deleteFirstBeforeText(String filePath,String textToBeSearched){
        
        
        FileWriter fw=null;
        Scanner scan=null;
        List<String> lines=new ArrayList<String>();
        String done="";
        
        try {
            
            File f=new File(filePath);
            
            File out=new File(filePath);
            
            
            scan=new Scanner(f);
            System.out.println("Doneeeeeeeeeee");
            while(scan.hasNext()){
                
                lines.add(scan.nextLine());
            }
            
            int p=0,k=0;
            for(String s:lines){
                
                System.out.println("Line["+p+"] :"+s);
                
                
                if(s.trim().contains(textToBeSearched.trim())){
                    
                    p--;
                    k++;
                    break;
                }
                
                p++;
            }
            
            if(k>0){
                
                lines.set(p,"");
            }
            
            int q=0;
            fw = new FileWriter(out.getAbsoluteFile(),false);
            BufferedWriter bw = new BufferedWriter(fw);
            for(String s : lines){
                
                System.out.println("Line["+q+"] :"+s);
                bw.write(s);
                bw.write("\r\n");
                q++;
            }
            
            System.out.println("Completed");
            done="ok";
            bw.close();
            
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        return done;
    }
    
    private void iterate(int position,List<String> list,String expectedText,String condition,String textToBeInserted){
        
        System.out.println("position is :-"+position);
        System.out.println("list size :-"+ list.size());
        int newPosition=0;int k=0;
        for(int p=position;p<list.size();p++){
            
            k=0;
            if(list.get(p).trim().contains(expectedText.trim())){
                
                
                if(condition.trim().equalsIgnoreCase("delete")){
                    
                    newPosition=p-1;
                }
                if(condition.trim().equalsIgnoreCase("insert")){
                    
                    newPosition=p;
                }
                
                System.out.println("new position:-"+newPosition);
                k++;
                break;
            }
        }
        
        if(k>0){
            
            
            if(condition.trim().equalsIgnoreCase("delete")){
                
                list.set(newPosition, "");
                iterate(newPosition +2, list, expectedText,"delete","");
            }
            if(condition.trim().equalsIgnoreCase("insert")){
                
                list.add(newPosition, textToBeInserted);
                System.out.println("line added");
                iterate(newPosition + 2, list, expectedText,"insert",textToBeInserted);
            }
            
        }
        
    }
    
    
    
}
