
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import javax.swing.JOptionPane;
import javax.swing.JPanel;




public class TextEditor extends Frame
{
        boolean b=true;
        Frame fm;
         FileDialog fd;
         Font f;
         int style=Font.PLAIN;
         int fsize=20;
         doableTextArea txt;
         String filename,st,fn="untitled",dn;
         Clipboard clip=getToolkit().getSystemClipboard();
         TextEditor()
         {
                 f=new Font("Courier",style,fsize);
                 setLayout(new GridLayout(1,1));
                 txt=new doableTextArea(80,25);

                 txt.setFont(f);
                 add(txt);
                 MenuBar mb=new MenuBar();
                 Menu fonttype=new Menu("FontType");


                 Menu fontmenu=new Menu("Font");

                 Menu file=new Menu("File");
                 MenuItem n=new MenuItem("New",new
MenuShortcut(KeyEvent.VK_N));
                 MenuItem o=new MenuItem("Open",new
MenuShortcut(KeyEvent.VK_O));
                 MenuItem s=new MenuItem("Save",new
MenuShortcut(KeyEvent.VK_S));
                 MenuItem e=new MenuItem("Exit",new
MenuShortcut(KeyEvent.VK_E));
                 n.addActionListener(new New());
                 file.add(n);
                 o.addActionListener(new Open());
                 file.add(o);
                 s.addActionListener(new Save());
                 file.add(s);
                 e.addActionListener(new Exit());
                 file.add(e);
                 mb.add(file);
                 addWindowListener(new Win());
                 Menu edit=new Menu("Edit");
                 MenuItem cut=new MenuItem("Cut",new
MenuShortcut(KeyEvent.VK_X));
                 MenuItem copy=new MenuItem("Copy",new
MenuShortcut(KeyEvent.VK_C));
                 MenuItem paste=new MenuItem("Paste",new
MenuShortcut(KeyEvent.VK_V));
                 cut.addActionListener(new Cut());
                 edit.add(cut);
                 copy.addActionListener(new Copy());
                 edit.add(copy);
                 paste.addActionListener(new Paste());
                 edit.add(paste);
                 Menu color =new Menu("Color");
                 MenuItem Bg=new MenuItem("Background");
                 MenuItem Fg=new MenuItem("Foreground");
                 Menu undo=new Menu("Undo&Redo");
             MenuItem un=new MenuItem("Undo");
             MenuItem re=new MenuItem("Redo");
             re.addActionListener(new WW());
             un.addActionListener(new WW());
             undo.add(un);
             undo.add(re);
                 color.add(Bg);
                 color.add(Fg);
                 mb.add(edit);
                 mb.add(fontmenu);
                 mb.add(fonttype);
             mb.add(undo);
                 setMenuBar(mb);


                 mylistener mylist=new mylistener();
                 addWindowListener(mylist);
         }

 
 class mylistener extends WindowAdapter
         {
         public void windowClosing(WindowEvent we)
                 {
                   if(!b)
                 System.exit(0);
                 }
         }
 class New implements ActionListener
        {
        public void actionPerformed(ActionEvent ae)
                  {
                  txt.setText(" ");
                  setTitle(filename);
                  fn="Untitled";
                  }
         }
 class Open implements ActionListener
         {
                  @SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent ae)
                  {
                   FileDialog fd=new FileDialog(TextEditor.this,"Select",FileDialog.LOAD);
                   fd.show();
                   if((fn=fd.getFile())!=null)
                    {
                    filename=fd.getDirectory()+fd.getFile();
                    dn=fd.getDirectory();
                    setTitle(filename);
                    readFile();
                    }
                    txt.requestFocus();
                  }
         }


 class Save implements ActionListener
          {
                  @SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent ae)
                  {
                    FileDialog fd=new FileDialog(TextEditor.this,"SaveFile",FileDialog.SAVE);
                    fd.setFile(fn);
                    fd.setDirectory(dn);
                    fd.show();

                   if(fd.getFile()!=null)
                    {
                     filename=fd.getDirectory()+fd.getFile();
                     setTitle(filename);
                     writeFile();
                  txt.requestFocus();
                    }
              }
         }

  class Exit implements ActionListener
          {
                  public void actionPerformed(ActionEvent ae)
                  {
                   System.exit(0);
                  }
          }
  void readFile()
          {
                  BufferedReader d;
                  StringBuffer sb=new StringBuffer();
                  try{
                      d=new BufferedReader(new FileReader(filename));
                      String line;
                      while((line=d.readLine())!=null)
                       sb.append(line+"");
                       txt.setText(sb.toString());
                       d.close();
                       }catch(FileNotFoundException e){
                         System.out.println("File not found");
                       }catch(IOException e){ }
          }

