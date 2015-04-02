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

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a <em>field</em> in an HTML <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html">form</a>,
 * a <em>field</em> being defined as the combination of all {@linkplain FormControl form controls}
 * having the same <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#control-name">name</a>.
 * <p>
 * In addition to the {@link #getFormControls()} method, which returns a <code>Collection</code> of all the
 * {@link FormControl} objects that make up this field, the properties of a <code>FormField</code> object
 * describe how the values associated with the the field's
 * <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#control-name">name</a>
 * in a submitted <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#form-data-set">form data set</a>
 * should be interpreted.
 * These properties include whether multiple values can be expected, the number of values which would typically
 * be set by the user, and a list of values which are predefined in the HTML.
 * This information allows the server to store and format the data in an appropriate way.
 * <p>
 * A form field which allows user values will normally consist of a single control whose
 * {@link FormControlType#isPredefinedValue()} method returns <code>false</code>,
 * such as a {@link FormControlType#TEXT TEXT} control.
 * <p>
 * When a form field consists of more than one control, these controls will normally be all
 * be of the same {@linkplain FormControlType type} which has predefined values,
 * such as the {@link FormControlType#CHECKBOX CHECKBOX} control.
 * <p>
 * Form fields consisting of more than one control do not necessarily return multiple values.
 * A form field consisting of {@link FormControlType#CHECKBOX CHECKBOX} controls can return multiple values, whereas
 * a form field consisting of {@link FormControlType#CHECKBOX RADIO} controls will return at most one value.
 * <p>
 * Note that a <code>select</code> element containing multiple <code>option</code> elements is counted as
 * a single control.  See the {@link FormControl} class for more details.
 * <p>
 * The HTML author can disregard convention and mix all types of controls with the same name in the same form,
 * or include multiple controls of the same name which do not have predefined values.
 * The evidence that such an unusual combination is present is a {@linkplain #getUserValueCount() user value count}
 * greater than one, so your application can either log a warning that a poorly designed form has been encountered,
 * or take special action to try to interpret the multiple user values that might be submitted.
 * <p>
 * FormField objects are created automatically with the creation of a {@link FormFields} object.
 * <p>
 * The case sensitivity of form field names is determined by the static {@link FormFields#FieldNameCaseSensitive} property.
 *
 * @see FormFields
 * @see FormControl
 */
public final class FormField {
	private String name;
	private int userValueCount=0;
	private boolean allowsMultipleValues=false;
	private LinkedHashSet predefinedValues=null; // null if none
	private LinkedHashSet formControls=new LinkedHashSet();
	private transient FormControl firstFormControl=null; // this field is simply a cache for the getFirstFormControl() method
	protected int columnIndex; // see FormFields.initColumns()

	/** Constructor called from FormFields class. */
	FormField(String name) {
		this.name=name;
	}

	/**
	 * Returns the <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#control-name">name</a> of the field.
	 * <p>
	 * If {@link FormFields#FieldNameCaseSensitive} is <code>true</code>, the name will be returned in lower case.
	 *
	 * @return the <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#control-name">name</a> of the field.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the number of values which would typically be set by the user,
	 * and are not included in the list of {@linkplain #getPredefinedValues() predefined values}.
	 * This should in most cases be either <code>0</code> or <code>1</code>.
	 * <p>
	 * The word "typically" is used because the use of scripts can cause
	 * <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#h-17.2.1">control types</a>
	 * which normally have predefined values to be set by the user, which is a condition which is beyond
	 * the scope of this library to test for.
	 * <p>
	 * A value of <code>0</code> indicates the field values will consist only of
	 * {@linkplain #getPredefinedValues() predefined values}.
	 * This is the case when the field consists of only
	 * {@link FormControlType#CHECKBOX CHECKBOX}, {@link FormControlType#RADIO RADIO}, {@link FormControlType#BUTTON BUTTON},
	 * {@link FormControlType#SUBMIT SUBMIT}, {@link FormControlType#IMAGE IMAGE}, {@link FormControlType#SELECT_SINGLE SELECT_SINGLE}
	 * and {@link FormControlType#SELECT_MULTIPLE SELECT_MULTIPLE} form control types.
	 * <p>
	 * A value of <code>1</code> indicates the field values will consist of at most one value set by the user.
	 * It is still possible to receive multiple values in the unlikely event that the HTML author mixed
	 * controls of different types with the same name, but any others should consist only of
	 * {@linkplain #getPredefinedValues() predefined values}.
	 * <p>
	 * A value greater than <code>1</code> indicates that the HTML author has included multiple controls of the same
	 * name which do not have predefined values.  This would nearly always indicate an unintentional
	 * error in the HTML source document.
	 *
	 * @return the number of values which would typically be set by the user.
	 */
	public int getUserValueCount() {
		return userValueCount;
	}

	/**
	 * Indicates whether the field allows multiple values.
	 * <p>
	 * Returns <code>false</code> in any one of the following circumstances:
	 * <ul>
	 *  <li>The field consists of only one control (unless it is a
	 *   {@linkplain FormControlType#SELECT_MULTIPLE multiple select} with more than one option)
	 *  <li>The field consists entirely of {@linkplain FormControlType#RADIO radio buttons}
	 *  <li>The field consists entirely of {@linkplain FormControlType#isSubmit() submit} buttons
	 * </ul>
	 * If none of these three conditions are met, the method returns <code>true</code>.
	 *
	 * @return <code>true</code> if the field allows multiple values, otherwise <code>false</code>.
	 */
	public boolean allowsMultipleValues() {
		return allowsMultipleValues;
	}

	/**
	 * Returns a collection of the {@linkplain FormControl#getPredefinedValue() predefined values} of all {@linkplain FormControl controls} that make up this field.
	 * <p>
	 * An interator over this collection will return the values in the order of appearance in the source.
	 *
	 * @return a collection of the {@linkplain FormControl#getPredefinedValue() predefined values} of all {@linkplain FormControl controls} that make up this field, or <code>null</code> if none.
	 */
	public Collection getPredefinedValues() {
		return predefinedValues!=null ? predefinedValues : Collections.EMPTY_SET;
	}

	/**
	 * Returns a collection of all the {@linkplain FormControl form controls} that make up this field.
	 * <p>
	 * An iterator over this collection will return the controls in the order of appearance in the source.
	 *
	 * @return a collection containing all the {@linkplain FormControl form controls} that make up this field.
	 */
	public Collection getFormControls() {
		return formControls;
	}

	/**
	 * ****
	 */
	public void clearValues() {
		for (Iterator i=formControls.iterator(); i.hasNext();)
			((FormControl)i.next()).clearValues();
	}

	/**
	 * **** does not contain nulls
	 */
	public Collection getValues() {
		HashSet values=new HashSet();
		for (Iterator i=formControls.iterator(); i.hasNext();)
			((FormControl)i.next()).addValuesTo(values);
		return values;
	}

	public void setValues(Collection values) {
		clearValues();
		addValues(values);
	}

	public boolean setValue(CharSequence value) {
		clearValues();
		return addValue(value);
	}

	/**
	 * **** returns true if value was taken
	 */
	public boolean addValue(CharSequence value) {
		List userValueControls=null;
		for (Iterator i=formControls.iterator(); i.hasNext();) {
			FormControl formControl=(FormControl)i.next();
			if (formControls.size()>1 && !formControl.getFormControlType().isPredefinedValue()) {
				// if a user value control exists, but is not the only control with this name
				// (which shouldn't normally happen in a well designed form), save the user value control
				// for later as we will give all predefined value controls first opportunity to take the value.
				if (userValueControls==null) userValueControls=new LinkedList();
				userValueControls.add(formControl);
				continue;
			}
			if (formControl.addValue(value)) return true; // return value of true from formControl.addValue(value) means the value was taken by the control
		}
		if (userValueControls==null) return false;
		for (Iterator i=userValueControls.iterator(); i.hasNext();) {
			FormControl formControl=(FormControl)i.next();
			if (formControl.addValue(value)) return true;
		}
		return false;
	}

	/**
	 * Returns a string representation of this object useful for debugging purposes.
	 * @return a string representation of this object useful for debugging purposes.
	 */
	public String getDebugInfo() {
		StringBuffer sb=new StringBuffer();
		sb.append("Field: ").append(name).append(", UserValueCount=").append(userValueCount).append(", AllowsMultipleValues=").append(allowsMultipleValues);
		if (predefinedValues!=null) {
			for (Iterator i=predefinedValues.iterator(); i.hasNext();) {
				sb.append("\nPredefinedValue: ");
				sb.append(i.next());
			}
		}
		for (Iterator i=formControls.iterator(); i.hasNext();) {
			sb.append("\nFormControl: ");
			sb.append(((FormControl)i.next()).getDebugInfo());
		}
		sb.append("\n\n");
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

	protected void addValues(Collection values) {
		if (values!=null) for (Iterator i=values.iterator(); i.hasNext();) addValue((CharSequence)i.next());
	}

	protected void addValues(CharSequence[] values) {
		if (values!=null) for (int i=0; i<values.length; i++) addValue(values[i]);
	}

	void addFormControl(FormControl formControl, String predefinedValue) {
		// predefinedValue==null if we are adding a user value
		if (predefinedValue==null) {
			userValueCount++;
		} else {
			if (predefinedValues==null) predefinedValues=new LinkedHashSet();
			predefinedValues.add(predefinedValue);
		}
		formControls.add(formControl);
		allowsMultipleValues=calculateAllowsMultipleValues(formControl);
	}

	private boolean calculateAllowsMultipleValues(FormControl newFormControl) {
		// false if only one control (unless it is a multiple select with more than one option),
		// or all of the controls are radio buttons, or all of the controls are submit buttons
		// allowsMultipleValues -> true
		// userValueCount>1 -> true
		// userValueCount==1 -> predefinedValues!=null ? true : false
		// userValueCount==0 ->
		//   predefinedValues.size<=1 -> false
		//   predefinedValues.size>1 ->
		//     only one control -> control is select_multiple ? true : false
		//     all controls are radio buttons -> false
		//     all controls.isSubmit() -> false
		//     otherwise allowsMultipleValues=true
		if (allowsMultipleValues || userValueCount>1) return true;
		if (userValueCount==1) return predefinedValues!=null;
		// at this stage we know userValueCount==0  && predefinedValues.size()>=1
		if (predefinedValues.size()==1) return false;
		FormControlType newFormControlType=newFormControl.getFormControlType();
		if (formControls.size()==1) return newFormControlType==FormControlType.SELECT_MULTIPLE;
		// at this stage we know there are multiple predefined values in multiple controls.
		// if all of the controls are radio buttons or all are submit buttons, allowsMultipleValues is false, otherwise true.
		// checking only the first control and the new control is equivalent to checking them all because if they weren't all the same allowsMultipleValues would already be true
		FormControlType firstFormControlType=getFirstFormControl().getFormControlType();
		if (newFormControlType==FormControlType.RADIO && firstFormControlType==FormControlType.RADIO) return false;
		if (newFormControlType.isSubmit() && firstFormControlType.isSubmit()) return false;
		return true;
	}

	protected FormControl getFirstFormControl() {
		// formControls must be ordered collection for this method to work.
		// It has to return the first FormControl entered into the collection
		// for the algorithm in calculateAllowsMultipleValues() to work.
		if (firstFormControl==null) firstFormControl=(FormControl)formControls.iterator().next();
		return firstFormControl;
	}

	/** only called from FormFields class */
	protected void merge(FormField formField) {
		if (formField.userValueCount>userValueCount) userValueCount=formField.userValueCount;
		allowsMultipleValues=allowsMultipleValues || formField.allowsMultipleValues;
		if (predefinedValues==null) {
			predefinedValues=formField.predefinedValues;
		} else if (formField.predefinedValues!=null) {
			for (Iterator i=formField.predefinedValues.iterator(); i.hasNext();)
				predefinedValues.add(i.next());
		}
		for (Iterator i=formField.getFormControls().iterator(); i.hasNext();)
			formControls.add(i.next());
	}
}

