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

import java.util.HashMap;

/**
 * Represents one of the HTML <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#h-17.2.1">control types</a> in a <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html">form</a>
 * which have the potential to be <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#successful-controls">successful</a>.
 * This means that they can contribute name/value pairs to the <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#form-data-set">form data set</a> when the form is <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#submit-format">submitted</a>.
 * <p>
 * Each type of control has a certain behaviour in regards to the name/value pairs submitted.
 * This class defines that behaviour, so that the meaning of the name/value pairs submitted from an arbitrary HTML page can be determined.
 * <p>
 * Use the {@link StartTag#getFormControlType()} method to determine the type of control (if any) represented by
 * a start tag.
 *
 * @see FormField
 * @see FormFields
 */
public final class FormControlType {
	private String formControlTypeId;
	private String tagName;
	private boolean predefinedValue;
	private boolean submit;

	private static final HashMap ID_MAP=new HashMap(16,1.0F); // 12 types in total
	private static final HashMap INPUT_ELEMENT_TYPE_MAP=new HashMap(11,1.0F); // 8 input element types in total

	/**
	 * <code>&lt;<a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#h-17.5">button</a> type="submit" name="FieldName" value="PredefinedValue"&gt;Send&lt;/button&gt;</code>.
	 * <p>
	 * <code>{@link #getFormControlTypeId()} = "button"</code><br />
	 * <code>{@link #getTagName()} = {@link Tag#BUTTON}</code><br />
	 * <code>{@link #isPredefinedValue()} = true</code><br />
	 * <code>{@link #isSubmit()} = true</code><br />
	 */
	public static final FormControlType BUTTON=new FormControlType("button",Tag.BUTTON,true,true).register();

	/**
	 * <code>&lt;<a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#h-17.4.1">input</a> type="checkbox" name="FieldName" value="PredefinedValue" /&gt;</code>.
	 * <p>
	 * <code>{@link #getFormControlTypeId()} = "checkbox"</code><br />
	 * <code>{@link #getTagName()} = {@link Tag#INPUT}</code><br />
	 * <code>{@link #isPredefinedValue()} = true</code><br />
	 * <code>{@link #isSubmit()} = false</code><br />
	 */
	public static final FormControlType CHECKBOX=new FormControlType("checkbox",Tag.INPUT,true,false).register();

	/**
	 * <code>&lt;<a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#h-17.4.1">input</a> type="file" name="FieldName" value="DefaultFileName" /&gt;</code>.
	 * <p>
	 * <code>{@link #getFormControlTypeId()} = "file"</code><br />
	 * <code>{@link #getTagName()} = {@link Tag#INPUT}</code><br />
	 * <code>{@link #isPredefinedValue()} = false</code><br />
	 * <code>{@link #isSubmit()} = false</code><br />
	 */
	public static final FormControlType FILE=new FormControlType("file",Tag.INPUT,false,false).register();

	/**
	 * <code>&lt;<a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#h-17.4.1">input</a> type="hidden" name="FieldName" value="PredefinedValue" /&gt;</code>.
	 * <p>
	 * <code>{@link #getFormControlTypeId()} = "hidden"</code><br />
	 * <code>{@link #getTagName()} = {@link Tag#INPUT}</code><br />
	 * <code>{@link #isPredefinedValue()} = false</code><br />
	 * <code>{@link #isSubmit()} = false</code><br />
	 * <p>
	 * Note that {@link #isPredefinedValue()} returns <code>false</code> for this control type because the value of hidden fields is usually set via server or client side scripting.
	 */
	public static final FormControlType HIDDEN=new FormControlType("hidden",Tag.INPUT,false,false).register();

	/**
	 * <code>&lt;<a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#h-17.4.1">input</a> type="image" name="FieldName" src="ImageURL" value="PredefinedValue" /&gt;</code>.
	 * <p>
	 * <code>{@link #getFormControlTypeId()} = "image"</code><br />
	 * <code>{@link #getTagName()} = {@link Tag#INPUT}</code><br />
	 * <code>{@link #isPredefinedValue()} = true</code><br />
	 * <code>{@link #isSubmit()} = true</code><br />
	 */
	public static final FormControlType IMAGE=new FormControlType("image",Tag.INPUT,true,true).register();

