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
import java.util.Iterator;
import java.util.List;

/**
 * Represents a segment of a {@link Source} document.
 * <p>
 * The "<i>span</i>" of a segment is defined by the combination of its begin and end character positions.
 */
public class Segment implements Comparable, CharSequence {
	protected int begin;
	protected int end;
	protected Source source;

	private static final String WHITESPACE=" \n\r\t\f\u200B"; // see comments in isWhiteSpace(char) method

	/**
	 * Constructs a new <code>Segment</code> with the specified <code>Source</code> and the specified begin and end character positions.
	 * @param source  the source document.
	 * @param begin  the character position in the source where this segment begins.
	 * @param end  the character position in the source where this segment ends.
	 */
	public Segment(Source source, int begin, int end) {
		this(begin,end);
		if (source==null) throw new IllegalArgumentException("source argument must not be null");
		this.source=source;
	}

	// Only called from Source constructor
	Segment(int begin, int end) {
		if (begin==-1 || end==-1 || begin>end) throw new IllegalArgumentException();
		this.begin=begin;
		this.end=end;
	}

	Segment() {} // used when creating CACHED_NULL objects

	/**
	 * Returns the character position in the Source where this segment begins.
	 * @return the character position in the Source where this segment begins.
	 */
	public final int getBegin() {
		return begin;
	}

	/**
	 * Returns the character position in the Source where this segment ends.
	 * @return the character position in the Source where this segment ends.
	 */
	public final int getEnd() {
		return end;
	}

	/**
	 * Compares the specified object with this <code>Segment</code> for equality.
	 * <p>
	 * Returns <code>true</code> if and only if the specified object is also a <code>Segment</code>,
	 * and both segments have the same <code>Source</code>, and the same begin and end positions.
	 * @param object  the object to be compared for equality with this <code>Segment</code>.
	 * @return <code>true</code> if the specified object is equal to this <code>Segment</code>, otherwise <code>false</code>.
	 */
	public final boolean equals(Object object) {
		if (object==null || !(object instanceof Segment)) return false;
		Segment segment=(Segment)object;
		return segment.begin==begin && segment.end==end && segment.source==source;
	}

	/**
	 * Returns a hash code value for the segment.
	 * <p>
	 * The current implementation returns the sum of the begin and end positions, although this is not
	 * guaranteed in future versions.
	 *
	 * @return a hash code value for the segment.
	 */
	public int hashCode() {
		return begin+end;
	}

	/**
	 * Returns the length of the segment.
	 * This is defined as the number of characters between the begin and end positions.
	 * @return the length of the segment.
	 */
	public final int length() {
		return end-begin;
	}

	/**
	 * Indicates whether this <code>Segment</code> encloses the specified <code>Segment</code>.
	 * @param segment  the segment to be tested for being enclosed by this segment.
	 * @return <code>true</code> if this <code>Segment</code> encloses the specified <code>Segment</code>, otherwise <code>false</code>.
	 */
	public final boolean encloses(Segment segment) {
		return begin<=segment.begin && end>=segment.end;
	}

	/**
	 * Indicates whether this segment encloses the specified character position in the {@link Source} document.
	 * <p>
	 * This is the case if <code>{@link #getBegin()} <= pos < {@link #getEnd()}</code>.
	 *
	 * @param pos  the position in the source document to be tested.
	 * @return <code>true</code> if this segment encloses the specified position, otherwise <code>false</code>.
	 */
	public final boolean encloses(int pos) {
		return begin<=pos && pos<end;
	}

	/**
	 * Indicates whether this <code>Segment</code> represents an HTML <a target="_blank" href="http://www.w3.org/TR/html401/intro/sgmltut.html#h-3.2.4">comment</a>.
	 * <p>
	 * An HTML comment is an area of the source document enclosed by the delimiters
	 * <code>&lt;!--</code> on the left and <code>--&gt;</code> on the right.
	 * <p>
	 * The HTML 4.01 Specification section <a target="_blank" href="http://www.w3.org/TR/html401/intro/sgmltut.html#h-3.2.4">3.2.4</a>
	 * states that the end of comment delimiter may contain white space between the "<code>--</code>" and "<code>&gt;</code>" characters,
	 * but this library does not recognise end of comment delimiters containing white space.
	 *
	 * @return <code>true</code> if this <code>Segment</code> represents an HTML comment, otherwise <code>false</code>.
	 */
	public boolean isComment() {
		return false; // overridden in StartTag
	}

	/**
	 * Returns the source text of this segment as a <code>String</code>.
	 * <p>
	 * The returned <code>String</code> is newly created with every call to this method, unless this
	 * segment is itself a {@link Source} object.
	 * <p>
	 * Note that before version 1.5 this returned a representation of this object useful for debugging purposes,
	 * which can now be obtained via the {@link #getDebugInfo()} method.
	 *
	 * @return the source text of this segment as a <code>String</code>.
	 */
	public String toString() {
		return source.text.subSequence(begin,end).toString();
	}

