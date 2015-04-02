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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#h-17.2">form controls</a>
 */
public abstract class FormControl extends Segment {
	FormControlType formControlType;
	String name;
	ElementContainer elementContainer;
	FormControlOutputStyle outputStyle=FormControlOutputStyle.NORMAL;

	private static final String CHECKBOX_NULL_DEFAULT_VALUE="on";
	private static Comparator COMPARATOR=new PositionComparator();

	static FormControl construct(Element element) {
		String tagName=element.getStartTag().getName();
		if (tagName==Tag.INPUT) {
			String typeAttributeValue=element.getAttributes().getRawValue(Attribute.TYPE);
			if (typeAttributeValue==null) return new InputFormControl(element,FormControlType.TEXT);
			FormControlType formControlType=FormControlType.getFromInputElementType(typeAttributeValue.toLowerCase());
			if (formControlType==null) {
				element.source.log(element.begin,"INPUT control with unrecognised type \""+typeAttributeValue+"\" assumed to be type \"text\"");
				formControlType=FormControlType.TEXT;
			}
			if (formControlType==FormControlType.TEXT) return new InputFormControl(element,formControlType);
			if (formControlType==FormControlType.CHECKBOX || formControlType==FormControlType.RADIO) return new RadioCheckboxFormControl(element,formControlType);
			if (formControlType==FormControlType.SUBMIT) return new SubmitFormControl(element,formControlType);
			if (formControlType==FormControlType.IMAGE) return new ImageSubmitFormControl(element);
			// formControlType is HIDDEN || PASSWORD || FILE
			return new InputFormControl(element,formControlType);
		} else if (tagName==Tag.SELECT) {
			return new SelectFormControl(element);
		} else if (tagName==Tag.TEXTAREA) {
			return new TextAreaFormControl(element);
		} else if (tagName==Tag.BUTTON) {
			return element.getAttributes().getRawValue(Attribute.TYPE).equalsIgnoreCase("submit") ? new SubmitFormControl(element,FormControlType.BUTTON) : null;
		} else {
			return null;
		}
	}

	private FormControl(Element element, FormControlType formControlType, boolean loadPredefinedValue) {
		super(element.source,element.begin,element.end);
		elementContainer=new ElementContainer(element,loadPredefinedValue);
		this.formControlType=formControlType;
		name=element.getAttributes().getValue(Attribute.NAME);
		verifyName();
	}

	public final FormControlType getFormControlType() {
		return formControlType;
	}

	public final String getName() {
		return name;
	}

	public final Element getElement() {
		return elementContainer.element;
	}

	public Iterator getOptionElementIterator() {
		throw new UnsupportedOperationException("Only SELECT controls contain option elements");
	}

	public FormControlOutputStyle getOutputStyle() {
		return outputStyle;
	}

	public void setOutputStyle(FormControlOutputStyle outputStyle) {
		this.outputStyle=outputStyle;
	}

	public final Map getAttributesMap() {
		return elementContainer.getAttributesMap();
	}

	public final boolean isDisabled() {
		return elementContainer.getBooleanAttribute(Attribute.DISABLED);
	}

	public final void setDisabled(boolean disabled) {
		elementContainer.setBooleanAttribute(Attribute.DISABLED,disabled);
	}

	public boolean isChecked() {
		throw new UnsupportedOperationException("This property is only relevant for CHECKBOX and RADIO controls");
	}

	/**
	 * Returns the <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#initial-value">initial value</a> of this control if it has a {@linkplain FormControlType#isPredefinedValue() predefined} value.
	 * <p>
	 * This method throws a <code>java.lang.UnsupportedOperationException</code>
	 * if called on a {@link FormControlType#isSelect() select} control
	 * since they typically contain multiple predefined values rather than just one.
	 * In this case the {@link #getPredefinedValues()} method should be used instead
	 * to get a collection of all the predefined values.
	 *
	 * @return the <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#initial-value">initial value</a> of this control if it has a {@linkplain FormControlType#isPredefinedValue() predefined} value, or <code>null</code> if none.
	 */
	public String getPredefinedValue() {
		return elementContainer.predefinedValue;
	}

