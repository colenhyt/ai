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

import java.io.IOException;
import java.io.Writer;

/**
 * Represents a single <a target="_blank" href="http://www.w3.org/TR/html401/intro/sgmltut.html#h-3.2.2">attribute</a>
 * name/value segment within a {@link StartTag}.
 * <p>
 * The methods in this class provide read-only access to a single attribute in the source document.
 * The {@link AttributesOutputSegment} class provides a means to add, delete or modify attributes and
 * their values for inclusion in an {@link OutputDocument}.
 * <p>
 * Created using the {@link Attributes#get(String key)} method.
 * <p>
 * See also the XML 1.0 specification for <a target="_blank" href="http://www.w3.org/TR/REC-xml#dt-attr">attributes</a>.
 *
 * @see Attributes
 */
public final class Attribute extends Segment {
	private String key;
	private Segment nameSegment;
	private Segment valueSegment;
	private Segment valueSegmentIncludingQuotes;

	protected static final String CHECKED="checked";
	protected static final String CLASS="class";
	protected static final String DISABLED="disabled";
	protected static final String ID="id";
	protected static final String MULTIPLE="multiple";
	protected static final String NAME="name";
	protected static final String SELECTED="selected";
	protected static final String STYLE="style";
	protected static final String TYPE="type";
	protected static final String VALUE="value";

	/**
	 * Constructs an Attribute with no value part, called from Attributes class.
	 * <p>
	 * Note that the resulting Attribute segment will have the same span as the supplied nameSegment.
	 *
	 * @param source  the source document.
	 * @param key  the name of the attribute in lower case.
	 * @param nameSegment  the segment representing the name.
	 */
	Attribute(Source source, String key, Segment nameSegment) {
		this(source, key, nameSegment, null, null);
	}

	/**
	 * Constructs an Attribute, called from Attributes class.
	 * <p>
	 * The resulting Attribute segment will begin at the start of the nameSegment
	 * and finish at the end of the valueSegmentIncludingQuotes.  If the attribute
	 * has no value, it will finish at the end of the nameSegment.
	 * <p>
	 * If the attribute has no value, the <code>valueSegment</code> and <code>valueSegmentIncludingQuotes</code> must be null.
	 * The <valueSegmentIncludingQuotes</code> parameter must not be null if the <code>valueSegment</code> is not null, and vice versa
	 *
	 * @param source  the source document.
	 * @param key  the name of the attribute in lower case.
	 * @param nameSegment  the segment spanning the name.
	 * @param valueSegment  the segment spanning the value.
	 * @param valueSegmentIncludingQuotes  the segment spanning the value, including quotation marks if any.
	 */
	Attribute(Source source, String key, Segment nameSegment, Segment valueSegment, Segment valueSegmentIncludingQuotes) {
		super(source, nameSegment.getBegin(), (valueSegmentIncludingQuotes==null ? nameSegment.getEnd() : valueSegmentIncludingQuotes.getEnd()));
		this.key=key;
		this.nameSegment=nameSegment;
		this.valueSegment=valueSegment;
		this.valueSegmentIncludingQuotes=valueSegmentIncludingQuotes;
	}

	/**
	 * Name of the attribute in lower case.
	 * <p>
	 * Note that this package treats all attribute names as case in-sensitive, contrary to the XHTML specification.
	 */
	public String getKey() {
		return key;
	}

	/** Segment spanning the Name of the attribute. */
	public Segment getNameSegment() {
		return nameSegment;
	}

	/** Segment spanning the Value of the attribute. */
	public Segment getValueSegment() {
		return valueSegment;
	}

	/**
	 * Segment spanning the value of the attribute, including quotation marks if any.
	 * <p>
	 * If the value is not enclosed by quotation marks, this is the same as the {@linkplain #getValueSegment() value segment}
	 */
	public Segment getValueSegmentIncludingQuotes() {
		return valueSegmentIncludingQuotes;
	}

	/**
	 * Returns the character used to quote the value.
	 * <p>
	 * This will be either a double-quote (") or a single-quote (').
	 *
	 * @return the character used to quote the value, or a space if the value is not quoted or the attribute has no value.
	 */
	public char getQuoteChar() {
		if (valueSegment==valueSegmentIncludingQuotes) return ' '; // no quotes
		return source.charAt(valueSegmentIncludingQuotes.getBegin());
	}

	/**
	 * Returns the name of the attribute in original case.
	 * @return the name of the attribute in original case.
	 */
	public String getName() {
		return nameSegment.toString();
	}

	/**
	 * Returns the {@linkplain CharacterReference#decode(CharSequence) decoded} value of the attribute.
	 * <p>
	 * Note that before version 1.5 this method returned the raw value of the attribute, without
	 * {@linkplain CharacterReference#decode(CharSequence) decoding}.
	 *
	 * @return the {@linkplain CharacterReference#decode(CharSequence) decoded} value of the attribute, or <code>null</code> if the attribute has no value.
	 */
	public String getValue() {
		return CharacterReference.decode(valueSegment);
	}

	/**
	 * Indicates whether the attribute has a value.
	 * @return <code>true</code> if the attribute has a value, otherwise <code>false</code>.
	 */
	public boolean hasValue() {
		return valueSegment!=null;
	}

	protected StringBuffer appendRegeneratedHTML(StringBuffer sb) {
		return appendHTML(sb,getName(),getValue());
	}

	protected static StringBuffer appendHTML(StringBuffer sb, CharSequence name, CharSequence value) {
		sb.append(' ');
		Util.appendTo(sb,name);
		if (value!=null) {
			sb.append("=\"");
			CharacterReference.appendEncode(sb,value,false);
			sb.append('"');
		}
		return sb;
	}

	protected static Writer appendHTML(Writer writer, CharSequence name, CharSequence value) throws IOException {
		writer.write(' ');
		Util.appendTo(writer,name);
		if (value!=null) {
			writer.write("=\"");
			writer.write(CharacterReference.encode(value));
			writer.write('"');
		}
		return writer;
	}

	public String getDebugInfo() {
		StringBuffer sb=new StringBuffer().append(key).append(super.getDebugInfo()).append(",name=").append(nameSegment.getDebugInfo());
		if (hasValue())
			sb.append(",value=").append(valueSegment.getDebugInfo()).append('"').append(valueSegment).append("\"\n");
		else
			sb.append(",NO VALUE\n");
		return sb.toString();
	}
}