	/**
	 * Returns a list of all {@link StartTag} objects enclosed by this segment.
	 * @return a list of all {@link StartTag} objects enclosed by this segment.
	 */
	public List findAllStartTags() {
		return findAllStartTags(null);
	}

	/**
	 * Returns a list of all {@link StartTag} objects with the specified name enclosed by this segment.
	 * <p>
	 * If the name argument is <code>null</code>, all StartTags are returned.
	 *
	 * @param name  the {@linkplain StartTag#getName() name} of the StartTags to find.
	 * @return a list of all StartTag objects with the specified name enclosed by this segment.
	 */
	public List findAllStartTags(String name) {
		if (name!=null) name=name.toLowerCase();
		StartTag startTag=checkEnclosure(source.findNextStartTag(begin,name));
		if (startTag==null) return Collections.EMPTY_LIST;
		ArrayList list=new ArrayList();
		do {
			list.add(startTag);
			startTag=checkEnclosure(source.findNextStartTag(startTag.end,name));
		} while (startTag!=null);
		return list;
	}

	/**
	 * Returns a list of all <code>StartTag</code> objects with the specified attribute name/value pair beginning at or immediately following the specified position in the source document.
	 *
	 * @param attributeName  the attribute name (case insensitive) to search for, must not be <code>null</code>.
	 * @param value  the value of the specified attribute to search for, must not be <code>null</code>.
	 * @param valueCaseSensitive  specifies whether the attribute value matching is case sensitive.
	 * @return a list of all <code>StartTag</code> objects with the specified attribute name/value pair beginning at or immediately following the specified position in the source document.
	 */
	public List findAllStartTags(String attributeName, String value, boolean valueCaseSensitive) {
		StartTag startTag=checkEnclosure(source.findNextStartTag(begin,attributeName,value,valueCaseSensitive));
		if (startTag==null) return Collections.EMPTY_LIST;
		ArrayList list=new ArrayList();
		do {
			list.add(startTag);
			startTag=checkEnclosure(source.findNextStartTag(startTag.end,attributeName,value,valueCaseSensitive));
		} while (startTag!=null);
		return list;
	}

	/**
	 * Returns a list of all <code>Segment</code> objects enclosed by this segment that represent HTML {@linkplain #isComment() comments}.
	 * @return a list of all <code>Segment</code> objects enclosed by this segment that represent HTML {@linkplain #isComment() comments}.
	 */
	public List findAllComments() {
		return findAllStartTags(SpecialTag.COMMENT.getName());
	}

	/**
	 * Returns a list of all {@link Element} objects enclosed by this segment.
	 * @return a list of all {@link Element} objects enclosed by this segment.
	 */
	public List findAllElements() {
		return findAllElements(null);
	}

	/**
	 * Returns a list of all {@link Element} objects with the specified name enclosed by this segment.
	 * <p>
	 * If the name argument is <code>null</code>, all Elements are returned.
	 *
	 * @param name  the {@linkplain Element#getName() name} of the Elements to find.
	 * @return a list of all <code>Element</code> objects with the specified name enclosed by this segment.
	 */
	public List<Element> findAllElements(String name) {
		if (name!=null) name=name.toLowerCase();
		List startTags=findAllStartTags(name);
		if (startTags.isEmpty()) return Collections.EMPTY_LIST;
		ArrayList<Element> elements=new ArrayList<Element>(startTags.size());
		for (Iterator i=startTags.iterator(); i.hasNext();) {
			StartTag startTag=(StartTag)i.next();
			Element element=startTag.getElement();
			if (element.end>end) break;
			elements.add(element);
		}
		return elements;
	}

	/**
	 * Returns a list of all {@link CharacterReference} objects enclosed by this segment.
	 *
	 * @return a list of all <code>CharacterReference</code> objects enclosed by this segment.
	 */
	public List findAllCharacterReferences() {
		CharacterReference characterReference=findNextCharacterReference(begin);
		if (characterReference==null) return Collections.EMPTY_LIST;
		ArrayList list=new ArrayList();
		do {
			list.add(characterReference);
			characterReference=findNextCharacterReference(characterReference.end);
		} while (characterReference!=null);
		return list;
	}

	/**
	 * Returns a list of the {@link FormControl} objects enclosed by this segment.
	 * @return a list of the {@link FormControl} objects enclosed by this segment.
	 */
	public List findFormControls() {
		return FormControl.findAll(this);
	}