	/**
	 * Returns a collection of all {@linkplain #getPredefinedValue() predefined values} in this control.
	 * <p>
	 * This method is most useful for {@link FormControlType#isSelect() select} controls since they typically contain multiple predefined values.
	 * In other controls it will return a collection with zero or one item based on the output of the
	 * {@link #getPredefinedValue()} method, so for better efficiency it is recommended to use the
	 * {@link #getPredefinedValue()} method instead.
	 * <p>
	 * The multiple predefined values of a {@link FormControlType#isSelect() select} control are defined by the <code>option</code>
	 * elements within it.
	 * Each <code>option</code> element has a
	 * <a target="_blank" href="http://www.w3.org/TR/html4/interact/forms.html#initial-value">initial value</a>
	 * defined by the value of its <code>value</code> attribute, or if this attribute is not present, by its
	 * {@linkplain CharacterReference#decode(CharSequence) decoded} {@linkplain Element#getContentText() content text}
	 * with collapsed white space.<br />
	 * The {@link CharacterReference#decodeCollapseWhiteSpace(CharSequence)} method internally provides the necessary
	 * conversion of the content text for this purpose.
	 *
	 * @return a collection of all {@linkplain #getPredefinedValue() predefined values} in this control, guaranteed not <code>null</code>.
	 * @see FormField#getPredefinedValues()
	 */
	public Collection getPredefinedValues() {
		return getPredefinedValue()!=null ? Collections.singleton(getPredefinedValue()) : Collections.EMPTY_SET;
	}

	public final void clearValues() {
		addValue(null);
	}

	public abstract boolean addValue(CharSequence value); // value of null means clear value
	abstract void addValuesTo(Collection collection); // should not add null values
	abstract void addToFormFields(FormFields formFields);
	abstract void addToOutputDocument(OutputDocument outputDocument);

	public String getDebugInfo() {
		StringBuffer sb=new StringBuffer();
		sb.append(formControlType).append(" name=\"").append(name).append('"');
		if (elementContainer.predefinedValue!=null) sb.append(" PredefinedValue=\"").append(elementContainer.predefinedValue).append('"');
		sb.append(" - ").append(getElement().getDebugInfo());
		return sb.toString();
	}

	static final class InputFormControl extends FormControl {
		// TEXT, HIDDEN, PASSORD or FILE
		public InputFormControl(Element element, FormControlType formControlType) {
			super(element,formControlType,false);
		}
		public boolean addValue(CharSequence value) {
			elementContainer.setAttributeValue(Attribute.VALUE,value);
			return true;
		}
		void addValuesTo(Collection collection) {
			CharSequence value=elementContainer.getAttributeValue(Attribute.VALUE);
			if (value!=null) collection.add(value);
		}
		void addToFormFields(FormFields formFields) {
			formFields.add(this);
		}
		void addToOutputDocument(OutputDocument outputDocument) {
			if (outputStyle==FormControlOutputStyle.REMOVE) {
				outputDocument.add(new StringOutputSegment(getElement(),null));
			} else if (outputStyle==FormControlOutputStyle.DISPLAY_VALUE) {
				String output=null;
				if (formControlType!=FormControlType.HIDDEN) {
					CharSequence value=elementContainer.getAttributeValue(Attribute.VALUE);
					if (formControlType==FormControlType.PASSWORD && value!=null) value=getString(FormControlOutputStyle.DisplayValueConfig.PasswordChar,value.length());
					output=getDisplayValueHTML(value,false);
				}
				outputDocument.add(new StringOutputSegment(getElement(),output));
			} else {
				addAttributesToOutputDocumentIfModified(outputDocument);
			}
		}
	}

	static final class TextAreaFormControl extends FormControl {
		// TEXTAREA
		public CharSequence value=UNCHANGED;
		private static final String UNCHANGED=new String();
		public TextAreaFormControl(Element element) {
			super(element,FormControlType.TEXTAREA,false);
		}
		public boolean addValue(CharSequence value) {
			this.value=value;
			return true;
		}
		void addValuesTo(Collection collection) {
			CharSequence value=getValue();
			if (value!=null) collection.add(value);
		}
		void addToFormFields(FormFields formFields) {
			formFields.add(this);
		}
		void addToOutputDocument(OutputDocument outputDocument) {
			if (outputStyle==FormControlOutputStyle.REMOVE) {
				outputDocument.add(new StringOutputSegment(getElement(),null));
			} else if (outputStyle==FormControlOutputStyle.DISPLAY_VALUE) {
				outputDocument.add(new StringOutputSegment(getElement(),getDisplayValueHTML(getValue(),true)));
			} else {
				addAttributesToOutputDocumentIfModified(outputDocument);
				if (value!=UNCHANGED)
					outputDocument.add(new StringOutputSegment(getElement().getContent(),CharacterReference.encode(value)));
			}
		}
		private CharSequence getValue() {
			return (value==UNCHANGED) ? CharacterReference.decode(getElement().getContentText()) : value;
		}
	}

