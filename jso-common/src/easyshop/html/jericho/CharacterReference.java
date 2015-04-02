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


/**
 * Represents either a {@link CharacterEntityReference} or {@link NumericCharacterReference}.
 * <p>
 * This class, together with its subclasses, contains static methods to perform most required operations without ever having to instantiate an object.
 * <p>
 * Objects of this class are useful when the positions of character references in a source document are required,
 * or to replace the found character references with customised text.
 * <p>
 * Objects are created using one of the following methods:
 * <ul>
 *  <li>{@link CharacterReference#parse(CharSequence characterReferenceText)}
 *  <li>{@link Source#findNextCharacterReference(int pos)}
 *  <li>{@link Source#findPreviousCharacterReference(int pos)}
 *  <li>{@link Segment#findAllCharacterReferences()}
 * </ul>
 */
public abstract class CharacterReference extends Segment {
	int codePoint;

	/**
	 * Represents an invalid Unicode code point.
	 * <p>
	 * This can be the result of parsing a numeric character reference outside of the valid Unicode range of 0x000000-0x10FFFF, or any other invalid character reference.
	 */
	public static final int INVALID_CODE_POINT=-1;

	/**
	 * The maximum codepoint allowed by unicode, 0x10FFFF (decimal 1114111).
	 * This can be replaced by Character.MAX_CODE_POINT in java 1.5
	 */
	protected static final int MAX_CODE_POINT=0x10FFFF;

	/** The number of spaces used to simulate a tab when {@linkplain #encodeWithWhiteSpaceFormatting encoding with white space formatting}. */
	private static final int TAB_LENGTH=4;

	CharacterReference(Source source, int begin, int end, int codePoint) {
		super(source,begin,end);
		this.codePoint=codePoint;
	}

	/**
	 * Returns the <a target="_blank" href="http://www.unicode.org">Unicode</a> code point represented by this character reference.
	 * @return the Unicode code point represented by this character reference.
	 */
	public int getCodePoint() {
		return codePoint;
	}

	/**
	 * Returns the character represented by this character reference.
	 * <p>
	 * If this character reference represents a Unicode
	 * <a target="_blank" href="http://www.unicode.org/glossary/#supplementary_code_point">supplimentary code point</a>,
	 * any bits outside of the least significant 16 bits of the code point are truncated, yielding an incorrect result.
	 *
	 * @return the character represented by this character reference.
	 */
	public char getChar() {
		return (char)codePoint;
	}

	/**
	 * Encodes the specified text, escaping special characters into character references.
	 * <p>
	 * Each character is encoded only if the {@link #requiresEncoding(char)} method would return <code>true</code> for that character,
	 * using its {@link CharacterEntityReference} if available, or a decimal {@link NumericCharacterReference} if their Unicode
	 * code point value is greater than U+007F.
	 * <p>
	 * The only exception to this is an {@linkplain CharacterEntityReference#_apos apostrophe} (U+0027),
	 * which depending on the current setting of the static {@link #ApostropheEncoded} property,
	 * is either encoded as the numeric character reference "<code>&amp;#39;</code>" (default setting), or left unencoded.
	 * <p>
	 * This method will never encode an apostrophe into its character entity reference "<code>&amp;apos;</code>" as this
	 * entity is not defined for use in HTML.  See the comments in the {@link CharacterEntityReference} class for more information.
	 * <p>
	 * To encode text using only numeric character references, use the<br />
	 * {@link NumericCharacterReference#encode(CharSequence unencodedText)} method instead.
	 *
	 * @param unencodedText  the text to encode.
	 * @return the encoded string.
	 * @see #decode(CharSequence encodedText)
	 */
	public static String encode(CharSequence unencodedText) {
		if (unencodedText==null) return null;
		return appendEncode(new StringBuffer(unencodedText.length()*2),unencodedText,false).toString();
	}

