import java.awt.TextArea;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.util.Hashtable;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.StateEdit;
import javax.swing.undo.StateEditable;
import javax.swing.undo.UndoManager;

class doableTextArea extends TextArea implements StateEditable
 {
  private final static String KEY_STATE="doableTextAreaKey";
  private boolean textChanged=false;
  private UndoManager doManager;
  private StateEdit currentEdit;

  public doableTextArea()
     {
      super();
     }
public doableTextArea(String string)
      {
       super(string);
      }
  public doableTextArea(int rows,int columns)
      {
       super(rows,columns);
      }
  public doableTextArea(String string,int rows,int columns)
         {
          super(string,rows,columns);
         }
  public doableTextArea(String string,int rows,int columns,int
scrollbars)
      {
       super(string,rows,columns,scrollbars);
      }

   public boolean undo(){
    try{
        doManager.undo();
        return true;
       }
     catch(CannotUndoException e)
        {
        System.out.println("cannot undo");
        return false;
        }
     }
    public boolean redo()
       {
       try{
         doManager.redo();
         return true;
         }
      catch(CannotRedoException e)
          {
            System.out.println("cannot redo");
            return false;
         }
       }


    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void storeState(Hashtable state)
       {
        state.put(KEY_STATE,getText());
       }

    @SuppressWarnings("rawtypes")
	public void restoreState(Hashtable state)
        {
         Object data=state.get(KEY_STATE);
         if(data!=null){
          setText((String)data);
          }
        }

   private void takeSnapshot()
       {
        if(textChanged)
        {
         currentEdit.end();
         doManager.addEdit(currentEdit);
         textChanged=false;
         currentEdit=new StateEdit(this);
         }
        }

     @SuppressWarnings("unused")
	private void initdoable1()
       {
         doManager =new UndoManager();
         currentEdit=new StateEdit(this);
         addKeyListener(new KeyAdapter(){
          public void keyPressed(KeyEvent event){
          if(event.isActionKey()){
          takeSnapshot();
          }
         }
        });

          addFocusListener(new FocusAdapter(){
         public void focusLost(FocusEvent fe){
          takeSnapshot();
          }
          });

         addTextListener(new TextListener(){
         public void textValueChanged(TextEvent e){
         textChanged=true;
         takeSnapshot();
         }
         });

      }
   }


