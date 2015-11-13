import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

    class WW implements ActionListener
          {
    	 doableTextArea txt;
            public void actionPerformed(ActionEvent e)
            {
              String se=e.getActionCommand();
              if(se.equals("Undo"))
              txt.undo();
            if(se.equals("Redo"))
            txt.redo();

            }
        }