public void writeFile()
            {
             try{
                 DataOutputStream d=new DataOutputStream(new
FileOutputStream(filename));
                 String line=txt.getText();
                 BufferedReader br=new BufferedReader(new
StringReader(line));
                 while((line=br.readLine())!=null)
                    {
                     d.writeBytes(line+"");
                    }d.close();
                 }catch(Exception e){
                   System.out.println("File not found");
                   }
          }
 class Cut implements ActionListener
         {
          public void actionPerformed(ActionEvent ae)
                  {
                   String sel=txt.getSelectedText();
                   StringSelection ss=new StringSelection(sel);
                   clip.setContents(ss,ss);
                   txt.replaceRange("",txt.getSelectionStart(),txt.getSelectionEnd());
                  }
         }
 class Copy implements ActionListener
         {
          public void actionPerformed(ActionEvent ae)
                  {
                   String sel=txt.getSelectedText();
                   StringSelection clipstring=new StringSelection(sel);
                   clip.setContents(clipstring,clipstring);
                  }
         }
 class Paste implements ActionListener
         {
          public void actionPerformed(ActionEvent ae)
                  {
                   Transferable
cliptran=clip.getContents(TextEditor.this);
                   try{
                   String
sel=(String)cliptran.getTransferData(DataFlavor.stringFlavor);

txt.replaceRange(sel,txt.getSelectionStart(),txt.getSelectionEnd());
                  }catch(Exception e){
                    System.out.println("not starting flavor");
                  }
                 }
         }
 class Win extends WindowAdapter
        {
         public void windowClosing(WindowEvent we)
                 {

             //  @SuppressWarnings("unused")
			ConfirmDialog cd=new ConfirmDialog();
               if(!b)
               {
                System.exit(0);
               }
                 }
         }

 class ConfirmDialog extends JPanel
 {
   @SuppressWarnings("deprecation")
	public ConfirmDialog()
     {
        int result;
        result=JOptionPane.showConfirmDialog(this,fn+" not saved do you want to save"/*,"Message Box",JOptionPane.QUESTION_MESSAGE*/);
         switch(result)
         {
         case JOptionPane.YES_OPTION:
                      FileDialog fd=new FileDialog(TextEditor.this,"SaveFile",FileDialog.SAVE);
             fd.setFile(fn);
             fd.setDirectory(dn);
             fd.show();
             if(fd.getFile()!=null)
              {
               filename=fd.getDirectory()+fd.getFile();
               setTitle(filename);
               writeFile();
               txt.requestFocus();
              }
             System.out.println("Yes button pressed");
             break;
         case JOptionPane.NO_OPTION:
              dispose();
          System.exit(0);
          System.out.println("NO button pressed");
            break;
         case JOptionPane.CANCEL_OPTION:
            setVisible(true);
          //b=false;
          repaint();
             System.out.println("Cancel button pressed");
             break;
        case JOptionPane.CLOSED_OPTION:
            System.out.println("Closed button pressed");
            break;
       }
    }
}


 

public static void main(String args[])
         {
                 Frame fm=new TextEditor();
                 fm.setSize(700,700);
                 fm.setVisible(true);
                 fm.show();
         }
 }
