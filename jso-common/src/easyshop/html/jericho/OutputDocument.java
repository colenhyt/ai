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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Represents a modified version of an original source text.
 * <p>
 * An OutputDocument represents an original source text that
 * has been modified by substituting segments of it with other text.
 * Each of these substitutions is registered by adding an {@link IOutputSegment} to the OutputDocument.
 * After all of the substitutions have been added, the modified text can be retrieved using the
 * {@link #output(Writer)} or {@link #toString()} methods.
 * <p>
 * The registered OutputSegments must not overlap each other, but may be adjacent.
 * <p>
 * The following example converts all externally referenced style sheets to internal style sheets:
 * <pre>
 *  OutputDocument outputDocument=new OutputDocument(htmlText);
 *  Source source=new Source(htmlText);
 *  StringBuffer sb=new StringBuffer();
 *  List linkStartTags=source.findAllStartTags(Tag.LINK);
 *  for (Iterator i=linkStartTags.iterator(); i.hasNext();) {
 *    StartTag startTag=(StartTag)i.next();
 *    Attributes attributes=startTag.getAttributes();
 *    String rel=attributes.getValue("rel");
 *    if (!"stylesheet".equalsIgnoreCase(rel)) continue;
 *    String href=attributes.getValue("href");
 *    if (href==null) continue;
 *    String styleSheetContent;
 *    try {
 *      styleSheetContent=CommonTools.getString(new URL(href).openStream()); // note CommonTools.getString method is not defined here
 *    } catch (Exception ex) {
 *      continue; // don't convert if URL is invalid
 *    }
 *    sb.setLength(0);
 *    sb.append("&lt;style");
 *    Attribute typeAttribute=attributes.get("type");
 *    if (typeAttribute!=null) sb.append(' ').append(typeAttribute);
 *    sb.append(">\n").append(styleSheetContent).append("\n&lt;/style>");
 *    outputDocument.add(new StringOutputSegment(startTag,sb.toString()));
 *  }
 *  String convertedHtmlText=outputDocument.toString();
 * </pre>
 *
 * @see IOutputSegment
 * @see StringOutputSegment
 */
public final class OutputDocument {
	private CharSequence sourceText;
	private ArrayList outputSegments=new ArrayList();

	/**
	 * Constructs a new <code>OutputDocument</code> based on the specified source text.
	 * <p>
	 * Note that a {@link Source} object can be passed directly as an argument to this constructor
	 * as it implements the <code>CharSequence</code> interface.
	 *
	 * @param sourceText  the source text.
	 */
	public OutputDocument(CharSequence sourceText) {
	  if (sourceText==null) throw new IllegalArgumentException();
		this.sourceText=sourceText;
	}

	/**
	 * Returns the original source text upon which this <code>OutputDocument</code> is based.
	 * @return the original source text upon which this <code>OutputDocument</code> is based.
	 */
	public CharSequence getSourceText() {
		return sourceText;
	}

	/**
	 * Adds the specified {@linkplain IOutputSegment output segment} to this <code>OutputDocument</code>.
	 * <p>
	 * Note that for efficiency reasons no exception is thrown if the added output segment overlaps another,
	 * however in this case an {@link OverlappingOutputSegmentsException} will be thrown when the output is generated.
	 */
	public void add(IOutputSegment outputSegment) {
		outputSegments.add(outputSegment);
	}

	/**
	 * ***************************
	 */
	public void add(FormControl formControl) {
		formControl.addToOutputDocument(this);
	}

	/**
	 * ***************************
	 */
	public void add(FormFields formFields) {
		formFields.addToOutputDocument(this);
	}

	/**
	 * Outputs the final content of this <code>OutputDocument</code> to the specified <code>Writer</code>.
	 * <p>
	 * An {@link OverlappingOutputSegmentsException} is thrown if any of the output segments overlap.
	 * For efficiency reasons this condition is not caught when the offending output segment is added.
	 *
	 * @throws IOException  if an I/O exception occurs.
	 * @throws OverlappingOutputSegmentsException  if any of the output segments overlap.
	 */
	public void output(Writer writer) throws IOException {
		if (outputSegments.isEmpty()) {
			Util.appendTo(writer,sourceText);
			return;
		}
		int pos=0;
		Collections.sort(outputSegments,IOutputSegment.COMPARATOR);
		IOutputSegment lastOutputSegment=null;
		for (Iterator i=outputSegments.iterator(); i.hasNext();) {
			IOutputSegment outputSegment=(IOutputSegment)i.next();
			if (outputSegment==lastOutputSegment) continue; // silently ignore duplicate output segment
			if (outputSegment.getBegin()<pos) throw new OverlappingOutputSegmentsException(lastOutputSegment,outputSegment);
			if (outputSegment.getBegin()>pos) Util.appendTo(writer,sourceText,pos,outputSegment.getBegin());
			outputSegment.output(writer);
			lastOutputSegment=outputSegment;
			pos=outputSegment.getEnd();
		}
		if (pos<sourceText.length()) Util.appendTo(writer,sourceText,pos,sourceText.length());
		writer.close();
	}

	/**
	 * Returns the final content of this <code>OutputDocument</code> as a <code>String</code>.
	 * @return the final content of this <code>OutputDocument</code> as a <code>String</code>.
	 * @throws OverlappingOutputSegmentsException  if any of the output segments overlap.
	 */
	public String toString() {
		StringWriter writer=new StringWriter((int)(sourceText.length()*1.5));
		try {
			output(writer);
		} catch (IOException ex) {throw new RuntimeException(ex);} // should never happen with StringWriter
		return writer.toString();
	}
}