	/**
	 * Returns the {@link FormFields} object representing all form fields enclosed by this segment.
	 * <p>
	 * This is equivalent to <code>FormFields.constructFrom(findFormControls())</code>
	 *
	 * @return the {@link FormFields} object representing all form fields enclosed by this segment.
	 * @see #findFormControls()
	 */
	public FormFields findFormFields() {
		return new FormFields(findFormControls());
	}

	/**
	 * Parses any {@link Attributes} within this segment.
	 * This method is only used in the unusual situation where attributes exist outside of a start tag.
	 * The {@link StartTag#getAttributes()} method should be used in normal situations.
	 * <p>
	 * This is equivalent to {@link Source#parseAttributes(int,int) source.parseAttributes(this.getBegin(),this.getEnd())}
	 *
	 * @return the {@link Attributes} within this segment, or <code>null</code> if too many errors occur while parsing.
	 */
	public Attributes parseAttributes() {
		return source.parseAttributes(begin,end);
	}

	/**
	 * Causes the this segment to be ignored when parsing.
	 * <p>
	 * This is equivalent to {@link Source#ignoreWhenParsing(int,int) source.ignoreWhenParsing(segment.getBegin(),segment.getEnd())}
	 *
	 * @see Source#ignoreWhenParsing(int begin, int end)
	 * @see Source#ignoreWhenParsing(Collection segments)
	 */
	public void ignoreWhenParsing() {
		source.ignoreWhenParsing(begin,end);
	}

	/**
	 * Compares this <code>Segment</code> object to another object.
	 * <p>
	 * If the argument is not a <code>Segment</code>, a <code>ClassCastException</code> is thrown.
	 * <p>
	 * A segment is considered to be before another segment if its begin position is earlier,
	 * or in the case that both segments begin at the same position, its end position is earlier.
	 * <p>
	 * Segments that begin and end at the same position are considered equal for
	 * the purposes of this comparison, even if they relate to different source documents.
	 * <p>
	 * Note: this class has a natural ordering that is inconsistent with equals.
	 * This means that this method may return zero in some cases where calling the
	 * {@link #equals(Object)} method with the same argument returns <code>false</code>.
	 *
	 * @param o  the segment to be compared
	 * @return a negative integer, zero, or a positive integer as this segment is before, equal to, or after the specified segment.
	 * @throws ClassCastException  if the argument is not a <code>Segment</code>
	 */
	public int compareTo(Object o) {
		if (this==o) return 0;
		Segment segment=(Segment)o;
		if (begin<segment.begin) return -1;
		if (begin>segment.begin) return 1;
		if (end<segment.end) return -1;
		if (end>segment.end) return 1;
		return 0;
	}

	/**
	 * Indicates whether the specified character is <a target="_blank" href="http://www.w3.org/TR/html401/struct/text.html#h-9.1">white space</a>.
	 * <p>
	 * The HTML 4.01 Specification section <a target="_blank" href="http://www.w3.org/TR/html401/struct/text.html#h-9.1">9.1</a>
	 * specifies the following white space characters:
	 * <ul>
	 *  <li>space (U+0020)
	 *  <li>tab (U+0009)
	 *  <li>form feed (U+000C)
	 *  <li>line feed (U+000A)
	 *  <li>carriage return (U+000D)
	 *  <li>zero-width space (U+200B)
	 * </ul>
	 * <p>
	 * Despite the explicit inclusion of the zero-width space in the HTML specification, Microsoft IE6 does not
	 * recognise them as whitespace and renders them as an unprintable character (empty square).
	 * Even zero-width spaces included using the numeric character reference <code>&#x200B;</code> are rendered this way.
	 * <p>
	 * Note that in versions prior to 1.5, this method did not recognise form feeds or zero-width spaces as white space.
	 *
	 * @param ch  the character to test.
	 * @return <code>true</code> if the specified character is white space, otherwise <code>false</code>.
	 */
	public static final boolean isWhiteSpace(char ch) {
		return WHITESPACE.indexOf(ch)!=-1;
	}

	/**
	 * Returns a string representation of this object useful for debugging purposes.
	 * @return a string representation of this object useful for debugging purposes.
	 */
	public String getDebugInfo() {
		return "("+begin+','+end+')';
	}

	/**
	 * Returns the character at the specified index.
	 * <p>
	 * This is logically equivalent to <code>toString().charAt(index)</code>
	 * for a valid argument values <code>0 <= index < length()</code>.
	 * <p>
	 * However because this implementation works directly on the underlying document source string,
	 * it should not be assumed that an <code>IndexOutOfBoundsException</code> will be thrown
	 * for an invalid argument value.
	 *
	 * @param index  the index of the character.
	 * @return the character at the specified index.
	 */
	public char charAt(int index) {
		return source.toString().charAt(begin+index);
	}

