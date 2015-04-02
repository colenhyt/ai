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
import java.util.Arrays;
import java.util.List;

/**
 * *************
 *
 * @see FormField
 * @see FormFields
 */
public final class FormControlOutputStyle {
	private String formControlOutputStyleId;

	/**
	 * Normal display of the {@link FormControl}.
	 * <p>
	 * This is the default display style.
	 */
	public static final FormControlOutputStyle NORMAL=new FormControlOutputStyle("normal");

	/**
	 * Remove the {@link FormControl} from the output document.
	 */
	public static final FormControlOutputStyle REMOVE=new FormControlOutputStyle("remove");

	/**
	 * The {@link FormControl} is replaced with a simple representation of its current value.
	 * <p>
	 * The representation is dependent on the {@linkplain FormControlType control type}, and can be configured using the
	 * static properties of the {@link DisplayValueConfig DisplayValueConfig} nested class.
	 * <p>
	 * Unless specified otherwise below, the control is replaced with an element (hereinafter refered to as the <i>value element</i>)
	 * having the tag name specified in the static {@link DisplayValueConfig#TagName DisplayValueConfig.TagName} property
	 * (<code>div</code> by default).
	 * The attributes specified in the static {@link DisplayValueConfig#AttributeNames DisplayValueConfig.AttributeNames} list
	 * (<code>id</code>, <code>class</code> and <code>style</code> by default) are copied with their current values from
	 * the control into the <i>value element</i>.
	 * <p>
	 * Details of the content of the <i>value element</i> or other representation of the control value are as follows:
	 * <dl>
	 *  <dt>{@link FormControlType#TEXT TEXT}, {@link FormControlType#FILE FILE}
	 *   <dd>The content of the <i>value element</i> is the {@linkplain CharacterReference#reencode(CharSequence) re-encoded}
	 *    value of the control's <code>value</code> attribute.
	 *  <dt>{@link FormControlType#TEXTAREA TEXTAREA}
	 *   <dd>The content of the <i>value element</i> is the content of the <code>TEXTAREA</code> element
	 *    re-encoded {@linkplain CharacterReference#encodeWithWhiteSpaceFormatting(CharSequence) with white space formatting}.
	 *  <dt>{@link FormControlType#CHECKBOX CHECKBOX}, {@link FormControlType#RADIO RADIO}
	 *   <dd>The control is replaced with the un-encoded content specified in the
	 *    {@link DisplayValueConfig#CheckedHTML DisplayValueConfig.CheckedHTML} or
	 *    {@link DisplayValueConfig#UncheckedHTML DisplayValueConfig.UncheckedHTML} static property, depending on
	 *    whether the control has a <code>checked</code> attribute.
	 *    If the relevant static property has a value of <code>null</code> (the default), the control is simply
	 *    {@linkplain FormControl#setDisabled(boolean) disabled}.
	 *    Attempting to determine which labels might apply to which checkbox or radio button, allowing only the
	 *    selected controls to be displayed, would require a very complex and inexact algorithm, so is best left to the developer
	 *    to implement if required.
	 *  <dt>{@link FormControlType#SELECT_SINGLE SELECT_SINGLE}, {@link FormControlType#SELECT_MULTIPLE SELECT_MULTIPLE}
	 *   <dd>The content of the <i>value element</i> is the {@linkplain CharacterReference#reencode(CharSequence) re-encoded}
	 *    label of the currently selected option.
	 *    In the case of a {@link FormControlType#SELECT_MULTIPLE SELECT_MULTIPLE} control, all labels of selected options
	 *    are listed, separated by the text specified in the static
	 *    {@link DisplayValueConfig#MultipleValueSeparator DisplayValueConfig.MultipleValueSeparator} property
	 *    ("<code>, </code>" by default).
	 *  <dt>{@link FormControlType#PASSWORD PASSWORD}
	 *   <dd>The content of the <i>value element</i> is the {@linkplain CharacterReference#encode(CharSequence) encoded}
	 *    character specified in the {@link DisplayValueConfig#PasswordChar DisplayValueConfig.PasswordChar} static property
	 *    ('<code>*</code>' by default), repeated <i>n</i> times, where <i>n</i> is the number of characters in the control's value.
	 *  <dt>{@link FormControlType#HIDDEN HIDDEN}
	 *   <dd>The control is {@linkplain #REMOVE removed}.
	 *  <dt>{@link FormControlType#BUTTON BUTTON}, {@link FormControlType#SUBMIT SUBMIT}, {@link FormControlType#IMAGE IMAGE}
	 *   <dd>The control is {@linkplain FormControl#setDisabled(boolean) disabled}.
	 * </dl>
	 * <p>
	 * If the current value of the control is <code>null</code> or an empty string, the <i>value element</i> is given the
	 * un-encoded content specified in the {@link DisplayValueConfig#EmptyHTML DisplayValueConfig.EmptyHTML} static property.
	 */
	public static final FormControlOutputStyle DISPLAY_VALUE=new FormControlOutputStyle("display_value");

	private FormControlOutputStyle(String formControlOutputStyleId) {
		this.formControlOutputStyleId=formControlOutputStyleId;
	}

	/**
	 * Returns a string representation of this object useful for debugging purposes.
	 * @return a string representation of this object useful for debugging purposes.
	 */
	public String toString() {
		return formControlOutputStyleId;
	}


	/**
	 * *************
	 * must not be null
	 */
	public static final class DisplayValueConfig {
		private DisplayValueConfig() {}
		public static String MultipleValueSeparator=", ";
		public static String TagName=Tag.DIV;
		public static List AttributeNames=new ArrayList(Arrays.asList(new String[] {Attribute.ID,Attribute.CLASS,Attribute.STYLE}));
		public static String EmptyHTML="&nbsp;";
		public static char PasswordChar='*';
		public static String CheckedHTML=null;
		public static String UncheckedHTML=null;
	}
}