	static final class RadioCheckboxFormControl extends FormControl {
		// RADIO or CHECKBOX
		public RadioCheckboxFormControl(Element element, FormControlType formControlType) {
			super(element,formControlType,true);
			if (elementContainer.predefinedValue==null) {
				elementContainer.predefinedValue=CHECKBOX_NULL_DEFAULT_VALUE;
				element.source.log(element.begin,"compulsory \"value\" attribute of "+formControlType.getFormControlTypeId()+" control \""+name+"\" is missing, assuming the value \""+CHECKBOX_NULL_DEFAULT_VALUE+'"');
			}
		}
		public boolean addValue(CharSequence value) {
			return elementContainer.setSelected(value,Attribute.CHECKED,formControlType==FormControlType.CHECKBOX);
		}
		void addValuesTo(Collection collection) {
			if (isChecked()) collection.add(getPredefinedValue());
		}
		public boolean isChecked() {
			return elementContainer.getBooleanAttribute(Attribute.CHECKED);
		}
		void addToFormFields(FormFields formFields) {
			formFields.add(this);
		}
		void addToOutputDocument(OutputDocument outputDocument) {
			if (outputStyle==FormControlOutputStyle.REMOVE) {
				outputDocument.add(new StringOutputSegment(getElement(),null));
			} else {
				if (outputStyle==FormControlOutputStyle.DISPLAY_VALUE) {
					String html=isChecked() ? FormControlOutputStyle.DisplayValueConfig.CheckedHTML : FormControlOutputStyle.DisplayValueConfig.UncheckedHTML;
					if (html!=null) {
						outputDocument.add(new StringOutputSegment(getElement(),html));
						return;
					}
					setDisabled(true);
				}
				addAttributesToOutputDocumentIfModified(outputDocument);
			}
		}
	}

	static class SubmitFormControl extends FormControl {
		// BUTTON, SUBMIT or (in subclass) IMAGE
		public SubmitFormControl(Element element, FormControlType formControlType) {
			super(element,formControlType,true);
		}
		public boolean addValue(CharSequence value) {
			return false;
		}
		void addValuesTo(Collection collection) {}
		void addToFormFields(FormFields formFields) {
			if (getPredefinedValue()!=null) formFields.add(this);
		}
		void addToOutputDocument(OutputDocument outputDocument) {
			if (outputStyle==FormControlOutputStyle.REMOVE) {
				outputDocument.add(new StringOutputSegment(getElement(),null));
			} else {
				if (outputStyle==FormControlOutputStyle.DISPLAY_VALUE) setDisabled(true);
				addAttributesToOutputDocumentIfModified(outputDocument);
			}
		}
	}

	static final class ImageSubmitFormControl extends SubmitFormControl {
		// IMAGE
		public ImageSubmitFormControl(Element element) {
			super(element,FormControlType.IMAGE);
		}
		void addToFormFields(FormFields formFields) {
			super.addToFormFields(formFields);
			formFields.addName(this,name+".x");
			formFields.addName(this,name+".y");
		}
	}