	/**
	 * <code>&lt;<a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#h-17.4.1">input</a> type="password" name="FieldName" value="DefaultValue" /&gt;</code>.
	 * <p>
	 * <code>{@link #getFormControlTypeId()} = "password"</code><br />
	 * <code>{@link #getTagName()} = {@link Tag#INPUT}</code><br />
	 * <code>{@link #isPredefinedValue()} = false</code><br />
	 * <code>{@link #isSubmit()} = false</code><br />
	 */
	public static final FormControlType PASSWORD=new FormControlType("password",Tag.INPUT,false,false).register();

	/**
	 * <code>&lt;<a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#h-17.4.1">input</a> type="radio" name="FieldName" value="PredefinedValue" /&gt;</code>.
	 * <p>
	 * <code>{@link #getFormControlTypeId()} = "radio"</code><br />
	 * <code>{@link #getTagName()} = {@link Tag#INPUT}</code><br />
	 * <code>{@link #isPredefinedValue()} = true</code><br />
	 * <code>{@link #isSubmit()} = false</code><br />
	 */
	public static final FormControlType RADIO=new FormControlType("radio",Tag.INPUT,true,false).register();

	/**
	 * <code>&lt;<a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#h-17.6">select</a> name="FieldName" multiple&gt; &lt;option value="PredefinedValue"&gt;Display Text&lt;/option&gt; &lt;/select&gt;</code>.
	 * <p>
	 * <code>{@link #getFormControlTypeId()} = "select_multiple"</code><br />
	 * <code>{@link #getTagName()} = {@link Tag#SELECT}</code><br />
	 * <code>{@link #isPredefinedValue()} = true</code><br />
	 * <code>{@link #isSubmit()} = false</code><br />
	 */
	public static final FormControlType SELECT_MULTIPLE=new FormControlType("select_multiple",Tag.SELECT,true,false).register();

	/**
	 * <code>&lt;<a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#h-17.6">select</a> name="FieldName"&gt; &lt;option value="PredefinedValue"&gt;Display Text&lt;/option&gt; &lt;/select&gt;</code>.
	 * <p>
	 * <code>{@link #getFormControlTypeId()} = "select_single"</code><br />
	 * <code>{@link #getTagName()} = {@link Tag#SELECT}</code><br />
	 * <code>{@link #isPredefinedValue()} = true</code><br />
	 * <code>{@link #isSubmit()} = false</code><br />
	 */
	public static final FormControlType SELECT_SINGLE=new FormControlType("select_single",Tag.SELECT,true,false).register();

	/**
	 * <code>&lt;<a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#h-17.4.1">input</a> type="submit" name="FieldName" value="PredefinedValue" /&gt;</code>.
	 * <p>
	 * <code>{@link #getFormControlTypeId()} = "submit"</code><br />
	 * <code>{@link #getTagName()} = {@link Tag#INPUT}</code><br />
	 * <code>{@link #isPredefinedValue()} = true</code><br />
	 * <code>{@link #isSubmit()} = true</code><br />
	 */
	public static final FormControlType SUBMIT=new FormControlType("submit",Tag.INPUT,true,true).register();

	/**
	 * <code>&lt;<a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#h-17.4.1">input</a> type="text" name="FieldName" value="DefaultValue" /&gt;</code>.
	 * <p>
	 * <code>{@link #getFormControlTypeId()} = "text"</code><br />
	 * <code>{@link #getTagName()} = {@link Tag#INPUT}</code><br />
	 * <code>{@link #isPredefinedValue()} = false</code><br />
	 * <code>{@link #isSubmit()} = false</code><br />
	 */
	public static final FormControlType TEXT=new FormControlType("text",Tag.INPUT,false,false).register();