	/**
	 * Determines whether apostrophes are encoded when calling the {@link #encode(CharSequence)} method.
	 * <p>
	 * This is a global setting which affects all threads.
	 * <p>
	 * Specifying a value of <code>false</code> means {@linkplain CharacterEntityReference#_apos apostrophe}
	 * (U+0027) characters will not be encoded.
	 * The only time apostrophes need to be encoded is within an attribute value delimited by
	 * single quotes (apostrophes), so in most cases ignoring apostrophes is perfectly safe and
	 * enhances readability of the source document.
	 * <p>
	 * The recommended setting is <code>false</code>, although the default value is <code>true</code> so that
	 * the behaviour of the {@link #encode(CharSequence)} method is consistent with previous versions.
	 */
	public static boolean ApostropheEncoded=true;

	/**
	 * {@linkplain #encode(CharSequence) Encodes} the specified text, preserving line breaks, tabs and spaces for rendering by converting them to markup.
	 * <p>
	 * This performs the same encoding as the {@link #encode(CharSequence)} method, but also performs the following conversions:
	 * <ul>
	 *  <li>Line breaks, being Carriage Return (U+000D) or Line Feed (U+000A) characters, and Form Feed characters (U+000C)
	 *   are converted to "<code>&lt;br /&gt;</code>".  CR/LF pairs are treated as a single line break.
	 *  <li>Multiple consecutive spaces are converted so that every second space is converted to "<code>&amp;nbsp;</code>"
	 *   while ensuring the last is always a normal space.
	 *  <li>Tab characters (U+0009) are converted as if they were four consecutive spaces.
	 * </ul>
	 * <p>
	 * The conversion of multiple consecutive spaces to alternating space/non-breaking-space allows the correct number of
	 * spaces to be rendered, but also allows the line to wrap in the middle of it.
	 * <p>
	 * Note that zero-width spaces (U+200B) are converted to the numeric character reference
	 * <code>&amp;#x200B;</code> through the normal encoding process, but IE6 does not render them properly
	 * either encoded or unencoded.
	 * <p>
	 * There is no method provided to reverse this encoding.
	 *
	 * @param unencodedText  the text to encode.
	 * @return the encoded string with whitespace formatting converted to markup.
	 * @see #encode(CharSequence unencodedText)
	 */
	public static String encodeWithWhiteSpaceFormatting(CharSequence unencodedText) {
		if (unencodedText==null) return null;
		return appendEncode(new StringBuffer(unencodedText.length()*2),unencodedText,true).toString();
	}

	/**
	 * Decodes the specified HTML encoded text into normal text.
	 * <p>
	 * All {@linkplain CharacterEntityReference character entity references} and {@linkplain NumericCharacterReference numeric character references} are converted to their respective characters.
	 * <p>
	 * The SGML specification allows character references without a terminating semicolon (<code>;</code>) in some circumstances.
	 * Although not permitted in HTML or XHTML, some browsers do accept them.<br />
	 * The behaviour of this library is as follows:
	 * <ul>
	 *  <li>{@linkplain CharacterEntityReference Character entity references} terminated by any non-alphabetic character are accepted
	 *  <li>{@linkplain NumericCharacterReference#encodeDecimal(CharSequence) Decimal numeric character references} terminated by any non-digit character are accepted
	 *  <li>{@linkplain NumericCharacterReference#encodeHexadecimal(CharSequence) Hexadecimal numeric character references} must be terminated correctly by a semicolon.
	 * </ul>
	 * <p>
	 * Although character entity references are case sensitive, and in some cases differ from other entity references only by their case,
	 * some browsers will also recognise them in a case-insensitive way.
	 * For this reason, all decoding methods in this library will recognise character entity references even if they are in the wrong case.
	 *
	 * @param encodedText  the text to decode.
	 * @return the decoded string.
	 * @see #encode(CharSequence unencodedText)
	 */
	public static String decode(CharSequence encodedText) {
		if (encodedText==null) return null;
		String encodedString=encodedText.toString();
		int pos=encodedString.indexOf('&');
		if (pos==-1) return encodedString;
		return appendDecode(new StringBuffer(encodedString.length()),encodedString,pos).toString();
	}

