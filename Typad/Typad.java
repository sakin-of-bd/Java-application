package typad;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;

public class Typad extends JFrame implements ActionListener{
    private ImageIcon npad;
    private Font f1,f2;
    private JMenuBar menubar;
    private JMenu file,edit,view,help;
    private JMenuItem newdoc,open,save,saveas,print,exit,undo,cut,copy,paste,selectall,zoomin,zoomout,about;
    private JTextArea ta;
    private JScrollPane scroll;
    private File currentFile;
    private UndoManager undoManager; 
    
    Typad(){
        init();
    }
    
    public void init(){
        
        
        //font
        f1=new Font("Lucida Consolas",Font.PLAIN,13);
        f2=new Font("Times New Roman",Font.PLAIN,18);
        
        
        //logo
        npad = new ImageIcon(getClass().getResource("typad.png"));
        Image img = npad.getImage(); 
        Image scaledImg = img.getScaledInstance(256,256,Image.SCALE_SMOOTH);
        this.setIconImage(scaledImg);
        
        
        //menubar
        menubar=new JMenuBar();
        this.setJMenuBar(menubar);
        menubar.setBackground(Color.white);
        
        
        //file
        file=new JMenu("File");
        file.setFont(f1);
        menubar.add(file);
        
        newdoc=new JMenuItem("New");
        file.add(newdoc);
        newdoc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
        newdoc.addActionListener(this);
        
        open=new JMenuItem("Open");
        file.add(open);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
        open.addActionListener(this);
        
        save=new JMenuItem("Save");
        file.add(save);
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
        save.addActionListener(this);
        
        saveas=new JMenuItem("Save as");
        file.add(saveas);
        saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        saveas.addActionListener(this);
        
        print=new JMenuItem("Print");
        file.add(print);
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,ActionEvent.CTRL_MASK));
        print.addActionListener(this);
        
        exit=new JMenuItem("Exit");
        file.add(exit);
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,ActionEvent.CTRL_MASK));
        exit.addActionListener(this);
        
        
        //edit
        edit=new JMenu("Edit");
        edit.setFont(f1);
        menubar.add(edit);
        
        undo=new JMenuItem("Undo");
        edit.add(undo);
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,ActionEvent.CTRL_MASK));
        undo.addActionListener(this);
        
        cut=new JMenuItem("Cut");
        edit.add(cut);
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));
        cut.addActionListener(this);
        
        copy=new JMenuItem("Copy");
        edit.add(copy);
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
        copy.addActionListener(this);
        
        paste=new JMenuItem("Paste");
        edit.add(paste);
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK));
        paste.addActionListener(this);
        
        selectall=new JMenuItem("Select all");
        edit.add(selectall);
        selectall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.CTRL_MASK));
        selectall.addActionListener(this);
        
        
        //view
        view=new JMenu("View");
        view.setFont(f1);
        menubar.add(view);
        
        zoomin=new JMenuItem("Zoom in");
        view.add(zoomin);
        zoomin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS,ActionEvent.CTRL_MASK));
        zoomin.addActionListener(this);
        
        zoomout=new JMenuItem("Zoom out");
        view.add(zoomout);
        zoomout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS,ActionEvent.CTRL_MASK));
        zoomout.addActionListener(this);
        
        
        //help
        help = new JMenu("Help");
        help.setFont(f1);
        menubar.add(help);

        //help menu
        JMenuItem helpItem = new JMenuItem("Help");
        help.add(helpItem);
        helpItem.addActionListener(this);
        
        
        //about
        about = new JMenu("About");
        about.setFont(f1);
        menubar.add(about);

        //help menu
        JMenuItem aboutItem = new JMenuItem("About");
        about.add(aboutItem);
        aboutItem.addActionListener(this);
        
        
        //text area
        ta=new JTextArea();
        ta.setFont(f2);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        this.add(ta);
        
        
        //scroll
        scroll=new JScrollPane(ta);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        this.add(scroll);
        
        
        
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        
        
    if (ae.getActionCommand().equals("New")) 
    {
        ta.setText("");  // Clear the text area
        currentFile = null;  // Reset current file
    } 
    
    
    else if (ae.getActionCommand().equals("Open")) 
    {
        JFileChooser choose = new JFileChooser();
        choose.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter restrict = new FileNameExtensionFilter("only .txt files", "txt");
        choose.addChoosableFileFilter(restrict);
        
        int action = choose.showOpenDialog(this);
        if (action != JFileChooser.APPROVE_OPTION) 
        {
            return;
        }
        currentFile = choose.getSelectedFile();  // Set current file
        try (BufferedReader reader = new BufferedReader(new FileReader(currentFile))) 
        {
            ta.read(reader, null);  // Read the file content into the text area
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    } 
    
    
    else if (ae.getActionCommand().equals("Save")) 
    {
        if (currentFile != null) 
        {  
            // If a file is already selected, save directly
            saveFile(currentFile);
        } 
        else 
        {
            // Otherwise,perform Save As
            saveAs();  
        }
    } 
    
    
    else if (ae.getActionCommand().equals("Save as")) {
        saveAs();
    }
    
    
    else if (ae.getActionCommand().equals("Print")) {
        try
        {
            ta.print();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    else if(ae.getActionCommand().equals("Print")) 
    {
        try{
            boolean complete=ta.print();
            if(complete)
            {
                JOptionPane.showMessageDialog(this,"Printing complete!","Print",JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(this,"Printing was cancelled.","Print", JOptionPane.WARNING_MESSAGE);
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, "Failed to print: "+e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    
    else if (ae.getActionCommand().equals("Exit")) 
    {
        int response=JOptionPane.showConfirmDialog(this,"Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
        if (response==JOptionPane.YES_OPTION) 
        {
          System.exit(0); // Close
        }
    }
    
    
    else if (ae.getActionCommand().equals("Undo")) 
    {
        // Implement undo functionality
        if (undoManager.canUndo()) 
        {
          undoManager.undo();
        }
    }
        
    
    else if (ae.getActionCommand().equals("Cut")) 
    {
        ta.cut();  
    }
        
    
    else if (ae.getActionCommand().equals("Copy")) 
    {
        ta.copy(); 
    }
        
    else if (ae.getActionCommand().equals("Paste")) 
    {
        ta.paste();  
    }
        
    else if (ae.getActionCommand().equals("Select all")) 
    {
        ta.selectAll();  
    }
        
    else if (ae.getActionCommand().equals("Zoom in")) 
    {
        Font currentFont = ta.getFont();
        float newSize = currentFont.getSize() + 2.0f; 
        ta.setFont(currentFont.deriveFont(newSize));
    }
        
    else if (ae.getActionCommand().equals("Zoom out")) 
    {
        Font currentFont = ta.getFont();
        float newSize = currentFont.getSize() - 2.0f; 
        if (newSize > 8.0f) 
            {
                ta.setFont(currentFont.deriveFont(newSize));
            }
    }


    else if(ae.getActionCommand().equals("Help")) 
    {
        try
        {
            // Use the Desktop class to open the default browser
            Desktop desktop=Desktop.getDesktop();
            URI uri=new URI("https://www.facebook.com/sakinOfbd");
                
            if(Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE)) 
            {
                desktop.browse(uri);
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Desktop or browse action not supported!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } 
        catch(Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to open the web page!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    else if(ae.getActionCommand().equals("About")) 
    {
        About aboutFrame = new About();
        aboutFrame.setVisible(true);
    }
    
    
}

   //Method to handle Save As
   private void saveAs(){
    JFileChooser fileChooser=new JFileChooser();
    int option=fileChooser.showSaveDialog(this); //Show Save Dialog
    if (option==JFileChooser.APPROVE_OPTION) {
        currentFile=fileChooser.getSelectedFile(); //Set the selected file
        saveFile(currentFile);  // Save the file
    }
   }

   // Method to save the content of the JTextArea to a file
   private void saveFile(File file){
    try(FileWriter writer = new FileWriter(file)) 
    {
        writer.write(ta.getText()); // Write the content from the text area to the file
        JOptionPane.showMessageDialog(this,"File saved successfully!");
    } 
    catch(IOException ex) 
    {
        JOptionPane.showMessageDialog(this,"Error saving file: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
    }
}


    
    public static void main(String[] args) {
       Typad frame=new Typad();
       frame.setVisible(true);
       frame.setTitle("Typad");
       frame.setMinimumSize(new Dimension(900,600));
       frame.setLocation(200,70);
    }
    
}