	/**
	 * <code>&lt;<a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#h-17.7">textarea</a> name="FieldName"&gt;Default Value&lt;/textarea&gt;</code>.
	 * <p>
	 * <code>{@link #getFormControlTypeId()} = "textarea"</code><br />
	 * <code>{@link #getTagName()} = {@link Tag#TEXTAREA}</code><br />
	 * <code>{@link #isPredefinedValue()} = false</code><br />
	 * <code>{@link #isSubmit()} = false</code><br />
	 */
	public static final FormControlType TEXTAREA=new FormControlType("textarea",Tag.TEXTAREA,false,false).register();

	private FormControlType(String formControlTypeId, String tagName, boolean predefinedValue, boolean submit) {
		this.formControlTypeId=formControlTypeId;
		this.tagName=tagName;
		this.predefinedValue=predefinedValue;
		this.submit=submit;
	}

	private FormControlType register() {
		ID_MAP.put(formControlTypeId,this);
		if (tagName==Tag.INPUT) INPUT_ELEMENT_TYPE_MAP.put(formControlTypeId,this);
		return this;
	}

	/**
	 * Returns a string which identifies this form control type.
	 * <p>
	 * This is the same as the control type's static field name in lower case, which is one of<br />
	 * <code>button</code>, <code>checkbox</code>, <code>file</code>, <code>hidden</code>, <code>image</code>,
	 * <code>password</code>, <code>radio</code>, <code>select_multiple</code>, <code>select_single</code>,
	 * <code>submit</code>, <code>text</code>, or <code>textarea</code>.
	 *
	 * @return a string which identifies this form control type.
	 */
	public String getFormControlTypeId() {
		return formControlTypeId;
	}

	/**
	 * Returns the {@linkplain StartTag#getName() name} of the tag that defines this form control type.
	 * @return the name of the tag that defines this form control type.
	 */
	public String getTagName() {
		return tagName;
	}

	/**
	 * Indicates whether any <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#current-value">value</a>
	 * submitted by this type of control is predefined in the HTML and typically not modified by the user or server/client scripts.
	 * <p>
	 * The predefined value is defined by the control's <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#initial-value">initial value</a>.
	 * <p>
	 * Note that the {@link #HIDDEN} type returns <code>false</code> for this method because the value of hidden fields is usually set via server or client side scripting.
	 *
	 * @return <code>true</code> if any <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#current-value">value</a> submitted by this type of control is predefined in the HTML and typically not modified by the user or server/client scripts, otherwise <code>false</code>.
	 */
	public boolean isPredefinedValue() {
		return predefinedValue;
	}

	/**
	 * Indicates whether this control type causes the form to be <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#submit-format">submitted</a>.
	 * <p>
	 * Returns <code>true</code> only for the {@link #SUBMIT}, {@link #BUTTON}, and {@link #IMAGE} instances.
	 *
	 * @return <code>true</code> if this control type causes the form to be <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#submit-format">submitted</a>, otherwise <code>false</code>.
	 */
	public boolean isSubmit() {
		return submit;
	}

	/**
	 * Indicates whether this control type represents a <code>select</code> element.
	 * <p>
	 * Returns <code>true</code> only for the {@link #SELECT_SINGLE} and {@link #SELECT_MULTIPLE} instances.
	 * <p>
	 * This method is exactly equivalent to testing {@link #getTagName()}<code>==</code>{@link Tag#SELECT}.
	 *
	 * @return <code>true</code> if this control type represents a <code>select</code> element, otherwise <code>false</code>.
	 */
	public boolean isSelect() {
		return tagName==Tag.SELECT;
	}

	/**
	 * Returns the {@link FormControlType} with the specified {@linkplain #getFormControlTypeId() ID}.
	 * @param formControlTypeId  the ID of a form control type.
	 * @return the {@link FormControlType} with the specified ID, or <code>null</code> if no such control exists.
	 * @see #getFormControlTypeId()
	 */
	public static FormControlType get(String formControlTypeId) {
		return (FormControlType)ID_MAP.get(formControlTypeId);
	}