	/**
	 * {@linkplain #decode(CharSequence) Decodes} the specified text after collapsing its {@linkplain #isWhiteSpace(char) white space}.
	 * <p>
	 * All leading and trailing white space is omitted, and any sections of internal white space are replaced by a single space.
	 * <p>
	 * The resultant text is what would normally be rendered by a user agent.
	 *
	 * @param text  the source text
	 * @return the decoded text with collapsed white space.
	 * @see FormControl#getPredefinedValues()
	 */
	public static String decodeCollapseWhiteSpace(CharSequence text) {
		return decode(appendCollapseWhiteSpace(new StringBuffer(text.length()),text));
	}

	/**
	 * Re-encodes the specified text, equivalent to {@linkplain #decode(CharSequence) decoding} and then {@linkplain #encode(CharSequence) encoding} again.
	 * <p>
	 * This process ensures that the specified encoded text does not contain any remaining unencoded characters.
	 * <p>
	 * IMPLEMENTATION NOTE: At present this method simply calls the {@link #decode(CharSequence) decode} method
	 * followed by the {@link #encode(CharSequence) encode} method, but a more efficient implementation
	 * may be used in future.
	 *
	 * @param encodedText  the text to re-encode.
	 * @return the re-encoded string.
	 */
	public static String reencode(CharSequence encodedText) {
		return encode(decode(encodedText));
	}

	/**
	 * Returns the encoded form of this character reference.
	 * <p>
	 * The exact behaviour of this method depends on the class of this object.
	 * See the {@link CharacterEntityReference#getCharacterReferenceString()} and
	 * {@link NumericCharacterReference#getCharacterReferenceString()} methods for more details.
	 * <p>
	 * <dl>
	 *  <dt><b>Examples:</b></dt>
	 *   <dd><code>CharacterReference.parse("&amp;GT;").getCharacterReferenceString()</code> returns "<code>&amp;gt;</code>"</dd>
	 *   <dd><code>CharacterReference.parse("&amp;#x3E;").getCharacterReferenceString()</code> returns "<code>&amp;#3e;</code>"</dd>
	 * </dl>
	 *
	 * @return the encoded form of this character reference.
	 * @see #getCharacterReferenceString(int codePoint)
	 * @see #getDecimalCharacterReferenceString()
	 */
	public abstract String getCharacterReferenceString();

	/**
	 * Returns the encoded form of the specified Unicode code point.
	 * <p>
	 * This method returns the {@linkplain CharacterEntityReference#getCharacterReferenceString(int) character entity reference} encoded form of the code point
	 * if one exists, otherwise it returns the {@linkplain #getDecimalCharacterReferenceString(int) decimal numeric character reference} encoded form.
	 * <p>
	 * The only exception to this is an {@linkplain CharacterEntityReference#_apos apostrophe} (U+0027),
	 * which is encoded as the numeric character reference "<code>&amp;#39;</code>" instead of its character entity reference
	 * "<code>&amp;apos;</code>".
	 * <p>
	 * <dl>
	 *  <dt><b>Examples:</b></dt>
	 *   <dd><code>CharacterReference.getCharacterReferenceString(62)</code> returns "<code>&amp;gt;</code>"</dd>
	 *   <dd><code>CharacterReference.getCharacterReferenceString('&gt;')</code> returns "<code>&amp;gt;</code>"</dd>
	 *   <dd><code>CharacterReference.getCharacterReferenceString('&#9786;')</code> returns "<code>&amp;#9786;</code>"</dd>
	 * </dl>
	 *
	 * @param codePoint  the Unicode code point to encode.
	 * @return the encoded form of the specified Unicode code point.
	 * @see #getHexadecimalCharacterReferenceString(int codePoint)
	 */
	public static String getCharacterReferenceString(int codePoint) {
		String characterReferenceString=null;
		if (codePoint!=CharacterEntityReference._apos) characterReferenceString=CharacterEntityReference.getCharacterReferenceString(codePoint);
		if (characterReferenceString==null) characterReferenceString=NumericCharacterReference.getCharacterReferenceString(codePoint);
		return characterReferenceString;
	}

