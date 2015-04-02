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
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Represents the list of {@link Attribute} objects present within a particular {@link StartTag}.
 * <p>
 * The attributes in this list are a representation of those found in the source document and are not modifiable.
 * The {@link AttributesOutputSegment} class provides a means to add, delete or modify attributes and
 * their values for inclusion in an {@link OutputDocument}.
 * <p>
 * This segment starts at the end of the StartTag's {@linkplain StartTag#getName() name}
 * and ends at the end of the last attribute.
 * <p>
 * Note that before version 1.5 the segment ended just before the closing '/', '?' or '&gt;' character of the <code>StartTag</code>
 * instead of at the end of the last attribute.
 * <p>
 * Created using the {@link StartTag#getAttributes()} method, or explicitly using the {@link Source#parseAttributes(int pos, int maxEnd)} method.
 * <p>
 * It is possible (and common) for instances of this class to contain no attributes.
 * <p>
 * See also the XML 1.0 specification for <a target="_blank" href="http://www.w3.org/TR/REC-xml#dt-attr">attributes</a>.
 *
 * @see StartTag
 * @see Attribute
 */
public final class Attributes extends SequentialListSegment {
	private LinkedList attributeList; // never null

	// parsing states:
	private static final int AFTER_TAG_NAME=0;
	private static final int BETWEEN_ATTRIBUTES=1;
	private static final int IN_NAME=2;
	private static final int AFTER_NAME=3; // this only happens if an attribute name is followed by whitespace
	private static final int START_VALUE=4;
	private static final int IN_VALUE=5;
	private static final int AFTER_VALUE_FINAL_QUOTE=6;

	private static int defaultMaxErrorCount=1; // defines maximum number of minor errors that can be encountered in attributes before entire start tag is rejected.

	private Attributes(Source source, int begin, int end, LinkedList attributeList) {
		super(source,begin,end);
		this.attributeList=attributeList;
	}

	/**
	 * called from Source.parseAttributes
	 */
	static Attributes construct(Source source, int begin, int maxEnd, int maxErrorCount) {
		return construct(source,"Attributes",BETWEEN_ATTRIBUTES,begin,-1,maxEnd,null,null,maxErrorCount);
	}

	/**
	 * called from StartTag.parseAttributes
	 */
	static Attributes construct(Source source, int startTagBegin, int attributesBegin, int maxEnd, String startTagName, int maxErrorCount) {
		return construct(source,"Attributes for StartTag",BETWEEN_ATTRIBUTES,startTagBegin,attributesBegin,maxEnd,null,startTagName,maxErrorCount);
	}

	/**
	 * called from StartTag.constructWithAttributes
	 */
	static Attributes construct(Source source, int startTagBegin, ByRefInt startTagEndDelimiterPos, String startTagName) {
		return construct(source,"StartTag",AFTER_TAG_NAME,startTagBegin,-1,-1,startTagEndDelimiterPos,startTagName,defaultMaxErrorCount);
	}

	/**
	 * Any < character found within the start tag is treated as though it is part of the attribute
	 * list, which is consistent with the way IE treats it.
	 * A processing instruction will be terminated by > as well as ?>, which is also consistent with IE.
	 * In some cases an invalid character will result in the entire start tag being rejected.
	 * This may seem ruthless, but we have to be able to distinguish whether any
	 * particular < found in the source is actually the start of a tag or not.
	 * Being too lenient with attributes means more chance of false positives, which in turn
	 * means surrounding tags may be ignored.
	 * @param source  the source document.
	 * @param logBegin  the position of the beginning of the object being searched (for logging)
	 * @param attributesBegin  the position of the beginning of the attribute list, or -1 if it should be calculated automatically from logBegin.
	 * @param maxEnd  the position at which the attributes must end if a terminating character is not found, or -1 if no maximum.
	 * @param startTagName  the name of the enclosing StartTag, or null if constucting attributes directly.
	 */
	private static Attributes construct(Source source, String logType, int state, int logBegin, int attributesBegin, int maxEnd, ByRefInt startTagEndDelimiterPos, String startTagName, int maxErrorCount) {
		char optionalTerminatingChar='/';
		if (startTagName!=null) {
			// 'logBegin' parameter is the start of the associated start tag
			if (attributesBegin==-1) attributesBegin=logBegin+1+startTagName.length();
			if (startTagName.charAt(0)=='?') optionalTerminatingChar='?'; // optionalTerminatingChar will normally be '/' but can also be '?' for xml processing instructions like <?xml version="1.0"?>
		} else {
			attributesBegin=logBegin;
		}
		int attributesEnd=attributesBegin;
		LinkedList attributeList=new LinkedList();
		String lsource=source.getParseTextLowerCase();
		int i=attributesBegin;
		char quote=' ';
		Segment nameSegment=null;
		String key=null;
		int currentBegin=-1;
		boolean isTerminatingCharacter=false;
		int errorCount=0;
		try {
			while (!isTerminatingCharacter) {
				char c=lsource.charAt(i);
				if (c=='>' || i==maxEnd || (c==optionalTerminatingChar && lsource.charAt(i+1)=='>')) isTerminatingCharacter=true;
				switch (state) {
					case IN_VALUE:
						if (isTerminatingCharacter || c==quote || (quote==' ' && isWhiteSpace(c))) {
							Segment valueSegment;
							Segment valueSegmentIncludingQuotes;
							if (quote==' ') {
								valueSegment=valueSegmentIncludingQuotes=new Segment(source,currentBegin,i);
							} else {
								if (isTerminatingCharacter) {
									if (i==maxEnd) {
										source.log(logType,startTagName,logBegin,"terminated in the middle of a quoted attribute value",i);
										if (reachedMaxErrorCount(++errorCount,source,logType,startTagName,logBegin,maxErrorCount)) return null;
										valueSegment=new Segment(source,currentBegin,i);
										valueSegmentIncludingQuotes=new Segment(source,currentBegin-1,i); // this is missing the end quote
									} else {
										// don't want to terminate, only encountered a terminating character in the middle of a quoted value
										isTerminatingCharacter=false;
										break;
									}
								} else {
									valueSegment=new Segment(source,currentBegin,i);
									valueSegmentIncludingQuotes=new Segment(source,currentBegin-1,i+1);
								}
							}
							attributeList.add(new Attribute(source, key, nameSegment, valueSegment, valueSegmentIncludingQuotes));
							attributesEnd=valueSegmentIncludingQuotes.getEnd();
							state=BETWEEN_ATTRIBUTES;
						} else if (c=='<' && quote==' ') {
							source.log(logType,startTagName,logBegin,"rejected because of '<' character in unquoted attribute value",i);
							return null;
						}
						break;
					case IN_NAME:
						if (isTerminatingCharacter || c=='=' || isWhiteSpace(c)) {
							nameSegment=new Segment(source,currentBegin,i);
							key=nameSegment.toString().toLowerCase();
							if (isTerminatingCharacter) {
								attributeList.add(new Attribute(source,key,nameSegment)); // attribute with no value
								attributesEnd=i;
							} else {
								state=(c=='=' ? START_VALUE : AFTER_NAME);
							}
						} else if (!isIdentifierPart(c)) {
							// invalid character detected in attribute name.
							// only reject whole start tag if it is a < character or if the error count is exceeded.
							if (c=='<') {
								source.log(logType,startTagName,logBegin,"rejected because of '<' character in attribute name",i);
								return null;
							}
							source.log(logType,startTagName,logBegin,"contains attribute name with invalid character",i);
							if (reachedMaxErrorCount(++errorCount,source,logType,startTagName,logBegin,maxErrorCount)) return null;
						}
						break;
					case AFTER_NAME:
						if (isTerminatingCharacter || !(c=='=' || isWhiteSpace(c))) {
							attributeList.add(new Attribute(source,key,nameSegment)); // attribute with no value
							attributesEnd=nameSegment.getEnd();
							if (isTerminatingCharacter) break;
							// The current character is the first character of an attribute name
							state=BETWEEN_ATTRIBUTES;
							i--; // want to reparse the same character again, so decrement i.  Note we could instead just fall into the next case statement without a break, but such code is always discouraged.
						} else if (c=='=') {
							state=START_VALUE;
						}
						break;
					case BETWEEN_ATTRIBUTES:
						if (!isTerminatingCharacter) {
							// the quote variable is used here to make sure whitespace has come after the last quoted attribute value
							if (isWhiteSpace(c)) {
								quote=' ';
							} else {
								if (quote!=' ') {
									source.log(logType,startTagName,logBegin,"has missing whitespace after quoted attribute value",i);
									// log this as an error but don't count it
								}
								if (!isIdentifierStart(c)) {
									// invalid character detected as first character of attribute name.
									// only reject whole start tag if it is a < character or if the error count is exceeded.
									if (c=='<') {
										source.log(logType,startTagName,logBegin,"rejected because of '<' character",i);
										return null;
									}
									source.log(logType,startTagName,logBegin,"contains attribute name with invalid first character",i);
									if (reachedMaxErrorCount(++errorCount,source,logType,startTagName,logBegin,maxErrorCount)) return null;
								}
								state=IN_NAME;
								currentBegin=i;
							}
						}
						break;
					case START_VALUE:
						currentBegin=i;
						if (isTerminatingCharacter) {
							source.log(logType,startTagName,logBegin,"has missing attribute value after '=' sign",i);
							// log this as an error but don't count it
							Segment valueSegment=new Segment(source,i,i);
							attributeList.add(new Attribute(source,key,nameSegment,valueSegment,valueSegment));
							attributesEnd=i;
							state=BETWEEN_ATTRIBUTES;
							break;
						}
						if (isWhiteSpace(c)) break; // just ignore whitespace after the '=' sign as nearly all browsers do.
						if (c=='<') {
							source.log(logType,startTagName,logBegin,"rejected because of '<' character at start of attribuite value",i);
							return null;
						} else if (c=='\'' || c=='"') {
							quote=c;
							currentBegin++;
						} else {
							quote=' ';
						}
						state=IN_VALUE;
						break;
					case AFTER_TAG_NAME:
						if (!isTerminatingCharacter) {
							if (!isWhiteSpace(c)) {
								source.log(logType,startTagName,logBegin,"rejected because name contains invalid character",i);
								return null;
							}
							state=BETWEEN_ATTRIBUTES;
						}
						break;
				}
				i++;
			}
			if (startTagEndDelimiterPos!=null) startTagEndDelimiterPos.value=i-1;
			return new Attributes(source,attributesBegin,attributesEnd,attributeList); // used to end at i-1
		} catch (IndexOutOfBoundsException ex) {
			source.log(logType,startTagName,logBegin,"rejected because it has no closing '>' character",-1);
			return null;
		}
	}

	private static boolean reachedMaxErrorCount(int errorCount, Source source, String logType, String startTagName, int logBegin, int maxErrorCount) {
		if (errorCount<=maxErrorCount) return false;
		source.log(logType,startTagName,logBegin,"rejected because it contains too many errors",-1);
		return true;
	}

	/**
	 * Returns the {@link Attribute} with the specified name (case insensitive).
	 * <p>
	 * If more than one attribute exists with the specified name (which is technically illegal HTML),
	 * the first is returned.
	 *
	 * @param name  the name of the attribute to get.
	 * @return the attribute with the specified name, or <code>null</code> if no attribute with the specified name exists.
	 * @see #getValue(String name)
	 */
	public Attribute get(String name) {
		if (size()==0) return null;
		for (int i=0; i<size(); i++) {
			Attribute attribute=(Attribute)get(i);
			if (attribute.getKey().equalsIgnoreCase(name)) return attribute;
		}
		return null;
	}

	/**
	 * Returns the {@linkplain CharacterReference#decode(CharSequence) decoded} value of the attribute with the specified name (case insensitive).
	 * <p>
	 * Returns <code>null</code> if no attribute with the specified name exists or no value has been assigned to
	 * the attribute.
	 * <p>
	 * This is equivalent to <code>get(name).getValue()</code>, although it will return <code>null</code>
	 * if no attribute with the specified name exists instead of throwing a
	 * <code>NullPointerException</code>.
	 * <p>
	 * Note that before version 1.5 this method returned the raw value of the attribute, without
	 * {@linkplain CharacterReference#decode(CharSequence) decoding}.
	 *
	 * @param name  the name of the attribute to get.
	 * @return the {@linkplain CharacterReference#decode(CharSequence) decoded} value of the attribute with the specified name, or <code>null</code> if the attribute has no value.
	 * @see #get(String name)
	 */
	public String getValue(String name) {
		Attribute attribute=get(name);
		return attribute==null ? null : attribute.getValue();
	}

	/**
	 * Returns the number of attributes.
	 * <p>
	 * This is equivalent to calling the <code>size()</code> method specified in the <code>List</code> interface.
	 *
	 * @return the number of attributes.
	 */
	public int getCount() {
		return attributeList.size();
	}

	/**
	 * Returns an iterator over the {@link Attribute} objects in this list in proper sequence.
	 * @return an iterator over the {@link Attribute} objects in this list in proper sequence.
	 */
	public Iterator iterator() {
		return listIterator();
	}

	/**
	 * Returns a list iterator of the {@link Attribute} objects in this list (in proper sequence),
	 * starting at the specified position in the list.
	 * <p>
	 * The specified index indicates the first item that would be returned by an initial call to the <code>next()</code> method.
	 * An initial call to the <code>previous()</code> method would return the item with the specified index minus one.
	 *
	 * @param index  the index of the first item to be returned from the list iterator (by a call to the <code>next()</code> method).
	 * @return a list iterator of the items in this list (in proper sequence), starting at the specified position in the list.
	 * @throws IndexOutOfBoundsException if the specified index is out of range (<code>index &lt; 0 || index &gt; size()</code>).
	 */
	public ListIterator listIterator(final int index) {
		return new ListIteratorImpl(index);
	}

	protected ListIterator internalListIterator(int index) {
		return attributeList.listIterator(index);
	}

	/**
	 * Populates the specified <code>Map</code> with the name/value pairs from these attributes.
	 * <p>
	 * Both names and values are stored as <code>String</code> objects.
	 * <p>
	 * The entries are added in order of apprearance in the source document.
	 * <p>
	 * An attribute with no value is represented by a map entry with a <code>null</code> value.
	 * <p>
	 * Attribute values are automatically {@linkplain CharacterReference#decode(CharSequence) decoded}
	 * before storage in the map.
	 *
	 * @param attributesMap  the map to populate, must not be <code>null</code>.
	 * @param convertNamesToLowerCase  specifies whether all attribute names are converted to lower case in the map.
	 * @return the same map specified as the <code>attributesMap</code> argument, populated with the name/value pairs from these attributes.
	 * @see #generateHTML(Map attributesMap)
	 */
	public Map populateMap(Map attributesMap, boolean convertNamesToLowerCase) {
		for (Iterator i=internalListIterator(0); i.hasNext();) {
			Attribute attribute=(Attribute)i.next();
			attributesMap.put(convertNamesToLowerCase ? attribute.getKey() : attribute.getName(),attribute.getValue());
		}
		return attributesMap;
	}

	public String getDebugInfo() {
		StringBuffer sb=new StringBuffer();
		sb.append("Attributes ").append(super.getDebugInfo()).append(": ");
		if (isEmpty()) {
			sb.append("EMPTY");
		} else {
			sb.append('\n');
			for (Iterator i=internalListIterator(0); i.hasNext();) {
				Attribute attribute=(Attribute)i.next();
				sb.append("  ").append(attribute.getDebugInfo());
			}
		}
		return sb.toString();
	}

	/**
	 * Returns the default maximum error count allowed when parsing attributes.
	 * <p>
	 * The system default value is 1.
	 *
	 * @return the default maximum error count allowed when parsing attributes.
	 * @see #setDefaultMaxErrorCount(int value)
	 * @see Source#parseAttributes(int pos, int maxEnd, int maxErrorCount)
	 */
	public static int getDefaultMaxErrorCount() {
		return defaultMaxErrorCount;
	}

	/**
	 * Sets the default maximum error count allowed when parsing attributes.
	 * <p>
	 * When searching for start tags, the parser can find the end of the start tag only by parsing
	 * the the attributes, as it is valid HTML for attribute values to contain '&gt;' characters
	 * (see section <a target="_blank" href="http://www.w3.org/TR/html401/charset.html#h-5.3.2">5.3.2</a> of the HTML spec).
	 * <p>
	 * If the source text being parsed does not follow the syntax of an attribute list at all, the parser assumes
	 * that the text which was originally identified as the beginning of of a start tag is in fact some other text,
	 * such as an invalid '&lt;' character in the middle of some text, or part of a script element.
	 * In this case the entire start tag is rejected.
	 * <p>
	 * On the other hand, it is quite common for attributes to contain minor syntactical errors,
	 * such as an invalid character in an attribute name, or a couple of special characters in
	 * {@linkplain StartTag#isServerTag() server tags} that otherwise contain only attributes.
	 * For this reason the parser allows a certain number of minor errors to occur while parsing an
	 * attribute list before the entire start tag or attribute list is rejected.
	 * This method sets the number of minor errors allowed.
	 * <p>
	 * Major syntactical errors will cause the start tag or attribute list to be rejected immediately, regardless
	 * of the maximum error count setting.
	 * <p>
	 * Some errors are considered too minor to count at all (ignorable), such as missing whitespace between the end
	 * of a quoted attribute value and the start of the next attribute name.
	 * <p>
	 * The classification of particular syntax errors in attribute lists into major, minor, and ignorable is
	 * not part of the specification and may change in future versions.
	 * <p>
	 * To track errors as they occur, use the {@link Source#setLogWriter(Writer writer)} method to set the
	 * destination of the error log.
	 *
	 * @param value  the default maximum error count allowed when parsing attributes.
	 * @see #getDefaultMaxErrorCount()
	 * @see Source#parseAttributes(int pos, int maxEnd, int maxErrorCount)
	 * @see Source#setLogWriter(Writer writer)
	 */
	public static void setDefaultMaxErrorCount(int value) {
		defaultMaxErrorCount=value;
	}

	/**
	 * Returns the raw (not {@linkplain CharacterReference#decode(CharSequence) decoded}) value of the attribute, or null if the attribute has no value.
	 * @return the raw (not {@linkplain CharacterReference#decode(CharSequence) decoded}) value of the attribute, or null if the attribute has no value.
	 */
	protected String getRawValue(String name) {
		Attribute attribute=get(name);
		return attribute==null || !attribute.hasValue() ? null : attribute.getValueSegment().toString();
	}

	/**
	 * Returns the contents of the specified {@linkplain #populateMap(Map,boolean) attributes map} as HTML attribute name/value pairs.
	 * <p>
	 * Each attribute (including the first) is preceded by a single space, and all values are
	 * {@linkplain CharacterReference#encode(CharSequence) encoded} and enclosed in double quotes.
	 * <p>
	 * The map keys must be of type <code>String</code> and values must be objects that implement the <code>CharSequence</code> interface.
	 * <p>
	 * A <code>null</code> value represents an attribute with no value.
	 *
	 * @param attributesMap  a map containing attribute name/value pairs.
	 * @return the contents of the specified {@linkplain #populateMap(Map,boolean) attributes map} as HTML attribute name/value pairs.
	 * @see StartTag#generateHTML(String tagName, Map attributesMap, boolean emptyElementTag)
	 */
	public static String generateHTML(Map attributesMap) {
		StringWriter stringWriter=new StringWriter();
		try {appendHTML(stringWriter,attributesMap);} catch (IOException ex) {} // IOException never occurs in StringWriter
		return stringWriter.toString();
	}

	/**
	 * Returns this instance.
	 * <p>
 	 * This method has been deprecated as of version 1.5 as the <code>Attributes</code> class now implements
 	 * the <code>List</code> interface, so the instance itself can be used instead.
	 *
	 * @return this instance.
	 * @deprecated  use this instance instead.
	 */
	public List getList() {
		return this;
	}

	/**
	 * Outputs the contents of the specified {@linkplain #populateMap(Map,boolean) attributes map} as HTML attribute name/value pairs to the specified <code>Writer</code>.
	 * <p>
	 * Each attribute is preceded by a single space, and all values are
	 * {@linkplain CharacterReference#encode(CharSequence) encoded} and enclosed in double quotes.
	 *
	 * @param out  the <code>Writer</code> to which the output is to be sent.
	 * @param attributesMap  a map containing attribute name/value pairs.
	 * @throws IOException  if an I/O exception occurs.
	 * @see #populateMap(Map attributesMap, boolean convertNamesToLowerCase)
	 */
	protected static void appendHTML(Writer writer, Map attributesMap) throws IOException {
		for (Iterator i=attributesMap.entrySet().iterator(); i.hasNext();) {
			Map.Entry entry=(Map.Entry)i.next();
			Attribute.appendHTML(writer,(String)entry.getKey(),(CharSequence)entry.getValue());
		}
	}

	protected StringBuffer appendRegeneratedHTML(StringBuffer sb) {
		for (Iterator i=internalListIterator(0); i.hasNext();)
			((Attribute)i.next()).appendRegeneratedHTML(sb);
		return sb;
	}

	protected Map getMap(boolean convertNamesToLowerCase) {
		return populateMap(new LinkedHashMap(getCount()*2,1.0F),convertNamesToLowerCase);
	}

	private class ListIteratorImpl implements ListIterator {
		private ListIterator listIterator;
		public ListIteratorImpl(int index) {
			listIterator=attributeList.listIterator();
		}
		public boolean hasNext() {
			return listIterator.hasNext();
		}
		public Object next() {
			return listIterator.next();
		}
		public boolean hasPrevious() {
			return listIterator.hasPrevious();
		}
		public Object previous() {
			return listIterator.previous();
		}
		public int nextIndex() {
			return listIterator.nextIndex();
		}
		public int previousIndex() {
			return listIterator.previousIndex();
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
		public void set(Object o) {
			throw new UnsupportedOperationException();
		}
		public void add(Object o) {
			throw new UnsupportedOperationException();
		}
	}
}
