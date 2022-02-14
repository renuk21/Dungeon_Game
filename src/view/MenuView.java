package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JTextField;

class MenuView extends JFrame {
  JTextField textField;
  MenuView menuView;

  MenuView() {
    setSize(200, 200);

    textField = new JTextField();
    add(textField);
    setVisible(true);
    textField.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        setValueToText(textField.getText());
      }
    });
    menuView = this;
    textField.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        super.keyTyped(e);
        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
          menuView.dispatchEvent(new WindowEvent(menuView, WindowEvent.WINDOW_CLOSING));
        }
      }
    });

  }

  String getText() {
    return textField.getText();
  }

  void setValueToText(String value) {
    textField.setText(value);
  }
}