	/**
	 * Returns the decimal encoded form of this character reference.
	 * <p>
	 * This is equivalent to {@link #getDecimalCharacterReferenceString(int) getDecimalCharacterReferenceString(getCodePoint())}.
	 * <p>
	 * <dl>
	 *  <dt><b>Example:</b></dt>
	 *  <dd><code>CharacterReference.parse("&amp;gt;").getDecimalCharacterReferenceString()</code> returns "<code>&amp;#62;</code>"</dd>
	 * </dl>
	 *
	 * @return the decimal encoded form of this character reference.
	 * @see #getCharacterReferenceString()
	 * @see #getHexadecimalCharacterReferenceString()
	 */
	public String getDecimalCharacterReferenceString() {
		return getDecimalCharacterReferenceString(codePoint);
	}

	/**
	 * Returns the decimal encoded form of the specified Unicode code point.
	 * <p>
	 * <dl>
	 *  <dt><b>Example:</b></dt>
	 *  <dd><code>CharacterReference.getDecimalCharacterReferenceString('&gt;')</code> returns "<code>&amp;#62;</code>"</dd>
	 * </dl>
	 *
	 * @param codePoint  the Unicode code point to encode.
	 * @return the decimal encoded form of the specified Unicode code point.
	 * @see #getCharacterReferenceString(int codePoint)
	 * @see #getHexadecimalCharacterReferenceString(int codePoint)
	 */
	public static String getDecimalCharacterReferenceString(int codePoint) {
		return appendDecimalCharacterReferenceString(new StringBuffer(),codePoint).toString();
	}

	/**
	 * Returns the hexadecimal encoded form of this character reference.
	 * <p>
	 * This is equivalent to {@link #getHexadecimalCharacterReferenceString(int) getHexadecimalCharacterReferenceString(getCodePoint())}.
	 * <p>
	 * <dl>
	 *  <dt><b>Example:</b></dt>
	 *  <dd><code>CharacterReference.parse("&amp;gt;").getHexadecimalCharacterReferenceString()</code> returns "<code>&amp;#x3e;</code>"</dd>
	 * </dl>
	 *
	 * @return the hexadecimal encoded form of this character reference.
	 * @see #getCharacterReferenceString()
	 * @see #getDecimalCharacterReferenceString()
	 */
	public String getHexadecimalCharacterReferenceString() {
		return getHexadecimalCharacterReferenceString(codePoint);
	}

	/**
	 * Returns the hexadecimal encoded form of the specified Unicode code point.
	 * <p>
	 * <dl>
	 *  <dt><b>Example:</b></dt>
	 *  <dd><code>CharacterReference.getHexadecimalCharacterReferenceString('&gt;')</code> returns "<code>&amp;#x3e;</code>"</dd>
	 * </dl>
	 *
	 * @param codePoint  the Unicode code point to encode.
	 * @return the hexadecimal encoded form of the specified Unicode code point.
	 * @see #getCharacterReferenceString(int codePoint)
	 * @see #getDecimalCharacterReferenceString(int codePoint)
	 */
	public static String getHexadecimalCharacterReferenceString(int codePoint) {
		return appendHexadecimalCharacterReferenceString(new StringBuffer(),codePoint).toString();
	}

	/**
	 * Returns the Unicode code point of this character reference in <a target="_blank" href="http://www.unicode.org/reports/tr27/#notation">U+ notation</a>.
	 * <p>
	 * This is equivalent to {@link #getUnicodeText(int) getUnicodeText(getCodePoint())}.
	 * <p>
	 * <dl>
	 *  <dt><b>Example:</b></dt>
	 *  <dd><code>CharacterReference.parse("&amp;gt;").getUnicodeText()</code> returns "<code>U+003E</code>"</dd>
	 * </dl>
	 *
	 * @return the Unicode code point of this character reference in U+ notation.
	 * @see #getUnicodeText(int codePoint)
	 */
	public String getUnicodeText() {
		return getUnicodeText(codePoint);
	}

	/**
	 * Returns the specified Unicode code point in <a target="_blank" href="http://www.unicode.org/reports/tr27/#notation">U+ notation</a>.
	 * <p>
	 * <dl>
	 *  <dt><b>Example:</b></dt>
	 *  <dd><code>CharacterReference.getUnicodeText('&gt;')</code> returns "<code>U+003E</code>"</dd>
	 * </dl>
	 *
	 * @param codePoint  the Unicode code point.
	 * @return the specified Unicode code point in U+ notation.
	 */
	public static String getUnicodeText(int codePoint) {
		return appendUnicodeText(new StringBuffer(),codePoint).toString();
	}

