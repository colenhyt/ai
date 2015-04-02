// Jericho HTML Parser - Java based library for analysing and manipulating HTML
// Version 1.5
// Copyright (C) 2004 Martin Jericho
// http://jerichohtml.sourceforge.net/
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
// http://www.gnu.org/copyleft/lesser.html
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

package easyshop.html.jericho;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a collection of {@link FormField} objects.
 * <p>
 * <code>FormFields</code> objects are created using the {@link #FormFields(Collection formControls)} constructor
 * or by calling the {@link Segment#findFormFields()} method.
 * <p>
 * See the documentation of the {@link FormField} class for a description of the relationship between
 * <i>fields</i> and <i>controls</i>.
 * <p>
 * The case sensitivity of form field names is determined by the static {@link #FieldNameCaseSensitive} property.
 * <p>
 * <b>Examples:</b>
 * <ol>
 *  <li>
 *   Output the data received from in the current <code>ServletRequest</code> (submitted from the form in the source document) to a <code>.CSV</code> file:
 *   <br />(See also the sample program FormFieldCSVOutput)
 *   <pre>
 *    Source source=new Source(htmlText);
 *    FormFields formFields=source.findFormFields();
 *    Writer writer=new FileWriter("FormData.csv");
 *    Util.outputCSVLine(writer,formFields.getColumnHeadings());
 *    Util.outputCSVLine(writer,formFields.getColumnValues(servletRequest.getParameterMap()));
 *    writer.close();
 *   </pre>
 *  <li>Output the form in the source document populated with the values received in the current <code>ServletRequest</code>:
 *   <pre>
 *    Source source=new Source(htmlText);
 *    FormFields formFields=source.findFormFields();
 *    formFields.setValuesMap(servletRequest.getParameterMap());
 *    OutputDocument outputDocument=new OutputDocument(source);
 *    outputDocument.add(formFields);
 *    outputDocument.output(servletResponse.getWriter());
 *   </pre>
 *  <li>Create a new document and set the values in the form named "MyForm" to new values:
 *   <br />(See also the sample program FormFieldSetValues)
 *   <pre>
 *    Source source=new Source(htmlText);
 *    Element myForm=null;
 *    List formElements=source.findAllElements(Tag.FORM);
 *    for (Iterator i=formElements.iterator(); i.hasNext();) {
 *      Element formElement=(Element)i.next();
 *      String formName=formElement.getAttributes().getValue("name");
 *      if ("MyForm".equals(formName)) {
 *        myForm=form;
 *        break;
 *      }
 *    }
 *    FormFields formFields=myForm.findFormFields();
 *    formFields.clearValues(); // clear any values that might be set in the source document
 *    formFields.addValue("Name","Humphrey Bear");
 *    formFields.addValue("MailingList","A");
 *    formFields.addValue("MailingList","B");
 *    formFields.addValue("FavouriteFair","honey");
 *    OutputDocument outputDocument=new OutputDocument(source);
 *    outputDocument.add(formFields);
 *    String newHtmlText=outputDocument.toString();
 *   </pre>
 * </ol>
 * @see FormField
 */
public final class FormFields extends AbstractCollection {
	private LinkedHashMap map=new LinkedHashMap();
	private ArrayList formControls=new ArrayList();

	/**
	 * Constructs a new <code>FormFields</code> object based on the specified {@linkplain FormControl form controls}.
	 * @param formControls  a collection of {@link FormControl} objects.
	 * @see Segment#findFormFields()
	 */
	public FormFields(Collection formControls) {
		// Not sure what the restrictions are on passing "this" as a parameter inside
		// a constructor, but it seems to work and is asthetically better than having a
		// static FormFields constructFrom(List formControls) method.
		for (Iterator i=formControls.iterator(); i.hasNext();) {
			FormControl formControl=(FormControl)i.next();
			if (formControl.getName()!=null && formControl.getName().length()!=0) {
				formControl.addToFormFields(this);
				this.formControls.add(formControl);
			}
		}
	}

	/**
	 * Returns the number of <code>FormField</code> objects.
	 * @return the number of <code>FormField</code> objects.
	 */
	public int getCount() {
		return map.size();
	}

	/**
	 * Returns the number of <code>FormField</code> objects.
	 * <p>
	 * This is equivalent to {@link #getCount()},
	 * and is necessary to for the implementation of the <code>java.util.Collection</code> interface.
	 *
	 * @return the number of <code>FormField</code> objects.
	 */
	public int size() {
		return getCount();
	}

	/**
	 * Returns the FormField with the specified name.
	 * <p>
	 * The case sensitivity of the name argument is determined by the static {@link #FieldNameCaseSensitive} property.
	 *
	 * @param name  the name of the FormField to get.
	 * @return the FormField with the specified name, or <code>null</code> if no FormField with the specified name exists.
	 */
	public FormField get(String name) {
		if (!FieldNameCaseSensitive) name=name.toLowerCase();
		return (FormField)map.get(name);
	}

	/**
	 * Returns an iterator over the {@link FormField} objects in the collection.
	 * <p>
	 * The order in which the form fields are iterated corresponds to the order of appearance
	 * of each form field's first {@link FormControl} in the source document.
	 * <p>
	 * If this <code>FormFields</code> object has been {@linkplain #merge(FormFields) merged} with another,
	 * the ordering is no longer guaranteed.
	 *
	 * @return an iterator over the {@link FormField} objects in the collection.
	 */
	public Iterator iterator() {
		return map.values().iterator();
	}

	/**
	 * ****
	 */
	public void clearValues() {
		for (Iterator i=formControls.iterator(); i.hasNext();)
			((FormControl)i.next()).clearValues();
	}

	/**
	 * **** Returns a map of name to String[], similar to
	 * <code><a target="_blank" href="http://java.sun.com/products/servlet/2.3/javadoc/javax/servlet/ServletRequest.html#getParameterMap()">javax.servlet.ServletRequest.getParameterMap()</a></code>
	 */
	public Map getValuesMap() {
		HashMap map=new HashMap((int)(getCount()/0.7));
		for (Iterator i=iterator(); i.hasNext();) {
			FormField formField=(FormField)i.next();
			Collection values=formField.getValues();
			if (values.isEmpty()) continue;
			String[] valuesArray=new String[values.size()];
			Iterator valuesIterator=values.iterator();
			for (int x=0; x<values.size(); x++) valuesArray[x]=valuesIterator.next().toString();
			map.put(formField.getName(),valuesArray);
		}
		return map;
	}

	/**
	 * **** Sets the values using a map of name to string[], compatible with result from
	 * <code><a target="_blank" href="http://java.sun.com/products/servlet/2.3/javadoc/javax/servlet/ServletRequest.html#getParameterMap()">javax.servlet.ServletRequest.getParameterMap()</a></code>
	 */
	public void setValuesMap(Map valuesMap) {
		clearValues();
		if (map==null) return;
		for (Iterator i=valuesMap.entrySet().iterator(); i.hasNext();) {
			Map.Entry entry=(Map.Entry)i.next();
			String name=entry.getKey().toString();
			FormField formField=get(name);
			if (formField!=null) {
				if (entry.getValue() instanceof Collection)
					formField.addValues((Collection)entry.getValue());
				else
					formField.addValues((CharSequence[])entry.getValue());
			}
		}
	}

	/**
	 * ****
	 */
	public boolean setValue(String name, CharSequence value) {
		FormField formField=get(name);
		return formField==null ? false : formField.setValue(value);
	}

	/**
	 * ****
	 */
	public boolean addValue(String name, CharSequence value) {
		FormField formField=get(name);
		return formField==null ? false : formField.addValue(value);
	}

	/**
	 * ****
	 */
	public String[] getColumnHeadings() {
		initColumns();
		String[] columnHeadings=new String[columns.length];
		for (int i=0; i<columns.length; i++) {
			Column column=columns[i];
			String fieldName=column.formField.getFirstFormControl().getName(); // use this instead of formControl.getName() so that the original case is used even if FieldNameCaseSensitive is false.
			columnHeadings[i]=column.predefinedValue!=null
				? fieldName+'.'+column.predefinedValue
				: fieldName;
		}
		return columnHeadings;
	}

	/**
	 * ****
	 */
	public String[] getColumnValues(Map valuesMap) {
		initColumns();
		String[] columnValues=new String[columns.length];
		if (ColumnFalse!=null) {
			// initialise all boolean columns with false string
			for (int i=0; i<columns.length; i++)
				if (columns[i].isBoolean) columnValues[i]=ColumnFalse;
		}
		for (Iterator i=valuesMap.entrySet().iterator(); i.hasNext();) {
			Map.Entry entry=(Map.Entry)i.next();
			String name=entry.getKey().toString();
			FormField formField=get(name);
			if (formField!=null) {
				Collection values=(entry.getValue() instanceof Collection)
					? (Collection)entry.getValue()
					: Arrays.asList((CharSequence[])entry.getValue());
				int columnIndex=formField.columnIndex;
				for (Iterator valueIterator=values.iterator(); valueIterator.hasNext();) {
					String value=valueIterator.next().toString();
					for (int ci=columnIndex; ci<columns.length; ci++) {
						Column column=columns[ci];
						if (column.formField!=formField) break;
						if (column.predefinedValue!=null) {
							if (!column.predefinedValue.equals(value)) continue;
							columnValues[ci]=ColumnTrue;
						} else {
							if (column.isBoolean) {
								if (value!=null) columnValues[ci]=ColumnTrue;
							} else if (columnValues[ci]==null) {
								columnValues[ci]=value;
							} else {
								columnValues[ci]=columnValues[ci]+ColumnMultipleValueSeparator+value;
							}
						}
						break;
					}
				}
			}
		}
		return columnValues;
	}

	/**
	 * ****
	 */
	public String[] getColumnValues() {
		return getColumnValues(getValuesMap());
	}

	private void initColumns() {
		if (columns!=null) return;
		ArrayList columnList=new ArrayList();
		for (Iterator i=iterator(); i.hasNext();) {
			FormField formField=(FormField)i.next();
			formField.columnIndex=columnList.size();
			if (!formField.allowsMultipleValues() || formField.getPredefinedValues().isEmpty()) {
				columnList.add(new Column(formField,formField.getPredefinedValues().size()==1,null));
			} else {
				// add a column for every predefined value
				for (Iterator pvi=formField.getPredefinedValues().iterator(); pvi.hasNext();)
					columnList.add(new Column(formField,true,(String)pvi.next()));
				if (formField.getUserValueCount()>0) columnList.add(new Column(formField,false,null)); // add a column for user values, must come after predefined values for algorithm in getColumnValues to work
			}
		}
		columns=(Column[])columnList.toArray(new Column[columnList.size()]);
	}
	private Column[] columns=null;
	public static String ColumnMultipleValueSeparator=",";
	public static String ColumnTrue=Boolean.toString(true);
	public static String ColumnFalse=null;

	private static class Column {
		public FormField formField;
		public boolean isBoolean;
		public String predefinedValue;
		public Column(FormField formField, boolean isBoolean, String predefinedValue) {
			this.formField=formField;
			this.isBoolean=isBoolean;
			this.predefinedValue=predefinedValue;
		}
	}

	/**
	 * ****
	 */
	public List getFormControls() {
		return formControls;
	}

	/**
	 * Merges the specified <code>FormFields</code> into this <code>FormFields</code> collection.
	 * This is useful if a full collection of possible form fields is required from multiple {@link Source} documents.
	 * <p>
	 * If both collections contain a <code>FormField</code> with the same {@linkplain FormField#getName() name},
	 * the resulting <code>FormField</code> will have the following properties:
	 * <ul>
	 * <li>{@link FormField#getUserValueCount() getUserValueCount()} : the maximum user value count from both form fields</li>
	 * <li>{@link FormField#allowsMultipleValues() allowsMultipleValues()} : <code>true</code> if either form field allows multiple values</li>
	 * <li>{@link FormField#getPredefinedValues() getPredefinedValues()} : the union of predefined values in both form fields</li>
	 * <li>{@link FormField#getFormControls() getFormControls()} : the union of {@linkplain FormControl form controls} from both form fields</li>
	 * </ul>
	 * <p>
	 * NOTE: Some underlying data structures may end up being shared between the two merged <code>FormFields</code> collections.
	 */
	public void merge(FormFields formFields) {
		for (Iterator i=formFields.iterator(); i.hasNext();) {
			FormField formField=(FormField)i.next();
			String name=formField.getName();
			FormField existingFormField=get(name);
			if (existingFormField==null)
				add(formField);
			else
				existingFormField.merge(formField);
		}
	}

	/**
	 * Returns a string representation of this object useful for debugging purposes.
	 * @return a string representation of this object useful for debugging purposes.
	 */
	public String getDebugInfo() {
		StringBuffer sb=new StringBuffer();
		for (Iterator i=iterator(); i.hasNext();) {
			sb.append(i.next());
		}
		return sb.toString();
	}

	/**
	 * Returns a string representation of this object useful for debugging purposes.
	 * <p>
	 * This is equivalent to {@link #getDebugInfo()}.
	 *
	 * @return a string representation of this object useful for debugging purposes.
	 */
	public String toString() {
		return getDebugInfo();
	}

	/**
	 * Determines whether field names are treated as case sensitive.
	 * <p>
	 * Microsoft Internet Explorer treats field names as case insensitive,
	 * while Mozilla treats them as case sensitive.
	 * <p>
	 * The default value is <code>false</code>, consistent with the interpretation of IE.
	 * <p>
	 * This is a global setting which affects all instances of the FormFields class.
	 * It should be set to the desired configuration before any instances are created.
	 */
	public static boolean FieldNameCaseSensitive=false;

	void add(FormControl formControl) {
		add(formControl,formControl.getPredefinedValue());
	}

	void add(FormControl formControl, String predefinedValue) {
		add(formControl,predefinedValue,formControl.name);
	}

	void addName(FormControl formControl, String name) {
		add(formControl,null,name);
	}

	void add(FormControl formControl, String predefinedValue, String name) {
		if (!FieldNameCaseSensitive) name=name.toLowerCase();
		FormField formField=(FormField)map.get(name);
		if (formField==null) {
			formField=new FormField(name);
			add(formField);
		}
		formField.addFormControl(formControl,predefinedValue);
	}

	void addToOutputDocument(OutputDocument outputDocument) {
		for (Iterator i=formControls.iterator(); i.hasNext();)
			outputDocument.add((FormControl)i.next());
	}

	private void add(FormField formField) {
		map.put(formField.getName(),formField);
	}
}
