import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class addContactForm extends JPanel {
  //create arrays of the forms parameters
  String[] labels = { "Name", "Phone Number", "E-Mail" };
  char[] mnemonics = { 'N', 'P', 'E' };
  int[] widths = { 30, 30, 30};

  //create array to store input
  public JTextField[] fields;

  // Create a form with the predefined labels, mnemonics, and sizes.
  public addContactForm() {
    super(new BorderLayout());
    JPanel labelPanel = new JPanel(new GridLayout(labels.length, 1));
    JPanel fieldPanel = new JPanel(new GridLayout(labels.length, 1));
    add(labelPanel, BorderLayout.WEST);
    add(fieldPanel, BorderLayout.CENTER);
    fields = new JTextField[labels.length];

    //iterate over each array, adding parameters to each field
    for (int i = 0; i < labels.length; i++) {
      fields[i] = new JTextField();
      if (i < widths.length)
        fields[i].setColumns(widths[i]);

      JLabel lab = new JLabel(labels[i], JLabel.RIGHT);
      lab.setLabelFor(fields[i]);
      if (i < mnemonics.length)
        lab.setDisplayedMnemonic(mnemonics[i]);

      labelPanel.add(lab);
      JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
      p.add(fields[i]);
      fieldPanel.add(p);
    }
  }
  //method to get text from each field
  public String getText(int i) {
    return (fields[i].getText());
  }

}