	protected static final StringBuffer appendUnicodeText(StringBuffer sb, int codePoint) {
		sb.append("U+");
		String hex=Integer.toString(codePoint,16).toUpperCase();
		for (int i=4-hex.length(); i>0; i--) sb.append('0');
		sb.append(hex);
		return sb;
	}

	/**
	 * Parses a single encoded character reference text into a CharacterReference object.
	 * <p>
	 * The character reference must be at the start of the given text, but may contain other characters at the end.
	 * The {@link #getEnd() getEnd()} method can be used on the resulting object to determine at which character position the character reference ended.
	 * <p>
	 * If the text does not represent a valid character reference, this method returns <code>null</code>.
	 * <p>
	 * To decode <i>all</i> character references in a given text, use the {@link #decode(CharSequence encodedText)} method instead.
	 * <p>
	 * <dl>
	 *  <dt><b>Example:</b></dt>
	 *  <dd><code>CharacterReference.parse("&amp;gt;").getChar()</code> returns '<code>&gt;</code>'</dd>
	 * </dl>
	 *
	 * @param characterReferenceText  the text containing a single encoded character reference.
	 * @return a <code>CharacterReference</code> object representing the specified text, or <code>null</code> if the text does not represent a valid character reference.
	 * @see #decode(CharSequence encodedText)
	 */
	public static CharacterReference parse(CharSequence characterReferenceText) {
		return construct(new Source(characterReferenceText.toString()),0);
	}

	/**
	 * Parses a single encoded character reference text into a Unicode code point.
	 * <p>
	 * The character reference must be at the start of the given text, but may contain other characters at the end.
	 * <p>
	 * If the text does not represent a valid character reference, this method returns {@link #INVALID_CODE_POINT}.
	 * <p>
	 * <dl>
	 *  <dt><b>Example:</b></dt>
	 *  <dd><code>CharacterReference.getCodePointFromCharacterReferenceString("&amp;gt;")</code> returns <code>38</code></dd>
	 * </dl>
	 *
	 * @param characterReferenceText  the text containing a single encoded character reference.
	 * @return the Unicode code point representing representing the specified text, or {@link #INVALID_CODE_POINT} if the text does not represent a valid character reference.
	 */
	public static int getCodePointFromCharacterReferenceString(CharSequence characterReferenceText) {
		CharacterReference characterReference=parse(characterReferenceText);
		return (characterReference!=null) ? characterReference.getCodePoint() : INVALID_CODE_POINT;
	}

	/**
	 * Indicates whether the specified character would need to be encoded in HTML text.
	 * <p>
	 * This is the case if a {@linkplain CharacterEntityReference character entity reference} exists for the character, or the Unicode code point value is greater than U+007F.
	 * <p>
	 * The only exception to this is an {@linkplain CharacterEntityReference#_apos apostrophe} (U+0027),
	 * which only returns <code>true</code> if the static {@link #ApostropheEncoded} property is currently set to <code>true</code>.
	 *
	 * @param ch  the character to be tested.
	 * @return <code>true</code> if the specified character would need to be encoded in HTML text, otherwise <code>false</code>.
	 */
	public static final boolean requiresEncoding(char ch) {
		return ch>127 || (CharacterEntityReference.getName(ch)!=null && (ch!='\'' || ApostropheEncoded));
	}

