/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolmanagement.view.component.table.celleditor;

import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author ivano
 */
public class JDateChooserCellEditor extends DefaultCellEditor {

    private JDateChooser date;
   
    public JDateChooserCellEditor(JCheckBox checkBox) {
        super(checkBox);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        date = new JDateChooser();
        date.setDateFormatString("yyyy-MM-dd");
        date.setDate( Date.from(((LocalDate) value).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return date;
    }

    @Override
    public Object getCellEditorValue() {
        return ((JTextField)date.getDateEditor().getUiComponent()).getText(); 
    }

    @Override
    public boolean stopCellEditing() {
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }

}