	static final class SelectFormControl extends FormControl {
		// SELECT_MULTIPLE or SELECT_SINGLE
		public ElementContainer[] optionElementContainers;
		public SelectFormControl(Element element) {
			super(element,element.getAttributes().get(Attribute.MULTIPLE)!=null ? FormControlType.SELECT_MULTIPLE : FormControlType.SELECT_SINGLE,false);
			List optionElements=element.findAllElements(Tag.OPTION);
			optionElementContainers=new ElementContainer[optionElements.size()];
			int x=0;
			for (Iterator i=optionElements.iterator(); i.hasNext();) {
				ElementContainer optionElementContainer=new ElementContainer((Element)i.next(),true);
				if (optionElementContainer.predefinedValue==null)
					// use the content of the element if it has no value attribute
					optionElementContainer.predefinedValue=CharacterReference.decodeCollapseWhiteSpace(optionElementContainer.element.getContent());
				optionElementContainers[x++]=optionElementContainer;
			}
		}
		public String getPredefinedValue() {
			throw new UnsupportedOperationException("Use getPredefinedValues() method instead on SELECT controls");
		}
		public Collection getPredefinedValues() {
			ArrayList arrayList=new ArrayList(optionElementContainers.length);
			for (int i=0; i<optionElementContainers.length; i++)
			arrayList.add(optionElementContainers[i].predefinedValue);
			return arrayList;
		}
		public Iterator getOptionElementIterator() {
			return new OptionElementIterator();
		}
		public boolean addValue(CharSequence value) {
			boolean valueFound=false;
			for (int i=0; i<optionElementContainers.length; i++) {
				if (optionElementContainers[i].setSelected(value,Attribute.SELECTED,formControlType==FormControlType.SELECT_MULTIPLE)) valueFound=true;
			}
			return valueFound;
		}
		void addValuesTo(Collection collection) {
			for (int i=0; i<optionElementContainers.length; i++) {
				if (optionElementContainers[i].getBooleanAttribute(Attribute.SELECTED))
					collection.add(optionElementContainers[i].predefinedValue);
			}
		}
		void addToFormFields(FormFields formFields) {
			for (int i=0; i<optionElementContainers.length; i++)
				formFields.add(this,optionElementContainers[i].predefinedValue);
		}
		void addToOutputDocument(OutputDocument outputDocument) {
			if (outputStyle==FormControlOutputStyle.REMOVE) {
				outputDocument.add(new StringOutputSegment(getElement(),null));
			} else if (outputStyle==FormControlOutputStyle.DISPLAY_VALUE) {
				StringBuffer sb=new StringBuffer(100);
				for (int i=0; i<optionElementContainers.length; i++) {
					if (optionElementContainers[i].getBooleanAttribute(Attribute.SELECTED)) {
						appendCollapseWhiteSpace(sb,optionElementContainers[i].element.getContent());
						sb.append(FormControlOutputStyle.DisplayValueConfig.MultipleValueSeparator);
					}
				}
				if (sb.length()>0) sb.setLength(sb.length()-FormControlOutputStyle.DisplayValueConfig.MultipleValueSeparator.length()); // remove last separator
				outputDocument.add(new StringOutputSegment(getElement(),getDisplayValueHTML(sb,false)));
			} else {
				addAttributesToOutputDocumentIfModified(outputDocument);
				for (int i=0; i<optionElementContainers.length; i++) {
					optionElementContainers[i].addAttributesToOutputDocumentIfModified(outputDocument);
				}
			}
		}
		private class OptionElementIterator implements Iterator {
			private int i=0;
			public boolean hasNext() {
				return i<optionElementContainers.length;
			}
			public Object next() {
				return optionElementContainers[i++].element;
			}
			public void remove() {
				throw new UnsupportedOperationException();
			}
		}
	}

	final String getDisplayValueHTML(CharSequence text, boolean whiteSpaceFormatting) {
		StringBuffer sb=new StringBuffer((text==null ? 0 : text.length()*2)+50);
		sb.append('<').append(FormControlOutputStyle.DisplayValueConfig.TagName);
		for (Iterator i=FormControlOutputStyle.DisplayValueConfig.AttributeNames.iterator(); i.hasNext();) {
			String attributeName=i.next().toString();
			CharSequence attributeValue=elementContainer.getAttributeValue(attributeName);
			if (attributeValue==null) continue;
			Attribute.appendHTML(sb,attributeName,attributeValue);
		}
		sb.append('>');
		if (text==null || text.length()==0)
			sb.append(FormControlOutputStyle.DisplayValueConfig.EmptyHTML);
		else
			CharacterReference.appendEncode(sb,text,whiteSpaceFormatting);
		sb.append("</").append(FormControlOutputStyle.DisplayValueConfig.TagName).append('>');
		return sb.toString();
	}

	final void addAttributesToOutputDocumentIfModified(OutputDocument outputDocument) {
		elementContainer.addAttributesToOutputDocumentIfModified(outputDocument);
	}