	protected static StringBuffer appendEncode(StringBuffer sb, CharSequence unencodedText, boolean whiteSpaceFormatting) {
		if (unencodedText==null) return sb;
		int beginPos=0;
		int endPos=unencodedText.length();
		if (unencodedText instanceof Segment) {
			// this might improve performance slightly
			Segment segment=(Segment)unencodedText;
			int segmentOffset=segment.getBegin();
			beginPos=segmentOffset;
			endPos+=segmentOffset;
			unencodedText=segment.source.toString();
		}
		for (int i=beginPos; i<endPos; i++) {
			char ch=unencodedText.charAt(i);
			String characterEntityReferenceName=CharacterEntityReference.getName(ch);
			if (characterEntityReferenceName!=null) {
				if (ch=='\'') {
					if (ApostropheEncoded)
						sb.append("&#39;");
					else
						sb.append(ch);
				} else {
					CharacterEntityReference.appendCharacterReferenceString(sb,characterEntityReferenceName);
				}
			} else if (ch>127) {
				appendDecimalCharacterReferenceString(sb,ch);
			} else if (!(whiteSpaceFormatting && isWhiteSpace(ch))) {
				sb.append(ch);
			} else {
				// whiteSpaceFormatting tries to simulate the formatting characters by converting them to markup
				int spaceCount;
				int nexti=i+1;
				if (ch!=' ') {
					if (ch!='\t') {
						// must be line feed, carriage return or form feed, since zero-width space should have been processed as a character reference string
						if (ch=='\r' && nexti<endPos && unencodedText.charAt(nexti)=='\n') i++; // process cr/lf pair as one line break
						sb.append("<br />"); // add line break
						continue;
					} else {
						spaceCount=TAB_LENGTH;
					}
				} else {
					spaceCount=1;
				}
				while (nexti<endPos) {
					ch=unencodedText.charAt(nexti);
					if (ch==' ')
						spaceCount+=1;
					else if (ch=='\t')
						spaceCount+=TAB_LENGTH;
					else
						break;
					nexti++;
				}
				if (spaceCount==1) {
					// handle the very common case of a single character to improve efficiency slightly
					sb.append(' ');
					continue;
				}
				if (spaceCount%2==1) sb.append(' '); // fist character is a space if we have an odd number of spaces
				while (spaceCount>=2) {
					sb.append("&nbsp; "); // use alternating &nbsp; and spaces to keep original number of spaces
					spaceCount-=2;
				}
				// note that the last character is never a nbsp, so that word wrapping won't result in a nbsp before the first character in a line
				i=nexti-1; // minus 1 because top level for loop will add it again
			}
		}
		return sb;
	}

	static CharacterReference findPreviousOrNext(Source source, int pos, boolean previous) {
		String lsource=source.getParseTextLowerCase();
		pos=previous ? lsource.lastIndexOf('&',pos) : lsource.indexOf('&',pos);
		while (pos!=-1) {
			CharacterReference characterReference=construct(source,pos);
			if (characterReference!=null) return characterReference;
			pos=previous ? lsource.lastIndexOf('&',pos-1) : lsource.indexOf('&',pos+1);
		}
		return null;
	}

	protected static final StringBuffer appendHexadecimalCharacterReferenceString(StringBuffer sb, int codePoint) {
		return sb.append("&#x").append(Integer.toString(codePoint,16)).append(';');
	}

	protected static final StringBuffer appendDecimalCharacterReferenceString(StringBuffer sb, int codePoint) {
		return sb.append("&#").append(codePoint).append(';');
	}

	static CharacterReference construct(Source source, int begin) {
		try {
			if (source.getParseTextLowerCase().charAt(begin)!='&') return null;
			return (source.getParseTextLowerCase().charAt(begin+1)=='#') ? NumericCharacterReference.construct(source,begin) : CharacterEntityReference.construct(source,begin);
		} catch (IndexOutOfBoundsException ex) {
			return null;
		}
	}

	private static StringBuffer appendDecode(StringBuffer sb, String encodedString) {
		if (encodedString==null) return sb;
		int pos=encodedString.indexOf('&');
		if (pos==-1) return sb.append(encodedString);
		return appendDecode(sb,encodedString,pos);
	}

	private static StringBuffer appendDecode(StringBuffer sb, String encodedString, int pos) {
		int lastEnd=0;
		Source source=new Source(encodedString);
		while (true) {
			CharacterReference characterReference=findPreviousOrNext(source,pos,false);
			if (characterReference==null) break;
			if (lastEnd!=characterReference.getBegin()) Util.appendTo(sb,encodedString,lastEnd,characterReference.getBegin());
			sb.append((char)characterReference.codePoint);
			pos=lastEnd=characterReference.getEnd();
		}
		if (lastEnd!=encodedString.length()) Util.appendTo(sb,encodedString,lastEnd,encodedString.length());
		return sb;
	}
}