	/**
	 * Returns a string which identifies this form control type.
	 * <p>
	 * This is equivalent to {@link #getFormControlTypeId()}.
	 *
	 * @return a string which identifies this form control type.
	 */
	public String toString() {
		return formControlTypeId;
	}

	protected static FormControlType getFromInputElementType(String typeAttributeValue) {
		return (FormControlType)INPUT_ELEMENT_TYPE_MAP.get(typeAttributeValue);
	}

	/**
	 * Indicates whether more than one control of this type with the same <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#control-name">name</a> can be <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#successful-controls">successful</a>.
	 * <p>
	 * Returns <code>false</code> only for the {@link #RADIO}, {@link #SUBMIT}, {@link #BUTTON}, and {@link #IMAGE} instances.
	 * <p>
	 * Note that before version 1.5 this method also returned <code>false</code> for the {@link #SELECT_SINGLE} instance.
	 * This was a bug resulting from confusion as to whether each <code>option</code> element in a
	 * <code>select</code> element constituted a control (since it is possible for multiple options to be
	 * <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#successful-controls">successful</a>)
	 * or only the <code>select</code> element as a whole.
	 * The {@link FormControl} class now clearly defines the control as the entire <code>select</code> element,
	 * meaning that multiple {@link #SELECT_SINGLE} controls with the same name will result in multiple values.
	 * <p>
	 * Because this may not be immediately intuitive, and the method is no longer used internally,
	 * this method has been deprecated as of version 1.5 to avoid any further confusion.
	 *
	 * @return <code>true</code> if more than one control of this type with the same <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#control-name">name</a> can be <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#successful-controls">successful</a>, otherwise <code>false</code>.
	 * @deprecated  Use the more useful {@link FormField#allowsMultipleValues()} method instead.
	 */
	public boolean allowsMultipleValues() {
		return !(this==RADIO || isSubmit());
	}

	/**
	 * Returns an array containing the additional field names submitted if a control of this type with the specified <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#control-name">name</a> is <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#successful-controls">successful</a>.
	 * <p>
	 * Returns <code>null</code> for all control types except {@link #IMAGE}.
	 * It relates to the extra <code><i>name</i>.x</code> and <code><i>name</i>.y</code> data submitted when a pointing device is used to activate an IMAGE control.
	 * <p>
 	 * This method has been deprecated as of version 1.5 as it is no longer used internally and
	 * was never very useful as a public method.
	 *
	 * @param name  the <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#control-name">name</a> of a form control.
	 * @return an array containing the additional field names submitted if a control of this type with the specified <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#control-name">name</a> is <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#successful-controls">successful</a>, or <code>null</code> if none.
	 * @deprecated  no replacement
	 */
	public String[] getAdditionalSubmitNames(String name) {
		if (this!=IMAGE) return null;
		String[] names=new String[2];
		names[0]=name+".x";
		names[1]=name+".y";
		return names;
	}

	/**
	 * Indicates whether an HTML tag with the specified name is potentially a form <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#h-17.2">control</a>.
	 * <p>
	 * Returns <code>true</code> if the specified tag name is one of
	 * "input", "textarea", "button" or "select" (ignoring case).
	 * <p>
 	 * This method has been deprecated as of version 1.5 as it is no longer used internally and
	 * was never very useful as a public method.
	 *
	 * @param tagName  the name of an HTML tag.
	 * @return <code>true</code> if an HTML tag with the specified name is potentially a form <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#h-17.2">control</a>, otherwise <code>false</code>.
	 * @deprecated  no replacement
	 */
	public static boolean isPotentialControl(String tagName) {
		return tagName.equalsIgnoreCase(Tag.INPUT) || tagName.equalsIgnoreCase(Tag.TEXTAREA) || tagName.equalsIgnoreCase(Tag.BUTTON) || tagName.equalsIgnoreCase(Tag.SELECT);
	}
}