	static List findAll(Segment segment) {
		ArrayList list=new ArrayList();
		findAll(segment,list,Tag.INPUT);
		findAll(segment,list,Tag.TEXTAREA);
		findAll(segment,list,Tag.SELECT);
		findAll(segment,list,Tag.BUTTON);
		Collections.sort(list,COMPARATOR);
		return list;
	}

	private static void findAll(Segment segment, ArrayList list, String tagName) {
		for (Iterator i=segment.findAllElements(tagName).iterator(); i.hasNext();)
			list.add(((Element)i.next()).getFormControl());
	}

	private static CharSequence getString(char ch, int length) {
		if (length==0) return "";
		StringBuffer sb=new StringBuffer(length);
		for (int i=0; i<length; i++) sb.append(ch);
		return sb.toString();
	}

	private void verifyName() {
		if (formControlType.isSubmit()) return;
		String missingOrBlank;
		if (name==null) {
			missingOrBlank="missing";
		} else {
			if (name.length()!=0) return;
			missingOrBlank="blank";
		}
		getElement().source.log(getElement().begin,"compulsory \"name\" attribute of "+formControlType.getFormControlTypeId()+" control is "+missingOrBlank);
	}

	private static final class PositionComparator implements Comparator {
		public int compare(Object o1, Object o2) {
			int formControl1Begin=((FormControl)o1).getElement().getBegin();
			int formControl2Begin=((FormControl)o2).getElement().getBegin();
			if (formControl1Begin<formControl2Begin) return -1;
			if (formControl1Begin>formControl2Begin) return 1;
			return 0;
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////

	static final class ElementContainer {
		// Contains the information common to both a FormControl and to each OPTION element
		// within a SELECT FormControl
		public Element element;
		public Map attributesMap=null;
		public String predefinedValue; // never null for option, checkbox or radio elements

		public ElementContainer(Element element, boolean loadPredefinedValue) {
			this.element=element;
			predefinedValue=loadPredefinedValue ? element.getAttributes().getValue(Attribute.VALUE) : null;
		}

		public Map getAttributesMap() {
			if (attributesMap==null) attributesMap=element.getAttributes().getMap(true);
			return attributesMap;
		}

		public boolean setSelected(CharSequence value, String selectedOrChecked, boolean allowMultipleValues) {
			if (value==null) {
				// clear value
				setBooleanAttribute(selectedOrChecked,false);
				return true; // return value not relevant when clearing values
			}
			if (value.toString().equals(predefinedValue)) {
				setBooleanAttribute(selectedOrChecked,true);
				return true;
			}
			if (allowMultipleValues) return false;
			setBooleanAttribute(selectedOrChecked,false);
			return false;
		}

		public CharSequence getAttributeValue(String attributeName) {
			if (attributesMap!=null)
				return (CharSequence)attributesMap.get(attributeName);
			else
				return element.getAttributes().getValue(attributeName);
		}

		public void setAttributeValue(String attributeName, CharSequence value) {
			// null value indicates attribute should be removed.
			if (value==null) {
				setBooleanAttribute(attributeName,false);
				return;
			}
			if (attributesMap!=null) {
				attributesMap.put(attributeName,value);
				return;
			}
			String valueString=value.toString();
			CharSequence existingValue=getAttributeValue(attributeName);
			if (existingValue!=null && existingValue.toString().equals(valueString)) return;
			getAttributesMap().put(attributeName,valueString);
		}

		public boolean getBooleanAttribute(String attributeName) {
			if (attributesMap!=null)
				return attributesMap.containsKey(attributeName);
			else
				return element.getAttributes().get(attributeName)!=null;
		}

		public void setBooleanAttribute(String attributeName, boolean value) {
			boolean oldValue=getBooleanAttribute(attributeName);
			if (value==oldValue) return;
			if (value)
				getAttributesMap().put(attributeName,attributeName); // xhtml compatible attribute
			else
				getAttributesMap().remove(attributeName);
		}

		public void addAttributesToOutputDocumentIfModified(OutputDocument outputDocument) {
			if (attributesMap!=null) outputDocument.add(new AttributesOutputSegment(element.getAttributes(),attributesMap));
		}
	}
}