	/**
	 * Returns a new character sequence that is a subsequence of this sequence.
	 * <p>
	 * This is logically equivalent to <code>toString().subSequence(beginIndex,endIndex)</code>
	 * for valid values of <code>beginIndex</code> and <code>endIndex</code>.
	 * <p>
	 * However because this implementation works directly on the underlying document source string,
	 * it should not be assumed that an <code>IndexOutOfBoundsException</code> will be thrown
	 * for invalid argument values as described in the <code>String.subSequence(int,int)</code> method.
	 *
	 * @param beginIndex  the begin index, inclusive.
	 * @param endIndex  the end index, exclusive.
	 * @return a new character sequence that is a subsequence of this sequence.
	 */
	public final CharSequence subSequence(int beginIndex, int endIndex) {
		return source.text.subSequence(begin+beginIndex,begin+endIndex);
	}

	/**
	 * Returns the source text of this segment.
	 * <p>
 	 * This method has been deprecated as of version 1.5 as it now duplicates the functionality of the {@link #toString()} method.
	 *
	 * @return the source text of this segment.
	 * @deprecated  Use the {@link #toString()} method instead
	 */
	public String getSourceText() {
		return toString();
	}

	/**
	 * Returns the source text of this segment without {@linkplain #isWhiteSpace(char) white space}.
	 * <p>
	 * All leading and trailing white space is omitted, and any sections of internal white space are replaced by a single space.
	 * <p>
 	 * This method has been deprecated as of version 1.5 as it is no longer used internally and
	 * was never very useful as a public method.
	 * It is similar to the new {@link CharacterReference#decodeCollapseWhiteSpace(CharSequence)} method, but
	 * does not {@linkplain CharacterReference#decode(CharSequence) decode} the text after collapsing the white space.
	 * <p>
	 * @return the source text of this segment without white space.
	 * @deprecated  Use the more useful {@link CharacterReference#decodeCollapseWhiteSpace(CharSequence)} method instead.
	 */
	public final String getSourceTextNoWhitespace() {
		return appendCollapseWhiteSpace(new StringBuffer(length()),this).toString();
	}

	/**
	 * Returns a list of <code>Segment</code> objects representing every word in this segment separated by {@linkplain #isWhiteSpace(char) white space}.
	 * Note that any markup contained in this segment will be regarded as normal text for the purposes of this method.
	 * <p>
 	 * This method has been deprecated as of version 1.5 as it has no discernable use.
	 *
	 * @return a list of <code>Segment</code> objects representing every word in this segment separated by white space.
	 * @deprecated  no replacement
	 */
	public final List findWords() {
		ArrayList words=new ArrayList();
		int wordBegin=-1;
		for (int i=begin; i<end; i++) {
			if (isWhiteSpace(source.charAt(i))) {
				if (wordBegin==-1) continue;
				words.add(new Segment(source,wordBegin,i));
				wordBegin=-1;
			} else {
				if (wordBegin==-1) wordBegin=i;
			}
		}
		if (wordBegin!=-1) words.add(new Segment(source, wordBegin,end));
		return words;
	}

	/**
	 * Collapses the {@linkplain #isWhiteSpace(char) white space} in the specified text.
	 * All leading and trailing white space is omitted, and any sections of internal white space are replaced by a single space.
	 */
	protected static StringBuffer appendCollapseWhiteSpace(StringBuffer sb, CharSequence text) {
		int i=0;
		boolean lastWasWhiteSpace=true;
		boolean isWhiteSpace=false;
		while (i<text.length()) {
			char ch=text.charAt(i++);
			if (isWhiteSpace=isWhiteSpace(ch)) {
				if (!lastWasWhiteSpace) sb.append(' ');
			} else {
				sb.append(ch);
			}
			lastWasWhiteSpace=isWhiteSpace;
		}
		if (isWhiteSpace && sb.length()!=0) sb.setLength(sb.length()-1);
		return sb;
	}

	protected static boolean isIdentifierStart(char c) {
		// see http://www.w3.org/TR/REC-xml/#NT-Name
		return Character.isLetter(c) || c=='_' || c==':';
	}

	protected static boolean isIdentifierPart(char c) {
		// see http://www.w3.org/TR/REC-xml/#NT-NameChar
		// Note this implementation does not check for CombiningChar and Extender characters as defined in the XML spec.
		return Character.isLetterOrDigit(c) || c=='.' || c=='-' || c=='_' || c==':';
	}

	private StartTag checkEnclosure(StartTag startTag) {
		if (startTag==null || startTag.end>end) return null;
		return startTag;
	}

	private CharacterReference findNextCharacterReference(int pos) {
		CharacterReference characterReference=source.findNextCharacterReference(pos);
		if (characterReference==null || characterReference.end>end) return null;
		return characterReference;
	}
}

